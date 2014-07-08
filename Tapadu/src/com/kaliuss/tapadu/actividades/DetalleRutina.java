package com.kaliuss.tapadu.actividades;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaliuss.tapadu.entidades.Catalogo;
import com.kaliuss.tapadu.entidades.Rutina;

public class DetalleRutina extends Activity {
	private ApplicationInfo appInfoSeleccionada;
	private ImageView ivApp;
	private TextView tvNombreApp;
	private TextView tvCategoria;
	private TextView tvEtiquetas;
	private TextView tvPalabraClave;
	private Button btModificarRutina;
	private Button btSubirRutina;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalle_rutina);
		initPantalla();

	}

	private void initPantalla() {
		ivApp =(ImageView) findViewById(R.id.detRutina_iconApp);
		tvNombreApp =(TextView) findViewById(R.id.detRutina_nombreApp);
		tvCategoria =(TextView) findViewById(R.id.detRutina_categoria);
		tvEtiquetas =(TextView) findViewById(R.id.detRutina_etiquetas);
		tvPalabraClave =(TextView) findViewById(R.id.detRutina_palabraClave);

		Bundle extras = getIntent().getExtras();
		String nombrePackage = (String)extras.get("rutinaPackage");
		try {
			appInfoSeleccionada = this.getPackageManager().getApplicationInfo(nombrePackage, 0);
			ivApp.setImageDrawable(appInfoSeleccionada.loadIcon(this.getPackageManager()));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Rutina rutina = Catalogo.getCatalogo().getRutinaByNombrePackage(nombrePackage);
		if(rutina!=null){
			tvNombreApp.setText(rutina.getNombre());
			tvCategoria.setText(rutina.getCategoria());
			tvEtiquetas.setText(rutina.getListaEtiquetas());
			tvPalabraClave.setText(rutina.getPalabraClave());
		}
		btModificarRutina =(Button) findViewById(R.id.btModificarRutina);
		btModificarRutina.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				modificarRutina(null);
			}
		});

		btSubirRutina =(Button) findViewById(R.id.btSubirRutina);
		btSubirRutina.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				subirRutina(null);
			}
		});


	}

	/**
	 * @param object
	 */
	protected void subirRutina(Object object) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param object
	 */
	protected void modificarRutina(Object object) {
		// TODO Auto-generated method stub

	}


}
