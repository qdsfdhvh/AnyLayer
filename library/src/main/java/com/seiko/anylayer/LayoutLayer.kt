package com.seiko.anylayer

import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat

@Suppress("UNCHECKED_CAST")
abstract class LayoutLayer<T : Layer>(
  anyLayer: AnyLayer,
  parent: ViewGroup
) : Layer(anyLayer, parent) {

  protected var background: Drawable? = null
  protected var alpha: Float = 1f
  protected var gravity: Int = Gravity.CENTER

  protected var leftMargin = 0
  protected var topMargin = 0
  protected var rightMargin = 0
  protected var bottomMargin = 0

  fun background(drawable: Drawable): T {
    this.background = drawable
    return this as T
  }

  fun background(@DrawableRes resId: Int): T {
    this.background = ContextCompat.getDrawable(context, resId)
    return this as T
  }

  fun backgroundColor(@ColorInt color: Int, radius: Int = 8.dp): T {
    this.background = GradientDrawable().apply {
      setColor(color)
      cornerRadii = floatArrayOf(
        radius.toFloat(), radius.toFloat(),
        radius.toFloat(), radius.toFloat(),
        radius.toFloat(), radius.toFloat(),
        radius.toFloat(), radius.toFloat(),
      )
    }
    return this as T
  }

  fun backgroundColorRes(@ColorRes colorId: Int, radius: Int = 8.dp) =
    backgroundColor(ContextCompat.getColor(context, colorId), radius)

  fun alpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float): T {
    this.alpha = alpha
    return this as T
  }

  fun gravity(gravity: Int): T {
    this.gravity = gravity
    return this as T
  }

  fun margin(value: Int) = margin(value, value, value, value)

  fun margin(left: Int, top: Int, right: Int, bottom: Int): T {
    leftMargin = left
    topMargin = top
    rightMargin = right
    bottomMargin = bottom
    return this as T
  }

  protected fun generateLayoutParams(): FrameLayout.LayoutParams {
    return FrameLayout.LayoutParams(
      FrameLayout.LayoutParams.WRAP_CONTENT,
      FrameLayout.LayoutParams.WRAP_CONTENT
    ).also {
      it.gravity = gravity
      it.leftMargin = leftMargin
      it.topMargin = topMargin
      it.rightMargin = rightMargin
      it.bottomMargin = bottomMargin
    }
  }

  protected fun generateLayoutParamsWithoutSetting(): FrameLayout.LayoutParams {
    return FrameLayout.LayoutParams(
      FrameLayout.LayoutParams.WRAP_CONTENT,
      FrameLayout.LayoutParams.WRAP_CONTENT
    )
  }

  override fun onCreate() {
    super.onCreate()
    child.tag = this
  }

  override fun onDestroy() {
    child.tag = null
    super.onDestroy()
  }

  protected fun removeOtherView(withAnim: Boolean = false) {
    var childView: View
    for (i in parent.childCount - 1 downTo 0) {
      childView = parent.getChildAt(i)
      if (childView.tag is Layer) {
        val layer = childView.tag as Layer
        if (layer != this) {
          layer.dismiss(withAnim)
        }
      }
    }
  }
}