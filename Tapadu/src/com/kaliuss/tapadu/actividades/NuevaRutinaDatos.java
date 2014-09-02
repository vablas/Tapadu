package com.kaliuss.tapadu.actividades;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kaliuss.tapadu.MCU;
import com.kaliuss.tapadu.entidades.Catalogo;
import com.kaliuss.tapadu.entidades.Rutina;
import com.kaliuss.tapadu.utiles.SpeechRecognitionHelper;

public class NuevaRutinaDatos extends Activity {
	private ApplicationInfo appInfoSeleccionada;
	private TextView etPalabraClave;
	private Button btGuardarRutina;
	private Button btPalabraClave;

	private int VOICE_RECOGNITION_REQUEST_CODE=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nueva_rutina_datos);
		initPantalla();

	}

	private void initPantalla() {

		Bundle extras = getIntent().getExtras();
		appInfoSeleccionada = (ApplicationInfo)extras.get("appInfo");

		etPalabraClave = (TextView) findViewById(R.id.palabraClave);

		btPalabraClave =(Button) findViewById(R.id.btPalabraClave);
		btPalabraClave.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				inicioReconocimientoVoz(null);
			}
		});

		btGuardarRutina =(Button) findViewById(R.id.btGuardarRutina);
		btGuardarRutina.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				guardarRutina(null);
			}
		});


	}

	protected void inicioReconocimientoVoz(Object object) {
		if(SpeechRecognitionHelper.run(this, false)){
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT,getResources().getString(R.string.msjPalabraClave));
			// Lanzamos la actividad esperando resultados
			startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK){
			//El intent nos envia un ArrayList aunque en este caso solo 
			//utilizaremos la pos.0
			ArrayList<String> matches = data.getStringArrayListExtra (RecognizerIntent.EXTRA_RESULTS);
			//Separo el texto en palabras.
			String palabras = matches.get(0).toString();
			etPalabraClave.setText(getResources().getString(R.string.txtPaso3PalabraClave) + " "+palabras);
			Toast.makeText(this, getResources().getString(R.string.txtPaso3PalabraClave) + " "+palabras, Toast.LENGTH_SHORT).show();
		}
	}

	protected void guardarRutina(Object object) {
		if(validaDatosRutina()){
			MCU mcu = new MCU();
			String palabraClave = etPalabraClave.getText().toString().replace(getResources().getString(R.string.txtPaso3PalabraClave), "").trim();
			mcu.guardarNuevaRutina(appInfoSeleccionada, palabraClave);
			Toast.makeText(this, R.string.msjRutinaGuardada, Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this, Tapadu.class);
			startActivity(i);
		}
	}

	/**
	 * @return
	 */
	private boolean validaDatosRutina() {
		String mensajeError = "";
		String sep = "";
		String palabraClave = "";
		if(etPalabraClave.getText().toString().equals(getResources().getString(R.string.txtPaso3PalabraClave))){
			mensajeError += sep+getResources().getString(R.string.errPalabraClave) ;
			sep="\n";
			etPalabraClave.setBackgroundResource(R.drawable.border_error);
		}else{
			palabraClave = etPalabraClave.getText().toString().replace(getResources().getString(R.string.txtPaso3PalabraClave), "").trim();
			if(!palabraClaveValida(palabraClave)){
				mensajeError += sep+getResources().getString(R.string.errPalabraClaveDuplicada) ;
				sep="\n";
				etPalabraClave.setBackgroundResource(R.drawable.border_error);
			}else{
				etPalabraClave.setBackgroundResource(R.drawable.border_normal);
			}
		}


		if(!"".equals(mensajeError)){
			Toast.makeText(this, mensajeError, Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
	public static boolean palabraClaveValida(String palabraClave){
		List<Rutina> listaRutinas = Catalogo.getCatalogo().getListaRutinas();
		List<String> listaPalabrasClave = despreciarArticulos(palabraClave);
		for(Rutina r:listaRutinas){
			for(String pc:listaPalabrasClave){
				if(r.getPalabraClave().toUpperCase().contains(pc)){
					return false;
				}	
			}
			
		}
		return true;
	}
	
	private static List<String> despreciarArticulos(String palabraClave){
		String vectorPalabraClave[] = palabraClave.toUpperCase().split(" ");
		List<String> listaFinal = new ArrayList<String>();
		List<String> listaArticulos = new ArrayList<String>();
		listaArticulos = initListaArticulos();
		for(int i =0; i<vectorPalabraClave.length;i++){
			if(!listaArticulos.contains(vectorPalabraClave[i])){
				listaFinal.add(vectorPalabraClave[i]);
			}
		}
		return listaFinal;
	}
	
	private static List<String> initListaArticulos(){
		List<String> listaArticulosDespreciar = new ArrayList<String>();
		listaArticulosDespreciar.add("EL");
		listaArticulosDespreciar.add("LA");
		listaArticulosDespreciar.add("LAS");
		listaArticulosDespreciar.add("LOS");
		listaArticulosDespreciar.add("EN");
		listaArticulosDespreciar.add("DEL");
		listaArticulosDespreciar.add("DESDE");
		return listaArticulosDespreciar;
	}

}
