package com.joybox.joyplug.plugcore.delegate

import android.app.ActionBar
import android.app.Activity
import android.app.ActivityManager.TaskDescription
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
import android.content.IntentSender.SendIntentException
import android.content.LocusId
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources.Theme
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
import java.io.FileDescriptor
import java.io.PrintWriter
import java.util.function.Consumer

interface HostActivityDelegate {
    /******************* Fake APIs  */
    fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher

    fun isChangingConfigurations(): Boolean

    fun finish()

    fun getLayoutInflater(): LayoutInflater

    fun recreate()

    fun getCallingActivity(): ComponentName

    fun onMultiWindowModeChanged(arg0: Boolean)

    fun onMultiWindowModeChanged(arg0: Boolean, arg1: Configuration?)

    fun onSaveInstanceState(arg0: Bundle?, arg1: PersistableBundle?)

    fun onPictureInPictureModeChanged(arg0: Boolean)

    fun onPictureInPictureModeChanged(arg0: Boolean, arg1: Configuration?)

    fun onPictureInPictureUiStateChanged(arg0: PictureInPictureUiState?)

    fun onTopResumedActivityChanged(arg0: Boolean)

    fun onCreateDescription(): CharSequence

    fun onProvideAssistData(arg0: Bundle?)

    fun onPerformDirectAction(
        arg0: String?, arg1: Bundle?, arg2: CancellationSignal?,
        arg3: Consumer<Bundle?>?
    )

    fun onProvideKeyboardShortcuts(
        arg0: List<KeyboardShortcutGroup?>?,
        arg1: Menu?,
        arg2: Int
    )

    fun onRestoreInstanceState(arg0: Bundle?, arg1: PersistableBundle?)

    fun onProvideAssistContent(arg0: AssistContent?)

    fun onLocalVoiceInteractionStarted()

    fun onLocalVoiceInteractionStopped()

    fun onPictureInPictureRequested(): Boolean

    fun onOptionsItemSelected(arg0: MenuItem?): Boolean

    fun onContextMenuClosed(arg0: Menu?)

    fun onPrepareNavigateUpTaskStack(arg0: TaskStackBuilder?)

    fun onCreateNavigateUpTaskStack(arg0: TaskStackBuilder?)

    fun onRetainNonConfigurationInstance(): Any

    fun onWindowAttributesChanged(arg0: WindowManager.LayoutParams?)

    fun onWindowFocusChanged(arg0: Boolean)

    fun onCreateOptionsMenu(arg0: Menu?): Boolean

    fun onConfigurationChanged(arg0: Configuration?)

    fun onPrepareOptionsMenu(arg0: Menu?): Boolean

    fun onGenericMotionEvent(arg0: MotionEvent?): Boolean

    fun onCreateContextMenu(arg0: ContextMenu?, arg1: View?, arg2: ContextMenuInfo?)

    fun onContextItemSelected(arg0: MenuItem?): Boolean

    fun onNavigateUpFromChild(arg0: Activity?): Boolean

    fun onOptionsMenuClosed(arg0: Menu?)

    fun onDetachedFromWindow()

    fun onActionModeStarted(arg0: ActionMode?)

    fun onActionModeFinished(arg0: ActionMode?)

    fun onRequestPermissionsResult(arg0: Int, arg1: Array<String?>?, arg2: IntArray?)

    fun onVisibleBehindCanceled()

    fun onEnterAnimationComplete()

    fun onWindowStartingActionMode(arg0: ActionMode.Callback?, arg1: Int): ActionMode?

    fun onWindowStartingActionMode(arg0: ActionMode.Callback?): ActionMode

    fun onCreate(arg0: Bundle?, arg1: PersistableBundle?)

    fun onPostCreate(arg0: Bundle?, arg1: PersistableBundle?)

    fun onStateNotSaved()

    fun onCreateThumbnail(arg0: Bitmap?, arg1: Canvas?): Boolean

    fun onGetDirectActions(arg0: CancellationSignal?, arg1: Consumer<List<DirectAction?>?>?)

    fun onLowMemory()

    fun onTrimMemory(arg0: Int)

    fun onAttachFragment(arg0: Fragment?)

    fun onKeyLongPress(arg0: Int, arg1: KeyEvent?): Boolean

