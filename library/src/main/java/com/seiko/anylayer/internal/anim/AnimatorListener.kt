package com.seiko.anylayer.internal.anim

import android.animation.Animator

internal open class AnimatorListener : Animator.AnimatorListener {
  private var canceled = false

  override fun onAnimationStart(animation: Animator?) {}

  override fun onAnimationEnd(animation: Animator?) {
    if (!canceled) {
      onAnimationEndWithoutCancel(animation)
    }
  }

  override fun onAnimationCancel(animation: Animator?) {
    canceled = true
  }

  override fun onAnimationRepeat(animation: Animator?) {}

  open fun onAnimationEndWithoutCancel(animation: Animator?) {}
}