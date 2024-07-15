package com.joybox.joyplug.host_sample.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Host Page.\nClick button below to jump to [Plugin Activity]."
    }
    private val _btn = MutableLiveData<String>().apply {
        value = "Jump to Plugin Activity"
    }
    val text: LiveData<String> = _text
    val btn: LiveData<String> = _btn
}