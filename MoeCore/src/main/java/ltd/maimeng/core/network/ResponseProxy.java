package ltd.maimeng.core.network;

import java.io.File;


/**
 * @param <T> 该代理类对应哪个request(必须为MoeRequest的子类)
 */
public abstract class ResponseProxy<T extends MoeRequest> {

    private T request = null;
    /**
     * Http响应码
     */
    private int httpResponseCode = -999;

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    /**
     * 请求网络前调用，UI线程
     */
    public void beforeRequest(T request) {
    }

    /**
     * 请求网络结束后，UI线程
     */
    public void afterRequest() {
    }

    /**
     * 对返回数据进行操作的回调， UI线程
     *
     * @param request  当前Request
     * @param response 网络返回结果
     */
    public abstract void onSuccess(T request, String response);
//    {
//        if (Tea.getInstance().isPrintLogEnable()) {
//            StringBuilder builder = new StringBuilder();
//            builder.append("<<<<<<<< Response <<<<<<<<" + "\n");
//            builder.append("|--url-->" + request.getUrl() + "\n");
//            builder.append("|--tag-->" + request.getTag() + "\n");
//            builder.append("|--description-->" + request.getRequestDesc() + "\n");
//            builder.append("|--Http.response-->" + response);
//            TeaLog.i(builder.toString());
//        }
//
//        if (beforeDoConversion(request, response)) {
//            dataConversion(request, response);
//        }
//    }

//    /**
//     * 是否执行数据转换
//     *
//     * @param request
//     * @param response
//     * @return true 执行dataConversion false 不执行
//     */
//    public abstract boolean beforeDoConversion(T request, String response);

//    /**
//     * 对数据进行变换，你可以将String解析成想要的任何对象
//     *
//     * @param request  当前Request
//     * @param response
//     */
//    public abstract void dataConversion(T request, String response);

    /**
     * 请求时报错都会回调该方法， UI线程
     */
    public abstract void onError(int code, String msg);

    /**
     * 下载完成后调用，UI线程
     */
    public void onDownloaded(File file) {
    }

    /**
     * 上传/下载进度，UI线程
     */
    public void progress(long currentSize, long totalSize, float progress, long networkSpeed) {
    }
}
