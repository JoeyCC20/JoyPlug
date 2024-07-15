package com.joybox.joyplug.plugcore.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.joybox.joyplug.plugcore.delegate.HostViewDelegate

abstract class JoyBaseView(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) {
    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    private var mHostViewDelegate: HostViewDelegate? = null

    fun setHostViewDelegate(hostViewDelegate : HostViewDelegate?) {
        mHostViewDelegate = hostViewDelegate
    }

    fun getHostViewDelegate() : HostViewDelegate? {
        return mHostViewDelegate
    }

    fun getContentView(parent : ViewGroup) : View? {
        return createContentView(parent)
    }

    abstract fun createContentView(parent : ViewGroup) : View?
}