package com.seiko.anylayer

import android.view.View

internal typealias OnClickListener = (layer: Layer, view: View) -> Unit

internal typealias OnLongClickListener = (layer: Layer, view: View) -> Boolean
