/**
 * 
 */
package com.kaliuss.tapadu.utiles;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kaliuss.tapadu.entidades.Rutina;

public class JsonUtiles {

	public static String escribeJSON(List<Rutina> listaRutinas) {
		JSONArray jsonArray = new JSONArray();
		try {
			for(Rutina rut:listaRutinas){
				JSONObject object = new JSONObject();
				object.put("nombre", rut.getNombre());
				object.put("nomPackage", rut.getNomPackage());
				object.put("palabraClave", rut.getPalabraClave());
				object.put("listaEtiquetas", rut.getListaEtiquetas());
				object.put("categoria", rut.getCategoria());
				object.put("estado", rut.getEstado());
				object.put("idCatalogo", rut.getIdCatalogo());
				jsonArray.put(object);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonArray.toString();
	} 

	public static List<Rutina> parseaJSON(String fichero) {
		List<Rutina> listaRutinas = new ArrayList<Rutina>();
		try {
			JSONArray jsonArray = new JSONArray(fichero);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Rutina rutina = new Rutina();
				rutina.setNombre(jsonObject.getString("nombre").trim());
				rutina.setNomPackage(jsonObject.getString("nomPackage").trim());
				rutina.setPalabraClave(jsonObject.getString("palabraClave").trim());
				rutina.setListaEtiquetas(jsonObject.getString("listaEtiquetas").trim());
				rutina.setCategoria(jsonObject.getString("categoria").trim());
				rutina.setEstado(jsonObject.getString("estado").trim());
				rutina.setIdCatalogo(jsonObject.getString("idCatalogo").trim());
				listaRutinas.add(rutina);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaRutinas;
	} 

}
