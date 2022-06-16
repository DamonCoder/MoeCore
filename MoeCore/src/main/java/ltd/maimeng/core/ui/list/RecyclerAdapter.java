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
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private Fragment fragment;
    private ListCache<ListBean> listCache;
    private HashMap<Integer, Pair<Integer, Class>> holderMapping = new HashMap<>();

    public RecyclerAdapter(Activity activity) {
        this.activity = activity;
    }

    public RecyclerAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public RecyclerAdapter bindData(ListCache listCache) {
        this.listCache = listCache;
        return this;
    }

    public RecyclerAdapter setViewHolder(int viewType, Class holderClazz) {
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


    @Override
    public int getItemCount() {
        // TODO
        return listCache.getData().size();
    }

    @Override
    public int getItemViewType(int position) {
        // return super.getItemViewType(position);
        return listCache.getData().get(position).itemType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Pair<Integer, Class> pair = holderMapping.get(viewType);
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
        if (listCache.getData().size() > position) {
            viewHolder.setData(listCache.getData().get(position));
        }
    }
}
