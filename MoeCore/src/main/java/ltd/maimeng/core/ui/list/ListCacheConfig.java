package ltd.maimeng.core.ui.list;

public class ListCacheConfig {

    private int startIndex = 0;
    private int startPageSize = 30;
    private boolean hasMoreData = true;

    public int getStartIndex() {
        return startIndex;
    }

    public ListCacheConfig setStartIndex(int startIndex) {
        this.startIndex = startIndex;
        return this ;
    }

    public int getStartPageSize() {
        return startPageSize;
    }

    public ListCacheConfig setStartPageSize(int startPageSize) {
        this.startPageSize = startPageSize;
        return this ;
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }

    public ListCacheConfig setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        return this ;
    }
}
