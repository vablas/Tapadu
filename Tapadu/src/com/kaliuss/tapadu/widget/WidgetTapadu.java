/**
 * 
 */
package com.kaliuss.tapadu.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.RemoteViews;

import com.kaliuss.tapadu.actividades.R;
import com.kaliuss.tapadu.actividades.WidgetActivity;

public class WidgetTapadu extends AppWidgetProvider{

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		ComponentName thisWidget = new ComponentName(context, WidgetTapadu.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		for (int widgetId : allWidgetIds) {

			RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget);
			remoteViews.setTextViewText(R.id.msjWidget, context.getResources().getText(R.string.wgPalabraClave));
			// La actividad que manejará los resultados del reconocimiento de voz
			Intent activityIntent = new Intent(context, WidgetActivity.class);
			activityIntent.putExtra("widgetID", widgetId);
			PendingIntent resultsPendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);

			// Intent para reconocimiento de voz
			Intent voiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, context.getResources().getString(R.string.msjPalabraClave));
			voiceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			voiceIntent.putExtra(RecognizerIntent.EXTRA_RESULTS_PENDINGINTENT, resultsPendingIntent);

			// Se lanza el intent de reconocimiento de voz sobre la que se ha indicado que se pasaran los resultados
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, voiceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.wgPalabraClave, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
	}
	
}