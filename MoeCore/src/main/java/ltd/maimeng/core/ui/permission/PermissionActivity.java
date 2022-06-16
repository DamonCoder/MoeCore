package ltd.maimeng.core.ui.permission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/10
 *     desc   : 处理权限相关的PraentActivity
 * </pre>
 */
public abstract class PermissionActivity extends AppCompatActivity implements PermissionBehavior {

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
            permissionPresenter = new PermissionPresenter(PermissionActivity.this);
        }
        permissionPresenter.checkRuntimePermission(deniedBlock, permissionCallBack, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissionPresenter == null) {
            permissionPresenter = new PermissionPresenter(PermissionActivity.this);
        }
        permissionPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
