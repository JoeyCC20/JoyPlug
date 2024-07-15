package com.joybox.joyplug.host.core.delegate

import android.view.DragEvent
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import com.joybox.joyplug.plugcore.delegate.HostViewDelegate

class DelegatedHostView : HostViewDelegate {
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun onLongClick(v: View?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }
}