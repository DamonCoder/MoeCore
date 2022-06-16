package ltd.maimeng.core.network;

import com.lzy.okgo.request.BaseRequest;

import java.io.File;

public interface MoeRequestBehavior {

    public abstract void buildParams(BaseRequest request, MoeRequest moeRequest);

    public abstract void beforeSend();

    public abstract void onResponse(String response);

    public abstract void afterResponse();

    public abstract void onDownloaded(File file);

    public abstract void onError(int code, String msg);

    public abstract void onProgress(long currentSize, long totalSize, float progress, long networkSpeed);

}
