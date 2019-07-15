package com.example.floatwindow.imageutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.GifRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.floatwindow.R;

import java.util.concurrent.TimeUnit;

/**
 * ************************************************ <br>
 * 创建人员: robin <br>
 * 文件描述: Glide图片加载工具类<br>
 * 修改时间: 2017/8/4 14:16 <br>
 * 修改历史: 2017/8/4 1.00 初始版本
 * ************************************************
 */
public class ImageLoaderUtil {
    /**
     * 圆角图片默认的radius和margin
     */
    public static final int DEFAULT_CORNER_RADIUS = 10;
    public static final int DEFAULT_CORNER_MARGIN = 0;
    /**
     * 默认的图片配置信息
     * setRoundedCorners 会导致图片变小，待修改
     */
    public static DisplayImageOptions defOption = new DisplayImageOptions.Builder().
            setCropType(DisplayImageOptions.CENTER_CROP).
            setAsBitmap(true).
            setImageResPlaceHolder(R.drawable.default_app_logo).
            setImageResError(R.drawable.default_app_logo).
            setDiskCacheStrategy(DisplayImageOptions.DiskCache.SOURCE).
            setRoundedCorners(true).
            setRadius(10).
            build();
    /**
     * 热门游戏
     */
    public static DisplayImageOptions hotGameOption = new DisplayImageOptions.Builder().
            setCropType(DisplayImageOptions.CENTER_CROP).
            setAsBitmap(true).
            setImageResPlaceHolder(R.drawable.hot_game_bg).
            setImageResError(R.drawable.hot_game_bg).
            setDiskCacheStrategy(DisplayImageOptions.DiskCache.SOURCE).
            setRoundedCorners(true).
            setRadius(7).
            build();
    /**
     * 详情页福利活动卡片小图配置信息
     */
    public static DisplayImageOptions benefitOption = new DisplayImageOptions.Builder().
            setCropType(DisplayImageOptions.CENTER_CROP).
            setAsBitmap(true).
            setImageResPlaceHolder(R.drawable.default_app_logo).
            setImageResError(R.drawable.default_app_logo).
            setDiskCacheStrategy(DisplayImageOptions.DiskCache.SOURCE).
            setRoundedCorners(false).
            build();

    /**
     * 高斯模糊配置信息
     */
    public static DisplayImageOptions blurOption = new DisplayImageOptions.Builder().
            setCropType(DisplayImageOptions.CENTER_CROP).
            setAsBitmap(true).
            setImageResPlaceHolder(R.drawable.default_app_logo).
            setImageResError(R.drawable.default_app_logo).
            setDiskCacheStrategy(DisplayImageOptions.DiskCache.SOURCE).
            setBlur(true).
            build();
    /**
     * 缩略图的图片配置option
     */
    public static DisplayImageOptions thumbnailImageOptions = new DisplayImageOptions.Builder().
            setCropType(DisplayImageOptions.CENTER_CROP).
            setAsBitmap(true).
            setImageResPlaceHolder(R.drawable.thumbnail_default_logo).
            setImageResError(R.drawable.thumbnail_default_logo).
            setDiskCacheStrategy(DisplayImageOptions.DiskCache.SOURCE).
            setRoundedCorners(true).
            setRadius(4).
            build();

    /**
     * 详情页卡片福利大图图片配置option
     */
    public static DisplayImageOptions benefitImageOptions = new DisplayImageOptions.Builder().
            setCropType(DisplayImageOptions.CENTER_CROP).
            setAsBitmap(true).
            setImageResPlaceHolder(R.drawable.benefit_default_logo).
            setImageResError(R.drawable.benefit_default_logo).
            setDiskCacheStrategy(DisplayImageOptions.DiskCache.SOURCE).
            setRoundedCorners(true).
            setRadius(4).
            build();

    /**
     * 海报配置
     */
    public static DisplayImageOptions advImageOptions = new DisplayImageOptions.Builder().
            setCropType(DisplayImageOptions.CENTER_CROP).
            setAsBitmap(true).
            setImageResPlaceHolder(R.drawable.banner_default).
            setImageResError(R.drawable.banner_default).
            setDiskCacheStrategy(DisplayImageOptions.DiskCache.SOURCE).
            setRoundedCorners(true).
            setRadius(12).
            build();

    public static DisplayImageOptions rectImageOptions = new DisplayImageOptions.Builder().
            setCropType(DisplayImageOptions.CENTER_CROP).
            setAsBitmap(true).
            setDiskCacheStrategy(DisplayImageOptions.DiskCache.SOURCE).
            build();

