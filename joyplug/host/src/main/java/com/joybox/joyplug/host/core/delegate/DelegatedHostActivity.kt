package com.joybox.joyplug.host.core.delegate

import android.app.ActionBar
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
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
import android.content.LocusId
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.media.session.MediaController
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.PersistableBundle
import android.transition.Scene
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.ActionMode
import android.view.ContextMenu
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
import androidx.annotation.RequiresApi
import com.joybox.joyplug.plugcore.delegate.HostActivityDelegate
import com.joybox.joyplug.utils.logd
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.function.Consumer

class DelegatedHostActivity(private var activity : Activity) : HostActivityDelegate {
    //
    private fun Activity.call(name: String, vararg args : Any?) : Any? {
        return if (args.isNotEmpty()) {
            var argTypes = arrayOfNulls<Class<*>>(args.size)
            for (i in args.indices) {
                argTypes[i] = args[i]!!::class.java
            }
            val method = this::class.java.getDeclaredMethod(name, *argTypes)
            method.isAccessible = true
            method.invoke(this, args)
        } else {
            val method = this::class.java.getDeclaredMethod(name)
            method.isAccessible = true
            return method.invoke(this)
        }
    }
    //

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun getOnBackInvokedDispatcher() : OnBackInvokedDispatcher {
        return activity.getOnBackInvokedDispatcher()
    }

    override fun onConfigurationChanged(arg0: Configuration?) {
        TODO("Not yet implemented")
    }

    override fun onLowMemory() {
        TODO("Not yet implemented")
    }