    fun onKeyMultiple(arg0: Int, arg1: Int, arg2: KeyEvent?): Boolean

    fun onKeyUp(arg0: Int, arg1: KeyEvent?): Boolean

    fun onKeyDown(arg0: Int, arg1: KeyEvent?): Boolean

    fun onBackPressed()

    fun onContentChanged()

    fun onAttachedToWindow()

    fun onKeyShortcut(arg0: Int, arg1: KeyEvent?): Boolean

    fun onMenuOpened(arg0: Int, arg1: Menu?): Boolean

    fun onTouchEvent(arg0: MotionEvent?): Boolean

    fun onUserInteraction()

    fun onPanelClosed(arg0: Int, arg1: Menu?)

    fun onCreatePanelMenu(arg0: Int, arg1: Menu?): Boolean

    fun onTrackballEvent(arg0: MotionEvent?): Boolean

    fun onMenuItemSelected(arg0: Int, arg1: MenuItem?): Boolean

    fun onPreparePanel(arg0: Int, arg1: View?, arg2: Menu?): Boolean

    fun onCreatePanelView(arg0: Int): View?

    fun onNavigateUp(): Boolean

    fun onSearchRequested(): Boolean

    fun onSearchRequested(arg0: SearchEvent?): Boolean

    fun onActivityReenter(arg0: Int, arg1: Intent?)

    fun onProvideReferrer(): Uri

    fun onCreateView(arg0: View?, arg1: String?, arg2: Context?, arg3: AttributeSet?): View?

    fun onCreateView(arg0: String?, arg1: Context?, arg2: AttributeSet?): View?

    fun onPointerCaptureChanged(arg0: Boolean)

    fun onSaveInstanceState(arg0: Bundle?)

    fun onRestoreInstanceState(arg0: Bundle?)

    fun onChildTitleChanged(arg0: Activity?, arg1: CharSequence?)

    fun onApplyThemeResource(arg0: Theme?, arg1: Int, arg2: Boolean)

    fun onStart()

    fun onCreate(arg0: Bundle?)

    fun onStop()

    fun onNewIntent(arg0: Intent?)

    fun onPostCreate(arg0: Bundle?)

    fun onPostResume()

    fun onResume()

    fun onUserLeaveHint()

    fun onRestart()

    fun onPause()

    fun onDestroy()

    fun onPrepareDialog(arg0: Int, arg1: Dialog?)

    fun onPrepareDialog(arg0: Int, arg1: Dialog?, arg2: Bundle?)

    fun onCreateDialog(arg0: Int, arg1: Bundle?): Dialog

    fun onCreateDialog(arg0: Int): Dialog

    fun onActivityResult(arg0: Int, arg1: Int, arg2: Intent?)

    fun onTitleChanged(arg0: CharSequence?, arg1: Int)

    fun dispatchKeyShortcutEvent(arg0: KeyEvent?): Boolean

    fun dispatchPopulateAccessibilityEvent(arg0: AccessibilityEvent?): Boolean

    fun dispatchTrackballEvent(arg0: MotionEvent?): Boolean

    fun dispatchGenericMotionEvent(arg0: MotionEvent?): Boolean

    fun dispatchKeyEvent(arg0: KeyEvent?): Boolean

    fun dispatchTouchEvent(arg0: MotionEvent?): Boolean

    fun getParent(): Activity?

    fun isDestroyed(): Boolean

    fun isVoiceInteractionRoot(): Boolean

    fun requestShowKeyboardShortcuts()

    fun isInPictureInPictureMode(): Boolean

    fun startLocalVoiceInteraction(arg0: Bundle?)

    fun isInMultiWindowMode(): Boolean

    fun enterPictureInPictureMode(arg0: PictureInPictureParams?): Boolean

    fun enterPictureInPictureMode()

    fun setPictureInPictureParams(arg0: PictureInPictureParams?)

    fun getMaxNumPictureInPictureActions(): Int

    fun stopLocalVoiceInteraction()

    fun isLocalVoiceInteractionSupported(): Boolean

    fun dismissKeyboardShortcutsHelper()

    fun startManagingCursor(arg0: Cursor?)

    fun registerForContextMenu(arg0: View?)

    fun unregisterForContextMenu(arg0: View?)

    fun setFinishOnTouchOutside(arg0: Boolean)

    fun getChangingConfigurations(): Int

