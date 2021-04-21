package com.seiko.anylayer.internal.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

internal abstract class BaseWidget(
  context: Context,
  attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

  protected fun View.layout(x: Int, y: Int, fromRight: Boolean = false) {
    if (fromRight) {
      layout(this@BaseWidget.measuredWidth - x - measuredWidth, y)
    } else {
      layout(x, y, x + measuredWidth, y + measuredHeight)
    }
  }

  protected fun View.autoMeasure() {
    measure(
      this.defaultWidthMeasureSpec(parentView = this@BaseWidget),
      this.defaultHeightMeasureSpec(parentView = this@BaseWidget)
    )
  }

  protected fun View.defaultWidthMeasureSpec(parentView: ViewGroup): Int {
    return when (layoutParams.width) {
      ViewGroup.LayoutParams.MATCH_PARENT -> (parentView.measuredWidth - paddingStart - paddingEnd).toExactlyMeasureSpec()
      ViewGroup.LayoutParams.WRAP_CONTENT -> (parentView.measuredWidth - paddingStart - paddingEnd).toAtMostMeasureSpec()
      0 -> throw IllegalAccessException("Need special treatment for $this")
      else -> layoutParams.width.toExactlyMeasureSpec()
    }
  }

  protected fun View.defaultHeightMeasureSpec(parentView: ViewGroup): Int {
    return when (layoutParams.height) {
      ViewGroup.LayoutParams.MATCH_PARENT -> (parentView.measuredHeight - paddingTop - paddingBottom).toExactlyMeasureSpec()
      ViewGroup.LayoutParams.WRAP_CONTENT -> (parentView.measuredHeight - paddingTop - paddingBottom).toAtMostMeasureSpec()
      0 -> throw IllegalAccessException("Need special treatment for $this")
      else -> layoutParams.height.toExactlyMeasureSpec()
    }
  }

  protected fun Int.toExactlyMeasureSpec(): Int {
    return MeasureSpec.makeMeasureSpec(this, MeasureSpec.EXACTLY)
  }

  protected fun Int.toAtMostMeasureSpec(): Int {
    return MeasureSpec.makeMeasureSpec(this, MeasureSpec.AT_MOST)
  }

  protected class LayoutParams(width: Int, height: Int) : MarginLayoutParams(width, height)

  protected val View.measureWidthWithMargins get() = (measuredWidth + leftMargin + rightMargin)
  protected val View.measureHeightWithMargins get() = (measuredHeight + topMargin + bottomMargin)

  protected inline val View.leftMargin: Int
    get() = (layoutParams as? LayoutParams)?.leftMargin ?: 0

  protected inline val View.topMargin: Int
    get() = (layoutParams as? LayoutParams)?.topMargin ?: 0

  protected inline val View.rightMargin: Int
    get() = (layoutParams as? LayoutParams)?.rightMargin ?: 0

  protected inline val View.bottomMargin: Int
    get() = (layoutParams as? LayoutParams)?.bottomMargin ?: 0

  protected val Int.dp: Int
    get() = (this * resources.displayMetrics.density + 0.5f).toInt()
}