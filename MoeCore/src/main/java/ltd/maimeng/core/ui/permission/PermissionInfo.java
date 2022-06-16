package ltd.maimeng.core.ui.permission;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/08/13
 *     desc   :
 * </pre>
 */
public class PermissionInfo {

    public int iconResID;
    public String title;
    public String desc;

    public PermissionInfo(int iconResID, String title, String desc) {
        this.iconResID = iconResID;
        this.title = title;
        this.desc = desc;
    }
}