    /**
     * 通用的图片加载调用方法:可以是网络图，本地图片，本地资源
     *
     * @param context
     * @param objUrl
     * @param view
     * @param option
     */
    public static void loadImage(Context context, Object objUrl, ImageView view, DisplayImageOptions option) {
        if (null == option) {
            option = defOption;
        }
        try {
            GenericRequestBuilder builder = null;
            //gif类型
            if (option.isAsGif()) {
                GifRequestBuilder request = Glide.with(context).load(objUrl).asGif();
                if (option.getCropType() == DisplayImageOptions.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            } else if (option.isAsBitmap()) {
                BitmapRequestBuilder request = Glide.with(context).load(objUrl).asBitmap();
                if (option.getCropType() == DisplayImageOptions.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                if (option.isRoundedCorners()) {
                    //圆角图片，获取radius和margin
                    int radius = option.getRadius() <= 0 ? DEFAULT_CORNER_RADIUS : option.getRadius();
                    int margin = option.getMargin() <= 0 ? DEFAULT_CORNER_MARGIN : option.getMargin();
                    request.transform(new RoundedCornersTransformation(context, radius, margin, option.getCornerType()));
                } else if (option.isCropCircle()) {
                    //圆形图片
                    request.transform(new CropCircleTransformation(context));
                } else if (option.isGrayscale()) {
                    //图片灰度
                    request.transform(new GrayscaleTransformation(context));
                } else if (option.isBlur()) {
                    //高斯模糊
                    request.transform(new BlurTransformation(context, 15, 1));
                }
                builder = request;
            } else if (option.isCrossFade()) {
                // 渐入渐出动画
                DrawableRequestBuilder request = Glide.with(context).load(objUrl).crossFade();
                if (option.getCropType() == DisplayImageOptions.CENTER_CROP) {
                    request.centerCrop();
                } else {
                    request.fitCenter();
                }
                builder = request;
            }
            //缓存设置
            builder.diskCacheStrategy(option.getDiskCacheStrategy().getStrategy()).
                    skipMemoryCache(option.isSkipMemoryCache());
            //缩略比例
            if (option.getThumbnail() > 0.0f) {
                builder.thumbnail(option.getThumbnail());
            }
            //错误资源图
            if (null != option.getImageResError()) {
                builder.error(option.getImageResError());
            } else if (null != option.getImageError()) {
                builder.error(option.getImageError());
            }
            //默认占位图
            if (null != option.getImageResPlaceHolder()) {
                builder.placeholder(option.getImageResPlaceHolder());
            } else if (null != option.getImagePlaceHolder()) {
                builder.placeholder(option.getImagePlaceHolder());
            }
            //图片最终尺寸
            if (null != option.getSize()) {
                builder.override(option.getSize().getWidth(), option.getSize().getHeight());
            }
            if (null != option.getThumbnailUrl()) {
                BitmapRequestBuilder thumbnailRequest = Glide.with(context).load(option.getThumbnailUrl()).asBitmap();
                builder.thumbnail(thumbnailRequest).into(view);
            } else {
                setTargetView(builder, option, view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 同步执行获取bitmap,需要在后台线程执行，默认30s超时
     *
     * @param context 上下文
     * @param url     图片路径，可以是文件路径/URI/URL
     * @param width   bitmap的宽度
     * @param height  bitmap的高度
     * @return 异常时返回null
     */
    @Nullable
    public static Bitmap loadBitmap(Context context, String url, int width, int height) {
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context).load(url).asBitmap().into(width, height).get(30L, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * 设置目标imageview
     *
     * @param request
     * @param option
     * @param view
     */
    private static void setTargetView(GenericRequestBuilder request, DisplayImageOptions option, ImageView view) {
        //set targetView
//        if (null != option.getSimpleTarget()) {
//            request.into(option.getSimpleTarget());
//        } else if (null != option.getViewTarget()) {
//            request.into(option.getViewTarget());
//        } else if (null != option.getNotificationTarget()) {
//            request.into(option.getNotificationTarget());
//        } else if (null != option.getAppWidgetTarget()) {
//            request.into(option.getAppWidgetTarget());
//        } else {
        request.into(view);
//        }
    }

    public static void loadImage(Context mContext, String url, final View view) {
        Glide.with(mContext)
                .load(url)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Drawable drawable = new BitmapDrawable(resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(drawable);//设置背景
                            view.setTag(drawable);
                        }
                    }
                });
    }
}
