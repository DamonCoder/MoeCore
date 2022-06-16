package ltd.maimeng.core.network;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.CookieStore;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;

/**
 * 网络管理类
 * 初始化一些网络的参数，比如超时时间，Cookie等
 */
public class NetManager {

    private static NetManager self = null;

    private NetManager() {
    }

    public static NetManager getInstance() {
        if (self == null) {
            self = new NetManager();
        }
        return self;
    }

    public void init(Application application) {
        OkGo.init(application);
    }

    /**
     * 信任所有Https证书
     */
    public void setTrustAllHost(boolean trust) {
        if (!trust) {
            return;
        }
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{HttpsUtils.UnSafeTrustManager}, new SecureRandom());
            OkHttpClient.Builder builder = OkGo.getInstance().getOkHttpClientBuilder();
            builder.sslSocketFactory(sc.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    /**
     * 全局cookie存取规则
     */
    public NetManager setCookieStore(CookieStore cookieStore) {
        OkGo.getInstance().setCookieStore(cookieStore);
        return this;
    }

    /**
     * 获取全局的cookie实例
     */
    public CookieJarImpl getCookieJar() {
        return OkGo.getInstance().getCookieJar();
    }

    /**
     * 全局读取超时时间
     */
    public NetManager setReadTimeOut(long readTimeOut) {
        OkGo.getInstance().setReadTimeOut(readTimeOut);
        return this;
    }

    /**
     * 全局写入超时时间
     */
    public NetManager setWriteTimeOut(long writeTimeout) {
        OkGo.getInstance().setWriteTimeOut(writeTimeout);
        return this;
    }

    /**
     * 全局连接超时时间
     */
    public NetManager setConnectTimeout(long connectTimeout) {
        OkGo.getInstance().setConnectTimeout(connectTimeout);
        return this;
    }

    /**
     * 超时重试次数
     */
    public NetManager setRetryCount(int retryCount) {
        OkGo.getInstance().setRetryCount(retryCount);
        return this;
    }

    /**
     * 超时重试次数
     */
    public int getRetryCount() {
        return OkGo.getInstance().getRetryCount();
    }

    public NetManager setCacheMode(CacheMode cacheMode) {
        OkGo.getInstance().setCacheMode(cacheMode);
        return this;
    }

    /**
     * 获取全局的缓存过期时间
     */
    public long getCacheTime() {
        return OkGo.getInstance().getCacheTime();
    }

    /**
     * 获取全局公共请求参数
     */
    public HttpParams getCommonParams() {
        return OkGo.getInstance().getCommonParams();
    }

    /**
     * 添加全局公共请求参数
     */
    public NetManager addCommonParams(HttpParams commonParams) {
        OkGo.getInstance().addCommonParams(commonParams);
        return this;
    }

    /**
     * 获取全局公共请求头
     */
    public HttpHeaders getCommonHeaders() {
        return OkGo.getInstance().getCommonHeaders();
    }

    /**
     * 添加全局公共请求参数
     */
    public NetManager addCommonHeaders(HttpHeaders commonHeaders) {
        OkGo.getInstance().addCommonHeaders(commonHeaders);
        return this;
    }
}
