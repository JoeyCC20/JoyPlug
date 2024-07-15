package com.joybox.joyplug.plugcore.api;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.DirectAction;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.PictureInPictureUiState;
import android.app.SharedElementCallback;
import android.app.TaskStackBuilder;
import android.app.VoiceInteractor;
import android.app.assist.AssistContent;
import android.content.ComponentCallbacks2;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.LocusId;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.PersistableBundle;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toolbar;
import android.window.OnBackInvokedDispatcher;
import android.window.SplashScreen;

import androidx.annotation.Nullable;

import com.joybox.joyplug.plugcore.PluginApplication;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Consumer;

public interface IPluginActivity extends ComponentCallbacks2, Window.Callback, KeyEvent.Callback, LayoutInflater.Factory2 {

    /******************* Fake APIs **********************/
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher();

    public boolean isChangingConfigurations();

    public void finish();

    public LayoutInflater getLayoutInflater();

    public void recreate();

    public ComponentName getCallingActivity();

    public void onMultiWindowModeChanged(boolean arg0);

    public void onMultiWindowModeChanged(boolean arg0, Configuration arg1);

    public void onSaveInstanceState(Bundle arg0, PersistableBundle arg1);

    public void onPictureInPictureModeChanged(boolean arg0);

    public void onPictureInPictureModeChanged(boolean arg0, Configuration arg1);

    public void onPictureInPictureUiStateChanged(PictureInPictureUiState arg0);

    public void onTopResumedActivityChanged(boolean arg0);

    public CharSequence onCreateDescription();

    public void onProvideAssistData(Bundle arg0);

    public void onPerformDirectAction(String arg0, Bundle arg1, CancellationSignal arg2,
                                      Consumer<Bundle> arg3);

    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> arg0, Menu arg1, int arg2);

    public void onRestoreInstanceState(Bundle arg0, PersistableBundle arg1);

    public void onProvideAssistContent(AssistContent arg0);

    public void onLocalVoiceInteractionStarted();

    public void onLocalVoiceInteractionStopped();

    public boolean onPictureInPictureRequested();

    public boolean onOptionsItemSelected(MenuItem arg0);

    public void onContextMenuClosed(Menu arg0);

    public void onPrepareNavigateUpTaskStack(TaskStackBuilder arg0);

    public void onCreateNavigateUpTaskStack(TaskStackBuilder arg0);

    public Object onRetainNonConfigurationInstance();

    public void onWindowAttributesChanged(WindowManager.LayoutParams arg0);

    public void onWindowFocusChanged(boolean arg0);

    public boolean onCreateOptionsMenu(Menu arg0);

    public void onConfigurationChanged(Configuration arg0);

    public boolean onPrepareOptionsMenu(Menu arg0);

    public boolean onGenericMotionEvent(MotionEvent arg0);

    public void onCreateContextMenu(ContextMenu arg0, View arg1, ContextMenu.ContextMenuInfo arg2);

    public boolean onContextItemSelected(MenuItem arg0);

    public boolean onNavigateUpFromChild(Activity arg0);

    public void onOptionsMenuClosed(Menu arg0);

    public void onDetachedFromWindow();

    public void onActionModeStarted(ActionMode arg0);

    public void onActionModeFinished(ActionMode arg0);

    public void onRequestPermissionsResult(int arg0, String[] arg1, int[] arg2);

    public void onVisibleBehindCanceled();

    public void onEnterAnimationComplete();

    public ActionMode onWindowStartingActionMode(@Nullable ActionMode.Callback arg0, int arg1);

    public ActionMode onWindowStartingActionMode(@Nullable ActionMode.Callback arg0);

    public void onCreate(Bundle arg0, PersistableBundle arg1);

    public void onPostCreate(Bundle arg0, PersistableBundle arg1);

    public void onStateNotSaved();

    public boolean onCreateThumbnail(Bitmap arg0, Canvas arg1);

    public void onGetDirectActions(CancellationSignal arg0, Consumer<List<DirectAction>> arg1);

    public void onLowMemory();

    public void onTrimMemory(int arg0);

    public void onAttachFragment(Fragment arg0);

    public boolean onKeyLongPress(int arg0, KeyEvent arg1);

    public boolean onKeyMultiple(int arg0, int arg1, KeyEvent arg2);

    public boolean onKeyUp(int arg0, KeyEvent arg1);

    public boolean onKeyDown(int arg0, KeyEvent arg1);

    public void onBackPressed();

    public void onContentChanged();

    public void onAttachedToWindow();

    public boolean onKeyShortcut(int arg0, KeyEvent arg1);

    public boolean onMenuOpened(int arg0, Menu arg1);

    public boolean onTouchEvent(MotionEvent arg0);

    public void onUserInteraction();

    public void onPanelClosed(int arg0, Menu arg1);

    public boolean onCreatePanelMenu(int arg0, Menu arg1);

    public boolean onTrackballEvent(MotionEvent arg0);

    public boolean onMenuItemSelected(int arg0, MenuItem arg1);

    public boolean onPreparePanel(int arg0, View arg1, Menu arg2);

    public View onCreatePanelView(int arg0);

    public boolean onNavigateUp();

    public boolean onSearchRequested();

    public boolean onSearchRequested(SearchEvent arg0);

    public void onActivityReenter(int arg0, Intent arg1);

    public Uri onProvideReferrer();

    public void onPointerCaptureChanged(boolean arg0);

    public void onSaveInstanceState(Bundle arg0);

    public void onRestoreInstanceState(Bundle arg0);

    public abstract void onChildTitleChanged(Activity arg0, CharSequence arg1);

