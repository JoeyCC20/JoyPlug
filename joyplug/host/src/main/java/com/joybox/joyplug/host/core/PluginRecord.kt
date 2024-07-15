package com.joybox.joyplug.host.core

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import com.joybox.joyplug.host.core.loader.dao.PluginEntity
import com.joybox.joyplug.utils.logd

class PluginRecord() : PluginEntity(), Parcelable {

    constructor(pluginEntity: PluginEntity) : this() {
        cloneFrom(pluginEntity)
    }

    fun cloneFrom(pluginEntity: PluginEntity) {
        val superFiledList = PluginEntity::class.java.declaredFields

        for (superFiled in superFiledList) {
            superFiled.isAccessible = true
            superFiled.set(this, superFiled.get(pluginEntity))
        }
    }

    lateinit var mResources: Resources

    lateinit var mClassLoader: ClassLoader

    lateinit var mApplication: Application

    lateinit var mApplicationInfo: ApplicationInfo

    constructor(parcel: Parcel) : this() {
        mApplicationInfo = parcel.readParcelable(ApplicationInfo::class.java.classLoader)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(mApplicationInfo, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PluginRecord> {
        override fun createFromParcel(parcel: Parcel): PluginRecord {
            return PluginRecord(parcel)
        }

        override fun newArray(size: Int): Array<PluginRecord?> {
            return arrayOfNulls(size)
        }
    }
}