    override fun onTrimMemory(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun dispatchKeyEvent(arg0: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchKeyShortcutEvent(arg0: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchTouchEvent(arg0: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchTrackballEvent(arg0: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchGenericMotionEvent(arg0: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun dispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCreatePanelView(arg0: Int): View? {
        TODO("Not yet implemented")
    }

    override fun onCreatePanelMenu(arg0: Int, arg1: Menu?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onPreparePanel(arg0: Int, arg1: View?, arg2: Menu?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMenuOpened(arg0: Int, arg1: Menu?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMenuItemSelected(arg0: Int, arg1: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onWindowAttributesChanged(arg0: WindowManager.LayoutParams?) {
        TODO("Not yet implemented")
    }

    override fun onContentChanged() {
        TODO("Not yet implemented")
    }

    override fun onWindowFocusChanged(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onAttachedToWindow() {
        TODO("Not yet implemented")
    }

    override fun onDetachedFromWindow() {
        TODO("Not yet implemented")
    }

    override fun onPanelClosed(arg0: Int, arg1: Menu?) {
        TODO("Not yet implemented")
    }

    override fun onSearchRequested(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSearchRequested(arg0: SearchEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback?, arg1: Int): ActionMode? {
        TODO("Not yet implemented")
    }

    override fun onWindowStartingActionMode(arg0: ActionMode.Callback?): ActionMode {
        TODO("Not yet implemented")
    }

    override fun onActionModeStarted(arg0: ActionMode?) {
        TODO("Not yet implemented")
    }

    override fun onActionModeFinished(arg0: ActionMode?) {
        TODO("Not yet implemented")
    }

    override fun onProvideKeyboardShortcuts(
        arg0: List<KeyboardShortcutGroup?>?,
        arg1: Menu?,
        arg2: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun onPointerCaptureChanged(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onKeyDown(arg0: Int, arg1: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onKeyLongPress(arg0: Int, arg1: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onKeyUp(arg0: Int, arg1: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onKeyMultiple(arg0: Int, arg1: Int, arg2: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isChangingConfigurations(): Boolean {
        TODO("Not yet implemented")
    }

    override fun finish() {
        TODO("Not yet implemented")
    }

    override fun getLayoutInflater(): LayoutInflater {
        TODO("Not yet implemented")
    }

    override fun recreate() {
        TODO("Not yet implemented")
    }

    override fun getCallingActivity(): ComponentName {
        TODO("Not yet implemented")
    }

    override fun onMultiWindowModeChanged(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onMultiWindowModeChanged(arg0: Boolean, arg1: Configuration?) {
        TODO("Not yet implemented")
    }

    override fun onSaveInstanceState(arg0: Bundle?, arg1: PersistableBundle?) {
        TODO("Not yet implemented")
    }

    override fun onSaveInstanceState(arg0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onPictureInPictureModeChanged(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onPictureInPictureModeChanged(arg0: Boolean, arg1: Configuration?) {
        TODO("Not yet implemented")
    }

    override fun onPictureInPictureUiStateChanged(arg0: PictureInPictureUiState?) {
        TODO("Not yet implemented")
    }

    override fun onTopResumedActivityChanged(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onCreateDescription(): CharSequence {
        TODO("Not yet implemented")
    }

    override fun onProvideAssistData(arg0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onPerformDirectAction(
        arg0: String?,
        arg1: Bundle?,
        arg2: CancellationSignal?,
        arg3: Consumer<Bundle?>?
    ) {
        TODO("Not yet implemented")
    }

    override fun onRestoreInstanceState(arg0: Bundle?, arg1: PersistableBundle?) {
        TODO("Not yet implemented")
    }

    override fun onRestoreInstanceState(arg0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onProvideAssistContent(arg0: AssistContent?) {
        TODO("Not yet implemented")
    }

    override fun onLocalVoiceInteractionStarted() {
        TODO("Not yet implemented")
    }

    override fun onLocalVoiceInteractionStopped() {
        TODO("Not yet implemented")
    }

    override fun onPictureInPictureRequested(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onOptionsItemSelected(arg0: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onContextMenuClosed(arg0: Menu?) {
        TODO("Not yet implemented")
    }

    override fun onPrepareNavigateUpTaskStack(arg0: TaskStackBuilder?) {
        TODO("Not yet implemented")
    }

    override fun onCreateNavigateUpTaskStack(arg0: TaskStackBuilder?) {
        TODO("Not yet implemented")
    }

    override fun onRetainNonConfigurationInstance(): Any {
        TODO("Not yet implemented")
    }

    override fun onCreateOptionsMenu(arg0: Menu?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onPrepareOptionsMenu(arg0: Menu?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onGenericMotionEvent(arg0: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCreateContextMenu(
        arg0: ContextMenu?,
        arg1: View?,
        arg2: ContextMenu.ContextMenuInfo?
    ) {
        TODO("Not yet implemented")
    }

    override fun onContextItemSelected(arg0: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onNavigateUpFromChild(arg0: Activity?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onOptionsMenuClosed(arg0: Menu?) {
        TODO("Not yet implemented")
    }

    override fun onRequestPermissionsResult(arg0: Int, arg1: Array<String?>?, arg2: IntArray?) {
        TODO("Not yet implemented")
    }

    override fun onVisibleBehindCanceled() {
        TODO("Not yet implemented")
    }

    override fun onEnterAnimationComplete() {
        TODO("Not yet implemented")
    }

    override fun onCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        TODO("Not yet implemented")
    }

    override fun onCreate(arg0: Bundle?) {
        logd("Host Delegate onCreate called")
    }

    override fun onPostCreate(arg0: Bundle?, arg1: PersistableBundle?) {
        TODO("Not yet implemented")
    }

    override fun onPostCreate(arg0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onStateNotSaved() {
        TODO("Not yet implemented")
    }

    override fun onCreateThumbnail(arg0: Bitmap?, arg1: Canvas?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onGetDirectActions(
        arg0: CancellationSignal?,
        arg1: Consumer<List<DirectAction?>?>?
    ) {
        TODO("Not yet implemented")
    }

    override fun onAttachFragment(arg0: Fragment?) {
        activity.onAttachFragment(arg0)
    }

    override fun onBackPressed() {
        TODO("Not yet implemented")
    }

    override fun onKeyShortcut(arg0: Int, arg1: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onTouchEvent(arg0: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onUserInteraction() {
        TODO("Not yet implemented")
    }

    override fun onTrackballEvent(arg0: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onNavigateUp(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onActivityReenter(arg0: Int, arg1: Intent?) {
        TODO("Not yet implemented")
    }

    override fun onProvideReferrer(): Uri {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        arg0: View?,
        arg1: String?,
        arg2: Context?,
        arg3: AttributeSet?
    ): View? {
        return activity.onCreateView(arg0, arg1!!, arg2!!, arg3!!)
    }

    override fun onCreateView(arg0: String?, arg1: Context?, arg2: AttributeSet?): View {
        return activity.onCreateView(arg0!!, arg1!!, arg2!!)!!
    }

    override fun onChildTitleChanged(arg0: Activity?, arg1: CharSequence?) {
        TODO("Not yet implemented")
    }

    override fun onApplyThemeResource(arg0: Resources.Theme?, arg1: Int, arg2: Boolean) {
//        activity.onApplyThemeResource(arg0, arg1, arg2) TODO
        activity.call("onApplyThemeResource", arg0, arg1, arg2)
    }

    override fun onStart() {
//        val method = activity::class.java.getDeclaredMethod("onStart")
//        method.isAccessible = true
//        method.invoke(activity)
        logd("Host Delegate onStart called")
    }

    override fun onStop() {
        TODO("Not yet implemented")
    }

    override fun onNewIntent(arg0: Intent?) {
        TODO("Not yet implemented")
    }

    override fun onPostResume() {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        TODO("Not yet implemented")
    }

    override fun onUserLeaveHint() {
        TODO("Not yet implemented")
    }

    override fun onRestart() {
        TODO("Not yet implemented")
    }

    override fun onPause() {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        TODO("Not yet implemented")
    }

    override fun onPrepareDialog(arg0: Int, arg1: Dialog?) {
        TODO("Not yet implemented")
    }

    override fun onPrepareDialog(arg0: Int, arg1: Dialog?, arg2: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onCreateDialog(arg0: Int, arg1: Bundle?): Dialog {
        TODO("Not yet implemented")
    }

    override fun onCreateDialog(arg0: Int): Dialog {
        TODO("Not yet implemented")
    }

    override fun onActivityResult(arg0: Int, arg1: Int, arg2: Intent?) {
        TODO("Not yet implemented")
    }

    override fun onTitleChanged(arg0: CharSequence?, arg1: Int) {
        TODO("Not yet implemented")
    }

    override fun getParent(): Activity {
        TODO("Not yet implemented")
    }

    override fun isDestroyed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isVoiceInteractionRoot(): Boolean {
        TODO("Not yet implemented")
    }

    override fun requestShowKeyboardShortcuts() {
        TODO("Not yet implemented")
    }

    override fun isInPictureInPictureMode(): Boolean {
        TODO("Not yet implemented")
    }

    override fun startLocalVoiceInteraction(arg0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun isInMultiWindowMode(): Boolean {
        TODO("Not yet implemented")
    }

    override fun enterPictureInPictureMode(arg0: PictureInPictureParams?): Boolean {
        TODO("Not yet implemented")
    }

    override fun enterPictureInPictureMode() {
        TODO("Not yet implemented")
    }

    override fun setPictureInPictureParams(arg0: PictureInPictureParams?) {
        TODO("Not yet implemented")
    }

    override fun getMaxNumPictureInPictureActions(): Int {
        TODO("Not yet implemented")
    }

    override fun stopLocalVoiceInteraction() {
        TODO("Not yet implemented")
    }

    override fun isLocalVoiceInteractionSupported(): Boolean {
        TODO("Not yet implemented")
    }

    override fun dismissKeyboardShortcutsHelper() {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun registerActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks?) {
        arg0?.let { activity.registerActivityLifecycleCallbacks(arg0) }
    }

    override fun startManagingCursor(arg0: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun registerForContextMenu(arg0: View?) {
        TODO("Not yet implemented")
    }

    override fun unregisterForContextMenu(arg0: View?) {
        TODO("Not yet implemented")
    }

    override fun setFinishOnTouchOutside(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getChangingConfigurations(): Int {
        TODO("Not yet implemented")
    }

    override fun getContentTransitionManager(): TransitionManager {
        TODO("Not yet implemented")
    }

    override fun invalidateOptionsMenu() {
        activity.invalidateOptionsMenu()
    }

    override fun getLastNonConfigurationInstance(): Any? {
        return activity.lastNonConfigurationInstance
    }

    override fun setContentTransitionManager(arg0: TransitionManager?) {
        TODO("Not yet implemented")
    }

    override fun requestWindowFeature(arg0: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun shouldUpRecreateTask(arg0: Intent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun finishActivityFromChild(arg0: Activity?, arg1: Int) {
        TODO("Not yet implemented")
    }

    override fun startActivityFromFragment(arg0: Fragment?, arg1: Intent?, arg2: Int) {
        activity.startActivityFromFragment(arg0!!, arg1, arg2)
    }

    override fun startActivityFromFragment(
        arg0: Fragment?,
        arg1: Intent?,
        arg2: Int,
        arg3: Bundle?
    ) {
        activity.startActivityFromFragment(arg0!!, arg1, arg2, arg3)
    }

    override fun overridePendingTransition(arg0: Int, arg1: Int) {
        TODO("Not yet implemented")
    }

    override fun setProgressBarIndeterminate(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun navigateUpToFromChild(arg0: Activity?, arg1: Intent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getParentActivityIntent(): Intent {
        TODO("Not yet implemented")
    }

    override fun startNextMatchingActivity(arg0: Intent?, arg1: Bundle?): Boolean {
        TODO("Not yet implemented")
    }

    override fun startNextMatchingActivity(arg0: Intent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun finishAfterTransition() {
        TODO("Not yet implemented")
    }

    override fun getRequestedOrientation(): Int {
        TODO("Not yet implemented")
    }

    override fun startActivityForResult(arg0: Intent?, arg1: Int, arg2: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun startActivityForResult(arg0: Intent?, arg1: Int) {
        TODO("Not yet implemented")
    }

    override fun createPendingResult(arg0: Int, arg1: Intent?, arg2: Int): PendingIntent {
        TODO("Not yet implemented")
    }

    override fun finishAndRemoveTask() {
        TODO("Not yet implemented")
    }

    override fun setVolumeControlStream(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun startActivityIfNeeded(arg0: Intent?, arg1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun startActivityIfNeeded(arg0: Intent?, arg1: Int, arg2: Bundle?): Boolean {
        TODO("Not yet implemented")
    }

    override fun getVolumeControlStream(): Int {
        TODO("Not yet implemented")
    }

    override fun setSecondaryProgress(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun requestVisibleBehind(arg0: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun setFeatureDrawableUri(arg0: Int, arg1: Uri?) {
        TODO("Not yet implemented")
    }

    override fun startActivityFromChild(arg0: Activity?, arg1: Intent?, arg2: Int) {
        TODO("Not yet implemented")
    }

    override fun startActivityFromChild(arg0: Activity?, arg1: Intent?, arg2: Int, arg3: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun setFeatureDrawableResource(arg0: Int, arg1: Int) {
        TODO("Not yet implemented")
    }

    override fun setProgressBarVisibility(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setFeatureDrawableAlpha(arg0: Int, arg1: Int) {
        TODO("Not yet implemented")
    }

    override fun isActivityTransitionRunning(): Boolean {
        TODO("Not yet implemented")
    }

    override fun startIntentSenderForResult(
        arg0: IntentSender?,
        arg1: Int,
        arg2: Intent?,
        arg3: Int,
        arg4: Int,
        arg5: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun startIntentSenderForResult(
        arg0: IntentSender?,
        arg1: Int,
        arg2: Intent?,
        arg3: Int,
        arg4: Int,
        arg5: Int,
        arg6: Bundle?
    ) {
        TODO("Not yet implemented")
    }

    override fun startIntentSenderFromChild(
        arg0: Activity?,
        arg1: IntentSender?,
        arg2: Int,
        arg3: Intent?,
        arg4: Int,
        arg5: Int,
        arg6: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun startIntentSenderFromChild(
        arg0: Activity?,
        arg1: IntentSender?,
        arg2: Int,
        arg3: Intent?,
        arg4: Int,
        arg5: Int,
        arg6: Int,
        arg7: Bundle?
    ) {
        TODO("Not yet implemented")
    }

    override fun setRequestedOrientation(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun isLaunchedFromBubble(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setInheritShowWhenLocked(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun postponeEnterTransition() {
        TODO("Not yet implemented")
    }

    override fun requestDragAndDropPermissions(arg0: DragEvent?): DragAndDropPermissions {
        TODO("Not yet implemented")
    }

    override fun setEnterSharedElementCallback(arg0: SharedElementCallback?) {
        TODO("Not yet implemented")
    }

    override fun showLockTaskEscapeMessage() {
        TODO("Not yet implemented")
    }

    override fun startPostponedEnterTransition() {
        TODO("Not yet implemented")
    }

    override fun setExitSharedElementCallback(arg0: SharedElementCallback?) {
        TODO("Not yet implemented")
    }

    override fun setResult(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun setResult(arg0: Int, arg1: Intent?) {
        TODO("Not yet implemented")
    }

    override fun dump(
        arg0: String?,
        arg1: FileDescriptor?,
        arg2: PrintWriter?,
        arg3: Array<String?>?
    ) {
        TODO("Not yet implemented")
    }

    override fun getTaskId(): Int {
        TODO("Not yet implemented")
    }

    override fun getComponentName(): ComponentName {
        return activity.componentName
    }

    override fun getTitle(): CharSequence {
        return activity.title
    }

    override fun setTitle(arg0: Int) {
        activity.setTitle(arg0)
    }

    override fun setTitle(arg0: CharSequence?) {
        activity.title = arg0
    }

    override fun setVisible(arg0: Boolean) {
        activity.setVisible(arg0)
    }

    override fun setContentView(arg0: View?, arg1: ViewGroup.LayoutParams?) {
        activity.setContentView(arg0)
    }

    override fun setContentView(arg0: View?) {
        activity.setContentView(arg0)
    }

    override fun setContentView(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun getWindow(): Window {
        return activity.window
    }

    override fun setLocusContext(arg0: LocusId?, arg1: Bundle?) {
        activity.setLocusContext(arg0, arg1)
    }

    override fun getIntent(): Intent {
        return activity.intent
    }

    override fun setIntent(arg0: Intent?) {
        activity.intent
    }

    override fun getWindowManager(): WindowManager {
        return activity.windowManager
    }

    override fun getCurrentFocus(): View {
        return activity.currentFocus!!
    }

    override fun getLoaderManager(): LoaderManager {
        TODO("Not yet implemented")
    }

    override fun isChild(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getSplashScreen(): SplashScreen {
        TODO("Not yet implemented")
    }

    override fun isVoiceInteraction(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getVoiceInteractor(): VoiceInteractor {
        TODO("Not yet implemented")
    }

    override fun showAssist(arg0: Bundle?): Boolean {
        TODO("Not yet implemented")
    }

    override fun reportFullyDrawn() {
        TODO("Not yet implemented")
    }

    override fun setActionBar(arg0: Toolbar?) {
        TODO("Not yet implemented")
    }

    override fun <T : View?> findViewById(arg0: Int): T {
        return activity.findViewById<T>(arg0)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun <T : View?> requireViewById(arg0: Int): T {
        return activity.requireViewById<T>(arg0)
    }

    override fun managedQuery(
        arg0: Uri?,
        arg1: Array<String?>?,
        arg2: String?,
        arg3: Array<String?>?,
        arg4: String?
    ): Cursor {
        TODO("Not yet implemented")
    }

    override fun addContentView(arg0: View?, arg1: ViewGroup.LayoutParams?) {
        activity.addContentView(arg0, arg1)
    }

    override fun setDefaultKeyMode(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun getContentScene(): Scene {
        TODO("Not yet implemented")
    }

    override fun getFragmentManager(): FragmentManager {
        return activity.fragmentManager
    }

    override fun stopManagingCursor(arg0: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun getActionBar(): ActionBar {
        TODO("Not yet implemented")
    }

    override fun hasWindowFocus(): Boolean {
        TODO("Not yet implemented")
    }

    override fun closeOptionsMenu() {
        TODO("Not yet implemented")
    }

    override fun showDialog(arg0: Int, arg1: Bundle?): Boolean {
        TODO("Not yet implemented")
    }

    override fun showDialog(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun removeDialog(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun triggerSearch(arg0: String?, arg1: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun openOptionsMenu() {
        TODO("Not yet implemented")
    }

    override fun getSearchEvent(): SearchEvent {
        TODO("Not yet implemented")
    }

    override fun closeContextMenu() {
        TODO("Not yet implemented")
    }

    override fun startSearch(arg0: String?, arg1: Boolean, arg2: Bundle?, arg3: Boolean) {
        TODO("Not yet implemented")
    }

    override fun openContextMenu(arg0: View?) {
        TODO("Not yet implemented")
    }

    override fun dismissDialog(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun requestPermissions(arg0: Array<String?>?, arg1: Int) {
        TODO("Not yet implemented")
    }

    override fun takeKeyEvents(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setFeatureDrawable(arg0: Int, arg1: Drawable?) {
        TODO("Not yet implemented")
    }

    override fun getMenuInflater(): MenuInflater {
        TODO("Not yet implemented")
    }

    override fun finishAffinity() {
        TODO("Not yet implemented")
    }

    override fun getReferrer(): Uri {
        TODO("Not yet implemented")
    }

    override fun releaseInstance(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCallingPackage(): String {
        TODO("Not yet implemented")
    }

    override fun finishFromChild(arg0: Activity?) {
        TODO("Not yet implemented")
    }

    override fun isTaskRoot(): Boolean {
        TODO("Not yet implemented")
    }

    override fun finishActivity(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun isFinishing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun moveTaskToBack(arg0: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun setImmersive(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setTitleColor(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun getLocalClassName(): String {
        TODO("Not yet implemented")
    }

    override fun getPreferences(arg0: Int): SharedPreferences {
        TODO("Not yet implemented")
    }

    override fun getMediaController(): MediaController {
        TODO("Not yet implemented")
    }

    override fun setTaskDescription(arg0: ActivityManager.TaskDescription?) {
        TODO("Not yet implemented")
    }

    override fun isImmersive(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setMediaController(arg0: MediaController?) {
        TODO("Not yet implemented")
    }

    override fun setProgress(arg0: Int) {
        TODO("Not yet implemented")
    }

    override fun runOnUiThread(arg0: Runnable?) {
        TODO("Not yet implemented")
    }

    override fun setTranslucent(arg0: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTitleColor(): Int {
        TODO("Not yet implemented")
    }

    override fun startActionMode(arg0: ActionMode.Callback?, arg1: Int): ActionMode {
        TODO("Not yet implemented")
    }

    override fun startActionMode(arg0: ActionMode.Callback?): ActionMode {
        TODO("Not yet implemented")
    }

    override fun setVrModeEnabled(arg0: Boolean, arg1: ComponentName?) {
        TODO("Not yet implemented")
    }

    override fun setTurnScreenOn(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setShowWhenLocked(arg0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun startLockTask() {
        TODO("Not yet implemented")
    }

    override fun stopLockTask() {
        TODO("Not yet implemented")
    }

    override fun navigateUpTo(arg0: Intent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun unregisterActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks?) {
        TODO("Not yet implemented")
    }

    override fun shouldShowRequestPermissionRationale(arg0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun setProgressBarIndeterminateVisibility(arg0: Boolean) {
        TODO("Not yet implemented")
    }
}