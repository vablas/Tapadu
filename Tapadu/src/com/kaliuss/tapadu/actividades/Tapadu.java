package com.kaliuss.tapadu.actividades;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Tapadu extends Activity {

	private Button btNuevaRutina;
	private Button btConsRutina;
	private Button btSubeCatalogo;
	private Button btDescargaCatalogo;
	private Button btConfig;
	
	public static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tapadu);

		btNuevaRutina =(Button) findViewById(R.id.btNuevaRutina);
		btNuevaRutina.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarNuevaRutina(null);
			}
		});

		btConsRutina =(Button) findViewById(R.id.btConsRutina);
		btConsRutina.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarConsultaRutina(null);
			}
		});

		btSubeCatalogo =(Button) findViewById(R.id.btSubeCatalogo);
		btSubeCatalogo.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarSubirCatalogo(null);
			}
		});
		
		btDescargaCatalogo =(Button) findViewById(R.id.btDescargaCatalogo);
		btDescargaCatalogo.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarDescargaCatalogo(null);
			}
		});

		btConfig =(Button) findViewById(R.id.btConfig);
		btConfig.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				lanzarConfig(null);
			}
		});
		
		context = getApplicationContext();
	}

	public void lanzarNuevaRutina(View view){
		Intent i = new Intent(this, NuevaRutinaAplicacion.class);
		startActivity(i);
		
	}

	public void lanzarConsultaRutina(View view){
		Intent i = new Intent(this, ConsultaRutina.class);
		startActivity(i);
	}

	public void lanzarSubirCatalogo(View view){
		//	      Intent i = new Intent(this, AcercaDe.class);
		//	            startActivity(i);
	}
	
	public void lanzarDescargaCatalogo(View view){
		//	      Intent i = new Intent(this, AcercaDe.class);
		//	            startActivity(i);
	}

	public void lanzarConfig(View view){
		//	      Intent i = new Intent(this, AcercaDe.class);
		//	            startActivity(i);
	}
}
