# JoyPlug v0.9

A lightweight plugin framework for Android.
For now, it's a basic version

## Features
- 1.View level plugin supported.
- 2.Activity (including Fragment) plugin supported. Other components support will be considered.
- 3.A pure logic plugin, which has no components or views, is supported.
- 4.A plugin will be developed as a independent APP which can be performed or debugged like a normal APP. 
<p></p>
  <img src="docs/readme_view_sample.png" width=300 height=450>

## Concept
- 1.Package name is treated as the identity of each plugin
- 2.Plugin should not be only a component, but a functional module as well, which can be presented as a service


## Guide
### [Host]
#### 1. gradle
> Demo：host_sample
```
implementation("com.joybox.joyplug:host:1.0.+") // host framework
implementation("com.joybox.joyplug:services:1.0.+") // host service interfaces
```

#### 2.add Apk Provider
<p>'Apk Provider' provides a way to submit a raw plugin package, which may come from the internet or other storage locations, into the PluginLoader for developers.</p>
<p>In other words, developers should be responsible for the source of the plugin APK, and this will not be considered by the framework.</p>
<p>When the APK is ready, build an 'ApkRecord' and notify the framework using an 'ApkProvideNotification'.</p>

```
PluginManager.addPluginApkProvider(applicationContext, object :
            IPluginApkProvider {
            override fun onGetAsync(notification: ApkProvideNotification) {
                // Activity Plugin Sample Apk
                provideApk("activity_plugin.apk") { apkPath ->
                    apkPath?.let {
                        val apkRecord = ApkRecord(
                            "com.joybox.joyplug.activity_plugin_sample",
                            1,
                            PluginPriority.HIGH,
                            it,
                            "",
                            System.currentTimeMillis()
                        )

                        notification.notifyApkReady(apkRecord)
                    }
                }
			}
}
```

### 3. init
```
PluginManager.init(applicationContext) {
    Log.i("PluginManager", "PluginManager init done")
}
```

### 4. Services for Plugin
Host can provide service for plugin by registerService (which will be replaced by Annotation in next version)
```
PluginManager.registerService<NetService>(object : NetService {
	override fun GET(url: URL): String {
		Log.i("Service", "[NetService] GET() called.")
		return "success"
	}

	override fun POST(url: URL, params: JSONArray): String {
		Log.i("Service", "[NetService] POST() called.")
		return "success"
	}
})
```

using in plugin:
```
val netService = HostServiceCenter.getService<NetService>()
val ret = netService?.GET(URL("https://www.test.com"))
```

### 5.Jump to a Activity of a certain plugin
```
PluginRouter.startActivity(
                requireActivity(),
                "com.joybox.joyplug.activity_plugin_sample",
                "com.joybox.joyplug.activity_plugin_sample.MainActivity",
                extras
            )
```
### 6. integrate a plugin View in the Host or an other Plugin layout 

```
<com.joybox.joyplug.host.core.component.PluginComponentView
        android:id="@+id/plugin_view"
        app:packageName="com.joybox.joyplug.view_plugin_sample"
        app:view="com.joybox.joyplug.view_plugin_sample.SampleView"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/text_dashboard"
        />
```

### [Plugin]
#### Independent App
> Demo：activity_plugin_sample

<p>apply the replacement plugin in gradle:</p>

```
id("com.joybox.joyplug.replacement")
```
<p>perform 'build' task.</p>
<p>in debug mode (BuildType is not release)，this plugin will not work</p>
<p>in release mode, origin Activity class of this Project will be replaced by customized Activity, which called JoyBaseActivity</p>

##### Jump to a Activity in the plugin app:
```
val intent = PluginIntent("com.joybox.joyplug.activity_plugin_sample.MainActivity")
context.startActivity(intent)

```

#### View Plugin

> Demo：view_plugin_sample, view_plugin_sample2
<p>gradle:</p>

```
implementation("com.joybox.joyplug:plugcore:1.0.+")
```

then perform build task.

#### host service for plugin:
```
implementation("com.joybox.joyplug:services:1.0.+")
```
