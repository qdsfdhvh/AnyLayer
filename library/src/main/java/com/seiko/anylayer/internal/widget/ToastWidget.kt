package com.seiko.anylayer.internal.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.max
import kotlin.math.min

internal class ToastWidget @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : BaseWidget(context, attrs) {

  val icon = ImageView(context).apply {
    scaleType = ImageView.ScaleType.FIT_CENTER
    layoutParams = LayoutParams(24.dp, 24.dp)
    addView(this)
  }

  val msg = TextView(context).apply {
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    maxLines = 5
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also {
      it.leftMargin = 8.dp
    }
    addView(this)
  }

  init {
    setPadding(24.dp, 16.dp, 24.dp, 16.dp)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
    var maxWidth = (parentWidth / 1.2).toInt()

    icon.autoMeasure()
    msg.let {
      it.measure(
        (maxWidth - paddingStart - paddingEnd - icon.measuredWidth - it.leftMargin).toAtMostMeasureSpec(),
        it.defaultHeightMeasureSpec(this)
      )
    }

    maxWidth = min(
      maxWidth,
      (msg.measuredWidth + msg.leftMargin + icon.measuredWidth + paddingStart + paddingEnd)
    )
    val maxHeight = max(
      icon.measureHeightWithMargins, msg.measureHeightWithMargins
    ) + paddingTop + paddingBottom
    setMeasuredDimension(maxWidth, maxHeight)
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    icon.layout(paddingLeft, (measuredHeight - icon.measuredHeight) / 2)
    msg.layout(icon.right + msg.leftMargin, (measuredHeight - msg.measuredHeight) / 2)
  }
}