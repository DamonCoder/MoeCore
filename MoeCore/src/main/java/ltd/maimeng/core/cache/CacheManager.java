package ltd.maimeng.core.cache;

import android.content.Context;
import android.os.Environment;

import ltd.maimeng.core.log.TeaLog;
import ltd.maimeng.core.utils.TextUtil;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import androidx.annotation.NonNull;

public class CacheManager {

    private static CacheManager cacheManager = null;
    private String FEATURE_NO_MEDIA = "no_media";

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (cacheManager == null) {
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }

    /**
     * 检查缓存文件是否存在及特性
     */
    public void checkCacheDir(@NonNull CacheDir cacheDir) throws IOException {
        String filename = cacheDir.getRelativePath();
        String[] eachDir = filename.split("/");
        String suffix = "";
        for (int i = 0; i < eachDir.length; i++) {
            suffix = suffix + "/" + eachDir[i];
            File file = new File(Environment.getExternalStorageDirectory(), suffix);
            if (file.exists() && !file.isDirectory()) {
                boolean deleteSuccess = file.delete();
                TeaLog.i("Coder", "删除" + (deleteSuccess ? "成功" : "失败"));
            } else {
                TeaLog.i("Coder", "不删除");
            }
        }

        File file = new File(Environment.getExternalStorageDirectory(), filename);

        if (!file.exists()) {
            file.mkdirs();
        } else {
            if (!file.isDirectory()) {
                file.delete();
                file.mkdirs();
            } else {
                // nothing to do
            }
        }

        // 设置缓存文件夹绝对路径
        cacheDir.setAbsolutePath(file.getAbsolutePath());

        // 检查缓存文件夹特性
        if (FEATURE_NO_MEDIA.equals(cacheDir.getFeature())) {
            // 媒体文件夹
            File nomedia = new File(file, ".nomedia");
            if (!nomedia.exists()) {
                nomedia.createNewFile();
            }
        }
        ltd.maimeng.core.cache.CacheStorage.getInstance().addCacheDir(cacheDir);
    }


    /**
     * 解析缓存配置文件，框架级别调用，开发者不要调用
     */
    public void loadAllCacheDirs(Context context) throws IOException, ParserConfigurationException, SAXException {
        CacheLoader.loadAllCacheDirs(context);
    }

    public void print() {
        TeaLog.i(ltd.maimeng.core.cache.CacheStorage.getInstance().toString());
    }

    /**
     * 根据缓存名获取缓存路径
     */
    public String getCacheAbsPath(String cacheName) {
        return ltd.maimeng.core.cache.CacheStorage.getInstance().getCacheAbsPath(cacheName);
    }

    /**
     * 根据缓存名称获取缓存文件夹
     */
    public File getCacheDir(String cacheName) {
        String path = getCacheAbsPath(cacheName);
        if (TextUtil.isEmpty(path)) {
            return null;
        }
        return new File(path);
    }
}
