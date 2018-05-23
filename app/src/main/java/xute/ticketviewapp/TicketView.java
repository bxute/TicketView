package xute.ticketviewapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class TicketView extends View {

    private static final int CUT_RADIUS = 16;
    private static final int CUT_HEIGHT = 256;
    private static final int SHAPE_CORNER_RADIUS = 6;
    private static final int DASHED_ON_DISTANCE = 10;
    public static final int DASHED_OFF_DISTANCE = 20;
    public static final int QR_PADDING = 24;
    private static final float TEXT_PADDING = 24;
    private static final float TEXT_SIZE = 18;

    private int width;
    private int height;
    private float yInterceptCut;
    private float cutRadius;
    private float shapeRadius;
    private float qrPadding;
    private Paint mPaint;
    private Path mPath;
    private Path linePath;
    private Resources resources;
    private Paint linePaint;
    private Paint textPaint;
    private Bitmap qrBitmap;
    private float textStartY;
    private float textSize;

    public TicketView(Context context) {
        super(context);
        init(context);
    }

    public TicketView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TicketView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        resources = context.getResources();

        yInterceptCut = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,CUT_HEIGHT,resources.getDisplayMetrics());
        cutRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,CUT_RADIUS,resources.getDisplayMetrics());
        shapeRadius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,SHAPE_CORNER_RADIUS,resources.getDisplayMetrics());
        qrPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,QR_PADDING,resources.getDisplayMetrics());
       // textStartX = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,TEXT_PADDING,resources.getDisplayMetrics());
        textStartY = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,TEXT_PADDING,resources.getDisplayMetrics()) * 2 + yInterceptCut;
        textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,TEXT_SIZE,resources.getDisplayMetrics());

        mPaint = new Paint();
        linePaint = new Paint();
        textPaint = new Paint();

        mPaint.setColor(Color.parseColor("#fefefe"));
        mPaint.setAntiAlias(true);

        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(4);
        linePaint.setPathEffect(new DashPathEffect(new float[]{DASHED_ON_DISTANCE,DASHED_OFF_DISTANCE},0));
        linePaint.setColor(Color.parseColor("#aaaaaa"));

        textPaint.setColor(Color.parseColor("#616161"));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        mPath = new Path();
        linePath = new Path();
        qrBitmap  = BitmapFactory.decodeResource(resources,R.drawable.qr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //set clippings
        //add left cut
        mPath.addCircle(0,yInterceptCut,cutRadius, Path.Direction.CCW);
        //add right cut
        mPath.addCircle(width,yInterceptCut,cutRadius, Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.DIFFERENCE);

        canvas.drawRoundRect(0,0,width,height,shapeRadius,shapeRadius,mPaint);
        //draw dashed line
        linePath.moveTo(cutRadius,yInterceptCut);
        linePath.lineTo(width-cutRadius,yInterceptCut);
        canvas.drawPath(linePath,linePaint);

        //draw qr
        float qrRight = width - qrPadding;
        float qrBottom = qrRight;
        canvas.drawBitmap(qrBitmap,null,new RectF(qrPadding,qrPadding,qrRight,qrBottom),null);

        //draw text
        canvas.drawText("SCAN THE QR CODE",(canvas.getWidth()/2),textStartY , textPaint);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
        invalidate();
    }
}
