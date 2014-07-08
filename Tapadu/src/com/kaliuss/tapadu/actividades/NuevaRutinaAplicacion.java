package com.kaliuss.tapadu.actividades;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.kaliuss.tapadu.entidades.Catalogo;
import com.kaliuss.tapadu.utiles.AdaptadorAppInstalada;

public class NuevaRutinaAplicacion extends ListActivity {

	private PackageManager packageManager = null;
	private List<ApplicationInfo> listaApps = new ArrayList<ApplicationInfo>();
	private AdaptadorAppInstalada adaptadorApp = null;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nueva_rutina_app);
		packageManager = getPackageManager();
		new CargaAplicaciones().execute();
	}

	@Override protected void onListItemClick(ListView listView, View view, int position, long id) {
		super.onListItemClick(listView, view, position, id);
		ApplicationInfo appInfo = (ApplicationInfo)getListAdapter().getItem(position);
		Intent i = new Intent(this, NuevaRutinaDatos.class);
		i.putExtra("appInfo", appInfo);
		startActivity(i);
	}

	private class CargaAplicaciones extends AsyncTask<Void, Void, Void> {
		private ProgressDialog progress = null;

		@Override
		protected Void doInBackground(Void... params) {
			List<ApplicationInfo> listaAppsTodas = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
			Catalogo catalogo = Catalogo.getCatalogo();
			for(ApplicationInfo appInfo:listaAppsTodas){
				if(!catalogo.existeRutina(appInfo.packageName)){
					listaApps.add(appInfo);
				}
			}
			adaptadorApp = new AdaptadorAppInstalada(NuevaRutinaAplicacion.this, R.layout.elemento_lista, listaApps);
			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Void result) {
			setListAdapter(adaptadorApp);
			progress.dismiss();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(NuevaRutinaAplicacion.this, null, "Loading application info...");
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}
	}

}
