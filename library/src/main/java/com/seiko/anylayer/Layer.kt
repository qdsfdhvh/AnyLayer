package com.seiko.anylayer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnPreDrawListener
import com.seiko.anylayer.internal.anim.AnimatorHelper

abstract class Layer(
  anyLayer: AnyLayer,
  protected val parent: ViewGroup
) : AnyLayer by anyLayer {

  protected val context: Context get() = parent.context
  private val viewTreeObserver get() = parent.viewTreeObserver

  private lateinit var viewHolder: ViewHolder
  protected val child: View get() = viewHolder.child

  private val viewManager = ViewManager()
  fun isShown() = viewManager.isAttached

  private val animHelper by lazy(LazyThreadSafetyMode.NONE) {
    AnimatorHelper(
      createInAnimFactory = { onCreateInAnimator(child) },
      createOutAnimFactory = { onCreateOutAnimator(child) }
    )
  }

  fun show(withAnim: Boolean = true) {
    handleShow(withAnim)
  }

  fun dismiss(withAnim: Boolean = true) {
    handleDismiss(withAnim)
  }

  private fun handleShow(withAnim: Boolean) {
    if (isShown()) {
      if (animHelper.isOutAnimRunning) {
        animHelper.startAnimatorIn(withAnim) { onResume() }
      }
      return
    }
    onCreate()
    onStart()
    child.visibility = View.VISIBLE
    viewTreeObserver.addOnPreDrawListener(
      object : OnPreDrawListener {
        override fun onPreDraw(): Boolean {
          if (viewTreeObserver.isAlive) {
            viewTreeObserver.removeOnPreDrawListener(this)
          }
          animHelper.startAnimatorIn(withAnim) { onResume() }
          return true
        }
      })
  }

  private fun handleDismiss(withAnim: Boolean) {
    if (!isShown()) return
    if (animHelper.isOutAnimRunning) return
    onPause()
    animHelper.startAnimatorOut(withAnim) { withOutCancel ->
      child.visibility = View.INVISIBLE
      if (withOutCancel) {
        // 动画执行结束后不能直接removeView，要在下一个dispatchDraw周期移除
        // 否则会崩溃，因为viewGroup的childCount没有来得及-1，获取到的view为空
        parent.post {
          onStop()
          onDestroy()
        }
      } else {
        onStop()
        onDestroy()
      }
    }
  }

  protected fun post(runnable: Runnable) {
    child.post(runnable)
  }

  protected open fun onCreate() {
    viewManager.parent = parent
    viewHolder = onCreateViewHolder(parent)
    viewManager.child = child
  }

  protected open fun onStart() {
    viewManager.attach()
  }

  protected open fun onResume() {

  }

  protected open fun onPause() {

  }

  protected open fun onStop() {
    viewManager.detach()
  }

  protected open fun onDestroy() {
    viewManager.parent = null
    viewManager.child = null
  }

  protected abstract fun onCreateViewHolder(parent: ViewGroup): ViewHolder

  abstract class ViewHolder(val child: View)

  protected val Int.dp: Int
    get() = (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
