package ltd.maimeng.core.cache;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class CacheStorage {

    private static CacheStorage self = null;
    public ArrayList<CacheDir> cacheDirs;

    private CacheStorage() {
        cacheDirs = new ArrayList<>();
    }

    public static CacheStorage getInstance() {
        if (self == null) {
            self = new CacheStorage();
        }
        return self;
    }

    public void addCacheDir(@NonNull CacheDir cacheDir) {
        cacheDirs.add(cacheDir);
    }

    public void clear() {
        if (cacheDirs != null) {
            cacheDirs.clear();
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("--------CacheDir---------").append("\n");
        for (CacheDir cacheDir : cacheDirs) {
            builder.append(cacheDir.toString()).append("\n");
        }
        return builder.toString();
    }

    /**
     * 根据缓存文件名获取缓存路径
     */
    public String getCacheAbsPath(String cacheName) {
        String path = null;
        for (int x = 0; x < cacheDirs.size(); x++) {
            CacheDir cacheDir = cacheDirs.get(x);
            String name = cacheDir.getName();
            if (cacheName.equals(name)) {
                path = cacheDir.getAbsolutePath();
            }
        }
        return path;
    }
}
