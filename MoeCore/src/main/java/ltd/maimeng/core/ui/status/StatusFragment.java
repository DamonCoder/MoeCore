package ltd.maimeng.core.ui.status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ltd.maimeng.core.R;
import ltd.maimeng.core.ui.permission.PermissionFragment;
import ltd.maimeng.core.ui.widget.MutlColorLoadingView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/13
 *     desc   :
 * </pre>
 */
public abstract class StatusFragment extends PermissionFragment implements StatusBehavior {

    private StatusLayout statusLayout;
    private MutlColorLoadingView loadingLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statusLayout = view.findViewById(R.id.fragment_status_layout);
        statusLayout.setContentView(setContentView());
        showContentLayout();

        loadingLayout = view.findViewById(R.id.fragment_loading);
    }

    public abstract View setContentView();

    @Override
    public void showLoadingLayout() {
        statusLayout.showLoading();
    }

    @Override
    public void showContentLayout() {
        statusLayout.showContent();
    }

    @Override
    public void showNetErrorLayout() {
        statusLayout.showNetError();
    }

    protected void showLoadding(){
        if(loadingLayout.getVisibility() == View.GONE){
            loadingLayout.setVisibility(View.VISIBLE);
        }
    }

    protected void hideLoadding(){
        if(loadingLayout.getVisibility() == View.VISIBLE){
            loadingLayout.setVisibility(View.GONE);
        }
    }
}
