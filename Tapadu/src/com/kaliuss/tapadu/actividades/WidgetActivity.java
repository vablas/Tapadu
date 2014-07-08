package com.kaliuss.tapadu.actividades;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.kaliuss.tapadu.MCU;

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
					    finish();
					    return;
					} catch (PackageManager.NameNotFoundException e) {

					}
				}
			}
			Toast.makeText(this, this.getResources().getText(R.string.errNoExisteRutina), Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, this.getResources().getText(R.string.errNoRecuperaVoz), Toast.LENGTH_SHORT).show();
		}

	}


}
