package com.seiko.anylayer

import android.animation.Animator
import android.app.Activity
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.seiko.anylayer.internal.anim.AnimatorUtils
import com.seiko.anylayer.internal.widget.ToastWidget

fun AnyLayer.toast(activity: Activity) =
  ToastLayer(this, activity.rootLayout())

class ToastLayer(
  anyLayer: AnyLayer,
  parent: ViewGroup
) : DurationLayoutLayer<ToastLayer>(anyLayer, parent) {

  private var iconId = 0
  private var message: CharSequence = ""
  private var textColor: Int = Color.DKGRAY

  fun icon(@DrawableRes icon: Int) = apply {
    this.iconId = icon
  }

  fun message(@StringRes messageId: Int) = apply {
    this.message = context.getString(messageId)
  }

  fun message(message: CharSequence) = apply {
    this.message = message
  }

  fun textColor(@ColorInt color: Int) = apply {
    this.textColor = color
  }

  fun textColorRes(@ColorRes colorId: Int) = apply {
    this.textColor = ContextCompat.getColor(context, colorId)
  }

  override fun onCreateInAnimator(view: View): Animator {
    var animator = super.onCreateInAnimator(view)
    if (animator == null) {
      animator = AnimatorUtils.createZoomAlphaInAnim(view)
    }
    return animator
  }

  override fun onCreateOutAnimator(view: View): Animator {
    var animator = super.onCreateOutAnimator(view)
    if (animator == null) {
      animator = AnimatorUtils.createZoomAlphaOutAnim(view)
    }
    return animator
  }

  override fun onCreate() {
    super.onCreate()
    removeOtherView()
  }

  override fun onCreateViewHolder(parent: ViewGroup): Layer.ViewHolder {
    val view = ToastWidget(context).also {
      it.background = this@ToastLayer.background
      it.icon.trySetImageResource(iconId)
      it.msg.setTextColor(textColor)
      it.msg.text = message
    }
    view.layoutParams = generateLayoutParams()
    return ViewHolder(view)
  }

  class ViewHolder(view: View) : Layer.ViewHolder(view)
}