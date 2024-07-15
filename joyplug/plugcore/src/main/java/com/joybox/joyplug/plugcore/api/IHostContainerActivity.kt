package com.joybox.joyplug.plugcore.api

import com.joybox.joyplug.plugcore.component.JoyBaseActivity

interface IHostContainerActivity {
    fun getPluginActivity() : JoyBaseActivity
}