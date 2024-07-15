package com.joybox.joyplug.host.core.loader

data class ApkRecord(var packageName : String,
                     var version : Int,
                     var priority : PluginPriority,
                     var dexPath : String,
                     var entryClazz : String,
                     var timestamp : Long) {

}