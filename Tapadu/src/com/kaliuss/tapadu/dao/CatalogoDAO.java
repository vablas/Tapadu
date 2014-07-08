/**
 * 
 */
package com.kaliuss.tapadu.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.kaliuss.tapadu.actividades.Tapadu;
import com.kaliuss.tapadu.entidades.Catalogo;
import com.kaliuss.tapadu.entidades.Rutina;
import com.kaliuss.tapadu.utiles.JsonUtiles;

public class CatalogoDAO {
	public static String NOMBRE_FICHERO="catalogo.jsn";
	
	
	/**
	 * @param context
	 */
	public CatalogoDAO() {
		super();
	}


	public void guardarCatalogo(){
		Catalogo catalogo = Catalogo.getCatalogo();
		String texto = JsonUtiles.escribeJSON(catalogo.getListaRutinas());
		FileOutputStream fos;
		try {
			fos = Tapadu.context.openFileOutput(NOMBRE_FICHERO,Context.MODE_PRIVATE);
			fos.write(texto.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			Log.e("TAPADU",e.getMessage(),e);
		} catch (IOException e) {
			Log.e("TAPADU",e.getMessage(),e);
		}
	}
	
	public List<Rutina> leerCatalogo(){
		String contenidoFichero = getContenidoFichero();
		List<Rutina> listaRutinas = new ArrayList<Rutina>();
		if(!"".equals(contenidoFichero)){
			listaRutinas = JsonUtiles.parseaJSON(contenidoFichero);
		}
		return listaRutinas;
	}
	
	public String getContenidoFichero(){
		String linea = "";
		try {
			FileInputStream f = Tapadu.context.openFileInput(NOMBRE_FICHERO);
			BufferedReader entrada = new BufferedReader(new InputStreamReader(f));
			linea = entrada.readLine();
			f.close();
		} catch (Exception e) {
			Log.e("TAPADU", e.getMessage(), e);
		}
		return linea;
	}

}
