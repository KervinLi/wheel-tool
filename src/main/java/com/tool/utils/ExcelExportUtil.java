package com.tool.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: Excel文件保存下载工具类
 * @since 2021/10/12
 */
@Slf4j
public class ExcelExportUtil {
    /**
     * 保存Excel文件
     *
     * @throws IOException
     */
    public static void saveExcel(Map<String, Object> result, Workbook workbook
            , String path, String fileName) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                log.error("创建目录报错！", e);
            }
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path + File.separator + fileName);
            workbook.write(fos);
        } catch (Exception e) {
            log.error("excel生成报错！", e);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
        result.put("status", true);
        result.put("fileName", fileName);
    }

    /**
     * 下载临时文件  根据路径、文件名下载文件，下载后删除文件
     *
     * @param path           路径，结尾不包含"/"
     * @param serverFileName 服务器文件名
     * @param downloadName   下载文件时的文件名称
     * @param response
     */
    public static void downloadTempExcel(String path, String serverFileName, String downloadName, HttpServletResponse response) {
        try {
            String serverFileNameAfter = fileNameFilter(serverFileName);
            File file = new File(path + File.separator + serverFileNameAfter);
            if (file.exists()) {
                //清空response
                response.reset();
                //设置header
                response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileNameFilter(downloadName)));
                response.setHeader("Content-Length", String.valueOf(file.length()));
                response.setContentType("application/octet-stream");
                try (FileInputStream is = new FileInputStream(file);
                     FileChannel fileChannel = is.getChannel()
                ) {
                    Long size = Long.valueOf(is.available());

                    ServletOutputStream outputStream = response.getOutputStream();
                    WritableByteChannel writableByteChannel = Channels.newChannel(outputStream);

                    //开始位置，总数，写出的地方
                    fileChannel.transferTo(0, size, writableByteChannel);

                } catch (Exception e) {
                    log.error("下载文件失败！文件：", path + File.separator + serverFileName + e.getMessage());
                }
                //删除文件
                boolean deleteFlag = file.delete();
                if (!deleteFlag) {
                    log.warn("删除文件失败！文件：" + path + File.separator + serverFileName);
                }
            } else {
                log.warn("下载文件失败！文件不存在！文件：" + path + File.separator + serverFileName);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e.getMessage());
        }
    }

    /**
     * 文件名在操作系统中不允许出现  / \ " : | * ? < > 以及一个以上.
     */
    private static String fileNameFilter(String source) {
        Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
        Matcher matcher = pattern.matcher(source);
        source = matcher.replaceAll(""); // 将匹配到的非法字符以空替换

        source = source.replaceAll("([.])\\1+", "");
        return source;
    }

}
