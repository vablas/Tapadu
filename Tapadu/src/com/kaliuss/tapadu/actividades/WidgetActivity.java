package com.kaliuss.tapadu.actividades;

import java.util.ArrayList;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.RemoteViews;

import com.kaliuss.tapadu.MCU;
import com.kaliuss.tapadu.widget.WidgetTapadu;

public class WidgetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Tapadu.context = getApplicationContext();
		lanzarAplicacion();
	}

	private void lanzarAplicacion() {
		ArrayList<String> voiceResults = this.getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
		MCU mcu = new MCU();
		if(voiceResults!=null){
			for(String palabraClave:voiceResults){
				String nomPackage = mcu.getPackageRutina(palabraClave);
				if(!"".equals(nomPackage)){
					Intent i;
					PackageManager manager = getPackageManager();
					try {
						i = manager.getLaunchIntentForPackage(nomPackage);
						if (i == null)
							throw new PackageManager.NameNotFoundException();
						i.addCategory(Intent.CATEGORY_LAUNCHER);
						startActivity(i);
						actualizaWidgetRutinaNoEncontrada(this.getResources().getText(R.string.wgPalabraClave).toString());
						return;
					} catch (PackageManager.NameNotFoundException e) {

					}
				}
			}
		}
		actualizaWidgetRutinaNoEncontrada(this.getResources().getText(R.string.errNoExisteRutina).toString());
		finish();
	}
	
	private void actualizaWidgetRutinaNoEncontrada(String msj) {
		AppWidgetManager appWidgetManager= AppWidgetManager.getInstance(this);
		RemoteViews remoteViews = new RemoteViews(this.getPackageName(),R.layout.widget);
		ComponentName thisWidget = new ComponentName(this, WidgetTapadu.class);
		remoteViews.setTextViewText(R.id.msjWidget, msj);
		appWidgetManager.updateAppWidget(thisWidget, remoteViews);
	}


}
