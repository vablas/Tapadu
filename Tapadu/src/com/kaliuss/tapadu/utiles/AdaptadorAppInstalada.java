package com.kaliuss.tapadu.utiles;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaliuss.tapadu.actividades.R;

public class AdaptadorAppInstalada extends ArrayAdapter<ApplicationInfo> {
	private List<ApplicationInfo> listaApps = null;
    private Context context;
    private PackageManager packageManager;
    
	public AdaptadorAppInstalada(Context context, int textViewResourceId, List<ApplicationInfo> appList) {
		super(context, textViewResourceId, appList);
		this.context = context;
        this.listaApps = appList;
        packageManager = context.getPackageManager();
	}
	
	 @Override
	    public int getCount() {
	        return ((null != listaApps) ? listaApps.size() : 0);
	    }
	 
	    @Override
	    public ApplicationInfo getItem(int position) {
	        return ((null != listaApps) ? listaApps.get(position) : null);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = convertView;
	        if (null == view) {
	            LayoutInflater layoutInflater = (LayoutInflater) context
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            view = layoutInflater.inflate(R.layout.elemento_lista, null);
	        }
	 
	        ApplicationInfo data = listaApps.get(position);
	        if (null != data) {
	            TextView appName = (TextView) view.findViewById(R.id.titulo);
	            TextView packageName = (TextView) view.findViewById(R.id.subtitulo);
	            ImageView iconview = (ImageView) view.findViewById(R.id.app_icon);
	 
	            appName.setText(data.loadLabel(packageManager));
	            packageName.setText(data.loadDescription(packageManager));
	            iconview.setImageDrawable(data.loadIcon(packageManager));
	        }
	        return view;
	    }

}