    fun getContentTransitionManager(): TransitionManager

    fun invalidateOptionsMenu()

    fun getLastNonConfigurationInstance(): Any?

    fun setContentTransitionManager(arg0: TransitionManager?)

    fun requestWindowFeature(arg0: Int): Boolean

    fun shouldUpRecreateTask(arg0: Intent?): Boolean

    fun finishActivityFromChild(arg0: Activity?, arg1: Int)

    fun startActivityFromFragment(arg0: Fragment?, arg1: Intent?, arg2: Int)

    fun startActivityFromFragment(arg0: Fragment?, arg1: Intent?, arg2: Int, arg3: Bundle?)

    fun overridePendingTransition(arg0: Int, arg1: Int)

    fun setProgressBarIndeterminate(arg0: Boolean)

    fun navigateUpToFromChild(arg0: Activity?, arg1: Intent?): Boolean

    fun getParentActivityIntent(): Intent

    fun startNextMatchingActivity(arg0: Intent?, arg1: Bundle?): Boolean

    fun startNextMatchingActivity(arg0: Intent?): Boolean

    fun finishAfterTransition()

    fun getRequestedOrientation(): Int

    fun startActivityForResult(arg0: Intent?, arg1: Int, arg2: Bundle?)

    fun startActivityForResult(arg0: Intent?, arg1: Int)

    fun createPendingResult(arg0: Int, arg1: Intent?, arg2: Int): PendingIntent

    fun finishAndRemoveTask()

    fun setVolumeControlStream(arg0: Int)

    fun startActivityIfNeeded(arg0: Intent?, arg1: Int): Boolean

    fun startActivityIfNeeded(arg0: Intent?, arg1: Int, arg2: Bundle?): Boolean

    fun getVolumeControlStream(): Int

    fun setSecondaryProgress(arg0: Int)

    fun requestVisibleBehind(arg0: Boolean): Boolean

    fun setFeatureDrawableUri(arg0: Int, arg1: Uri?)

    fun startActivityFromChild(arg0: Activity?, arg1: Intent?, arg2: Int)

    fun startActivityFromChild(
        arg0: Activity?, arg1: Intent?, arg2: Int,
        arg3: Bundle?
    )

    fun setFeatureDrawableResource(arg0: Int, arg1: Int)

    fun setProgressBarVisibility(arg0: Boolean)

    fun setFeatureDrawableAlpha(arg0: Int, arg1: Int)

    fun isActivityTransitionRunning(): Boolean

    @Throws(SendIntentException::class)
    fun startIntentSenderForResult(
        arg0: IntentSender?, arg1: Int, arg2: Intent?, arg3: Int,
        arg4: Int, arg5: Int
    )

    @Throws(SendIntentException::class)
    fun startIntentSenderForResult(
        arg0: IntentSender?, arg1: Int, arg2: Intent?, arg3: Int,
        arg4: Int, arg5: Int, arg6: Bundle?
    )

    @Throws(SendIntentException::class)
    fun startIntentSenderFromChild(
        arg0: Activity?, arg1: IntentSender?, arg2: Int,
        arg3: Intent?, arg4: Int, arg5: Int, arg6: Int
    )

    @Throws(SendIntentException::class)
    fun startIntentSenderFromChild(
        arg0: Activity?, arg1: IntentSender?, arg2: Int,
        arg3: Intent?, arg4: Int, arg5: Int, arg6: Int, arg7: Bundle?
    )

    fun setRequestedOrientation(arg0: Int)

    fun isLaunchedFromBubble(): Boolean

    fun setInheritShowWhenLocked(arg0: Boolean)

    fun postponeEnterTransition()

    fun requestDragAndDropPermissions(arg0: DragEvent?): DragAndDropPermissions

    fun setEnterSharedElementCallback(arg0: SharedElementCallback?)

    fun showLockTaskEscapeMessage()

    fun startPostponedEnterTransition()

    fun setExitSharedElementCallback(arg0: SharedElementCallback?)

    fun setResult(arg0: Int)

    fun setResult(arg0: Int, arg1: Intent?)

    fun dump(arg0: String?, arg1: FileDescriptor?, arg2: PrintWriter?, arg3: Array<String?>?)

    fun getTaskId(): Int

    fun getComponentName(): ComponentName

