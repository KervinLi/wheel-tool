package com.tool.utils;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Description:本地文件压缩工具 提供文件压缩下载
 * @since 2021/10/10
 */
@Slf4j
public class ZipUtils {

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 压缩成ZIP
     *
     * @param srcDir               压缩文件夹路径
     * @param specifiedFolderNames 压缩指定的文件夹名
     * @param specifiedFileNames   压缩指定的文件名
     * @param fileName             压缩包文件名
     * @param response             响应对象
     * @param keepDirStructure     是否保留原来的目录结构,true:保留目录结构;
     *                             false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcDir, List<String> specifiedFolderNames, List<String> specifiedFileNames, String fileName, HttpServletResponse response, boolean keepDirStructure) throws RuntimeException {
        String methodName = "ZipUtils#toZip(srcDir, specifiedFolderNames, response, keepDirStructure)";
        long startTime = System.currentTimeMillis();
        // 响应头的设置
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName + ".zip");
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(response.getOutputStream());
            File sourceFile = new File(srcDir);
            compress(sourceFile, specifiedFolderNames, specifiedFileNames, zos, sourceFile.getName(), keepDirStructure);
            log.info("{} 压缩完成，耗时{}ms", methodName, System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("{} 压缩失败", methodName, e);
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归压缩方法
     *
     * @param sourceFile           源文件
     * @param specifiedFolderNames 压缩指定的文件夹名
     * @param specifiedFileNames   压缩指定的文件名
     * @param zos                  zip输出流
     * @param name                 压缩后的名称
     * @param keepDirStructure     是否保留原来的目录结构,true:保留目录结构;
     *                             false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception 压缩失败会抛出运行时异常
     */
    public static void compress(File sourceFile, List<String> specifiedFolderNames, List<String> specifiedFileNames, ZipOutputStream zos, String name, boolean keepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (keepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + File.separator));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断当前文件名是否不匹配指定的文件/文件夹名, 不匹配则进行下一次循环
                    if (judgeIsIgnoredFile(specifiedFolderNames, specifiedFileNames, file)){
                        continue;
                    }
                    // 判断是否需要保留原来的文件结构
                    if (keepDirStructure) {
                        // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                        // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                        compress(file, specifiedFolderNames, specifiedFileNames, zos, name + File.separator + file.getName(), true);
                    } else {
                        compress(file, specifiedFolderNames, specifiedFileNames, zos, file.getName(), false);
                    }
                }
            }
        }
    }

    /**
     * 判断是否为忽略的文件或文件夹
     *
     * @param specifiedFolderNames 压缩指定的文件夹名
     * @param specifiedFileNames   压缩指定的文件名
     * @param file                 当前文件对象
     * @return true 是, false 否
     */
    private static boolean judgeIsIgnoredFile(List<String> specifiedFolderNames, List<String> specifiedFileNames, File file) {
        boolean flag = false;
        String name = file.getName();
        if (file.isDirectory() && specifiedFolderNames != null && specifiedFolderNames.size() > 0
                && specifiedFolderNames.stream().noneMatch(name::contains)) {
            flag = true;
        }
        if (file.isFile() && specifiedFileNames != null && specifiedFileNames.size() > 0
                && specifiedFileNames.stream().noneMatch(name::contains)) {
            flag = true;
        }
        return flag;
    }
}