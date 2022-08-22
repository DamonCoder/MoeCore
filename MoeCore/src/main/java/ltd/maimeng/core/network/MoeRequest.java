package ltd.maimeng.core.network;

import android.app.Activity;
import android.content.Context;

import ltd.maimeng.core.exception.MoeCommonException;
import ltd.maimeng.core.log.MoeLog;
import ltd.maimeng.core.network.lifecycle.Lifecycle;
import ltd.maimeng.core.ui.BaseActivity;
import ltd.maimeng.core.ui.BaseFragment;
import ltd.maimeng.core.utils.TextUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.BaseRequest;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.utils.HttpUtils;
import com.lzy.okserver.download.DownloadInfo;
import com.lzy.okserver.download.DownloadManager;
import com.lzy.okserver.download.DownloadService;
import com.lzy.okserver.listener.DownloadListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import okhttp3.Call;
import okhttp3.Response;

/**
 * * Moe使用的Request数据模型,所需参数在父类中定义
 *
 * @param <T> 如果有子类继承自MoeRequest，则范型为子类类型
 */
public class MoeRequest<T extends MoeRequest> extends MoeBaseRequest implements MoeRequestBehavior {

    public static final String TYPE_GET = "GET";
    public static final String TYPE_POST = "POST";
    public static final String TYPE_UPLOAD = "UPLOAD";
    public static final String TYPE_DOWNLOAD = "DOWNLOAD";

    /**
     * 请求的标号，用于取消请求使用
     */
    protected String tag = "";
    /**
     * 基于Activity的请求需要设置
     */
    protected Activity activity;
    /**
     * 基于Fragment的请求需要设置
     */
    protected Fragment fragment;

    /**
     * 适合在无Activity/Fragment的情况下使用
     */
    protected Context context;

    protected HttpParams mCommonParams = new HttpParams();
    protected HashMap<String, String> headerMap = new HashMap<>();
    protected HashMap<String, Object> paramMap = new HashMap<>();
    protected Object paramBody = new Object();

    protected HashMap<String, ArrayList<File>> fileMap = new HashMap<>();

    /**
     * 请求的的文字描述
     */
    protected String requestDesc = "";

    /**
     * 请求的Url
     */
    protected String url = "";

    /**
     * 文件名称
     */
    protected String downloadFileName = "";
    /**
     * 文件目录
     */
    protected String downloadFileDir = "";

    /**
     * 默认post请求
     */
    protected String requestType = TYPE_POST;

    /**
     * 请求回调代理类
     */
    private ResponseProxy proxy;

    private boolean isNoHost = false;

    private boolean supportResumeBreakPoint = false;

    private Lifecycle lifecycle = new Lifecycle() {
        @Override
        public void onLifecycleChange(int status) {
            if (status == Lifecycle.STATUS_DESTROY) {
                MoeLog.i("载体已销毁!");
                // MoeHttp.cancelRequest(tag);
            }
        }
    };

    public MoeRequest() {
    }

    public MoeRequest(String tag) {
        this.tag = tag;
    }


    public String getTag() {
        return tag;
    }

    public Activity getActivity() {
        return activity;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getRequestDesc() {
        return requestDesc;
    }

    public T setRequestDesc(String requestDesc) {
        this.requestDesc = requestDesc;
        return (T) this;
    }

    public T setTag(String tag) {
        this.tag = tag;
        return (T) this;
    }

    /**
     * 是否是无载体模式
     *
     * @return
     */
    public boolean isNoHost() {
        return isNoHost;
    }

    public T setNoHost(boolean noHost) {
        this.isNoHost = noHost;
        return (T) this;
    }

    /**
     * 是否支持断点续传
     *
     * @return
     */
    public boolean isSupportResumeBreakPoint() {
        return supportResumeBreakPoint;
    }

    /**
     * 设置是否支持断点续传
     *
     * @param supportResumeBreakPoint
     * @return
     */
    public T setSupportResumeBreakPoint(boolean supportResumeBreakPoint) {
        this.supportResumeBreakPoint = supportResumeBreakPoint;
        return (T) this;
    }


    public String getUrl() {
        return url;
    }

    public T setUrl(String url) {
        this.url = url;
        return (T) this;
    }

    public T bind(@NonNull Fragment fragment) {
        return setFragment(fragment);
    }

    public T bind(@NonNull Activity activity) {
        return setActivity(activity);
    }

    public T bind(@NonNull Context context) {
        return setAppContext(context);
    }

    public T setActivity(@NonNull Activity activity) {
        this.activity = activity;
        if (TextUtil.isEmpty(tag)) {
            tag = activity.getClass().getName();
        }
        if (activity instanceof BaseActivity) {
            ((BaseActivity) activity).addLifecycleListener(lifecycle);
        }
        return (T) this;
    }


    public T setFragment(@NonNull Fragment fragment) {
        this.fragment = fragment;
        if (TextUtil.isEmpty(tag)) {
            tag = fragment.getClass().getName();
        }
        if (fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).addLifecycleListener(lifecycle);
        }
        return (T) this;
    }

