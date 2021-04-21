package com.seiko.anylayer

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView

internal fun Activity.rootLayout(): FrameLayout {
  return findViewById(android.R.id.content)
}

internal fun Activity.decorView(): ViewGroup {
  return window.decorView as ViewGroup
}

internal val Context.statusBarHeight: Int
  get() {
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
      return resources.getDimensionPixelSize(resourceId)
    }
    return 0
  }

internal fun ImageView.trySetImageResource(resId: Int) {
  if (resId != 0) {
    setImageResource(resId)
  }
}