package ltd.maimeng.core.ui.list;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/13
 *     desc   :
 * </pre>
 */
public class ListCache<T extends ListBean> {

    /**
     * 页码
     */
    private int pageIndex = 1;

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void indexAdd() {
        pageIndex++;
    }

    /**
     * 每页数量
     */
    private int pageSize = 20;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 页码标识
     */
    private String indexStr = "";

    public String getIndexStr() {
        return indexStr;
    }

    public void setIndexStr(String indexStr) {
        this.indexStr = indexStr;
    }

    /**
     * 数据缓存集合
     */
    private List<T> data = new ArrayList<>();

    public List<T> getData() {
        return data;
    }

    public void addData(List<T> beans) {
        this.data.addAll(beans);
    }

    public void addData(int index, List<T> beans) {
        this.data.addAll(index, beans);
    }

    public void addData(int index, T bean) {
        this.data.add(index, bean);
    }

    public void addData(T bean) {
        this.data.add(bean);
    }

    public int getDataSize() {
        return this.data.size();
    }

    /**
     * 是否有更多数据
     */
    private boolean hasMore = false;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void remove(int position) {
        data.remove(position);
    }

    public void reset() {
        pageIndex = 1;
        data.clear();
    }
}
