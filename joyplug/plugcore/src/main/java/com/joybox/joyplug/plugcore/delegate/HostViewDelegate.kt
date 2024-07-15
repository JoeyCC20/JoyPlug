package com.joybox.joyplug.plugcore.delegate

import android.view.View

interface HostViewDelegate : View.OnClickListener, View.OnLongClickListener, View.OnDragListener,
    View.OnFocusChangeListener, View.OnKeyListener, View.OnLayoutChangeListener,
    View.OnTouchListener {

}