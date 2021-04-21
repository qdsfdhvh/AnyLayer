package com.seiko.anylayer

import android.view.View
import android.view.ViewGroup

class ViewManager {

  var parent: ViewGroup? = null

  var child: View? = null
    set(value) {
      field = value
      checkChildParent()
    }

  private fun checkChildParent() {
    child?.let { child ->
      val childParent = child.parent as? ViewGroup ?: return
      if (childParent != parent) {
        childParent.removeView(child)
      }
    }
  }

  val isAttached: Boolean
    get() = child?.parent != null

  fun attach() {
    if (!isAttached) {
      onAttach()
    }
  }

  fun detach() {
    if (isAttached) {
      onDetach()
    }
  }

  private fun onAttach() {
    parent?.addView(child)
  }

  private fun onDetach() {
    parent?.removeView(child)
  }
}