    public Context getAppContext() {
        return context;
    }


    public T setAppContext(@NonNull Context context) {
        this.context = context;
        if (TextUtil.isEmpty(tag)) {
            tag = context.getClass().getName();
        }
        return (T) this;
    }

    public HashMap<String, ArrayList<File>> getFileMap() {
        return fileMap;
    }

    public T setFileMap(HashMap<String, ArrayList<File>> fileMap) {
        this.fileMap = fileMap;
        return (T) this;
    }

    public String getRequestType() {
        return requestType;
    }

    public T setRequestType(String requestType) {
        this.requestType = requestType;
        return (T) this;
    }

    public String getDownloadFileDir() {
        return downloadFileDir;
    }

    public T setDownloadFileDir(String downloadFileDir) {
        this.downloadFileDir = downloadFileDir;
        return (T) this;
    }

    public HashMap<String, Object> getParamMap() {
        return paramMap;
    }

    public Object getParamBody() {
        return paramBody;
    }

    public HttpParams getmCommonParams() {
        return mCommonParams;
    }

    public T setParamMap(HashMap<String, Object> paramMap) {
        this.paramMap = paramMap;
        return (T) this;
    }

    public HashMap<String, String> getHeaderMap() {
        return headerMap;
    }

    public T setHeaderMap(HashMap<String, String> headerMap) {
        this.headerMap = headerMap;
        return (T) this;
    }

    public T addParam(String key, String value) {
        paramMap.put(key, value);
        return (T) this;
    }

    public T addParam(String key, Object value) {
        paramMap.put(key, value);
        return (T) this;
    }

    public T addBodyParam(Object value) {
        paramMap = null;
        paramBody = value;
        return (T) this;
    }

    public T removeParam(String key) {
        if (paramMap != null && paramMap.containsKey(key)) {
            paramMap.remove(key);
        }
        return (T) this;
    }

    public T addCommonParam(String key, String value) {
        mCommonParams.put(new HttpParams(key, value));
        return (T) this;
    }

    public T clearParams() {
        paramMap.clear();
        return (T) this;
    }

    public T addHeaderParam(String key, String value) {
        headerMap.put(key, value);
        return (T) this;
    }

    public T removeHeaderParam(String key) {
        if (headerMap != null && headerMap.containsKey(key)) {
            headerMap.remove(key);
        }
        return (T) this;
    }

    public T clearHeaderParams() {
        headerMap.clear();
        return (T) this;
    }

    public T addFileParam(@NonNull String key, @NonNull File file) {

        if (fileMap.containsKey(key)) {
            ArrayList<File> fileList = fileMap.get(key);
            fileList.add(file);
        } else {
            ArrayList<File> fileList = new ArrayList<>();
            fileList.add(file);
            fileMap.put(key, fileList);
        }

        return (T) this;
    }

    public T removeFileParam(String key) {
        if (fileMap != null && fileMap.containsKey(key)) {
            fileMap.remove(key);
        }
        return (T) this;
    }

    public T clearFileParams() {
        fileMap.clear();
        return (T) this;
    }

    public String getDownloadFileName() {
        return downloadFileName;
    }

    public T setDownloadFileName(String downloadFileName) {
        this.downloadFileName = downloadFileName;
        return (T) this;
    }

    public ResponseProxy getResponseProxy() {
        return proxy;
    }

    public T send() {
        return send(null);
    }

    public T send(ResponseProxy proxy) {
        try {
            if (proxy != null) {
                this.proxy = proxy;
                this.proxy.setRequest(this);
            }
            if (MoeRequestChecker.check(this)) {
                if (getRequestType().equals(TYPE_GET)) {
                    // HttpExecutor.getInstance().sendGet(this);
                    String urlGet = HttpUtils.createUrlFromParams(getUrl(), getmCommonParams().urlParamsMap);
                    GetRequest getRequest = OkGo.get(urlGet);
                    excuteRequest(getRequest, this);
                } else if (getRequestType().equals(TYPE_POST)) {
                    // HttpExecutor.getInstance().sendPost(this);
                    String urlPost = HttpUtils.createUrlFromParams(getUrl(), getmCommonParams().urlParamsMap);
                    PostRequest postRequest = OkGo.post(urlPost);
                    excuteRequest(postRequest, this);
                } else if (getRequestType().equals(TYPE_UPLOAD)) {
                    // HttpExecutor.getInstance().upload(this);
                    PostRequest postRequest = OkGo.post(getUrl());
                    excuteUploadRequest(postRequest, this);
                } else if (getRequestType().equals(TYPE_DOWNLOAD)) {
                    // HttpExecutor.getInstance().download(this);
                    GetRequest getRequest = OkGo.get(getUrl());
                    if (isSupportResumeBreakPoint()) {
                        excuteSupportBreakPointRequest(getRequest, this);
                    } else {
                        excuteUnSupportBreakPointRequest(getRequest, this);
                    }
                }
            } else {
                // can't reach
                // 发送请求前被检测出问题，MoeRequestChecker会自行处理，所以这里不需要做任何事情
            }
        } catch (MoeCommonException e) {
            e.printStackTrace();
        }

        return (T) this;
    }

