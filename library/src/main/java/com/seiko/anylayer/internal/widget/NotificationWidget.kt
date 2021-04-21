package com.seiko.anylayer.internal.widget

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.seiko.anylayer.R

internal class NotificationWidget @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : BaseWidget(context, attrs) {

  val icon = ImageView(context).apply {
    layoutParams = LayoutParams(24.dp, 24.dp)
    addView(this)
  }

  val label = TextView(context).apply {
    setTextColor(ContextCompat.getColor(context, R.color.anylayer_notification_label_text_color))
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
    maxLines = 1
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also {
      it.leftMargin = 8.dp
    }
    addView(this)
  }

  val contentTime = TextView(context).apply {
    setTextColor(ContextCompat.getColor(context, R.color.anylayer_notification_time_text_color))
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
    maxLines = 1
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also {
      it.leftMargin = 8.dp
    }
    addView(this)
  }

  val title = TextView(context).apply {
    setTextColor(ContextCompat.getColor(context, R.color.anylayer_notification_title_text_color))
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f)
    maxLines = 1
    paint.isFakeBoldText = true
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also {
      it.topMargin = 6.dp
    }
    addView(this)
  }

  val desc = TextView(context).apply {
    setTextColor(ContextCompat.getColor(context, R.color.anylayer_notification_desc_text_color))
    setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f)
    maxLines = 3
    layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).also {
      it.topMargin = 6.dp
    }
    addView(this)
  }

  init {
    setPadding(16.dp, 16.dp, 16.dp, 16.dp)
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    icon.autoMeasure()
    label.autoMeasure()
    contentTime.autoMeasure()
    title.autoMeasure()
    desc.measure(
      widthMeasureSpec,
      (measuredHeight
        - label.measureHeightWithMargins
        - title.measureHeightWithMargins
        - desc.measureHeightWithMargins
        ).toAtMostMeasureSpec()
    )

    val maxHeight = (label.measureHeightWithMargins
      + title.measureHeightWithMargins
      + desc.measureHeightWithMargins
      + paddingTop
      + paddingBottom)
    setMeasuredDimension(
      widthMeasureSpec,
      maxHeight.toExactlyMeasureSpec()
    )
  }

  override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    icon.layout(paddingStart, paddingTop)
    label.layout(icon.right + label.leftMargin, paddingTop)
    contentTime.layout(label.right + contentTime.leftMargin, paddingTop)
    title.layout(paddingStart, label.bottom + title.topMargin)
    desc.layout(paddingStart, title.bottom + desc.topMargin)
  }
}