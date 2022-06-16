package ltd.maimeng.core.ui.permission;

import java.util.List;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/11
 *     desc   :
 * </pre>
 */
public abstract class PermissionCallBack {

    public abstract void onGranted();

    public abstract void onDenied(List<String> permissions);

    public void onRequestPermissionDialogDenied() {

    }
}
