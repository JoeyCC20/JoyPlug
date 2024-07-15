package com.joybox.joyplug.plugcore

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View

class PluginLayoutInflater : LayoutInflater {

    private val sClassPrefixList = arrayOf(
        "android.widget.",
        "android.webkit.",
        "android.app."
    )

    /**
     * Instead of instantiating directly, you should retrieve an instance
     * through [Context.getSystemService]
     *
     * @param context The Context in which in which to find resources and other
     * application-specific things.
     *
     * @see Context.getSystemService
     */
    constructor(context: Context?) :super(context){
//        setFactory2(PluginFactory2(this.context))
    }

    constructor(original: LayoutInflater?, newContext: Context?) :super(original, newContext){
//        setFactory2(PluginFactory2(context, factory2))
    }

    /** Override onCreateView to instantiate names that correspond to the
     * widgets known to the Widget factory. If we don't find a match,
     * call through to our super class.
     */
    @Throws(ClassNotFoundException::class)
    override fun onCreateView(name: String?, attrs: AttributeSet?): View {
        for (prefix in sClassPrefixList) {
            try {
                val view = createView(name, prefix, attrs)
                if (view != null) {
                    return view
                }
            } catch (e: ClassNotFoundException) {
                // In this case we want to let the base class take a crack
                // at it.
            }
        }
        return super.onCreateView(name, attrs)
    }

    override fun cloneInContext(newContext: Context?): LayoutInflater {
        return PluginLayoutInflater(this, newContext)
    }
}