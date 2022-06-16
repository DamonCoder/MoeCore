package ltd.maimeng.core.ui.permission;

import android.Manifest;

import java.util.ArrayList;
import java.util.List;

import ltd.maimeng.core.R;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/09/21
 *     desc   :
 * </pre>
 */
public class PermissionUtil {

    public static List<PermissionInfo> convertString2Bean(List<String> permissionDeniedList) {
        List<PermissionInfo> permissionInfos = new ArrayList<>();
        for (int i = 0; i < permissionDeniedList.size(); i++) {
            String permission = permissionDeniedList.get(i);
            if (Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission) || Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission)) {
                permissionInfos.add(new PermissionInfo(R.drawable.permission_ic_storage, "文件存储", "允许应用读取、写入外部存储"));
            }
            if (Manifest.permission.CAMERA.equals(permission)) {
                permissionInfos.add(new PermissionInfo(R.drawable.permission_ic_camera, "相机相册", "允许应用访问摄像头进行拍照"));
            }
            if (Manifest.permission.RECORD_AUDIO.equals(permission)) {
                permissionInfos.add(new PermissionInfo(R.drawable.permission_ic_micro_phone, "录制声音", "允许应用通过手机或耳机的麦克录制声音"));
            }
            if (Manifest.permission.RECORD_AUDIO.equals(permission)) {
                permissionInfos.add(new PermissionInfo(R.drawable.permission_ic_location, "定位信息", "允许应用获取定位信息"));
            }
        }
        return permissionInfos;
    }
}
