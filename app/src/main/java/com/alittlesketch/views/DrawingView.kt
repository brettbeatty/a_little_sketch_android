package com.alittlesketch.views


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.alittlesketch.R

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var bitmap: Bitmap? = null
    private var canvas: Canvas? = null
    private val canvas_paint: Paint = Paint(Paint.DITHER_FLAG)
    var erasing = false
        get
        private set
    private val paint: Paint = Paint()
    private val path: Path = Path()

    init {
        paint.color = ContextCompat.getColor(context, R.color.paint_color)
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeWidth = resources.getDimension(R.dimen.brush_size)
        paint.style = Paint.Style.STROKE

        // Gets rid of the black path mask when user switches to eraser
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    fun clear() {
        canvas!!.drawColor(0, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {

        canvas.drawBitmap(bitmap!!, 0f, 0f, canvas_paint)
        canvas.drawPath(path, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> path.moveTo(event.x, event.y)
            MotionEvent.ACTION_MOVE -> path.lineTo(event.x, event.y)
            MotionEvent.ACTION_UP -> {
                path.lineTo(event.x, event.y)
                canvas!!.drawPath(path, paint)
                path.reset()
            }
            else -> return false
        }
        //  redraw view
        invalidate()
        return true
    }

    fun startErasing() {
        erasing = true
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    fun stopErasing() {
        erasing = false
        paint.xfermode = null
    }
}