package ltd.maimeng.core.security;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


public class DesUtil {

    /**
     * 加密
     *
     * @param datasource byte[]
     * @param password   String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src      byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        return cipher.doFinal(src);
    }

    public static String encrypt(String str, String password) {
        byte[] byteMi = null;
        byte[] byteMing = null;
        String strMi = "";
        try {
            byteMing = str.getBytes("utf-8");
            byteMi = encrypt(byteMing, password);
            // BASE64Encoder base64Encoder = new BASE64Encoder();
            // strMi = base64Encoder.encode(byteMi);
            // strMi = new String(java.util.Base64.getEncoder().encode(byteMi));
            strMi = Base64.encodeToString(byteMi, Base64.NO_WRAP);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return strMi;
    }

    public static String decrypt(String str, String password) {
        byte[] byteMing = null;
        String strMing = "";
        try {
            // BASE64Decoder decoder = new BASE64Decoder();
            // byteMing = decoder.decodeBuffer(str);
            // byteMing = java.util.Base64.getDecoder().decode(str);
            byteMing = Base64.decode(str,Base64.NO_WRAP);
            byteMing = decrypt(byteMing, password);
            strMing = new String(byteMing);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return strMing;
    }
}
