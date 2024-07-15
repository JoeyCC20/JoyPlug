package com.joybox.joyplug.activity_plugin_sample.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is [Plugin] notifications Fragment"
    }
    val text: LiveData<String> = _text
}