package com.kaliuss.tapadu.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaliuss.tapadu.MCU;
import com.kaliuss.tapadu.entidades.Catalogo;
import com.kaliuss.tapadu.entidades.Rutina;

public class DetalleRutina extends Activity {
	private ApplicationInfo appInfoSeleccionada;
	private ImageView ivApp;
	private TextView tvNombreApp;
	private TextView tvPalabraClave;
	private Button btModificarRutina;
	private Button btEliminarRutina;
	
	private Rutina rutina;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detalle_rutina);
		initPantalla();

	}

	private void initPantalla() {
		ivApp =(ImageView) findViewById(R.id.detRutina_iconApp);
		tvNombreApp =(TextView) findViewById(R.id.detRutina_nombreApp);
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

		rutina = Catalogo.getCatalogo().getRutinaByNombrePackage(nombrePackage);
		if(rutina!=null){
			tvNombreApp.setText(rutina.getNombre());
			tvPalabraClave.setText(rutina.getPalabraClave());
		}
		btModificarRutina =(Button) findViewById(R.id.btModificarRutina);
		btModificarRutina.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				modificarRutina(null);
			}
		});

		btEliminarRutina =(Button) findViewById(R.id.btEliminarRutina);
		btEliminarRutina.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				eliminarRutina(null);
			}
		});

	}

	/**
	 * @param object
	 */
	protected void modificarRutina(Object object) {
		Intent i = new Intent(this, ModificaRutinaDatos.class);
		i.putExtra("rutinaPackage", rutina.getNomPackage());
		startActivity(i);

	}

	protected void regresa() {
		Intent i = new Intent(this, ConsultaRutina.class);
		startActivity(i);
	}

	/**
	 * @param object
	 */
	protected void eliminarRutina(Object object) {

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle(this.getResources().getText(R.string.titEliminarRutina));
		alertDialogBuilder
		.setMessage(this.getResources().getText(R.string.msjEliminarRutina))
		.setCancelable(false)
		.setPositiveButton(this.getResources().getText(R.string.btOk),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				MCU mcu = new MCU();
				mcu.eliminarRutinaByPalabraClave(tvPalabraClave.getText().toString());
				regresa();
			}
		})
		.setNegativeButton(this.getResources().getText(R.string.btCancel),new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			}
		});

		AlertDialog alertDialog = alertDialogBuilder.create();

		alertDialog.show();
	}


}
