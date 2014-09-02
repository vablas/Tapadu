/**
 * 
 */
package com.kaliuss.tapadu.utiles;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.kaliuss.tapadu.actividades.R;

/**
 * @author vablas
 *
 */
/**
 * A helper class for speech recognition
 */
public class SpeechRecognitionHelper {
	/**
	 * Running the recognition process. Checks availability of recognition Activity,
	 * If Activity is absent, send user to Google Play to install Google Voice Search.
	 * If Activity is available, send Intent for running.
	 *
	 * @param callingActivity = Activity, that initializing recognition process
	 */
	public static boolean run(Context context, boolean esWidget) {
		// check if there is recognition Activity
		if (!isSpeechRecognitionActivityPresented(context)) {
			// if no, then showing notification to install Voice Search
			Toast.makeText(context, context.getResources().getText(R.string.msjErrorInstalarGoogleVoice), Toast.LENGTH_LONG).show();
			// start installing process
			if(!esWidget){
				installGoogleVoiceSearch(context);
			}
			return isSpeechRecognitionActivityPresented(context);
		}
		return true;
	}

	/**
	 * Checks availability of speech recognizing Activity
	 *
	 * @param callerActivity – Activity that called the checking
	 * @return true – if Activity there available, false – if Activity is absent
	 */
	private static boolean isSpeechRecognitionActivityPresented(Context callerActivity) {
		try {
			// getting an instance of package manager
			PackageManager pm = callerActivity.getPackageManager();
			// a list of activities, which can process speech recognition Intent
			List activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);

			if (activities.size() != 0) {    // if list not empty
				return true;                // then we can recognize the speech
			}
		} catch (Exception e) {

		}

		return false; // we have no activities to recognize the speech
	}

	/**
	 * Asking the permission for installing Google Voice Search.
	 * If permission granted – sent user to Google Play
	 * @param callerActivity – Activity, that initialized installing
	 */
	private static void installGoogleVoiceSearch(final Context ownerActivity) {

		// creating a dialog asking user if he want
		// to install the Voice Search
		Dialog dialog = new AlertDialog.Builder(ownerActivity)
		.setMessage(ownerActivity.getResources().getText(R.string.msjErrorInstalarGoogleVoice))    // dialog message
		.setTitle(ownerActivity.getResources().getText(R.string.msjPreguntarInstalarGoogleVoice))    // dialog header
		.setPositiveButton(ownerActivity.getResources().getText(R.string.btInstall), new DialogInterface.OnClickListener() {    // confirm button

			// Install Button click handler
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					// creating an Intent for opening applications page in Google Play
					// Voice Search package name: com.google.android.voicesearch
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.voicesearch"));
					// setting flags to avoid going in application history (Activity call stack)
					intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					// sending an Intent
					ownerActivity.startActivity(intent);
				} catch (Exception ex) {
					// if something going wrong
					// doing nothing
				}
			}})

			.setNegativeButton(ownerActivity.getResources().getText(R.string.btCancel), null)    // cancel button
			.create();

		dialog.show();    // showing dialog
	}
}
