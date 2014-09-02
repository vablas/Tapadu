package com.kaliuss.tapadu.actividades;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;

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
		String msjError = "";
		if(voiceResults!=null){
			msjError = voiceResults.get(0);
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
						this.finish();
						startActivity(i);
						return;
					} catch (PackageManager.NameNotFoundException e) {

					}
				}
			}
		}
		actualizaWidgetRutinaNoEncontrada(this.getResources().getText(R.string.errNoExisteRutina1).toString()+msjError+this.getResources().getText(R.string.errNoExisteRutina2).toString());
	}

	private void actualizaWidgetRutinaNoEncontrada(String msj) {

		AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);  
		dialogo1.setTitle("Tapadu");  
		dialogo1.setMessage(msj);            
		dialogo1.setCancelable(false); 
		dialogo1.setPositiveButton(this.getResources().getText(R.string.btOk).toString(), new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialogo1, int id) {  
				terminaAplicacion();
			}  
		});  
		dialogo1.show();        
	}
	
	private void terminaAplicacion(){
		Intent i = new Intent(this, Tapadu.class);
		startActivity(i);
		
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
	}


}
