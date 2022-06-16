package ltd.maimeng.core.network;

import com.lzy.okgo.request.BaseRequest;

/**
 * Http/Https请求的行为
 */
public interface HttpBehavior {

    void sendGet(MoeRequest moeRequest);

    void sendPost(MoeRequest moeRequest);

    void download(MoeRequest moeRequest);

    void upload(MoeRequest moeRequest);

    void cancel(MoeRequest moeRequest);

    void cancel(String tag);

    void cancelAll();

    void buildParams(BaseRequest request, MoeRequest moeRequest);

}
