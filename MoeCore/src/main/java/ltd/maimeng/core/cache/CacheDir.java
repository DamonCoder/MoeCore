package ltd.maimeng.core.cache;

public class CacheDir {

    /**
     * 缓存文件夹名称
     */
    private String name;
    /**
     * 缓存文件夹作者
     */
    private String author;

    /**
     * 缓存文件夹描述
     */
    private String desc;

    /**
     * 缓存文件夹相对路径
     */
    private String relativePath;

    /**
     * 缓存文件夹绝对路径
     */
    private String absolutePath;

    /**
     * 缓存文件夹特性，如临时文件、媒体文件等
     */
    private String feature;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    @Override
    public String toString() {
        return "CacheDir{" +
                "name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", desc='" + desc + '\'' +
                ", relativePath='" + relativePath + '\'' +
                ", absolutePath='" + absolutePath + '\'' +
                ", feature='" + feature + '\'' +
                '}';
    }
}
