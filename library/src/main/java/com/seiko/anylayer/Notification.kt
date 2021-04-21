package com.seiko.anylayer

import android.animation.Animator
import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.seiko.anylayer.internal.anim.AnimatorUtils
import com.seiko.anylayer.internal.widget.NotificationWidget
import com.seiko.anylayer.internal.widget.SwipeLayout

fun AnyLayer.notification(activity: Activity) =
  NotificationLayer(this, activity.decorView())

class NotificationLayer(
  anyLayer: AnyLayer,
  parent: ViewGroup
) : DurationLayoutLayer<NotificationLayer>(anyLayer, parent) {

  private var iconId = 0
  private var label = ""
  private var contentTime = ""
  private var title: CharSequence = ""
  private var description: CharSequence = ""

  private var clickListener: OnClickListener? = null

  init {
    gravity = Gravity.TOP
    margin(8.dp)
    showDuration(5000L)
  }

  fun icon(@DrawableRes icon: Int) = apply {
    this.iconId = icon
  }

  fun label(label: String) = apply {
    this.label = label
  }

  fun contentTime(contentTime: String) = apply {
    this.contentTime = contentTime
  }

  fun title(@StringRes titleId: Int) = apply {
    this.title = context.getString(titleId)
  }

  fun title(title: CharSequence) = apply {
    this.title = title
  }

  fun desc(@StringRes descriptionId: Int) = apply {
    this.description = context.getString(descriptionId)
  }

  fun desc(description: CharSequence) = apply {
    this.description = description
  }

  fun setOnClickListener(listener: OnClickListener?) = apply {
    clickListener = listener
  }

  override fun onCreateInAnimator(view: View): Animator {
    return AnimatorUtils.createTopInAnim(child)
  }

  override fun onCreateOutAnimator(view: View): Animator {
    return AnimatorUtils.createTopOutAnim(child)
  }

  override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
    val view = NotificationWidget(context).also {
      it.background = background
      it.icon.trySetImageResource(iconId)
      it.label.text = label
      it.contentTime.text = contentTime
      it.title.text = title
      it.desc.text = description
      it.setOnClickListener {
        clickListener?.invoke(this, it)
      }
    }

    val container = SwipeLayout(context).apply {
      clipToPadding = false
      setSwipeDirection(SwipeLayout.Direction.TOP or SwipeLayout.Direction.LEFT or SwipeLayout.Direction.RIGHT)
      setOnSwipeListener(object : SwipeLayout.OnSwipeListener {
        override fun onStart(direction: Int, fraction: Float) {
          removeDelayDismiss()
        }

        override fun onEnd(direction: Int, fraction: Float) {
          when(fraction) {
            1f -> parent.post { dismiss(false) }
            0f -> postDelayDismiss()
          }
        }

        override fun onSwiping(direction: Int, fraction: Float) {}
      })
    }

    view.layoutParams = generateLayoutParams()
    container.addView(view)
    container.setPadding(0, context.statusBarHeight, 0, 0)
    container.layoutParams = generateLayoutParamsWithoutSetting()
    return ViewHolder(container)
  }

  class ViewHolder(view: View) : Layer.ViewHolder(view)
}