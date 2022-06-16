package ltd.maimeng.core.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;

import ltd.maimeng.core.MoeApplication;
import com.hjq.toast.ToastUtils;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/07/29
 *     desc   :
 * </pre>
 */
public class ClipboardUtil {

    public static void copyTxt(String txt) {
        copyTxt(txt, true);
    }

    public static void copyTxt(String txt, boolean showToast) {
        ClipboardManager cm = (ClipboardManager) MoeApplication.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        if (!TextUtil.isEmpty(txt)) {
            ClipData mClipData = ClipData.newPlainText("clipboard_txt", txt);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            if (showToast) {
                ToastUtils.show("已复制到剪切板");
            }
        }
    }

    public static String paste() {
        ClipboardManager manager = (ClipboardManager) MoeApplication.getAppContext().getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager != null) {
            if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
                CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
                String addedTextString = String.valueOf(addedText);
                if (!TextUtils.isEmpty(addedTextString)) {
                    return addedTextString;
                }
            }
        }
        return "";
    }
}