//    public void onApplyThemeResource(Resources.Theme arg0, int arg1, boolean arg2);

    public void onStart();

    public void onCreate(Bundle arg0);

    public void onStop();

    public void onNewIntent(Intent arg0);

    public void onPostCreate(Bundle arg0);

    public void onPostResume();

    public void onResume();

    public void onUserLeaveHint();

    public void onRestart();

    public void onPause();

    public void onDestroy();

    public void onPrepareDialog(int arg0, Dialog arg1);

    public void onPrepareDialog(int arg0, Dialog arg1, Bundle arg2);

    public Dialog onCreateDialog(int arg0, Bundle arg1);

    public Dialog onCreateDialog(int arg0);

    public void onActivityResult(int arg0, int arg1, Intent arg2);

    public void onTitleChanged(CharSequence arg0, int arg1);

    public boolean dispatchKeyShortcutEvent(KeyEvent arg0);

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent arg0);

    public boolean dispatchTrackballEvent(MotionEvent arg0);

    public boolean dispatchGenericMotionEvent(MotionEvent arg0);

    public boolean dispatchKeyEvent(KeyEvent arg0);

    public boolean dispatchTouchEvent(MotionEvent arg0);

    public abstract Activity getParent();

    public boolean isDestroyed();

    public boolean isVoiceInteractionRoot();

    public void requestShowKeyboardShortcuts();

    public boolean isInPictureInPictureMode();

    public void startLocalVoiceInteraction(Bundle arg0);

    public boolean isInMultiWindowMode();

    public boolean enterPictureInPictureMode(PictureInPictureParams arg0);

    public void enterPictureInPictureMode();

    public void setPictureInPictureParams(PictureInPictureParams arg0);

    public int getMaxNumPictureInPictureActions();

    public void stopLocalVoiceInteraction();

    public boolean isLocalVoiceInteractionSupported();

    public void dismissKeyboardShortcutsHelper();
    public void registerActivityLifecycleCallbacks(PluginApplication.PluginActivityLifecycleCallbacks arg0);

    public void startManagingCursor(Cursor arg0);

    public void registerForContextMenu(View arg0);

    public void unregisterForContextMenu(View arg0);

    public void setFinishOnTouchOutside(boolean arg0);

    public int getChangingConfigurations();

    public TransitionManager getContentTransitionManager();

    public void invalidateOptionsMenu();

    public Object getLastNonConfigurationInstance();

    public void setContentTransitionManager(TransitionManager arg0);

    public boolean requestWindowFeature(int arg0);

    public boolean shouldUpRecreateTask(Intent arg0);

    public abstract void finishActivityFromChild(Activity arg0, int arg1);

    public void startActivityFromFragment(Fragment arg0, Intent arg1, int arg2);

    public void startActivityFromFragment(Fragment arg0, Intent arg1, int arg2, Bundle arg3);

    public void overridePendingTransition(int arg0, int arg1);

    public void setProgressBarIndeterminate(boolean arg0);

    public abstract boolean navigateUpToFromChild(Activity arg0, Intent arg1);

    public Intent getParentActivityIntent();

    public boolean startNextMatchingActivity(Intent arg0, Bundle arg1);

    public boolean startNextMatchingActivity(Intent arg0);

    public void finishAfterTransition();

    public int getRequestedOrientation();

    public void startActivityForResult(Intent arg0, int arg1, Bundle arg2);

    public void startActivityForResult(Intent arg0, int arg1);

    public PendingIntent createPendingResult(int arg0, Intent arg1, int arg2);

    public void finishAndRemoveTask();

    public void setVolumeControlStream(int arg0);

    public boolean startActivityIfNeeded(Intent arg0, int arg1);

    public boolean startActivityIfNeeded(Intent arg0, int arg1, Bundle arg2);

    public int getVolumeControlStream();

    public void setSecondaryProgress(int arg0);

    public boolean requestVisibleBehind(boolean arg0);

    public void setFeatureDrawableUri(int arg0, Uri arg1);

    public abstract void startActivityFromChild(Activity arg0, Intent arg1, int arg2);

    public abstract void startActivityFromChild(Activity arg0, Intent arg1, int arg2,
                                                Bundle arg3);

    public void setFeatureDrawableResource(int arg0, int arg1);

    public void setProgressBarVisibility(boolean arg0);

    public void setFeatureDrawableAlpha(int arg0, int arg1);

    public boolean isActivityTransitionRunning();

    public void startIntentSenderForResult(IntentSender arg0, int arg1, Intent arg2, int arg3,
                                           int arg4, int arg5) throws IntentSender.SendIntentException;

    public void startIntentSenderForResult(IntentSender arg0, int arg1, Intent arg2, int arg3,
                                           int arg4, int arg5, Bundle arg6) throws IntentSender.SendIntentException;

    public abstract void startIntentSenderFromChild(Activity arg0, IntentSender arg1, int arg2,
                                                    Intent arg3, int arg4, int arg5, int arg6) throws IntentSender.SendIntentException;

    public abstract void startIntentSenderFromChild(Activity arg0, IntentSender arg1, int arg2,
                                                    Intent arg3, int arg4, int arg5, int arg6, Bundle arg7) throws
            IntentSender.SendIntentException;

    public void setRequestedOrientation(int arg0);

    public boolean isLaunchedFromBubble();

    public void setInheritShowWhenLocked(boolean arg0);

    public void postponeEnterTransition();

    public DragAndDropPermissions requestDragAndDropPermissions(DragEvent arg0);

    public void setEnterSharedElementCallback(SharedElementCallback arg0);

    public void showLockTaskEscapeMessage();

    public void startPostponedEnterTransition();

    public void setExitSharedElementCallback(SharedElementCallback arg0);

    public void setResult(int arg0);

    public void setResult(int arg0, Intent arg1);

    public void dump(String arg0, FileDescriptor arg1, PrintWriter arg2, String[] arg3);

    public int getTaskId();

    public ComponentName getComponentName();

    public CharSequence getTitle();

    public void setTitle(int arg0);

    public void setTitle(CharSequence arg0);

    public void setVisible(boolean arg0);

    public void setContentView(View arg0, ViewGroup.LayoutParams arg1);

    public void setContentView(View arg0);

    public void setContentView(int arg0);

    public Window getWindow();

    public void setLocusContext(LocusId arg0, Bundle arg1);

    public Intent getIntent();

    public void setIntent(Intent arg0);

    public WindowManager getWindowManager();

    public View getCurrentFocus();

    public LoaderManager getLoaderManager();

    public boolean isChild();

    public SplashScreen getSplashScreen();

    public boolean isVoiceInteraction();

    public VoiceInteractor getVoiceInteractor();

    public boolean showAssist(Bundle arg0);

    public void reportFullyDrawn();

    public void setActionBar(Toolbar arg0);

    public <T extends View> T findViewById(int arg0);

    public <T extends View> T requireViewById(int arg0);

    public Cursor managedQuery(Uri arg0, String[] arg1, String arg2, String[] arg3, String arg4);

    public void addContentView(View arg0, ViewGroup.LayoutParams arg1);

    public void setDefaultKeyMode(int arg0);

    public Scene getContentScene();

    public FragmentManager getFragmentManager();

    public void stopManagingCursor(Cursor arg0);

    public ActionBar getActionBar();

    public boolean hasWindowFocus();

    public void closeOptionsMenu();

    public boolean showDialog(int arg0, Bundle arg1);

    public void showDialog(int arg0);

    public void removeDialog(int arg0);

    public void triggerSearch(String arg0, Bundle arg1);

    public void openOptionsMenu();

    public SearchEvent getSearchEvent();

    public void closeContextMenu();

    public void startSearch(String arg0, boolean arg1, Bundle arg2, boolean arg3);

    public void openContextMenu(View arg0);

    public void dismissDialog(int arg0);

    public void requestPermissions(String[] arg0, int arg1);

    public void takeKeyEvents(boolean arg0);

    public void setFeatureDrawable(int arg0, Drawable arg1);

    public MenuInflater getMenuInflater();

    public void finishAffinity();

    public Uri getReferrer();

    public boolean releaseInstance();

    public String getCallingPackage();

    public abstract void finishFromChild(Activity arg0);

    public boolean isTaskRoot();

    public void finishActivity(int arg0);

    public boolean isFinishing();

    public boolean moveTaskToBack(boolean arg0);

    public void setImmersive(boolean arg0);

    public void setTitleColor(int arg0);

    public String getLocalClassName();

    public SharedPreferences getPreferences(int arg0);

    public MediaController getMediaController();

    public void setTaskDescription(ActivityManager.TaskDescription arg0);

    public boolean isImmersive();

    public void setMediaController(MediaController arg0);

    public void setProgress(int arg0);

    public void runOnUiThread(Runnable arg0);

    public boolean setTranslucent(boolean arg0);

    public int getTitleColor();

    public ActionMode startActionMode(ActionMode.Callback arg0, int arg1);

    public ActionMode startActionMode(ActionMode.Callback arg0);

    public void setVrModeEnabled(boolean arg0, ComponentName arg1) throws
            PackageManager.NameNotFoundException;

    public void setTurnScreenOn(boolean arg0);

    public void setShowWhenLocked(boolean arg0);

    public void startLockTask();

    public void stopLockTask();

    public boolean navigateUpTo(Intent arg0);

    public void unregisterActivityLifecycleCallbacks(PluginApplication.PluginActivityLifecycleCallbacks arg0);

    public boolean shouldShowRequestPermissionRationale(String arg0);

    public void setProgressBarIndeterminateVisibility(boolean arg0);
}
