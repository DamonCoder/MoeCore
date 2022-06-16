package ltd.maimeng.core.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;

public class JsonUtil {

    /**
     * 判断一个字符串是否为JsonObject格式
     *
     * @param src 字符串
     * @return
     */
    public static boolean isJsonObject(@NonNull String src) {
        boolean re = true;
        try {
            new JSONObject(src);
        } catch (JSONException e) {
            re = false;
        } finally {
            return re;
        }
    }

    /**
     * 判断父Json对象中某一个子对象是否为JsonArray
     *
     * @param obj  父Json对象
     * @param name 子对象的名称
     * @return true是，false不是
     */
    public static boolean isJsonArray(@NonNull JSONObject obj, @NonNull String name) {
        boolean re = true;
        try {
            obj.getJSONArray(name);
        } catch (JSONException e) {
            re = false;
        } finally {
            return re;
        }
    }

    /**
     * 判断对应Json字符串是否是JsonArray格式
     *
     * @return
     */
    public static boolean isJsonArray(@NonNull String src) {
        boolean re = true;
        try {
            new JSONArray(src);
        } catch (JSONException e) {
            re = false;
        } finally {
            return re;
        }
    }

    /**
     * 判断Json字符串中是否包含此key
     *
     * @param key
     * @param jsonObject
     * @return
     */
    private static boolean isKeyValid(@NonNull String key, @NonNull JSONObject jsonObject) {
        if (jsonObject == null) {
            return false;
        }
        if (!jsonObject.has(key)) {
            return false;
        }
        return true;
    }


    /**
     * 根据key值获取JSONObject中对应JsonArray对象
     *
     * @param obj
     * @param key
     * @return
     */
    public static JSONArray getJsonArray(@NonNull JSONObject obj, @NonNull String key) {
        if (obj == null) {
            return new JSONArray();
        }
        if (!obj.has(key)) {
            return new JSONArray();
        }
        if (isJsonArray(obj.optString(key))) {
            return obj.optJSONArray(key);
        }
        return new JSONArray();
    }

    /**
     * 根据key值从JSONObject里取String值
     *
     * @param key
     * @param jsonObject
     * @return
     */
    public static String getString(@NonNull String key, @NonNull JSONObject jsonObject) {
        return getString(key, jsonObject, "");
    }

    /**
     * 根据key值从JSONObject里取String值
     *
     * @param key
     * @param jsonObject
     * @param defaultValue 指定String默认值
     * @return
     */
    public static String getString(@NonNull String key, @NonNull JSONObject jsonObject, @NonNull String defaultValue) {
        if (!isKeyValid(key, jsonObject)) {
            return defaultValue;
        }
        return jsonObject.optString(key, defaultValue);
    }

    /**
     * 根据key值从JSONObject里取int值
     *
     * @param key
     * @param jsonObject
     * @return
     */
    public static int getInt(@NonNull String key, @NonNull JSONObject jsonObject) {
        return getInt(key, jsonObject, 0);
    }

    /**
     * 根据key值从JSONObject里取int值
     *
     * @param key
     * @param jsonObject
     * @param defaultValue 指定int默认值
     * @return
     */
    public static int getInt(@NonNull String key, @NonNull JSONObject jsonObject, int defaultValue) {
        if (!isKeyValid(key, jsonObject)) {
            return defaultValue;
        }
        return jsonObject.optInt(key, defaultValue);
    }

    /**
     * 根据key值从JSONObject里取long值
     *
     * @param key
     * @param jsonObject
     * @return
     */
    public static long getLong(@NonNull String key, @NonNull JSONObject jsonObject) {
        return getLong(key, jsonObject, 0);
    }

    /**
     * 根据key值从JSONObject里取long值
     *
     * @param key
     * @param jsonObject
     * @param defaultValue 指定long默认值
     * @return
     */
    public static long getLong(@NonNull String key, @NonNull JSONObject jsonObject, long defaultValue) {
        if (!isKeyValid(key, jsonObject)) {
            return defaultValue;
        }
        return jsonObject.optLong(key, defaultValue);
    }

    /**
     * 根据key值从JSONObject里取boolean值
     *
     * @param key
     * @param jsonObject
     * @return
     */
    public static boolean getBoolean(@NonNull String key, @NonNull JSONObject jsonObject) {
        return getBoolean(key, jsonObject, false);
    }

    /**
     * 根据key值从JSONObject里取boolean值
     *
     * @param key
     * @param jsonObject
     * @param defaultValue 指定boolean默认值
     * @return
     */
    public static boolean getBoolean(@NonNull String key, @NonNull JSONObject jsonObject, boolean defaultValue) {
        if (!isKeyValid(key, jsonObject)) {
            return defaultValue;
        }
        return jsonObject.optBoolean(key, defaultValue);
    }
}
