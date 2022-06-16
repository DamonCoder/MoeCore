package ltd.maimeng.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;

import androidx.annotation.NonNull;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/08/04
 *     desc   :
 * </pre>
 */
public class FileUtil {

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(@NonNull File file) {
        return file != null && file.exists();
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(@NonNull String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(@NonNull String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(@NonNull String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取文件格式名
     */
    public static String getFormatName(String filePath) {
        filePath = filePath.trim();
        String s[] = filePath.split("\\.");
        if (s.length >= 2) {
            return s[s.length - 1];
        }
        return "";
    }

    /**
     * 根据文件本地路径后去文件名
     */
    public static String getFileName(String filePath) {
        if (!TextUtil.isEmpty(filePath) && filePath.contains("/")) {
            String[] filePathArray = filePath.split("/");
            return filePathArray[filePathArray.length - 1];
        } else {
            return filePath;
        }
    }

    /**
     * 获取本地文件大小
     */
    public static long getFileSize(File file) {
        long fileSize = 0;
        if (file.exists()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                fileSize = fis.available();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileSize;
    }

    /**
     * 获取本地文件大小
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        long fileSize = 0;
        try {
            fileSize = getFileSize(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileSize;
    }

    /**
     * 转换文件大小
     */
    public static String formetFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(File file) {
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
}
