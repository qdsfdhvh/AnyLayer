package com.seiko.anylayer

import android.view.ViewGroup

@Suppress("UNCHECKED_CAST")
abstract class DurationLayoutLayer<T : Layer>(
  anyLayer: AnyLayer,
  parent: ViewGroup
) : LayoutLayer<T>(anyLayer, parent) {

  protected var showDuration: Long = 2500L

  protected val dismissRunnable = {
    if (isShown()) dismiss()
  }

  fun showDuration(duration: Long) = apply {
    showDuration = duration
  }

  override fun onResume() {
    super.onResume()
    postDelayDismiss()
  }

  override fun onPause() {
    super.onPause()
    removeDelayDismiss()
  }

  protected fun postDelayDismiss() {
    removeDelayDismiss()
    if (showDuration > 0) {
      child.postDelayed(dismissRunnable, showDuration)
    }
  }

  protected fun removeDelayDismiss() {
    child.removeCallbacks(dismissRunnable)
  }

}