    fun getTitle(): CharSequence

    fun setTitle(arg0: Int)

    fun setTitle(arg0: CharSequence?)

    fun setVisible(arg0: Boolean)

    fun setContentView(arg0: View?, arg1: ViewGroup.LayoutParams?)

    fun setContentView(arg0: View?)

    fun setContentView(arg0: Int)

    fun getWindow(): Window

    fun setLocusContext(arg0: LocusId?, arg1: Bundle?)

    fun getIntent(): Intent

    fun setIntent(arg0: Intent?)

    fun getWindowManager(): WindowManager

    fun getCurrentFocus(): View

    fun getLoaderManager(): LoaderManager

    fun isChild(): Boolean

    fun getSplashScreen(): SplashScreen

    fun isVoiceInteraction(): Boolean

    fun getVoiceInteractor(): VoiceInteractor

    fun showAssist(arg0: Bundle?): Boolean

    fun reportFullyDrawn()

    fun setActionBar(arg0: Toolbar?)

    fun <T : View?> findViewById(arg0: Int): T

    fun <T : View?> requireViewById(arg0: Int): T

    fun managedQuery(
        arg0: Uri?,
        arg1: Array<String?>?,
        arg2: String?,
        arg3: Array<String?>?,
        arg4: String?
    ): Cursor

    fun addContentView(arg0: View?, arg1: ViewGroup.LayoutParams?)

    fun setDefaultKeyMode(arg0: Int)

    fun getContentScene(): Scene

    fun getFragmentManager(): FragmentManager

    fun stopManagingCursor(arg0: Cursor?)

    fun getActionBar(): ActionBar

    fun hasWindowFocus(): Boolean

    fun closeOptionsMenu()

    fun showDialog(arg0: Int, arg1: Bundle?): Boolean

    fun showDialog(arg0: Int)

    fun removeDialog(arg0: Int)

    fun triggerSearch(arg0: String?, arg1: Bundle?)

    fun openOptionsMenu()

    fun getSearchEvent(): SearchEvent

    fun closeContextMenu()

    fun startSearch(arg0: String?, arg1: Boolean, arg2: Bundle?, arg3: Boolean)

    fun openContextMenu(arg0: View?)

    fun dismissDialog(arg0: Int)

    fun requestPermissions(arg0: Array<String?>?, arg1: Int)

    fun takeKeyEvents(arg0: Boolean)

    fun setFeatureDrawable(arg0: Int, arg1: Drawable?)

    fun getMenuInflater(): MenuInflater

    fun finishAffinity()

    fun getReferrer(): Uri

    fun releaseInstance(): Boolean

    fun getCallingPackage(): String

    fun finishFromChild(arg0: Activity?)

    fun isTaskRoot(): Boolean

    fun finishActivity(arg0: Int)

    fun isFinishing(): Boolean

    fun moveTaskToBack(arg0: Boolean): Boolean

    fun setImmersive(arg0: Boolean)

    fun setTitleColor(arg0: Int)

    fun getLocalClassName(): String

    fun getPreferences(arg0: Int): SharedPreferences

    fun getMediaController(): MediaController

    fun setTaskDescription(arg0: TaskDescription?)

    fun isImmersive(): Boolean

    fun setMediaController(arg0: MediaController?)

    fun setProgress(arg0: Int)

    fun runOnUiThread(arg0: Runnable?)

    fun setTranslucent(arg0: Boolean): Boolean

    fun getTitleColor(): Int

    fun startActionMode(arg0: ActionMode.Callback?, arg1: Int): ActionMode

    fun startActionMode(arg0: ActionMode.Callback?): ActionMode

    @Throws(PackageManager.NameNotFoundException::class)
    fun setVrModeEnabled(arg0: Boolean, arg1: ComponentName?)

    fun setTurnScreenOn(arg0: Boolean)

    fun setShowWhenLocked(arg0: Boolean)

    fun startLockTask()

    fun stopLockTask()

    fun navigateUpTo(arg0: Intent?): Boolean

    fun unregisterActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks?)

    fun registerActivityLifecycleCallbacks(arg0: Application.ActivityLifecycleCallbacks?)

    fun shouldShowRequestPermissionRationale(arg0: String?): Boolean

    fun setProgressBarIndeterminateVisibility(arg0: Boolean)
}