package com.joybox.joyplug.plugcore

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater.Factory2
import android.view.View
import com.joybox.joyplug.plugcore.component.JoyBaseActivity
import java.lang.reflect.Constructor

class PluginFactory2(private var mContext : Context) : Factory2 {

    private val sConstructorMap = HashMap<String, Constructor<out View?>>()
    private val mConstructorArgs = arrayOfNulls<Any>(2)
    private val mSystemPrefix = arrayOf("androidx.",
                                        "com.android.",
                                        "android.")
    private val mExcludePrefix = arrayOf("androidx.constraintlayout.widget",
                                        "androidx.appcompat.widget")

    private val mConstructorSignature = arrayOf(
        Context::class.java, AttributeSet::class.java
    )

    private fun verifyClassLoader(constructor: Constructor<out View?>): Boolean {
        val constructorLoader = constructor.declaringClass.getClassLoader()
        var cl: ClassLoader? = mContext.classLoader
        do {
            if (constructorLoader === cl) {
                return true
            }
            cl = cl!!.parent
        } while (cl != null)
        return false
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
//        android.util.Log.i("[JOY]", "[PluginFactroy2] name:$name, context: $context")

        // mExcludePrefix的类， 直接构建
        val excludePrefix = mExcludePrefix.firstOrNull() {
            name.startsWith(it)
        }
        if (excludePrefix == null) {
            for (prefix in mSystemPrefix) {
                if (name.startsWith(prefix) || !name.contains(".")) {
                    return if (context is JoyBaseActivity) {
//                        android.util.Log.i("[JOY]", "[PluginFactroy2] go JoyBaseActivity:$name, class:${context::class.java}")
                        // 执行Plugin的Activity的onCreateView, 主要用于Fragment
                        // FragmentActivity自定义了onCreateView
                        context.onCreateView(parent, name, context, attrs)
                    } else {
//                        android.util.Log.i("[JOY]", "[PluginFactroy2] return null. name:$name")
                        null
                    }
                }
            }
        }

        var constructor: Constructor<out View?>? = sConstructorMap[name]
        if (constructor != null && !verifyClassLoader(constructor)) {
            constructor = null
            sConstructorMap.remove(name)
        }
        var clazz: Class<out View?>? = null

        try {
            if (constructor == null) {
//                android.util.Log.i("[JOY]", "factory context: $mContext")
//                android.util.Log.i("[JOY]", "factory classLoader: ${mContext.classLoader}")
                // Class not found in the cache, see if it's real, and try to add it
                clazz = mContext.classLoader.loadClass(name).asSubclass(View::class.java)
                constructor = clazz.getConstructor(*mConstructorSignature)
                constructor.isAccessible = true
                sConstructorMap[name] = constructor
            }
            val lastContext: Any? = mConstructorArgs[0]
            if (mConstructorArgs[0] == null) {
                // Fill in the context if not already within inflation.
                mConstructorArgs[0] = context
            }
            mConstructorArgs[1] = attrs

            val view = constructor!!.newInstance(*mConstructorArgs)!!
            mConstructorArgs[0] = lastContext
            return view
        } catch (e: Exception) {
            throw e
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }
}