package com.example.floatwindow.imageutils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * ************************************************ <br>
 * 创建人员: robin <br>
 * 文件描述: Glide图片圆角处理<br>
 * 修改时间: 2017/8/4 17:29 <br>
 * 修改历史: 2017/8/4 1.00 初始版本
 * ************************************************
 */
public class RoundedCornersTransformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;
    private float radius;
    private float margin;
    private float diameter;
    private DisplayImageOptions.CornerType cornerType;

    public RoundedCornersTransformation(Context context, int radius, int margin, DisplayImageOptions.CornerType cornerType) {
        mBitmapPool = Glide.get(context).getBitmapPool();
        this.radius = Resources.getSystem().getDisplayMetrics().density * radius;
        this.margin = Resources.getSystem().getDisplayMetrics().density * margin;
        this.diameter = this.radius * 2;
        this.cornerType = cornerType;
    }


    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        drawRoundRect(canvas, paint, width, height);

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "RoundedTransformation(radius=" + radius + ", margin=" + margin + ")";
    }


    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
        float right = width - margin;
        float bottom = height - margin;

        switch (cornerType) {
            case ALL:
                canvas.drawRoundRect(new RectF(margin, margin, right, bottom), radius, radius, paint);
                break;
            case TOP_LEFT:
                drawTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case TOP_RIGHT:
                drawTopRightRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM_LEFT:
                drawBottomLeftRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM_RIGHT:
                drawBottomRightRoundRect(canvas, paint, right, bottom);
                break;
            case TOP:
                drawTopRoundRect(canvas, paint, right, bottom);
                break;
            case BOTTOM:
                drawBottomRoundRect(canvas, paint, right, bottom);
                break;
            case LEFT:
                drawLeftRoundRect(canvas, paint, right, bottom);
                break;
            case RIGHT:
                drawRightRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_TOP_LEFT:
                drawOtherTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_TOP_RIGHT:
                drawOtherTopRightRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_BOTTOM_LEFT:
                drawOtherBottomLeftRoundRect(canvas, paint, right, bottom);
                break;
            case OTHER_BOTTOM_RIGHT:
                drawOtherBottomRightRoundRect(canvas, paint, right, bottom);
                break;
            case DIAGONAL_FROM_TOP_LEFT:
                drawDiagonalFromTopLeftRoundRect(canvas, paint, right, bottom);
                break;
            case DIAGONAL_FROM_TOP_RIGHT:
                drawDiagonalFromTopRightRoundRect(canvas, paint, right, bottom);
                break;
            default:
                canvas.drawRoundRect(new RectF(margin, margin, right, bottom), radius, radius, paint);
                break;
        }
    }

    private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(margin, margin, margin + diameter, margin + diameter),
                radius, radius, paint);
        canvas.drawRect(new RectF(margin, margin + radius, margin + radius, bottom), paint);
        canvas.drawRect(new RectF(margin + radius, margin, right, bottom), paint);
    }

    private void drawTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - diameter, margin, right, margin + diameter), radius,
                radius, paint);
        canvas.drawRect(new RectF(margin, margin, right - radius, bottom), paint);
        canvas.drawRect(new RectF(right - radius, margin + radius, right, bottom), paint);
    }

    private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(margin, bottom - diameter, margin + diameter, bottom),
                radius, radius, paint);
        canvas.drawRect(new RectF(margin, margin, margin + diameter, bottom - radius), paint);
        canvas.drawRect(new RectF(margin + radius, margin, right, bottom), paint);
    }

    private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - diameter, bottom - diameter, right, bottom), radius,
                radius, paint);
        canvas.drawRect(new RectF(margin, margin, right - radius, bottom), paint);
        canvas.drawRect(new RectF(right - radius, margin, right, bottom - radius), paint);
    }

    private void drawTopRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(margin, margin, right, margin + diameter), radius, radius,
                paint);
        canvas.drawRect(new RectF(margin, margin + radius, right, bottom), paint);
    }

    private void drawBottomRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(margin, bottom - diameter, right, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(margin, margin, right, bottom - radius), paint);
    }

    private void drawLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(margin, margin, margin + diameter, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(margin + radius, margin, right, bottom), paint);
    }

    private void drawRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - diameter, margin, right, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(margin, margin, right - radius, bottom), paint);
    }

    private void drawOtherTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(margin, bottom - diameter, right, bottom), radius, radius,
                paint);
        canvas.drawRoundRect(new RectF(right - diameter, margin, right, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(margin, margin, right - radius, bottom - radius), paint);
    }

    private void drawOtherTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(margin, margin, margin + diameter, bottom), radius, radius,
                paint);
        canvas.drawRoundRect(new RectF(margin, bottom - diameter, right, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(margin + radius, margin, right, bottom - radius), paint);
    }

    private void drawOtherBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
        canvas.drawRoundRect(new RectF(margin, margin, right, margin + diameter), radius, radius,
                paint);
        canvas.drawRoundRect(new RectF(right - diameter, margin, right, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(margin, margin + radius, right - radius, bottom), paint);
    }

    private void drawOtherBottomRightRoundRect(Canvas canvas, Paint paint, float right,
                                               float bottom) {
        canvas.drawRoundRect(new RectF(margin, margin, right, margin + diameter), radius, radius,
                paint);
        canvas.drawRoundRect(new RectF(margin, margin, margin + diameter, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(margin + radius, margin + radius, right, bottom), paint);
    }

    private void drawDiagonalFromTopLeftRoundRect(Canvas canvas, Paint paint, float right,
                                                  float bottom) {
        canvas.drawRoundRect(new RectF(margin, margin, margin + diameter, margin + diameter),
                radius, radius, paint);
        canvas.drawRoundRect(new RectF(right - diameter, bottom - diameter, right, bottom), radius,
                radius, paint);
        canvas.drawRect(new RectF(margin, margin + radius, right - diameter, bottom), paint);
        canvas.drawRect(new RectF(margin + diameter, margin, right, bottom - radius), paint);
    }

    private void drawDiagonalFromTopRightRoundRect(Canvas canvas, Paint paint, float right,
                                                   float bottom) {
        canvas.drawRoundRect(new RectF(right - diameter, margin, right, margin + diameter), radius,
                radius, paint);
        canvas.drawRoundRect(new RectF(margin, bottom - diameter, margin + diameter, bottom),
                radius, radius, paint);
        canvas.drawRect(new RectF(margin, margin, right - radius, bottom - radius), paint);
        canvas.drawRect(new RectF(margin + radius, margin + radius, right, bottom), paint);
    }
}
