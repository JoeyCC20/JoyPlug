package com.joybox.joyplug.plugcore.component

import android.app.ActionBar
import android.app.Activity
import android.app.ActivityManager.TaskDescription
import android.app.Dialog
import android.app.DirectAction
import android.app.Fragment
import android.app.FragmentManager
import android.app.LoaderManager
import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.PictureInPictureUiState
import android.app.SharedElementCallback
import android.app.TaskStackBuilder
import android.app.VoiceInteractor
import android.app.assist.AssistContent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.IntentSender.SendIntentException
import android.content.LocusId
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.session.MediaController
import android.net.Uri
import android.os.Bundle
import android.os.CancellationSignal
import android.os.PersistableBundle
import android.transition.Scene
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.ActionMode
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.DragAndDropPermissions
import android.view.DragEvent
import android.view.KeyEvent
import android.view.KeyboardShortcutGroup
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Toolbar
import android.window.OnBackInvokedDispatcher
import android.window.SplashScreen
import com.joybox.joyplug.plugcore.JoyContext
import com.joybox.joyplug.plugcore.PluginApplication
import com.joybox.joyplug.plugcore.api.IPluginActivity
import com.joybox.joyplug.plugcore.delegate.HostActivityDelegate
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.function.Consumer

abstract class JoyBaseActivity : JoyContext(null, 0),
    IPluginActivity {
    private lateinit var mHostActivityDelegate : HostActivityDelegate

    open fun setHostActivityDelegate(delegate: HostActivityDelegate) {
        mHostActivityDelegate = delegate
    }

    open fun attachPluginContext(context: JoyContext) {
        cloneFrom(context)
    }

    open fun getHostActivityDelegate() : HostActivityDelegate? {
        return mHostActivityDelegate
    }

    fun getApplication(): PluginApplication {
        return getPluginApplication()
    }

    //
    override fun registerActivityLifecycleCallbacks(callback: PluginApplication.PluginActivityLifecycleCallbacks?) {
        callback?:return
        val wrap = PluginApplication.PluginActivityLifecycleCallbacksWrap(callback)
        mHostActivityDelegate.registerActivityLifecycleCallbacks(wrap)
    }

    override fun unregisterActivityLifecycleCallbacks(callback: PluginApplication.PluginActivityLifecycleCallbacks?) {
        callback?:return
        val wrap = PluginApplication.PluginActivityLifecycleCallbacksWrap(callback)
        mHostActivityDelegate.unregisterActivityLifecycleCallbacks(wrap)
    }
    //

    override fun onCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        mHostActivityDelegate.onCreate(arg0, arg1)
    }

    override fun setContentView(layoutResID: Int) {
        android.util.Log.i("[JOY]","[setContentView] context: $this")
        val view = LayoutInflater.from(this).inflate(layoutResID, null)
        mHostActivityDelegate.setContentView(view)
    }

    override fun getOnBackInvokedDispatcher() : OnBackInvokedDispatcher {
        return mHostActivityDelegate.getOnBackInvokedDispatcher()
    }

    override fun isChangingConfigurations(): Boolean {
        return mHostActivityDelegate.isChangingConfigurations()
    }

    override fun finish() {
        mHostActivityDelegate.finish()
    }

    override fun getLayoutInflater(): LayoutInflater {
        return getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun recreate() {
        mHostActivityDelegate.recreate()
    }

    override fun getCallingActivity(): ComponentName {
        return mHostActivityDelegate.getCallingActivity()
    }

    override fun onMultiWindowModeChanged(arg0: Boolean) {
        mHostActivityDelegate.onMultiWindowModeChanged(arg0)
    }

    override fun onMultiWindowModeChanged(arg0: Boolean, arg1: Configuration?) {
        mHostActivityDelegate.onMultiWindowModeChanged(arg0, arg1)
    }

    override fun onSaveInstanceState(arg0: Bundle?, arg1: PersistableBundle?) {
        mHostActivityDelegate.onSaveInstanceState(arg0, arg1)
    }

    override fun onPictureInPictureModeChanged(arg0: Boolean) {
        mHostActivityDelegate.onPictureInPictureModeChanged(arg0)
    }

    override fun onPictureInPictureModeChanged(arg0: Boolean, arg1: Configuration?) {
        mHostActivityDelegate.onPictureInPictureModeChanged(arg0, arg1)
    }

    override fun onPictureInPictureUiStateChanged(arg0: PictureInPictureUiState?) {
        mHostActivityDelegate.onPictureInPictureUiStateChanged(arg0)
    }

    override fun onTopResumedActivityChanged(arg0: Boolean) {
        mHostActivityDelegate.onTopResumedActivityChanged(arg0)
    }

    override fun onCreateDescription(): CharSequence {
        return mHostActivityDelegate.onCreateDescription()
    }

    override fun onProvideAssistData(arg0: Bundle?) {
        mHostActivityDelegate.onProvideAssistData(arg0)
    }

    override fun onPerformDirectAction(
        arg0: String?, arg1: Bundle?, arg2: CancellationSignal?,
        arg3: Consumer<Bundle?>?
    ) {
        mHostActivityDelegate.onPerformDirectAction(arg0, arg1, arg2, arg3)
    }

    override fun onProvideKeyboardShortcuts(
        arg0: List<KeyboardShortcutGroup?>?,
        arg1: Menu?,
        arg2: Int
    ) {
        mHostActivityDelegate.onProvideKeyboardShortcuts(arg0, arg1, arg2)
    }

    override fun onRestoreInstanceState(arg0: Bundle?, arg1: PersistableBundle?) {
        mHostActivityDelegate.onRestoreInstanceState(arg0, arg1)
    }

    override fun onProvideAssistContent(arg0: AssistContent?) {
        mHostActivityDelegate.onProvideAssistContent(arg0)
    }

    override fun onLocalVoiceInteractionStarted() {
        mHostActivityDelegate.onLocalVoiceInteractionStarted()
    }

    override fun onLocalVoiceInteractionStopped() {
        mHostActivityDelegate.onLocalVoiceInteractionStopped()
    }

    override fun onPictureInPictureRequested(): Boolean {
        return mHostActivityDelegate.onPictureInPictureRequested()
    }

    override fun onOptionsItemSelected(arg0: MenuItem?): Boolean {
        return mHostActivityDelegate.onOptionsItemSelected(arg0)
    }

    override fun onContextMenuClosed(arg0: Menu?) {
        mHostActivityDelegate.onContextMenuClosed(arg0)
    }

    override fun onPrepareNavigateUpTaskStack(arg0: TaskStackBuilder?) {
        mHostActivityDelegate.onPrepareNavigateUpTaskStack(arg0)
    }

    override fun onCreateNavigateUpTaskStack(arg0: TaskStackBuilder?) {
        mHostActivityDelegate.onCreateNavigateUpTaskStack(arg0)
    }

    override fun onRetainNonConfigurationInstance(): Any {
        return mHostActivityDelegate.onRetainNonConfigurationInstance()
    }

    override fun onWindowAttributesChanged(arg0: WindowManager.LayoutParams?) {
        mHostActivityDelegate.onWindowAttributesChanged(arg0)
    }

    override fun onWindowFocusChanged(arg0: Boolean) {
        mHostActivityDelegate.onWindowFocusChanged(arg0)
    }

    override fun onCreateOptionsMenu(arg0: Menu?): Boolean {
        return mHostActivityDelegate.onCreateOptionsMenu(arg0)
    }

    override fun onPrepareOptionsMenu(arg0: Menu?): Boolean {
        return mHostActivityDelegate.onPrepareOptionsMenu(arg0)
    }

    override fun onGenericMotionEvent(arg0: MotionEvent?): Boolean {
        return mHostActivityDelegate.onGenericMotionEvent(arg0)
    }

    override fun onCreateContextMenu(arg0: ContextMenu?, arg1: View?, arg2: ContextMenuInfo?) {
        mHostActivityDelegate.onCreateContextMenu(arg0, arg1, arg2)
    }

    override fun onContextItemSelected(arg0: MenuItem?): Boolean {
        return mHostActivityDelegate.onContextItemSelected(arg0)
    }

    override fun onNavigateUpFromChild(arg0: Activity?): Boolean {
        return mHostActivityDelegate.onNavigateUpFromChild(arg0)
    }

    override fun onOptionsMenuClosed(arg0: Menu?) {
        mHostActivityDelegate.onOptionsMenuClosed(arg0)
    }

    override fun onDetachedFromWindow() {
        mHostActivityDelegate.onDetachedFromWindow()
    }

    override fun onPanelClosed(arg0: Int, arg1: Menu) {
        TODO("Not yet implemented")
    }

    override fun onActionModeStarted(arg0: ActionMode?) {
        mHostActivityDelegate.onActionModeStarted(arg0)
    }

    override fun onActionModeFinished(arg0: ActionMode?) {
        mHostActivityDelegate.onActionModeFinished(arg0)
    }

    override fun onRequestPermissionsResult(arg0: Int, arg1: Array<String?>?, arg2: IntArray?) {
        mHostActivityDelegate.onRequestPermissionsResult(arg0, arg1, arg2)
    }

    override fun onVisibleBehindCanceled() {
        mHostActivityDelegate.onVisibleBehindCanceled()
    }

    override fun onEnterAnimationComplete() {
        mHostActivityDelegate.onEnterAnimationComplete()
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback?, arg1: Int): ActionMode? {
        return mHostActivityDelegate.onWindowStartingActionMode(arg0, arg1)
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback?): ActionMode? {
        return mHostActivityDelegate.onWindowStartingActionMode(arg0)
    }

    override fun onPostCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        mHostActivityDelegate.onPostCreate(arg0, arg1)
    }

    override fun onStateNotSaved() {
        mHostActivityDelegate.onStateNotSaved()
    }

    override fun onCreateThumbnail(arg0: Bitmap?, arg1: Canvas?): Boolean {
        return mHostActivityDelegate.onCreateThumbnail(arg0, arg1)
    }

    override fun onGetDirectActions(
        arg0: CancellationSignal?,
        arg1: Consumer<List<DirectAction?>?>?
    ) {
        mHostActivityDelegate.onGetDirectActions(arg0, arg1)
    }

    override fun onConfigurationChanged(arg0: Configuration) {
        TODO("Not yet implemented")
    }

    override fun onLowMemory() {
        mHostActivityDelegate.onLowMemory()
    }

    override fun onTrimMemory(arg0: Int) {
        mHostActivityDelegate.onTrimMemory(arg0)
    }

    override fun onAttachFragment(arg0: Fragment?) {
        mHostActivityDelegate.onAttachFragment(arg0)
    }

    override fun onKeyLongPress(arg0: Int, arg1: KeyEvent?): Boolean {
        return mHostActivityDelegate.onKeyLongPress(arg0, arg1)
    }

    override fun onKeyMultiple(arg0: Int, arg1: Int, arg2: KeyEvent?): Boolean {
        return mHostActivityDelegate.onKeyMultiple(arg0, arg1, arg2)
    }

    override fun onKeyUp(arg0: Int, arg1: KeyEvent?): Boolean {
        return mHostActivityDelegate.onKeyUp(arg0, arg1)
    }

    override fun onKeyDown(arg0: Int, arg1: KeyEvent?): Boolean {
        return mHostActivityDelegate.onKeyDown(arg0, arg1)
    }

    override fun onBackPressed() {
        mHostActivityDelegate.onBackPressed()
    }

    override fun onContentChanged() {
        mHostActivityDelegate.onContentChanged()
    }

    override fun onAttachedToWindow() {
        mHostActivityDelegate.onAttachedToWindow()
    }

    override fun onKeyShortcut(arg0: Int, arg1: KeyEvent?): Boolean {
        return mHostActivityDelegate.onKeyShortcut(arg0, arg1)
    }

    override fun onTouchEvent(arg0: MotionEvent?): Boolean {
        return mHostActivityDelegate.onTouchEvent(arg0)
    }

    override fun onUserInteraction() {
        mHostActivityDelegate.onUserInteraction()
    }

    override fun onTrackballEvent(arg0: MotionEvent?): Boolean {
        return mHostActivityDelegate.onTrackballEvent(arg0)
    }

    override fun onCreatePanelView(arg0: Int): View? {
        return mHostActivityDelegate.onCreatePanelView(arg0)
    }

    override fun onCreatePanelMenu(arg0: Int, arg1: Menu): Boolean {
        TODO("Not yet implemented")
    }

    override fun onPreparePanel(arg0: Int, arg1: View?, arg2: Menu): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMenuOpened(arg0: Int, arg1: Menu): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMenuItemSelected(arg0: Int, arg1: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    override fun onNavigateUp(): Boolean {
        return mHostActivityDelegate.onNavigateUp()
    }

    override fun onSearchRequested(): Boolean {
        return mHostActivityDelegate.onSearchRequested()
    }

    override fun onSearchRequested(arg0: SearchEvent?): Boolean {
        return mHostActivityDelegate.onSearchRequested(arg0)
    }

    override fun onActivityReenter(arg0: Int, arg1: Intent?) {
        mHostActivityDelegate.onActivityReenter(arg0, arg1)
    }

    override fun onProvideReferrer(): Uri {
        return mHostActivityDelegate.onProvideReferrer()
    }

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        return mHostActivityDelegate.onCreateView(parent, name, context, attrs)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return mHostActivityDelegate.onCreateView(name, context, attrs)
    }

    override fun onPointerCaptureChanged(arg0: Boolean) {
        mHostActivityDelegate.onPointerCaptureChanged(arg0)
    }

    override fun onSaveInstanceState(arg0: Bundle?) {
        mHostActivityDelegate.onSaveInstanceState(arg0)
    }

    override fun onRestoreInstanceState(arg0: Bundle?) {
        mHostActivityDelegate.onRestoreInstanceState(arg0)
    }

    override fun onChildTitleChanged(arg0: Activity?, arg1: CharSequence?) {
        
    }

