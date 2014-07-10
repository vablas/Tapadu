/**
 * 
 */
package com.kaliuss.tapadu;

import android.content.pm.ApplicationInfo;

import com.kaliuss.tapadu.actividades.Tapadu;
import com.kaliuss.tapadu.entidades.Catalogo;
import com.kaliuss.tapadu.entidades.Rutina;

public class MCU {
	Catalogo catalogo = null;

	public MCU(){
		init();
	}
	
	public void init(){
		catalogo = Catalogo.getCatalogo();
	}

	public void guardarNuevaRutina(ApplicationInfo appInfo, 
			String categoria, String listaEtiquetas, 
			String palabraClave){
		Rutina rutina = new Rutina();
		rutina.setNombre(appInfo.loadLabel(Tapadu.context.getPackageManager()).toString());
		rutina.setNomPackage(appInfo.packageName);
		rutina.setCategoria(categoria);
		rutina.setListaEtiquetas(listaEtiquetas);
		rutina.setPalabraClave(palabraClave);
		rutina.setEstado("");
		
		catalogo.addRutina(rutina);
	}
	
	public String getPackageRutina(String palabraClave){
		
		Rutina rutina = catalogo.getRutinaByPalabraClave(palabraClave);
		if(rutina!=null){
			return rutina.getNomPackage();
		}
		return "";
	}

	/**
	 * @param string
	 */
	public boolean eliminarRutinaByPalabraClave(String palabraClave) {
		return catalogo.eliminaRutinaByPalabraClave(palabraClave);
		
	}

}
