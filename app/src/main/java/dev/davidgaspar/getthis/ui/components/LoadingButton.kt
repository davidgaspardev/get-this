package dev.davidgaspar.getthis.ui.components

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import dev.davidgaspar.getthis.ui.components.utils.ButtonState
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private val valueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 5000
        repeatCount = 0
        addUpdateListener {
            invalidate()
        }
    }

    private var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> valueAnimator.start()
            ButtonState.Completed -> valueAnimator.cancel()
            else -> {}
        }
    }

    init {
        isClickable = true
        setBackgroundColor(0xFF0000)

        setOnClickListener {
            buttonState = ButtonState.Loading
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        setBackground(canvas)
        drawForeground(canvas)
        drawText(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minWidth = paddingLeft + paddingRight + suggestedMinimumWidth
        val width = resolveSizeAndState(minWidth, widthMeasureSpec, 1)
//        val height = resolveSizeAndState(MeasureSpec.getSize(width), heightMeasureSpec, 0)
        val height = (width * 0.16).toInt()

        widthSize = width
        heightSize = height

        Log.d(TAG, "width: $width, height: $height")

        setMeasuredDimension(width, height)
    }

    private fun setBackground(canvas: Canvas) {
        val paint: Paint = Paint().apply {
            color = Color.RED
        }

        canvas.drawRoundRect(
            0f,
            0f,
            widthSize.toFloat(),
            heightSize.toFloat(),
            32f,
            32f,
            paint
        )
    }

    private fun drawForeground(canvas: Canvas) {
        canvas.save()

        val limit = width.toFloat() * valueAnimator.animatedValue as Float

        val path: Path = Path()
        path.addRect(
            0f,
            0f,
            limit,
            heightSize.toFloat(),
            Path.Direction.CW
        )

        canvas.clipPath(path)

        val paint: Paint = Paint().apply {
            color = 0x64FFFFFF
        }

        canvas.drawRoundRect(
            0f,
            0f,
            widthSize.toFloat(),
            heightSize.toFloat(),
            32f,
            32f,
            paint
        )

        canvas.restore()
    }

    private fun drawText(canvas: Canvas) {
        val paint: Paint = Paint().apply {
            color = Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = 48f
        }

        canvas.drawText(
            if (buttonState == ButtonState.Loading) "We are loading" else "Download",
            widthSize / 2f,
            heightSize / 2f,
            paint
        )
    }

    companion object {
        private const val TAG = "LoadingButton"
    }
}