//    override fun onApplyThemeResource(arg0: Theme?, arg1: Int, arg2: Boolean) {
//        mHostActivityDelegate.onApplyThemeResource(arg0, arg1, arg2)
//    }

    override fun onStart() {
        mHostActivityDelegate.onStart()
    }

    override fun onCreate(arg0: Bundle?) {
        mHostActivityDelegate.onCreate(arg0)
    }

    override fun onStop() {
        mHostActivityDelegate.onStop()
    }

    override fun onNewIntent(arg0: Intent?) {
        mHostActivityDelegate.onNewIntent(arg0)
    }

    override fun onPostCreate(arg0: Bundle?) {
        mHostActivityDelegate.onPostCreate(arg0)
    }

    override fun onPostResume() {
        mHostActivityDelegate.onPostResume()
    }

    override fun onResume() {
        mHostActivityDelegate.onResume()
    }

    override fun onUserLeaveHint() {
        mHostActivityDelegate.onUserLeaveHint()
    }

    override fun onRestart() {
        mHostActivityDelegate.onRestart()
    }

    override fun onPause() {
        mHostActivityDelegate.onPause()
    }

    override fun onDestroy() {
        mHostActivityDelegate.onDestroy()
    }

    override fun onPrepareDialog(arg0: Int, arg1: Dialog?) {
        mHostActivityDelegate.onPrepareDialog(arg0, arg1)
    }

    override fun onPrepareDialog(arg0: Int, arg1: Dialog?, arg2: Bundle?) {
        mHostActivityDelegate.onPrepareDialog(arg0, arg1, arg2)
    }

    override fun onCreateDialog(arg0: Int, arg1: Bundle?): Dialog {
        return mHostActivityDelegate.onCreateDialog(arg0, arg1)
    }

    override fun onCreateDialog(arg0: Int): Dialog {
        return mHostActivityDelegate.onCreateDialog(arg0)
    }

    override fun onActivityResult(arg0: Int, arg1: Int, arg2: Intent?) {
        mHostActivityDelegate.onActivityResult(arg0, arg1, arg2)
    }

    override fun onTitleChanged(arg0: CharSequence?, arg1: Int) {
        mHostActivityDelegate.onTitleChanged(arg0, arg1)
    }

    override fun dispatchKeyShortcutEvent(arg0: KeyEvent?): Boolean {
        return mHostActivityDelegate.dispatchKeyShortcutEvent(arg0)
    }

    override fun dispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent?): Boolean {
        return mHostActivityDelegate.dispatchPopulateAccessibilityEvent(arg0)
    }

    override fun dispatchTrackballEvent(arg0: MotionEvent?): Boolean {
        return mHostActivityDelegate.dispatchTrackballEvent(arg0)
    }

    override fun dispatchGenericMotionEvent(arg0: MotionEvent?): Boolean {
        return mHostActivityDelegate.dispatchGenericMotionEvent(arg0)
    }

    override fun dispatchKeyEvent(arg0: KeyEvent?): Boolean {
        return mHostActivityDelegate.dispatchKeyEvent(arg0)
    }

    override fun dispatchTouchEvent(arg0: MotionEvent?): Boolean {
        return mHostActivityDelegate.dispatchTouchEvent(arg0)
    }

    override fun getParent(): Activity? {
        return mHostActivityDelegate.getParent()
    }

    override fun isDestroyed(): Boolean {
        return mHostActivityDelegate.isDestroyed()
    }

    override fun isVoiceInteractionRoot(): Boolean {
        return mHostActivityDelegate.isVoiceInteractionRoot()
    }

    override fun requestShowKeyboardShortcuts() {
        mHostActivityDelegate.requestShowKeyboardShortcuts()
    }

    override fun isInPictureInPictureMode(): Boolean {
        return mHostActivityDelegate.isInPictureInPictureMode()
    }

    override fun startLocalVoiceInteraction(arg0: Bundle?) {
        mHostActivityDelegate.startLocalVoiceInteraction(arg0)
    }

    override fun isInMultiWindowMode(): Boolean {
        return mHostActivityDelegate.isInMultiWindowMode()
    }

    override fun enterPictureInPictureMode(arg0: PictureInPictureParams?): Boolean {
        return mHostActivityDelegate.enterPictureInPictureMode(arg0)
    }

    override fun enterPictureInPictureMode() {
        mHostActivityDelegate.enterPictureInPictureMode()
    }

    override fun setPictureInPictureParams(arg0: PictureInPictureParams?) {
        mHostActivityDelegate.setPictureInPictureParams(arg0)
    }

    override fun getMaxNumPictureInPictureActions(): Int {
        return mHostActivityDelegate.getMaxNumPictureInPictureActions()
    }

    override fun stopLocalVoiceInteraction() {
        mHostActivityDelegate.stopLocalVoiceInteraction()
    }

    override fun isLocalVoiceInteractionSupported(): Boolean {
        return mHostActivityDelegate.isLocalVoiceInteractionSupported()
    }

    override fun dismissKeyboardShortcutsHelper() {
        mHostActivityDelegate.dismissKeyboardShortcutsHelper()
    }

    override fun startManagingCursor(arg0: Cursor?) {
        mHostActivityDelegate.startManagingCursor(arg0)
    }

    override fun registerForContextMenu(arg0: View?) {
        mHostActivityDelegate.registerForContextMenu(arg0)
    }

    override fun unregisterForContextMenu(arg0: View?) {
        mHostActivityDelegate.unregisterForContextMenu(arg0)
    }

    override fun setFinishOnTouchOutside(arg0: Boolean) {
        mHostActivityDelegate.setFinishOnTouchOutside(arg0)
    }

    override fun getChangingConfigurations(): Int {
        return mHostActivityDelegate.getChangingConfigurations()
    }

    override fun getContentTransitionManager(): TransitionManager {
        return mHostActivityDelegate.getContentTransitionManager()
    }

    override fun invalidateOptionsMenu() {
        mHostActivityDelegate.invalidateOptionsMenu()
    }

    override fun getLastNonConfigurationInstance(): Any? {
        return mHostActivityDelegate.getLastNonConfigurationInstance()
    }

    override fun setContentTransitionManager(arg0: TransitionManager?) {
        mHostActivityDelegate.setContentTransitionManager(arg0)
    }

    override fun requestWindowFeature(arg0: Int): Boolean {
        return mHostActivityDelegate.requestWindowFeature(arg0)
    }

    override fun shouldUpRecreateTask(arg0: Intent?): Boolean {
        return mHostActivityDelegate.shouldUpRecreateTask(arg0)
    }

    override fun finishActivityFromChild(arg0: Activity?, arg1: Int) {
        return mHostActivityDelegate.finishActivityFromChild(arg0, arg1)
    }

    override fun startActivityFromFragment(arg0: Fragment?, arg1: Intent?, arg2: Int) {
        android.util.Log.i("[JOY]", "[JoyBaseActivity] startActivityFromFragment1");
        mHostActivityDelegate.startActivityFromFragment(arg0, arg1, arg2)
    }

    override fun startActivityFromFragment(
        arg0: Fragment?,
        arg1: Intent?,
        arg2: Int,
        arg3: Bundle?
    ) {
        android.util.Log.i("[JOY]", "[JoyBaseActivity] startActivityFromFragment2");
        mHostActivityDelegate.startActivityFromFragment(arg0, arg1, arg2, arg3)
    }

    override fun overridePendingTransition(arg0: Int, arg1: Int) {
        mHostActivityDelegate.overridePendingTransition(arg0, arg1)
    }

    override fun setProgressBarIndeterminate(arg0: Boolean) {
        mHostActivityDelegate.setProgressBarIndeterminate(arg0)
    }

    override fun navigateUpToFromChild(arg0: Activity?, arg1: Intent?): Boolean {
        return mHostActivityDelegate.navigateUpToFromChild(arg0, arg1)
    }

    override fun getParentActivityIntent(): Intent {
        return mHostActivityDelegate.getParentActivityIntent()
    }

    override fun startNextMatchingActivity(arg0: Intent?, arg1: Bundle?): Boolean {
        return mHostActivityDelegate.startNextMatchingActivity(arg0, arg1)
    }

    override fun startNextMatchingActivity(arg0: Intent?): Boolean {
        return mHostActivityDelegate.startNextMatchingActivity(arg0)
    }

    override fun finishAfterTransition() {
        mHostActivityDelegate.finishAfterTransition()
    }

    override fun getRequestedOrientation(): Int {
        return mHostActivityDelegate.getRequestedOrientation()
    }

    override fun startActivityForResult(arg0: Intent?, arg1: Int, arg2: Bundle?) {
        mHostActivityDelegate.startActivityForResult(arg0, arg1, arg2)
    }

    override fun startActivityForResult(arg0: Intent?, arg1: Int) {
        mHostActivityDelegate.startActivityForResult(arg0, arg1)
    }

    override fun createPendingResult(arg0: Int, arg1: Intent?, arg2: Int): PendingIntent {
        return mHostActivityDelegate.createPendingResult(arg0, arg1, arg2)
    }

    override fun finishAndRemoveTask() {
        mHostActivityDelegate.finishAndRemoveTask()
    }

    override fun setVolumeControlStream(arg0: Int) {
        mHostActivityDelegate.setVolumeControlStream(arg0)
    }

    override fun startActivityIfNeeded(arg0: Intent?, arg1: Int): Boolean {
        return mHostActivityDelegate.startActivityIfNeeded(arg0, arg1)
    }

    override fun startActivityIfNeeded(arg0: Intent?, arg1: Int, arg2: Bundle?): Boolean {
        return mHostActivityDelegate.startActivityIfNeeded(arg0, arg1, arg2)
    }

    override fun getVolumeControlStream(): Int {
        return mHostActivityDelegate.getVolumeControlStream()
    }

    override fun setSecondaryProgress(arg0: Int) {
        mHostActivityDelegate.setSecondaryProgress(arg0)
    }

    override fun requestVisibleBehind(arg0: Boolean): Boolean {
        return mHostActivityDelegate.requestVisibleBehind(arg0)
    }

    override fun setFeatureDrawableUri(arg0: Int, arg1: Uri?) {
        mHostActivityDelegate.setFeatureDrawableUri(arg0, arg1)
    }

    override fun startActivityFromChild(arg0: Activity?, arg1: Intent?, arg2: Int) {
        mHostActivityDelegate.startActivityFromChild(arg0, arg1, arg2)
    }

    override fun startActivityFromChild(
        arg0: Activity?, arg1: Intent?, arg2: Int,
        arg3: Bundle?
    ) {
        mHostActivityDelegate.startActivityFromChild(arg0, arg1, arg2, arg3)
    }

    override fun setFeatureDrawableResource(arg0: Int, arg1: Int) {
        mHostActivityDelegate.setFeatureDrawableResource(arg0, arg1)
    }

    override fun setProgressBarVisibility(arg0: Boolean) {
        mHostActivityDelegate.setProgressBarVisibility(arg0)
    }

    override fun setFeatureDrawableAlpha(arg0: Int, arg1: Int) {
        mHostActivityDelegate.setFeatureDrawableAlpha(arg0, arg1)
    }

    override fun isActivityTransitionRunning(): Boolean {
        return mHostActivityDelegate.isActivityTransitionRunning()
    }

    @Throws(SendIntentException::class)
    override fun startIntentSenderForResult(
        arg0: IntentSender?, arg1: Int, arg2: Intent?, arg3: Int,
        arg4: Int, arg5: Int
    ) {
        mHostActivityDelegate.startIntentSenderForResult(arg0, arg1, arg2, arg3, arg4, arg5)
    }

    @Throws(SendIntentException::class)
    override fun startIntentSenderForResult(
        arg0: IntentSender?, arg1: Int, arg2: Intent?, arg3: Int,
        arg4: Int, arg5: Int, arg6: Bundle?
    ) {
        mHostActivityDelegate.startIntentSenderForResult(arg0, arg1, arg2, arg3, arg4, arg5, arg6)
    }

    @Throws(SendIntentException::class)
    override fun startIntentSenderFromChild(
        arg0: Activity?, arg1: IntentSender?, arg2: Int,
        arg3: Intent?, arg4: Int, arg5: Int, arg6: Int
    ) {

    }

    @Throws(SendIntentException::class)
    override fun startIntentSenderFromChild(
        arg0: Activity?, arg1: IntentSender?, arg2: Int,
        arg3: Intent?, arg4: Int, arg5: Int, arg6: Int, arg7: Bundle?
    ) {

    }

    override fun setRequestedOrientation(arg0: Int) {
        mHostActivityDelegate.setRequestedOrientation(arg0)
    }

    override fun isLaunchedFromBubble(): Boolean {
        return mHostActivityDelegate.isLaunchedFromBubble()
    }

    override fun setInheritShowWhenLocked(arg0: Boolean) {
        mHostActivityDelegate.setInheritShowWhenLocked(arg0)
    }

    override fun postponeEnterTransition() {
        mHostActivityDelegate.postponeEnterTransition()
    }

    override fun requestDragAndDropPermissions(arg0: DragEvent?): DragAndDropPermissions {
        return mHostActivityDelegate.requestDragAndDropPermissions(arg0)
    }

    override fun setEnterSharedElementCallback(arg0: SharedElementCallback?) {
        mHostActivityDelegate.setEnterSharedElementCallback(arg0)
    }

    override fun showLockTaskEscapeMessage() {
        mHostActivityDelegate.showLockTaskEscapeMessage()
    }

    override fun startPostponedEnterTransition() {
        mHostActivityDelegate.startPostponedEnterTransition()
    }

    override fun setExitSharedElementCallback(arg0: SharedElementCallback?) {
        mHostActivityDelegate.setExitSharedElementCallback(arg0)
    }

    override fun setResult(arg0: Int) {
        mHostActivityDelegate.setResult(arg0)
    }

    override fun setResult(arg0: Int, arg1: Intent?) {
        mHostActivityDelegate.setResult(arg0, arg1)
    }

    override fun dump(
        arg0: String?,
        arg1: FileDescriptor?,
        arg2: PrintWriter?,
        arg3: Array<String?>?
    ) {
        mHostActivityDelegate.dump(arg0, arg1, arg2, arg3)
    }

    override fun getTaskId(): Int {
        return mHostActivityDelegate.getTaskId()
    }

    override fun getComponentName(): ComponentName {
//        return ComponentName(packageName, this.javaClass::class.java.name)
        return mHostActivityDelegate.getComponentName()
    }

    override fun getTitle(): CharSequence {
        return mHostActivityDelegate.getTitle()
    }

    override fun setTitle(arg0: Int) {
        mHostActivityDelegate.setTitle(arg0)
    }

    override fun setTitle(arg0: CharSequence?) {
        mHostActivityDelegate.setTitle(arg0)
    }

    override fun setVisible(arg0: Boolean) {
        mHostActivityDelegate.setVisible(arg0)
    }

    override fun setContentView(arg0: View?, arg1: ViewGroup.LayoutParams?) {
        mHostActivityDelegate.setContentView(arg0, arg1)
    }

    override fun setContentView(arg0: View?) {
        mHostActivityDelegate.setContentView(arg0)
    }

    override fun getWindow(): Window {
        return mHostActivityDelegate.getWindow()
    }

    override fun setLocusContext(arg0: LocusId?, arg1: Bundle?) {
        mHostActivityDelegate.setLocusContext(arg0, arg1)
    }

    override fun getIntent(): Intent {
        return mHostActivityDelegate.getIntent()
    }

    override fun setIntent(arg0: Intent?) {
        mHostActivityDelegate.setIntent(arg0)
    }

    override fun getWindowManager(): WindowManager {
        return mHostActivityDelegate.getWindowManager()
    }

    override fun getCurrentFocus(): View? {
        return mHostActivityDelegate.getCurrentFocus()
    }

    override fun getLoaderManager(): LoaderManager {
        return mHostActivityDelegate.getLoaderManager()
    }

    override fun isChild(): Boolean {
        return mHostActivityDelegate.isChild()
    }

    override fun getSplashScreen(): SplashScreen {
        return mHostActivityDelegate.getSplashScreen()
    }

    override fun isVoiceInteraction(): Boolean {
        return mHostActivityDelegate.isVoiceInteraction()
    }

    override fun getVoiceInteractor(): VoiceInteractor {
        return mHostActivityDelegate.getVoiceInteractor()
    }

    override fun showAssist(arg0: Bundle?): Boolean {
        return mHostActivityDelegate.showAssist(arg0)
    }

    override fun reportFullyDrawn() {
        mHostActivityDelegate.reportFullyDrawn()
    }

    override fun setActionBar(arg0: Toolbar?) {
        mHostActivityDelegate.setActionBar(arg0)
    }

    override fun <T : View?> findViewById(arg0: Int): T {
        return mHostActivityDelegate.findViewById(arg0)
    }

    override fun <T : View?> requireViewById(arg0: Int): T {
        return mHostActivityDelegate.requireViewById(arg0)
    }

    override fun managedQuery(
        arg0: Uri?,
        arg1: Array<String?>?,
        arg2: String?,
        arg3: Array<String?>?,
        arg4: String?
    ): Cursor {
        return mHostActivityDelegate.managedQuery(arg0, arg1, arg2, arg3, arg4)
    }

    override fun addContentView(arg0: View?, arg1: ViewGroup.LayoutParams?) {
        mHostActivityDelegate.addContentView(arg0, arg1)
    }

    override fun setDefaultKeyMode(arg0: Int) {
        mHostActivityDelegate.setDefaultKeyMode(arg0)
    }

    override fun getContentScene(): Scene {
        return mHostActivityDelegate.getContentScene()
    }

    override fun getFragmentManager(): FragmentManager {
        return mHostActivityDelegate.getFragmentManager()
    }

    override fun stopManagingCursor(arg0: Cursor?) {
        mHostActivityDelegate.stopManagingCursor(arg0)
    }

    override fun getActionBar(): ActionBar {
        return mHostActivityDelegate.getActionBar()
    }

    override fun hasWindowFocus(): Boolean {
        return mHostActivityDelegate.hasWindowFocus()
    }

    override fun closeOptionsMenu() {
        mHostActivityDelegate.closeOptionsMenu()
    }

    override fun showDialog(arg0: Int, arg1: Bundle?): Boolean {
        return mHostActivityDelegate.showDialog(arg0, arg1)
    }

    override fun showDialog(arg0: Int) {
        mHostActivityDelegate.showDialog(arg0)
    }

    override fun removeDialog(arg0: Int) {
        mHostActivityDelegate.removeDialog(arg0)
    }

    override fun triggerSearch(arg0: String?, arg1: Bundle?) {
        mHostActivityDelegate.triggerSearch(arg0, arg1)
    }

    override fun openOptionsMenu() {
        mHostActivityDelegate.openOptionsMenu()
    }

    override fun getSearchEvent(): SearchEvent {
        return mHostActivityDelegate.getSearchEvent()
    }

    override fun closeContextMenu() {
        mHostActivityDelegate.closeContextMenu()
    }

    override fun startSearch(arg0: String?, arg1: Boolean, arg2: Bundle?, arg3: Boolean) {
        mHostActivityDelegate.startSearch(arg0, arg1, arg2, arg3)
    }

    override fun openContextMenu(arg0: View?) {
        mHostActivityDelegate.openContextMenu(arg0)
    }

    override fun dismissDialog(arg0: Int) {
        mHostActivityDelegate.dismissDialog(arg0)
    }

    override fun requestPermissions(arg0: Array<String?>?, arg1: Int) {
        mHostActivityDelegate.requestPermissions(arg0, arg1)
    }

    override fun takeKeyEvents(arg0: Boolean) {
        mHostActivityDelegate.takeKeyEvents(arg0)
    }

    override fun setFeatureDrawable(arg0: Int, arg1: Drawable?) {
        mHostActivityDelegate.setFeatureDrawable(arg0, arg1)
    }

    override fun getMenuInflater(): MenuInflater {
        return mHostActivityDelegate.getMenuInflater()
    }

    override fun finishAffinity() {
        mHostActivityDelegate.finishAffinity()
    }

    override fun getReferrer(): Uri {
        return mHostActivityDelegate.getReferrer()
    }

    override fun releaseInstance(): Boolean {
        return mHostActivityDelegate.releaseInstance()
    }

    override fun getCallingPackage(): String {
        return mHostActivityDelegate.getCallingPackage()
    }

    override fun finishFromChild(arg0: Activity?) {
        mHostActivityDelegate.finishFromChild(arg0)
    }

    override fun isTaskRoot(): Boolean {
        return mHostActivityDelegate.isTaskRoot()
    }

    override fun finishActivity(arg0: Int) {
        mHostActivityDelegate.finishActivity(arg0)
    }

    override fun isFinishing(): Boolean {
        return mHostActivityDelegate.isFinishing()
    }

    override fun moveTaskToBack(arg0: Boolean): Boolean {
        return mHostActivityDelegate.moveTaskToBack(arg0)
    }

    override fun setImmersive(arg0: Boolean) {
        mHostActivityDelegate.setImmersive(arg0)
    }

    override fun setTitleColor(arg0: Int) {
        mHostActivityDelegate.setTitleColor(arg0)
    }

    override fun getLocalClassName(): String {
        return mHostActivityDelegate.getLocalClassName()
    }

    override fun getPreferences(arg0: Int): SharedPreferences {
        return mHostActivityDelegate.getPreferences(arg0)
    }

    override fun getMediaController(): MediaController {
        return mHostActivityDelegate.getMediaController()
    }

    override fun setTaskDescription(arg0: TaskDescription?) {
        mHostActivityDelegate.setTaskDescription(arg0)
    }

    override fun isImmersive(): Boolean {
        return mHostActivityDelegate.isImmersive()
    }

    override fun setMediaController(arg0: MediaController?) {
        mHostActivityDelegate.setMediaController(arg0)
    }

    override fun setProgress(arg0: Int) {
        mHostActivityDelegate.setProgress(arg0)
    }

    override fun runOnUiThread(arg0: Runnable?) {
        mHostActivityDelegate.runOnUiThread(arg0)
    }

    override fun setTranslucent(arg0: Boolean): Boolean {
        return mHostActivityDelegate.setTranslucent(arg0)
    }

    override fun getTitleColor(): Int {
        return mHostActivityDelegate.getTitleColor()
    }

    override fun startActionMode(arg0: ActionMode.Callback?, arg1: Int): ActionMode {
        return mHostActivityDelegate.startActionMode(arg0, arg1)
    }

    override fun startActionMode(arg0: ActionMode.Callback?): ActionMode {
        return mHostActivityDelegate.startActionMode(arg0)
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun setVrModeEnabled(arg0: Boolean, arg1: ComponentName?) {
        mHostActivityDelegate.setVrModeEnabled(arg0, arg1)
    }

    override fun setTurnScreenOn(arg0: Boolean) {
        mHostActivityDelegate.setTurnScreenOn(arg0)
    }

    override fun setShowWhenLocked(arg0: Boolean) {
        mHostActivityDelegate.setShowWhenLocked(arg0)
    }

    override fun startLockTask() {
        mHostActivityDelegate.startLockTask()
    }

    override fun stopLockTask() {
        mHostActivityDelegate.stopLockTask()
    }

    override fun navigateUpTo(arg0: Intent?): Boolean {
        return mHostActivityDelegate.navigateUpTo(arg0)
    }

    override fun shouldShowRequestPermissionRationale(arg0: String?): Boolean {
        return mHostActivityDelegate.shouldShowRequestPermissionRationale(arg0)
    }

    override fun setProgressBarIndeterminateVisibility(arg0: Boolean) {
        mHostActivityDelegate.setProgressBarIndeterminateVisibility(arg0)
    }

//    override fun startActivity(intent: Intent?) {
//        intent ?: return
//        val newIntent = Intent(baseContext, baseContext.classLoader.loadClass(PluginCommonConst.HOST_CONTAINER_ACTIVITY))
//        newIntent.replaceExtras(intent.extras)
//        newIntent.putExtra(PluginCommonConst.KEY_TARGET_PLUGIN_PACKAGE_NAME, intent.component?.packageName)
//        newIntent.putExtra(PluginCommonConst.KEY_TARGET_ACTIVITY, intent.component?.className)
//        super.startActivity(newIntent)
//    }

//    override fun startActivity(intent: Intent?, options: Bundle?) {
//        intent ?: return
//        val newIntent = Intent(baseContext, baseContext.classLoader.loadClass(PluginCommonConst.HOST_CONTAINER_ACTIVITY))
//        intent.extras?.let { newIntent.putExtras(it) }
//        newIntent.putExtra(PluginCommonConst.KEY_TARGET_PLUGIN_PACKAGE_NAME, intent.component?.packageName)
//        newIntent.putExtra(PluginCommonConst.KEY_TARGET_ACTIVITY, intent.component?.className)
//        super.startActivity(newIntent, options)
//    }
}