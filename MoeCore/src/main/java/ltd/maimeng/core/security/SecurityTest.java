package ltd.maimeng.core.security;

import org.json.JSONException;
import org.json.JSONObject;

public class SecurityTest {

    static String encryptedRespBody = "{\"data\":\"k84fTV6cgjjfQ0dspx6A6A==\",\"key\":\"X7NHz1VOjVJun2Aln0++X5YASFCkV/9P7TjDQybD0OcOATZqg3b5fXZkNNzPWuY8DWXsPN/VaBI3\\nQ/V0iB/BQsSc9k3mjh6YlsVV6zAbKbX61f8CgRxs1l1BOJokjD54p/2/oPL3F2rSvNwE0J5ESXsU\\nBI8gmf6r+aHmk+biAbM=\"}";

    public static void test() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("key1", "value1");
            jsonObject.put("key2", "value2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String encryptedReqBody = HttpEncryptUtil.encryptRequest(jsonObject.toString());

        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("data", "k84fTV6cgjjfQ0dspx6A6A==");
            jsonObject1.put("key", "X7NHz1VOjVJun2Aln0++X5YASFCkV/9P7TjDQybD0OcOATZqg3b5fXZkNNzPWuY8DWXsPN/VaBI3\nQ/V0iB/BQsSc9k3mjh6YlsVV6zAbKbX61f8CgRxs1l1BOJokjD54p/2/oPL3F2rSvNwE0J5ESXsU\nBI8gmf6r+aHmk+biAbM=");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String decryptedResponse = HttpEncryptUtil.decryptResponse(jsonObject1.toString());
    }
}
