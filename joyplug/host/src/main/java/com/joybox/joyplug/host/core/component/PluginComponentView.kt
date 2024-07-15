package com.joybox.joyplug.host.core.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.joybox.joyplug.host.R
import com.joybox.joyplug.host.core.loader.PluginLoader
import com.joybox.joyplug.host.core.tokens.PluginViewToken
import com.joybox.joyplug.plugcore.delegate.HostViewDelegate
import com.joybox.joyplug.utils.loge
import com.joybox.joyplug.utils.runInUIThread

class PluginComponentView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    var mHostViewDelegate : EventDelegate? = null

    init {
        val styledAttr = context.obtainStyledAttributes(attrs, R.styleable.PluginComponentView)
        var packageName = styledAttr.getString(R.styleable.PluginComponentView_packageName)
        val targetView = styledAttr.getString(R.styleable.PluginComponentView_view)
        if (packageName == null && targetView != null) {
            packageName = targetView.substringBeforeLast(".")
        }

        if (packageName != null && targetView != null) {
            PluginLoader.getInstance(context).acquirePlugin(packageName) {
                it ?: return@acquirePlugin

                val toke = PluginViewToken(context, packageName, targetView, it)
                val pluginView = toke.setup()
                pluginView ?: return@acquirePlugin
                pluginView.setHostViewDelegate(InnerHostViewDelegate())

                runInUIThread {
                    val subView = pluginView.getContentView(this)
                    subView ?. let {
                        addView(subView)
                    }
                }
            }
        } else {
            loge("[PluginComponentView] packageName or view not assigned.")
        }
    }

    fun setDelegate(delegate : EventDelegate) {
        this.mHostViewDelegate = delegate
    }

    /**
     * Event delegate for outer who uses this plugin view
     **/
    open class EventDelegate : View.OnClickListener, View.OnLongClickListener, View.OnDragListener,
        View.OnFocusChangeListener, View.OnKeyListener, View.OnLayoutChangeListener,
        View.OnTouchListener {
        override fun onClick(v: View?) {
        }

        override fun onLongClick(v: View?): Boolean {
            return false
        }

        override fun onDrag(v: View?, event: DragEvent?): Boolean {
            return false
        }

        override fun onFocusChange(v: View?, hasFocus: Boolean) {
        }

        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            return false
        }

        override fun onLayoutChange(
            v: View?,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            return false
        }

    }

    /**
     * Delegate for transaction from plugin to host
     */
    inner class InnerHostViewDelegate : HostViewDelegate {
        override fun onClick(v: View?) {
            mHostViewDelegate?.onClick(v)
        }

        override fun onLongClick(v: View?): Boolean {
            return mHostViewDelegate ?. onLongClick(v) ?: false
        }

        override fun onDrag(v: View?, event: DragEvent?): Boolean {
            return mHostViewDelegate ?.onDrag(v, event) ?: false
        }

        override fun onFocusChange(v: View?, hasFocus: Boolean) {
            mHostViewDelegate?.onFocusChange(v, hasFocus)
        }

        override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
            return mHostViewDelegate?.onKey(v, keyCode, event) ?: false
        }

        override fun onLayoutChange(
            v: View?,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            mHostViewDelegate?.onLayoutChange(v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom)
        }

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            return mHostViewDelegate ?. onTouch(v, event) ?: false
        }

    }
}