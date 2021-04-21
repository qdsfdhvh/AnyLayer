package com.seiko.anylayer

import android.animation.Animator
import android.view.View

interface AnyLayer {

  fun onCreateInAnimator(view: View): Animator?

  fun onCreateOutAnimator(view: View): Animator?

  companion object : AnyLayer {

    override fun onCreateInAnimator(view: View): Animator? {
      return null
    }

    override fun onCreateOutAnimator(view: View): Animator? {
      return null
    }
  }
}