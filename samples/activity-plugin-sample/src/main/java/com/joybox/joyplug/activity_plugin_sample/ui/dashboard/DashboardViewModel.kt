package com.joybox.joyplug.activity_plugin_sample.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is [Plugin] dashboard Fragment"
    }
    val text: LiveData<String> = _text
}