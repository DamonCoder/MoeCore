package ltd.maimeng.core.ui.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import ltd.maimeng.core.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/11
 *     desc   :
 * </pre>
 */
public class PermissionPresenter {

    public static final int PERMISSION_REQUEST = 0x11;

    private PermissionBehavior behavior;
    /**
     * 用户禁止了全部或者部分敏感权限时是否阻断操作：true=阻断；false=不阻断
     */
    private boolean deniedBlock = true;
    private PermissionCallBack permissionCallBack;

    public PermissionPresenter(PermissionBehavior behavior) {
        this.behavior = behavior;
    }

    public void checkRuntimePermission(boolean deniedBlock, PermissionCallBack permissionCallBack, String... permissions) {
        this.deniedBlock = deniedBlock;
        this.permissionCallBack = permissionCallBack;
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            Context context = null;
            if (behavior instanceof Activity) {
                context = (Context) behavior;
            } else if (behavior instanceof Fragment) {
                context = ((Fragment) behavior).getContext();
            }
            int checkPermission = ContextCompat.checkSelfPermission(context, permission);
            if (checkPermission == PackageManager.PERMISSION_DENIED) {
                permissionDeniedList.add(permission);
            }
        }
        if (permissionDeniedList.size() > 0) {
            if (behavior instanceof Activity) {
                ActivityCompat.requestPermissions((Activity) behavior, convertList2Array(permissionDeniedList), PERMISSION_REQUEST);
            } else if (behavior instanceof Fragment) {
                ((Fragment) behavior).requestPermissions(convertList2Array(permissionDeniedList), PERMISSION_REQUEST);
            }
        } else {
            permissionCallBack.onGranted();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (PERMISSION_REQUEST == requestCode) {
            List<String> permissionDeniedList = new ArrayList<>();
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionDeniedList.add(permissions[i]);
                }
            }
            if (permissionDeniedList.size() > 0) {
                List<String> permissionsDenied = new ArrayList<>();
                List<String> permissionsNeverAskAgain = new ArrayList<>();
                for (String permission : permissionDeniedList) {
                    Activity activity = null;
                    if (behavior instanceof Activity) {
                        activity = (Activity) behavior;
                    } else if (behavior instanceof Fragment) {
                        activity = ((Fragment) behavior).getActivity();
                    }
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                        permissionsDenied.add(permission);
                    } else {
                        permissionsNeverAskAgain.add(permission);
                    }
                }
                if (permissionsDenied.size() > 0 || permissionsNeverAskAgain.size() > 0) {
                    if (deniedBlock) {
                        showPermissionDialog((permissionsNeverAskAgain.size() > 0 ? true : false), permissionDeniedList);
                    }
                    permissionCallBack.onDenied(permissionDeniedList);
                }

            } else {
                permissionCallBack.onGranted();
            }
        }
    }

    private void showPermissionDialog(final boolean hasNeverAskAgaginPermission, final List<String> permissionDeniedList) {
        // TODO
        Activity activity = null;
        if (behavior instanceof Activity) {
            activity = (Activity) behavior;
        } else if (behavior instanceof Fragment) {
            activity = ((Fragment) behavior).getActivity();
        }
        PermissionDialog permissionDialog = new PermissionDialog(activity, PermissionUtil.convertString2Bean(permissionDeniedList));
        final Activity finalActivity = activity;
        permissionDialog.setOnButtonClickListener(new PermissionDialog.OnButtonClickListener() {
            @Override
            public void onAgreeButtonClick() {
                if (hasNeverAskAgaginPermission) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", AppUtil.getPackageName(), null);
                    intent.setData(uri);
                    finalActivity.startActivity(intent);
                } else {
                    if (behavior instanceof Activity) {
                        ActivityCompat.requestPermissions((Activity) behavior, convertList2Array(permissionDeniedList), PERMISSION_REQUEST);
                    } else if (behavior instanceof Fragment) {
                        ((Fragment) behavior).requestPermissions(convertList2Array(permissionDeniedList), PERMISSION_REQUEST);
                    }
                }
            }

            @Override
            public void onDenyButtonClick() {
                permissionCallBack.onRequestPermissionDialogDenied();
            }
        });
        permissionDialog.show();
    }

    private String[] convertList2Array(List<String> permissionDeniedList) {
        String[] permissionDeniedArray = new String[permissionDeniedList.size()];
        for (int i = 0; i < permissionDeniedList.size(); i++) {
            permissionDeniedArray[i] = permissionDeniedList.get(i);
        }
        return permissionDeniedArray;
    }


}
