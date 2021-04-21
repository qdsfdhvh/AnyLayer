package com.seiko.anylayer.internal.anim

import android.animation.Animator

internal class AnimatorHelper(
  private val createInAnimFactory: () -> Animator?,
  private val createOutAnimFactory: () -> Animator?
) {

  private var animatorIn: Animator? = null
  private var animatorOut: Animator? = null

  val isInAnimRunning: Boolean get() = animatorIn?.isStarted == true
  val isOutAnimRunning: Boolean get() = animatorOut?.isStarted == true

  fun startAnimatorIn(withAnim: Boolean, onEnd: () -> Unit) {
    if (!withAnim) {
      onEnd()
      return
    }

    animatorIn?.cancel()
    animatorIn = createInAnimFactory()

    if (animatorIn == null) {
      onEnd()
      return
    }

    animatorIn!!.addListener(object : AnimatorListener() {
      override fun onAnimationEnd(animation: Animator?) {
        super.onAnimationEnd(animation)
        animatorIn = null
      }

      override fun onAnimationEndWithoutCancel(animation: Animator?) {
        onEnd()
      }
    })
    animatorIn!!.start()
  }

  fun startAnimatorOut(withAnim: Boolean, onEnd: (withoutCancel: Boolean) -> Unit) {
    if (!withAnim) {
      onEnd(false)
      return
    }

    animatorOut?.cancel()
    animatorOut = createOutAnimFactory()

    if (animatorOut == null) {
      onEnd(false)
      return
    }

    animatorOut!!.addListener(object : AnimatorListener() {
      override fun onAnimationEnd(animation: Animator?) {
        super.onAnimationEnd(animation)
        animatorOut = null
      }

      override fun onAnimationEndWithoutCancel(animation: Animator?) {
        onEnd(true)
      }
    })
    animatorOut!!.start()
  }
}