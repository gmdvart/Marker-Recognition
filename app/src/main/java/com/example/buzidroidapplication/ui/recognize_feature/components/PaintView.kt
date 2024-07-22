package com.example.buzidroidapplication.ui.recognize_feature.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class PaintView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private var currentX = 0f
    private var currentY = 0f
    private lateinit var currentPath: Path

    private val paintBrush = Paint().apply {
        isAntiAlias = Defaults.IS_ANTI_ALIAS
        isDither = Defaults.IS_DITHER
        color = Defaults.PAINT_BRUSH_COLOR
        style = Defaults.STYLE
        strokeWidth = Defaults.STROKE_WIDTH
        strokeJoin = Defaults.STROKE_JOIN
        strokeCap = Defaults.STROKE_CAP
        alpha = Defaults.ALPHA
    }

    private val paths = mutableListOf<Path>()
    private lateinit var globalCanvas: Canvas
    private lateinit var bitmap: Bitmap
    private val bitmapPaintBrush = Paint(Paint.DITHER_FLAG)

    fun create(width: Int, height: Int) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        globalCanvas = Canvas(bitmap)
    }

    fun undo() {
        if (paths.isNotEmpty()) {
            paths.removeLast()
            invalidate()
        }
    }

    fun clear() {
        if (paths.isNotEmpty()) {
            paths.clear()
            invalidate()
        }
    }

    fun save(): Bitmap = bitmap

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        globalCanvas.drawColor(Defaults.CANVAS_BACKGROUND_COLOR)

        for (path in paths)
            globalCanvas.drawPath(path, paintBrush)

        canvas.drawBitmap(bitmap, 0f, 0f, bitmapPaintBrush)
        canvas.restore()
    }

    private fun touchDown(x: Float, y: Float) {
        currentPath = Path()
        paths.add(currentPath)

        currentPath.reset()
        currentPath.moveTo(x, y)

        currentX = x
        currentY = y
    }

    private fun touchMove(x: Float, y: Float) {
        val deltaX = abs(x - currentX)
        val deltaY = abs(y - currentY)

        if (deltaX >= Defaults.TOUCH_TOLERANCE || deltaY >= Defaults.TOUCH_TOLERANCE) {
            currentPath.quadTo(currentX, currentY, (currentX + x) / 2, (currentY + y) / 2)
            currentX = x
            currentY = y

            invalidate()
        }
    }

    private fun touchUp() {
        currentPath.lineTo(currentX, currentY)
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false

        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> { touchDown(x, y) }
            MotionEvent.ACTION_MOVE -> { touchMove(x, y) }
            MotionEvent.ACTION_UP -> { touchUp() }
        }

        return true
    }

    object Defaults {
        const val TOUCH_TOLERANCE = 4f
        const val IS_ANTI_ALIAS = true
        const val IS_DITHER = true
        const val PAINT_BRUSH_COLOR = Color.BLACK
        const val CANVAS_BACKGROUND_COLOR = Color.WHITE
        const val STROKE_WIDTH = 16f
        const val ALPHA = 0xFF
        val STYLE = Paint.Style.STROKE
        val STROKE_JOIN = Paint.Join.ROUND
        val STROKE_CAP = Paint.Cap.ROUND
    }
}