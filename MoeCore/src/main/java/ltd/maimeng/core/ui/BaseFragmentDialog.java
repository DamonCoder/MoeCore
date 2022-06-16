package ltd.maimeng.core.ui;

import ltd.maimeng.core.ui.permission.PermissionBehavior;
import ltd.maimeng.core.ui.permission.PermissionCallBack;
import ltd.maimeng.core.ui.permission.PermissionPresenter;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class BaseFragmentDialog extends DialogFragment implements PermissionBehavior {
    private PermissionPresenter permissionPresenter;

    /**
     * 运行时权限，阻断式
     */
    protected void checkPermission(PermissionCallBack permissionCallBack, String... permissions) {
        checkRuntimePermission(true, permissionCallBack, permissions);
    }

    /**
     * 运行时权限，非阻断式
     */
    protected void checkPermissionOptional(PermissionCallBack permissionCallBack, String... permissions) {
        checkRuntimePermission(false, permissionCallBack, permissions);
    }

    private void checkRuntimePermission(boolean deniedBlock, PermissionCallBack permissionCallBack, String... permissions) {
        if (permissionPresenter == null) {
            permissionPresenter = new PermissionPresenter(BaseFragmentDialog.this);
        }
        permissionPresenter.checkRuntimePermission(deniedBlock, permissionCallBack, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
