package com.joybox.joyplug.view_plugin_sample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joybox.joyplug.plugcore.component.JoyBaseView
import com.joybox.joyplug.plugcore.component.PluginIntent

class SampleView(private var context : Context) : JoyBaseView(context) {

    override fun createContentView(parent: ViewGroup): View? {
        try {
            val contentView = LayoutInflater.from(context).inflate(R.layout.view_sample, parent)
            contentView.findViewById<View>(R.id.btn_plugin_action).setOnClickListener {
                val intent = PluginIntent("com.joybox.joyplug.activity_plugin_sample.MainActivity")
                context.startActivity(intent)
            }
            contentView.findViewById<View>(R.id.btn_host_action).setOnClickListener {
                getHostViewDelegate()?.onClick(it)
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return null
    }
}