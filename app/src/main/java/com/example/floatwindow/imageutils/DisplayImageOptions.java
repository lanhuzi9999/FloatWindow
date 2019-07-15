package com.example.floatwindow.imageutils;

import android.graphics.drawable.Drawable;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * ************************************************ <br>
 * 创建人员: robin <br>
 * 文件描述: Glide图片加载配置项类<br>
 * 修改时间: 2017/8/4 15:47 <br>
 * 修改历史: 2017/8/4 1.00 初始版本
 * ************************************************
 */
public class DisplayImageOptions {

    public enum CornerType {
        ALL,
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
        TOP, BOTTOM, LEFT, RIGHT,
        OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
        DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
    }

    public static final int CENTER_CROP = 0;
    public static final int FIT_CENTER = 1;
    /**
     * 默认占位图
     */
    private final Integer imageResPlaceHolder;
    /**
     * 错误显示资源
     */
    private final Integer imageResError;
    /**
     * 默认占位图
     */
    private final Drawable imagePlaceHolder;
    /**
     * 错误显示资源
     */
    private final Drawable imageError;
    /**
     * 圆角或者圆形图片
     */
    private final BitmapTransformation transformation;
    /**
     * 裁剪类型,默认为中部裁剪
     */
    private int CropType = CENTER_CROP;
    /**
     * 圆角类型，默认是四个角都为圆角
     */
    private CornerType cornerType = CornerType.ALL;
    /**
     * 是否淡入淡出动画
     */
    private boolean crossFade;
    /**
     * 淡入淡出动画持续的时间
     */
    private int crossDuration;
    /**
     * 图片最终显示在ImageView上的宽高度像素
     */
    private OverrideSize size;
    /**
     * true,强制显示的是gif动画,如果url不是gif的资源,那么会回调error()
     */
    private boolean asGif;
    /**
     * true,强制显示为常规图片,如果是gif资源,则加载第一帧作为图片
     */
    private boolean asBitmap;
    /**
     * true,跳过内存缓存,默认false
     */
    private boolean skipMemoryCache;
    /**
     * 硬盘缓存,默认为all类型
     */
    private DiskCache diskCacheStrategy;
    /**
     * 设置缩略图的缩放比例0.0f-1.0f,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
     */
    private float thumbnail;
    /**
     * 设置缩略图的url,如果缩略图比全尺寸图先加载完，就显示缩略图，否则就不显示
     */
    private String thumbnailUrl;
    /**
     * 圆形裁剪
     */
    private boolean cropCircle;
    /**
     * 圆角处理
     */
    private boolean roundedCorners;
    private int radius;
    private int margin;
    /**
     * 灰度处理
     */
    private boolean grayscale;
    /**
     * 高斯模糊处理
     */
    private boolean blur;

    /**
     * 硬盘缓存策略
     */
    public enum DiskCache {
        //跳过硬盘缓存
        NONE(DiskCacheStrategy.NONE),
        //仅仅保存原始分辨率的图片
        SOURCE(DiskCacheStrategy.SOURCE),
        //仅仅缓存最终分辨率的图像(降低分辨率后的或者转换后的)
        RESULT(DiskCacheStrategy.RESULT),
        //缓存所有版本的图片,默认行为
        ALL(DiskCacheStrategy.ALL);
        private DiskCacheStrategy strategy;

        DiskCache(DiskCacheStrategy strategy) {
            this.strategy = strategy;
        }

        public DiskCacheStrategy getStrategy() {
            return strategy;
        }
    }

    private DisplayImageOptions(Builder builder) {
        this.imageResPlaceHolder = builder.imageResPlaceHolder;
        this.imageResError = builder.imageResError;
        this.imagePlaceHolder = builder.imagePlaceHolder;
        this.imageError = builder.imageError;
        this.transformation = builder.transformation;
        this.CropType = builder.CropType;
        this.crossFade = builder.crossFade;
        this.crossDuration = builder.crossDuration;
        this.size = builder.size;
//        this.CropType = builder.CropType;
        this.asGif = builder.asGif;
        this.asBitmap = builder.asBitmap;
        this.skipMemoryCache = builder.skipMemoryCache;
        this.diskCacheStrategy = builder.diskCacheStrategy;
        this.thumbnail = builder.thumbnail;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.blur = builder.blur;
        this.cropCircle = builder.cropCircle;
        this.roundedCorners = builder.roundedCorners;
        this.radius = builder.radius;
        this.margin = builder.margin;
        this.grayscale = builder.grayscale;
        this.cornerType = builder.cornerType;
    }

    /**
     * builder类
     */
    public static class Builder {
        private Integer imageResPlaceHolder = 0;
        private Integer imageResError = 0;
        private Drawable imagePlaceHolder = null;
        private Drawable imageError = null;
        private BitmapTransformation transformation = null;
        private int CropType = CENTER_CROP;
        private CornerType cornerType = CornerType.ALL;
        private boolean crossFade;
        private int crossDuration;
        private OverrideSize size;
        private boolean asGif;
        private boolean asBitmap;
        private boolean skipMemoryCache;
        private DiskCache diskCacheStrategy;
        private float thumbnail;
        private String thumbnailUrl;
        private boolean cropCircle;
        private boolean roundedCorners;
        private int radius;
        private int margin;
        private boolean grayscale;
        private boolean blur;

