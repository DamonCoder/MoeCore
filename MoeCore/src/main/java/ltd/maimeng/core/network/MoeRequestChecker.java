package ltd.maimeng.core.network;

import ltd.maimeng.core.Moe;
import ltd.maimeng.core.exception.MoeCommonException;
import ltd.maimeng.core.exception.MoeRuntimeException;
import ltd.maimeng.core.log.MoeLog;
import ltd.maimeng.core.utils.NetworkUtil;
import ltd.maimeng.core.utils.TextUtil;


/**
 * 负责检查Request的信息是否合法，如不合法就会抛出异常。
 */
public class MoeRequestChecker {

    /**
     * 在发送请求前检查数据的完整性
     *
     * @param request
     * @return
     * @throws MoeCommonException
     */
    public static boolean check(MoeRequest request) throws MoeCommonException {

        if (!NetworkUtil.isNetConnected(Moe.getInstance().getApplication())) {
            MoeLog.e("Tag为 " + request.getTag() + " 的请求网络不可用!");
            request.onError(ErrorCode.NET_ERROR, "网络不可用");
            request.afterResponse();
            return false;
        }

        if (TextUtil.isEmpty(request.getTag())) {
            throw new MoeRuntimeException("请求缺少tag参数!");
        }

        if (TextUtil.isEmpty(request.getUrl())) {
            throw new MoeRuntimeException("请求缺少url参数!");
        }

        if (TextUtil.isEmpty(request.getRequestDesc())) {
            MoeLog.e("Tag为 " + request.getTag() + " 的请求没有设置文字描述，这可能不利于进行数据调试!");
        }

        if (!request.isNoHost()) {
            if (request.getActivity() == null && request.getFragment() == null && request.getAppContext() == null) {
                MoeLog.e("请求被废弃： Tag为 " + request.getTag() + " 的请求未关联任何Activity、Fragment或Context载体，" + "在请求完毕刷新UI时，可能会导致程序崩溃!");
                request.afterResponse();
                return false;
            }
        }

        return true;
    }
}
