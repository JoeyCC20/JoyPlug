package com.joybox.joyplug.plugcore

import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.app.Application.OnProvideAssistDataListener
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import com.joybox.joyplug.plugcore.api.IHostContainerActivity
import com.joybox.joyplug.plugcore.component.JoyBaseActivity
import com.joybox.joyplug.plugcore.delegate.HostApplicationDelegate

class PluginApplication : JoyContext(null, 0) {
    private lateinit var mHostApplication : HostApplicationDelegate

    // ------------------ Internal API ------------------
    fun attachPluginContext(context: JoyContext) {
        cloneFrom(context)
    }

    fun setHostApplicationDelegate(delegate: HostApplicationDelegate) {
        mHostApplication = delegate
    }

    // ------------------ Public API ------------------
    /**
     * 移交给宿主处理
     */
    override fun registerComponentCallbacks(callback: ComponentCallbacks?) {
        mHostApplication.registerComponentCallbacks(callback)
    }

    /**
     * 移交给宿主处理
     */
    override fun unregisterComponentCallbacks(callback: ComponentCallbacks?) {
        mHostApplication.unregisterComponentCallbacks(callback)
    }

    /**
     * 移交给宿主处理
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun registerOnProvideAssistDataListener(callback: OnProvideAssistDataListener?) {
        mHostApplication.registerOnProvideAssistDataListener(callback)
    }

    /**
     * 移交给宿主处理
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    fun unregisterOnProvideAssistDataListener(callback: OnProvideAssistDataListener?) {
        mHostApplication.unregisterOnProvideAssistDataListener(callback)
    }

    @CallSuper
    fun onConfigurationChanged(newConfig: Configuration) {
        mHostApplication.onConfigurationChanged(newConfig)
    }

    /** 不会主动调用 */
    @CallSuper
    fun onLowMemory() {
    }

    /** 不会主动调用 */
    @CallSuper
    fun onTrimMemory(level: Int) {

    }

    fun registerActivityLifecycleCallbacks(callback: PluginActivityLifecycleCallbacks?) {
        callback?:return
        val wrap = PluginActivityLifecycleCallbacksWrap(callback)
        mHostApplication.registerActivityLifecycleCallbacks(wrap)
    }

    fun unregisterActivityLifecycleCallbacks(callback: PluginActivityLifecycleCallbacks?) {
        callback?:return
        // 通过重写equal来识别宿主缓存里的callback
        val wrap = PluginActivityLifecycleCallbacksWrap(callback)
        mHostApplication.unregisterActivityLifecycleCallbacks(wrap)
    }

    companion object {
        @RequiresApi(Build.VERSION_CODES.P)
        fun getProcessName(): String {
            return Application.getProcessName()
        }
    }


    interface PluginActivityLifecycleCallbacks {
        fun onActivityCreated(activity: JoyBaseActivity, savedInstanceState: Bundle?);

        fun onActivityStarted(activity: JoyBaseActivity);

        fun onActivityResumed(activity: JoyBaseActivity);

        fun onActivityPaused(activity: JoyBaseActivity);

        fun onActivityStopped(activity: JoyBaseActivity);

        fun onActivitySaveInstanceState(activity: JoyBaseActivity, outState: Bundle);

        fun onActivityDestroyed(activity: JoyBaseActivity);
    }

    class PluginActivityLifecycleCallbacksWrap(private val pluginActivityLifecycleCallbacks: PluginActivityLifecycleCallbacks) :
        Application.ActivityLifecycleCallbacks {

        private fun transform(activity: Activity, block: (JoyBaseActivity) -> Unit) {
            if (activity is IHostContainerActivity) {
                val pluginActivity = (activity as IHostContainerActivity).getPluginActivity()
                block.invoke(pluginActivity)
            }
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            transform(activity) {
                pluginActivityLifecycleCallbacks.onActivityCreated(it, savedInstanceState)
            }
        }

        override fun onActivityStarted(activity: Activity) {
            transform(activity) {
                pluginActivityLifecycleCallbacks.onActivityStarted(it)
            }
        }

        override fun onActivityResumed(activity: Activity) {
            transform(activity) {
                pluginActivityLifecycleCallbacks.onActivityResumed(it)
            }
        }

        override fun onActivityPaused(activity: Activity) {
            transform(activity) {
                pluginActivityLifecycleCallbacks.onActivityPaused(it)
            }
        }

        override fun onActivityStopped(activity: Activity) {
            transform(activity) {
                pluginActivityLifecycleCallbacks.onActivityStopped(it)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            transform(activity) {
                pluginActivityLifecycleCallbacks.onActivitySaveInstanceState(it, outState)
            }
        }

        override fun onActivityDestroyed(activity: Activity) {
            transform(activity) {
                pluginActivityLifecycleCallbacks.onActivityDestroyed(it)
            }
        }

        override fun equals(other: Any?): Boolean {
            if (other is PluginActivityLifecycleCallbacksWrap) {
                val otherWrap = other as PluginActivityLifecycleCallbacksWrap
                return pluginActivityLifecycleCallbacks == otherWrap.pluginActivityLifecycleCallbacks
            }

            return super.equals(other)
        }

        override fun hashCode(): Int {
            return pluginActivityLifecycleCallbacks.hashCode()
        }
    }

}