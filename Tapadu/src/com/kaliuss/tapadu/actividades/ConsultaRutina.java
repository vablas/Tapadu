package com.kaliuss.tapadu.actividades;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.kaliuss.tapadu.entidades.Catalogo;
import com.kaliuss.tapadu.entidades.Rutina;
import com.kaliuss.tapadu.utiles.AdaptadorRutinaInstalada;

public class ConsultaRutina extends ListActivity {

	private AdaptadorRutinaInstalada adaptadorRutina = null;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consulta_rutina);
		new CargaRutinas().execute();
	}

	@Override protected void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		Rutina rutina = (Rutina)getListAdapter().getItem(position);
		Intent i = new Intent(this, DetalleRutina.class);
		i.putExtra("rutinaPackage", rutina.getNomPackage());
		startActivity(i);

	}

	private class CargaRutinas extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progress = null;

		@Override
		protected Void doInBackground(Void... params) {
			Catalogo catalogo = Catalogo.getCatalogo();
			adaptadorRutina = new AdaptadorRutinaInstalada(ConsultaRutina.this, R.layout.elemento_lista, catalogo.getListaRutinas());
			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Void result) {
			setListAdapter(adaptadorRutina);
			progress.dismiss();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(ConsultaRutina.this, null, Tapadu.context.getResources().getString(R.string.msjLoadingRutinas));
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}

}
