package ltd.maimeng.core.ui.list;

import java.util.List;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2021/02/01
 *     desc   :
 * </pre>
 */
public interface RecyclerBehavior<T extends ListBean> {

    void onBeforeRequest();

    void onAfterRequest();

    void onDataSuccess(List<T> datas);

    void onDataFailure(String msg);
}
