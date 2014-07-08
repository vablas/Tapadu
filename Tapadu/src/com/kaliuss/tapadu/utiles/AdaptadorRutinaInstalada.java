package com.kaliuss.tapadu.utiles;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaliuss.tapadu.actividades.R;
import com.kaliuss.tapadu.entidades.Rutina;

public class AdaptadorRutinaInstalada extends BaseAdapter {
	private List<Rutina> listaRutinas = null;
	private Context context;
	private PackageManager packageManager;

	public AdaptadorRutinaInstalada(Context context, int textViewResourceId, List<Rutina> appList) {
		this.context = context;
		this.listaRutinas = appList;
		packageManager = context.getPackageManager();
	}

	@Override
	public int getCount() {
		return ((null != listaRutinas) ? listaRutinas.size() : 0);
	}

	@Override
	public Rutina getItem(int position) {
		return ((null != listaRutinas) ? listaRutinas.get(position) : null);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (null == view) {
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.elemento_lista, null);
		}

		Rutina rutina = listaRutinas.get(position);
		if (null != rutina) {
			TextView appName = (TextView) view.findViewById(R.id.titulo);
			TextView palabraClave = (TextView) view.findViewById(R.id.subtitulo);
			ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);

			appName.setText(rutina.getNombre());
			palabraClave.setText(rutina.getPalabraClave());
			ApplicationInfo app;
			try {
				app = packageManager.getApplicationInfo(rutina.getNomPackage(), 0);
				iconview.setImageDrawable(app.loadIcon(packageManager));	 
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			} 
		}
		return view;
	}

}
