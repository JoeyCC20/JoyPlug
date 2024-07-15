package com.joybox.joyplug.host.core.loader.dao

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.joybox.joyplug.host.core.loader.PluginPriority

@Entity(tableName = "plugins")
open class PluginEntity() : Cloneable, Parcelable {
    @PrimaryKey
    lateinit var mPackageName: String

    @ColumnInfo(name = "version")
    var mVersion : Int = 0

    @ColumnInfo(name = "priority")
    var mPriority : Int = PluginPriority.MEDIUM.ordinal

    @ColumnInfo(name = "entryClass")
    lateinit var mEntryClazz : String

    @ColumnInfo(name = "baseDir")
    lateinit var mBaseDir : String

    @ColumnInfo(name = "apkDir")
    lateinit var mApkDir : String

    @ColumnInfo(name = "odexDir")
    lateinit var mOdexDir : String

    @ColumnInfo(name = "appDir")
    lateinit var mAppDir: String

    @ColumnInfo(name = "dataDir")
    lateinit var mDataDir: String

    @ColumnInfo(name = "libDir")
    lateinit var mLibDir: String

    @ColumnInfo(name = "timestamp")
    public var mTimestamp : Long = 0

    constructor(parcel: Parcel) : this() {
        mPackageName = parcel.readString()!!
        mVersion = parcel.readInt()
        mPriority = parcel.readInt()
        mEntryClazz = parcel.readString()!!
        mBaseDir = parcel.readString()!!
        mApkDir = parcel.readString()!!
        mOdexDir = parcel.readString()!!
        mAppDir = parcel.readString()!!
        mDataDir = parcel.readString()!!
        mLibDir = parcel.readString()!!
        mTimestamp = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(mPackageName)
        parcel.writeInt(mVersion)
        parcel.writeInt(mPriority)
        parcel.writeString(mEntryClazz)
        parcel.writeString(mBaseDir)
        parcel.writeString(mApkDir)
        parcel.writeString(mOdexDir)
        parcel.writeString(mAppDir)
        parcel.writeString(mDataDir)
        parcel.writeString(mLibDir)
        parcel.writeLong(mTimestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PluginEntity> {
        override fun createFromParcel(parcel: Parcel): PluginEntity {
            return PluginEntity(parcel)
        }

        override fun newArray(size: Int): Array<PluginEntity?> {
            return arrayOfNulls(size)
        }
    }
}