    @Override
    public void buildParams(BaseRequest request, MoeRequest moeRequest) {
    }

    /**
     * 执行普通Http、Https请求
     *
     * @param request
     * @param moeRequest
     */
    private void excuteRequest(final BaseRequest request, final MoeRequest moeRequest) {
        buildParams(request, moeRequest);
        MoeLog.i("Coder", "Http.url=" + request.getUrl());
        MoeLog.i("Coder", moeRequest.toString());
        request.execute(new StringCallback() {

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                moeRequest.beforeSend();
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                moeRequest.afterResponse();
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                moeRequest.onResponse(s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (response != null) {
                    moeRequest.setHttpResponseCode(response.code());
                    moeRequest.onError(response.code(), response.message());
                } else {
                    moeRequest.onError(ErrorCode.UNKNOWN_ERROR, "网络错误！");
                }
            }
        });
    }

    /**
     * 执行上传请求
     *
     * @param request
     * @param moeRequest
     */
    private void excuteUploadRequest(BaseRequest request, final MoeRequest moeRequest) {
        buildParams(request, moeRequest);
        request.execute(new StringCallback() {

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                moeRequest.beforeSend();
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (response != null) {
                    moeRequest.setHttpResponseCode(response.code());
                    moeRequest.onError(ErrorCode.HTTP_ERROR, response.message());
                } else if (e != null && e instanceof IOException) {
                    moeRequest.onError(ErrorCode.NET_ERROR, "网络错误!");
                } else {
                    moeRequest.onError(ErrorCode.UNKNOWN_ERROR, "请求出错了!");
                }
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                moeRequest.afterResponse();
            }

            @Override
            public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.upProgress(currentSize, totalSize, progress, networkSpeed);
                moeRequest.onProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onSuccess(String s, Call call, Response response) {
                moeRequest.setHttpResponseCode(response.code());
                moeRequest.onResponse(s);
            }
        });
    }

    /**
     * 执行支持断点续传的下载请求
     *
     * @param request
     * @param moeRequest
     */
    private void excuteSupportBreakPointRequest(BaseRequest request, final ltd.maimeng.core.network.MoeRequest moeRequest) {

        buildParams(request, moeRequest);

        String fileName = null;
        String fileDir = null;

        if (!TextUtil.isEmpty(moeRequest.getDownloadFileName())) {
            fileName = moeRequest.getDownloadFileName();
        } else {
            fileName = null;
        }

        DownloadManager downloadManager = DownloadService.getDownloadManager();

        if (!TextUtil.isEmpty(moeRequest.getDownloadFileDir())) {
            fileDir = moeRequest.getDownloadFileDir();
            File file = new File(fileDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            downloadManager.setTargetFolder(fileDir);
        }

        downloadManager.addTask(fileName, moeRequest.getTag(), request, new DownloadListener() {

            @Override
            public void onAdd(DownloadInfo downloadInfo) {
                super.onAdd(downloadInfo);
                moeRequest.beforeSend();
            }

            @Override
            public void onProgress(DownloadInfo downloadInfo) {
                moeRequest.onProgress(
                        downloadInfo.getDownloadLength(),
                        downloadInfo.getTotalLength(),
                        downloadInfo.getProgress(),
                        downloadInfo.getNetworkSpeed());
            }

            @Override
            public void onFinish(DownloadInfo downloadInfo) {
                File file = new File(downloadInfo.getTargetFolder(), downloadInfo.getFileName());
                moeRequest.onDownloaded(file);
                moeRequest.afterResponse();
            }

            @Override
            public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
                // 写OkGo框架的人比较屎，把所有出错的情况都混到一起给返回，
                // 哥出于无奈，只能根据他返回的字符串区分错误类型。
                // 具体字符串见DownloadTask类的118行左右。
                moeRequest.onError(ErrorCode.HTTP_ERROR, errorMsg);
            }
        });
    }

    /**
     * 执行不支持断点续传的下载请求
     *
     * @param request
     * @param moeRequest
     */
    private void excuteUnSupportBreakPointRequest(BaseRequest request, final ltd.maimeng.core.network.MoeRequest moeRequest) {

        String fileDir = moeRequest.getDownloadFileDir();
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        buildParams(request, moeRequest);
        request.execute(new FileCallback(fileDir, moeRequest.getDownloadFileName()) {

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                moeRequest.beforeSend();
            }

            @Override
            public void onSuccess(File file, Call call, Response response) {
                moeRequest.setHttpResponseCode(response.code());
                moeRequest.onDownloaded(file);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                if (response != null) {
                    moeRequest.setHttpResponseCode(response.code());
                    moeRequest.onError(ltd.maimeng.core.network.ErrorCode.HTTP_ERROR, response.message());
                } else if (e != null && e instanceof IOException) {
                    moeRequest.onError(ltd.maimeng.core.network.ErrorCode.NET_ERROR, "网络错误!");
                } else {
                    moeRequest.onError(ltd.maimeng.core.network.ErrorCode.UNKNOWN_ERROR, "请求出错了!");
                }
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                moeRequest.onProgress(currentSize, totalSize, progress, networkSpeed);
            }

            @Override
            public void onAfter(File file, Exception e) {
                super.onAfter(file, e);
                moeRequest.afterResponse();
            }
        });
    }

    public void retry() {
        send();
    }


    /**
     * 判断是否要进行回调
     */
    public boolean canCallProxy() {
        if (this.proxy == null) {
            // 如果代理为空，则不允许调用
            MoeLog.e("Tag为" + tag + "的请求proxy = null , 不予回调!");
            return false;
        }

        if (isNoHost()) {
            return true;
        }

        if (isOnBackgroundThread()) {
            // 如果当前线程不是主线程，则不允许调用
            MoeLog.e("Tag为" + tag + "的请求回调未运行在主线程 , 不予回调!");
            return false;
        }


        if (lifecycle != null && lifecycle.getStatus() == Lifecycle.STATUS_DESTROY) {
            // 如果代理为空，则不允许调用
            MoeLog.e("Tag为" + tag + "的请求载体生命周期已结束, 不予回调!");
            return false;
        }

        boolean result = isActivityOk(activity) || isFragmentOk(fragment) || isContextOk(context);
        if (!result) {
            MoeLog.i("Tag为" + tag + "的请求 canCallProxy 判定结果为 " + result + "无法回调！");
        }
        return result;
    }

    @Override
    public void beforeSend() {
        if (canCallProxy()) {
            proxy.beforeRequest(this);
        }
    }

    @Override
    public void onResponse(String response) {
        if (canCallProxy()) {
            proxy.onSuccess(this, response);
        }
    }

    @Override
    public void afterResponse() {
        if (canCallProxy()) {
            proxy.afterRequest();
        }
    }

    @Override
    public void onDownloaded(File file) {
        if (canCallProxy()) {
            proxy.onDownloaded(file);
        }
    }

    @Override
    public void onError(int code, String msg) {
        if (canCallProxy()) {
            proxy.onError(code, msg);
        }
    }

    @Override
    public void onProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
        if (canCallProxy()) {
            proxy.progress(currentSize, totalSize, progress, networkSpeed);
        }
    }

    /**
     * 设置Http响应码，框架级别API，禁止个人调用
     */
    public void setHttpResponseCode(int httpResponseCode) {
        if (canCallProxy()) {
            proxy.setHttpResponseCode(httpResponseCode);
        }
    }

    /**
     * 释放引用的资源
     */
    public void releaseReference() {
        activity = null;
        fragment = null;
        context = null;
        proxy = null;
        paramMap = null;
        mCommonParams = null;
        headerMap = null;
        fileMap = null;
        lifecycle = null;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(">>>>>>>> Request >>>>>>>>" + "\n");
        if (headerMap.size() > 0) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                builder.append("|--(Header)--" + entry.getKey() + "=" + entry.getValue() + "\n");
            }
        }
        builder.append("|--url=" + url + "\n");
        builder.append("|--tag=" + tag + "\n");
        builder.append("|--requestType=" + requestType + "\n");
        builder.append("|--requestDesc=" + requestDesc + "\n");

        if (paramMap != null && paramMap.size() > 0) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                builder.append("|--(Param)--" + entry.getKey() + "=" + entry.getValue() + "\n");
            }
        }

        return builder.toString();
    }

    /**
     * 使用任何手段去获取一个context。
     * 有可能是从Activity获取；有可能从Fragment获取；
     * 也有可能是Application的Context
     *
     * @return
     */
    public Context getContextByAnyMeans() {

        if (activity != null) {
            return activity;
        }

        if (fragment != null) {
            return fragment.getContext();
        }

        if (context != null) {
            return context;
        }

        return null;
    }
}
