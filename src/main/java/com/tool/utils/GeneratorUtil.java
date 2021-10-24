package com.tool.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @Description: 业务流水号生成器
 * @Author KerVinLi
 * @since 2021/10/24
 */
public class GeneratorUtil {
    private static final int SIZE=10000;//业务流水号的缓存
    private static String[] INIT_ARRAY=new String[SIZE];//业务流水号的产生缓冲
    private static int SELECT_INDEX=0;//业务流水号当前的下标

    /**
     * 生成以W结尾的11位业务流水号
     * @return
     */
    public synchronized static String produceBusinessNumber() {
        String[] seed = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        if(SELECT_INDEX>=SIZE-1){
            INIT_ARRAY=new String[SIZE];
            SELECT_INDEX=0;
        }
        if(SELECT_INDEX==0){//初始化业务流水号缓冲第一次请求
            int count=0;
            StringBuilder sd = new StringBuilder();
            while(count<SIZE){//随机进行业务流水号缓存
                for (int i = 0; i < 4; i++) {//生成四位随机序列
                    int index = (int) Math.round((Math.random()*1000) % 35);
                    sd.append(seed[index]);
                }
                boolean hasIn=false;
                String tempString=sd.toString();
                for(int i=0;i<SIZE;i++){
                    if(INIT_ARRAY[i]!=null){
                        if(INIT_ARRAY[i].equals(tempString)){
                            hasIn=true;
                            break;
                        }
                    }
                }
                if(!hasIn){
                    for(int i=0;i<SIZE;i++){
                        if(INIT_ARRAY[i]==null){
                            INIT_ARRAY[i]=tempString;
                            count++;
                            break;
                        }
                    }
                }
                sd.delete(0, sd.length());
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        StringBuffer restSd = new StringBuffer();
        restSd.append(sdf.format(new Timestamp(System.currentTimeMillis())));
        restSd.append(getSelect());
        restSd.append("W");
        return restSd.toString();
    }

    private synchronized static String getSelect() {
        String result=INIT_ARRAY[SELECT_INDEX];
        SELECT_INDEX++;
        return result;
    }
}
