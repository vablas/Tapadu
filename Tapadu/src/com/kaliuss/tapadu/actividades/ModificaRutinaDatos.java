package com.kaliuss.tapadu.actividades;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kaliuss.tapadu.MCU;
import com.kaliuss.tapadu.entidades.Catalogo;
import com.kaliuss.tapadu.entidades.Rutina;
import com.kaliuss.tapadu.utiles.SpeechRecognitionHelper;

public class ModificaRutinaDatos extends Activity {
	private ApplicationInfo appInfoSeleccionada;
	private ImageView ivApp;
	private TextView tvNombreApp;
	private Spinner spCategoria;
	private EditText etListaEtiquetas;
	private TextView etPalabraClave;
	private Button btGuardarRutina;
	private Button btPalabraClave;
	
	private Rutina rutina;

	private int VOICE_RECOGNITION_REQUEST_CODE=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifica_rutina_datos);
		initPantalla();

	}

	private void initPantalla() {
		//Inicializamos la pantalla
		ivApp =(ImageView) findViewById(R.id.modRutina_iconApp);
		tvNombreApp =(TextView) findViewById(R.id.modRutina_nombreApp);
		spCategoria = (Spinner) findViewById(R.id.spCategoriaMod);
		ArrayAdapter adaptadorSpinner = ArrayAdapter.createFromResource( this, R.array.categoriasApp , android.R.layout.simple_spinner_item);
		adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spCategoria.setAdapter(adaptadorSpinner);

		etPalabraClave = (TextView) findViewById(R.id.palabraClaveMod);
		etListaEtiquetas = (EditText) findViewById(R.id.etListaEtiquetasMod);

		spCategoria = (Spinner) findViewById(R.id.spCategoriaMod);

		btPalabraClave =(Button) findViewById(R.id.btModRutinaPC);
		btPalabraClave.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				inicioReconocimientoVoz(null);
			}
		});

		btGuardarRutina =(Button) findViewById(R.id.btModRutina);
		btGuardarRutina.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				modificarRutina(null);
			}
		});

		
		//Cargamos los datos
		Bundle extras = getIntent().getExtras();
		String nombrePackage = (String)extras.get("rutinaPackage");
		try {
			appInfoSeleccionada = this.getPackageManager().getApplicationInfo(nombrePackage, 0);
			ivApp.setImageDrawable(appInfoSeleccionada.loadIcon(this.getPackageManager()));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rutina = Catalogo.getCatalogo().getRutinaByNombrePackage(nombrePackage);
		if(rutina!=null){
			tvNombreApp.setText(rutina.getNombre());
			String[] categorias = this.getResources().getStringArray(R.array.categoriasApp);
			for(int i=0; i<categorias.length;i++){
				if(rutina.getCategoria().equals(categorias[i])){
					spCategoria.setSelection(i);
				}
			}
			etListaEtiquetas.setText(rutina.getListaEtiquetas());
			etPalabraClave.setText(getResources().getString(R.string.txtPaso3PalabraClave) + " "+rutina.getPalabraClave());
		}

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
		}
	}

	protected void modificarRutina(Object object) {
		if(validaDatosRutina()){
			MCU mcu = new MCU();
			String palabraClave = etPalabraClave.getText().toString().replace(getResources().getString(R.string.txtPaso3PalabraClave), "");
			mcu.modificarRutina(appInfoSeleccionada, spCategoria.getSelectedItem().toString(), etListaEtiquetas.getText().toString(), palabraClave);
			Toast.makeText(this, R.string.msjRutinaModificada, Toast.LENGTH_SHORT).show();
			Intent i = new Intent(this, ConsultaRutina.class);
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
		String[] categorias = getResources().getStringArray(R.array.categoriasApp);
		if(spCategoria.getSelectedItem().toString().equals(categorias[0])){
			mensajeError += sep+getResources().getString(R.string.errCategoria) ;
			sep="\n";
			spCategoria.setBackgroundResource(R.drawable.border_error);
		}else{
			spCategoria.setBackgroundResource(R.drawable.border_normal);
		}
		if("".equals(etListaEtiquetas.getText().toString())){
			mensajeError += sep+getResources().getString(R.string.errEtiquetas) ;
			sep="\n";
			etListaEtiquetas.setBackgroundResource(R.drawable.border_error);
		}else{
			etListaEtiquetas.setBackgroundResource(R.drawable.border_normal);
		}
		if(etPalabraClave.getText().toString().equals(getResources().getString(R.string.txtPaso3PalabraClave))){
			mensajeError += sep+getResources().getString(R.string.errPalabraClave) ;
			sep="\n";
			etPalabraClave.setBackgroundResource(R.drawable.border_error);
		}else{
			palabraClave = etPalabraClave.getText().toString().replace(getResources().getString(R.string.txtPaso3PalabraClave), "");
			if(Catalogo.getCatalogo().existePalabraClave(palabraClave, rutina.getNomPackage())){
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

}
