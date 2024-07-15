package com.joybox.joyplug.activity_plugin_sample.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is [Plugin] home Fragment"
    }
    val text: LiveData<String> = _text
}