        public Builder setImageResPlaceHolder(Integer imageResPlaceHolder) {
            this.imageResPlaceHolder = imageResPlaceHolder;
            return this;
        }

        public Builder setImageResError(Integer imageResError) {
            this.imageResError = imageResError;
            return this;
        }

        public Builder setImagePlaceHolder(Drawable imagePlaceHolder) {
            this.imagePlaceHolder = imagePlaceHolder;
            return this;
        }

        public Builder setImageError(Drawable imageError) {
            this.imageError = imageError;
            return this;
        }

        public Builder setTransformation(BitmapTransformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder setCropType(int cropType) {
            this.CropType = cropType;
            return this;
        }

        public Builder setCornerType(CornerType cornerType) {
            this.cornerType = cornerType;
            return this;
        }

        public Builder setAsBitmap(boolean asBitmap) {
            this.asBitmap = asBitmap;
            return this;
        }

        public Builder setCrossFade(boolean crossFade) {
            this.crossFade = crossFade;
            return this;
        }

        public Builder setCrossDuration(int crossDuration) {
            this.crossDuration = crossDuration;
            return this;
        }

        public Builder setSize(OverrideSize size) {
            this.size = size;
            return this;
        }

        public Builder setAsGif(boolean asGif) {
            this.asGif = asGif;
            return this;
        }

        public Builder setSkipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public Builder setDiskCacheStrategy(DiskCache diskCacheStrategy) {
            this.diskCacheStrategy = diskCacheStrategy;
            return this;
        }

        public Builder setThumbnail(float thumbnail) {
            this.thumbnail = thumbnail;
            return this;
        }

        public Builder setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder setCropCircle(boolean cropCircle) {
            this.cropCircle = cropCircle;
            return this;
        }

        public Builder setRoundedCorners(boolean roundedCorners) {
            this.roundedCorners = roundedCorners;
            return this;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setMargin(int margin) {
            this.margin = margin;
            return this;
        }

        public Builder setGrayscale(boolean grayscale) {
            this.grayscale = grayscale;
            return this;
        }

        public Builder setBlur(boolean blur) {
            this.blur = blur;
            return this;
        }

        public DisplayImageOptions build() {
            return new DisplayImageOptions(this);
        }
    }

    public static Builder parseBuilder(DisplayImageOptions options) {
        Builder builder = new Builder();
        builder.imageResPlaceHolder = options.imageResPlaceHolder;
        builder.imageResError = options.imageResError;
        builder.imagePlaceHolder = options.imagePlaceHolder;
        builder.imageError = options.imageError;
        builder.transformation = options.transformation;
        builder.CropType = options.CropType;
        builder.asGif = options.asGif;
        builder.asBitmap = options.asBitmap;
        builder.skipMemoryCache = options.skipMemoryCache;
        builder.diskCacheStrategy = options.diskCacheStrategy;
        builder.thumbnail = options.thumbnail;
        builder.thumbnailUrl = options.thumbnailUrl;
        builder.cropCircle = options.cropCircle;
        builder.roundedCorners = options.roundedCorners;
        builder.radius = options.radius;
        builder.margin = options.margin;
        builder.grayscale = options.grayscale;
        builder.blur = options.blur;
        return builder;
    }

    public Integer getImageResPlaceHolder() {
        return imageResPlaceHolder;
    }

    public Integer getImageResError() {
        return imageResError;
    }

    public Drawable getImagePlaceHolder() {
        return imagePlaceHolder;
    }

    public Drawable getImageError() {
        return imageError;
    }

    public BitmapTransformation getTransformation() {
        return transformation;
    }

    public int getCropType() {
        return CropType;
    }

    public CornerType getCornerType(){
        return cornerType;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public boolean isCrossFade() {
        return crossFade;
    }

    public int getCrossDuration() {
        return crossDuration;
    }

    public OverrideSize getSize() {
        return size;
    }

    public boolean isAsBitmap() {
        return asBitmap;
    }

    public boolean isSkipMemoryCache() {
        return skipMemoryCache;
    }

    public DiskCache getDiskCacheStrategy() {
        return diskCacheStrategy;
    }

    public float getThumbnail() {
        return thumbnail;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public boolean isCropCircle() {
        return cropCircle;
    }

    public boolean isRoundedCorners() {
        return roundedCorners;
    }

    public int getRadius() {
        return radius;
    }

    public int getMargin() {
        return margin;
    }

    public boolean isGrayscale() {
        return grayscale;
    }

    public boolean isBlur() {
        return blur;
    }

    /**
     * 图片最终显示在ImageView上的宽高像素,一般不会去特意指定
     */
    public static class OverrideSize {
        private final int width;
        private final int height;

        public OverrideSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

}
