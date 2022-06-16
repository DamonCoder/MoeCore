package ltd.maimeng.core.ui.list;

import android.app.Activity;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/13
 *     desc   :
 * </pre>
 */
public class HeaderFooterRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // item类型
    public static final int ITEM_TYPE_HEADER = 99;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 100;

    private int mHeaderCount = 0;// 头部View个数
    private int mBottomCount = 0;// 底部View个数

    private Activity activity;
    private Fragment fragment;
    private ListCache<ListBean> listCache;
    private HashMap<Integer, Pair<Integer, Class>> headerMapping = new HashMap<>();
    private HashMap<Integer, Pair<Integer, Class>> footerMapping = new HashMap<>();
    private HashMap<Integer, Pair<Integer, Class>> holderMapping = new HashMap<>();

    public HeaderFooterRecyclerAdapter(Activity activity) {
        this.activity = activity;
    }

    public HeaderFooterRecyclerAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public HeaderFooterRecyclerAdapter bindData(ListCache listCache) {
        this.listCache = listCache;
        return this;
    }

    public HeaderFooterRecyclerAdapter setHeaderHolder(Class holderClazz) {
        if (!RecyclerViewHolder.class.isAssignableFrom(holderClazz)) {
            // 如果holderClass不是RecyclerViewHolder的子类
            // throw new RuntimeException("holderClass必须是RecyclerViewHolder或其子类!");
        }
        int layoutId = 0;
        try {
            layoutId = holderClazz.getField("LAYOUT_ID").getInt(holderClazz);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        headerMapping.put(ITEM_TYPE_HEADER, new Pair<>(layoutId, holderClazz));
        mHeaderCount++;
        return this;
    }

    public HeaderFooterRecyclerAdapter setViewHolder(int viewType, Class holderClazz) {
        if (!RecyclerViewHolder.class.isAssignableFrom(holderClazz)) {
            // 如果holderClass不是RecyclerViewHolder的子类
            // throw new RuntimeException("holderClass必须是RecyclerViewHolder或其子类!");
        }
        int layoutId = 0;
        try {
            layoutId = holderClazz.getField("LAYOUT_ID").getInt(holderClazz);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        holderMapping.put(viewType, new Pair<>(layoutId, holderClazz));
        return this;
    }

    // 内容长度
    public int getContentItemCount() {
        return listCache.getData().size();
    }

    @Override
    public int getItemCount() {
        // TODO
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    // 判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    // 判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    @Override
    public int getItemViewType(int position) {
        // return super.getItemViewType(position);
        // return listCache.getData().get(position).itemType;
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            // 头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            // 底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            // 内容View
            return listCache.getData().get(position - mHeaderCount).itemType;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Pair<Integer, Class> pair = null;
        if (viewType == ITEM_TYPE_HEADER) {
            pair = headerMapping.get(viewType);
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            pair = footerMapping.get(viewType);
        } else {
            pair = holderMapping.get(viewType);
        }
        View view = View.inflate(activity == null ? fragment.getActivity() : activity, pair.first, null);
        RecyclerViewHolder holder = null;
        try {
            Constructor constructor = activity == null ? pair.second.getConstructor(Fragment.class, View.class) : pair.second.getConstructor(Activity.class, View.class);
            holder = (RecyclerViewHolder) constructor.newInstance(activity == null ? fragment : activity, view);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolder viewHolder = (RecyclerViewHolder) holder;

        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            // 头部View
            viewHolder.setData(null);
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            // 底部View
            viewHolder.setData(null);
        } else {
            // 内容View
            viewHolder.setData(listCache.getData().get(position - mHeaderCount));
        }
    }
}
