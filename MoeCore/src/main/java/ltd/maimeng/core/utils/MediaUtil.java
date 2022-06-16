package ltd.maimeng.core.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.hjq.toast.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * <pre>
 *     author : chenzimeng
 *     e-mail : 1989583040@qq.com
 *     time   : 2020/08/06
 *     desc   :
 *     动态图片>3M，压缩上传
 *     任务图片>1M，压缩上传
 *     任务视频>
 *
 * </pre>
 */
public class MediaUtil {

    public static final long VIDEO_SIZE_MAX = 5;

    public static int[] getVideoSize(String filePath) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filePath);
        String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); //宽
        String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); //高
        // String rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);//视频的方向角度
        // long duration = Long.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) * 1000;//视频的长度
        return new int[]{Integer.parseInt(width), Integer.parseInt(height)};
    }

    /**
     * 获取视频时长，单位：ms
     *
     * @param filePath
     * @return
     */
    public static long getVideoDuration(String filePath) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(filePath);
        // String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH); //宽
        // String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT); //高
        // String rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);//视频的方向角度
        long duration = Long.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)) * 1000;//视频的长度
        return duration / 1000;
    }

    /**
     * 获取视频大小，单位：M
     *
     * @param filePath
     * @return
     */
    public static long getVideoUnitM(String filePath) {
        long unitBit = new File(filePath).length();
        return unitBit / 1024 / 1024;
    }

    /**
     * 是否是图片资源
     *
     * @param filePath
     * @return
     */
    public static boolean isImage(String filePath) {
        String mimeType = MediaFile.getMimeTypeForFile(filePath);
        return MediaFile.isImageFileType(MediaFile.getFileTypeForMimeType(mimeType));
    }

    /**
     * 是否是音频资源
     *
     * @param filePath
     * @return
     */
    public static boolean isAudio(String filePath) {
        String mimeType = MediaFile.getMimeTypeForFile(filePath);
        return MediaFile.isAudioFileType(MediaFile.getFileTypeForMimeType(mimeType));
    }

    /**
     * 是否是视频资源
     *
     * @param filePath
     * @return
     */
    public static boolean isVideo(String filePath) {
        String mimeType = MediaFile.getMimeTypeForFile(filePath);
        return MediaFile.isVideoFileType(MediaFile.getFileTypeForMimeType(mimeType));
    }

    /**
     * 是否是PDF资源
     *
     * @param filePath
     * @return
     */
    public static boolean isPDF(String filePath) {
        String mimeType = MediaFile.getMimeTypeForFile(filePath);
        return MediaFile.isPDFFileType(MediaFile.getFileTypeForMimeType(mimeType));
    }

    /**
     * 是否是WORD资源
     *
     * @param filePath
     * @return
     */
    public static boolean isDOC(String filePath) {
        String mimeType = MediaFile.getMimeTypeForFile(filePath);
        return MediaFile.isDOCFileType(MediaFile.getFileTypeForMimeType(mimeType));
    }

    /**
     * 是否是TXT资源
     *
     * @param filePath
     * @return
     */
    public static boolean isTXT(String filePath) {
        String mimeType = MediaFile.getMimeTypeForFile(filePath);
        return MediaFile.isTXTFileType(MediaFile.getFileTypeForMimeType(mimeType));
    }

    /**
     * 是否是MarkDown资源
     *
     * @param filePath
     * @return
     */
    public static boolean isMarkdown(String filePath) {
        String mimeType = MediaFile.getMimeTypeForFile(filePath);
        return MediaFile.isMarkdownFileType(MediaFile.getFileTypeForMimeType(mimeType));
    }

    /**
     * 是否是HTML资源
     *
     * @param filePath
     * @return
     */
    public static boolean isHTML(String filePath) {
        String mimeType = MediaFile.getMimeTypeForFile(filePath);
        return MediaFile.isHtmlFileType(MediaFile.getFileTypeForMimeType(mimeType));
    }

    /**
     * 获取图片资源宽高
     *
     * @param filePath
     * @return
     */
    public static int[] getImageSize(String filePath) {
        // 获取Options对象
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 仅做解码处理，不加载到内存
        options.inJustDecodeBounds = true;
        // 解析文件
        BitmapFactory.decodeFile(filePath, options);
        // 获取宽高
        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        return new int[]{imgWidth, imgHeight};
    }

    public static boolean saveBitmap(Context context, String filename, Bitmap bitmap) {
        String fileDirStr = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "share";
        String fileNameStr = StringUtils.getMD5Str(filename) + ".png";
        File fileDir = new File(fileDirStr);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        File file = new File(fileDirStr, fileNameStr);
        String filePath = fileDirStr + File.separator + fileNameStr;
        if (FileUtil.isFileExists(filePath)) {
            ToastUtils.show("已保存到相册");
            return true;
        } else {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
//                // 保存图片后发送广播通知更新数据库
//                Uri uri = Uri.fromFile(file);
//                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                updateSystemMedia(context, file, fileNameStr);
                if (isSuccess) {
                    ToastUtils.show("已保存到相册");
                    return true;
                } else {
                    ToastUtils.show("保存失败！");
                    return false;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                ToastUtils.show("保存失败！");
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                ToastUtils.show("保存失败！");
                return false;
            }
        }
    }

    private static void updateSystemMedia(Context context, File file, String fileName) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
    }

    public static String getFileSuffixNameByUrl(String fileUrl) {
        if (fileUrl.contains(".")) {
            int startIndex = fileUrl.lastIndexOf(".");
            int endIndex = fileUrl.length();
            return fileUrl.substring(startIndex, endIndex);
        } else {
            return "";
        }
    }

    public static Bitmap getVideoCover(String videoUrl) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            // 根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            // 获得第一帧图片
            // bitmap = retriever.getFrameAtTime();
            bitmap = retriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }
}
