package ltd.maimeng.core.network;

import com.lzy.okgo.OkGo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;


/**
 * 网络请求快速构建器
 */
public class MoeHttp {

    /**
     * 根据tag取消网络请求
     *
     * @param tag
     */
    public static void cancelRequest(String tag) {
        // HttpExecutor.getInstance().cancel(tag);
        OkGo.getInstance().cancelTag(tag);
    }

    /**
     * 取消某个Request的网络请求
     *
     * @param moeRequest
     */
    public static void cancelRequest(MoeRequest moeRequest) {
        // HttpExecutor.getInstance().cancel(moeRequest);
        OkGo.getInstance().cancelTag(moeRequest.getTag());
    }

    /**
     * 取消所有网络请求
     */
    public static void cancelAll() {
        // HttpExecutor.getInstance().cancelAll();
        OkGo.getInstance().cancelAll();
    }

    /**
     * 构建Get请求
     *
     * @param tag
     * @return
     */
    public static MoeRequest get(@NonNull String tag) {
        MoeRequest request = new MoeRequest(tag).setRequestType(MoeRequest.TYPE_GET);
        return request;
    }

    /**
     * 构建Post请求
     *
     * @param tag
     * @return
     */
    public static MoeRequest post(@NonNull String tag) {
        MoeRequest request = new MoeRequest(tag).setRequestType(MoeRequest.TYPE_POST);
        return request;
    }

    /**
     * 文件上传，适用于所有文件共用一个key参数
     *
     * @param tag
     * @param key
     * @param files
     * @return
     */
    public static MoeRequest upload(@NonNull String tag, @NonNull String key, File... files) {
        MoeRequest request = new MoeRequest(tag).setRequestType(MoeRequest.TYPE_UPLOAD);
        if (files != null && files.length > 0) {
            for (File file : files) {
                request.addFileParam(key, file);
            }
        }
        return request;
    }

    /**
     * 文件上传，适用于自己添加上传文件
     *
     * @param tag
     * @return
     */
    public static MoeRequest upload(@NonNull String tag) {
        MoeRequest request = new MoeRequest(tag).setRequestType(MoeRequest.TYPE_UPLOAD);
        return request;
    }

    /**
     * 文件上传，适用于所有文件共用一个key参数
     *
     * @param tag
     * @param key
     * @param files
     * @return
     */
    public static MoeRequest upload(@NonNull String tag, @NonNull String key, ArrayList<File> files) {
        MoeRequest request = new MoeRequest(tag).setRequestType(MoeRequest.TYPE_UPLOAD);
        if (files != null && files.size() > 0) {
            for (File file : files) {
                request.addFileParam(key, file);
            }
        }
        return request;
    }

    /**
     * 文件上传，适用于每个/多个文件对应不同的key参数
     *
     * @param tag
     * @param files
     * @return
     */
    public static MoeRequest upload(@NonNull String tag, HashMap<String, ArrayList<File>> files) {
        MoeRequest request = new MoeRequest(tag).setRequestType(MoeRequest.TYPE_UPLOAD);
        if (files != null && files.size() > 0) {
            request.getFileMap().putAll(files);
        }
        return request;
    }

    /**
     * 文件下载
     *
     * @param tag
     * @param destFileDir  目标文件夹
     * @param destFileName 文件名
     * @return
     */
    public static MoeRequest download(@NonNull String tag, String destFileDir, String destFileName) {
        MoeRequest request = new MoeRequest(tag)
                .setRequestType(MoeRequest.TYPE_DOWNLOAD)
                .setDownloadFileDir(destFileDir)
                .setDownloadFileName(destFileName);
        return request;
    }

    /**
     * 文件下载
     *
     * @param tag
     * @param destFileDir 目标文件夹
     * @return
     */
    public static MoeRequest download(@NonNull String tag, String destFileDir) {
        MoeRequest request = new MoeRequest(tag)
                .setRequestType(MoeRequest.TYPE_DOWNLOAD)
                .setDownloadFileDir(destFileDir);
        return request;
    }
}
