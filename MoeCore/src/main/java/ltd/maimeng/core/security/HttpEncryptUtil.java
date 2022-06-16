package ltd.maimeng.core.security;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import java.util.Map;

/**
 * @author wqz
 * @date 2020-04-19
 */
public class HttpEncryptUtil {

    /**
     * 解密响应体
     *
     * @param data
     * @return
     */
    public static String decryptResponse(String data) {
        try {
            // HttpData httpData = JSONObject.parseObject(data, HttpData.class);
            // String key = RsaUtil.decryptResponse(httpData.getKey());
            // String decrypt = DesUtil.decrypt(httpData.getData(), key);
            // return prettyJson(decrypt);
            JSONObject jsonObject = new JSONObject(data);
            String key = RsaUtil.decryptResponse(jsonObject.getString("key"));
            String decrypt = DesUtil.decrypt(jsonObject.getString("data"), key);
            return decrypt;
        } catch (Exception e) {
            throw new SecurityException("解密失败:" + e.getMessage());
        }
    }

    /**
     * 加密请求体
     *
     * @param obj
     * @return
     */
    public static String encryptRequest(Object obj) {
        String random = RandomStringUtils.randomAlphanumeric(8);
        try {
            String key = ltd.maimeng.core.security.RsaUtil.encryptRequest(random);
            // String decrypt = DesUtil.encrypt(JSON.toJSONString(obj), random);
            String decrypt = DesUtil.encrypt(obj.toString(), random);
            // HttpData httpdata = new HttpData(key, decrypt);
            // return JSON.toJSONString(httpdata);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", key);
            jsonObject.put("data", decrypt);
            return jsonObject.toString();
        } catch (Exception e) {
            throw new SecurityException("加密失败:" + e.getMessage());
        }
    }

//    private static String prettyJson(String input) {
//        try {
//            JSONObject obj = JSON.parseObject(input);
//            return JSON.toJSONString(obj, true);
//        } catch (Exception e) {
//            return input;
//        }
//    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = ltd.maimeng.core.security.RsaUtil.genKeyPair();
        String privateKey = ltd.maimeng.core.security.RsaUtil.getPrivateKey(map);
        String publicKey = ltd.maimeng.core.security.RsaUtil.getPublicKey(map);
        System.out.println(privateKey);
        System.out.println(publicKey);
    }
}
