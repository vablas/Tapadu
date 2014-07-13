/**
 * 
 */
package com.kaliuss.tapadu.entidades;

import java.util.List;

import com.kaliuss.tapadu.dao.CatalogoDAO;

public class Catalogo {
	public List<Rutina> listaRutinas;
	public CatalogoDAO catalogoDAO = new CatalogoDAO();
	private static Catalogo catalogo;
	
	
	private Catalogo() {
		super();
		listaRutinas = catalogoDAO.leerCatalogo();
	}
	
	public static Catalogo getCatalogo() {
		if(catalogo==null){
			catalogo = new Catalogo();
		}
		return catalogo;
	}

	public void addRutina(Rutina rutina){
		listaRutinas.add(rutina);
		actualizaCatalogo();
	}
	
	public void actualizaCatalogo(){
		catalogoDAO.guardarCatalogo();
		catalogoDAO.leerCatalogo();
	}
	
	public boolean existeRutina(String packageName){
		for(Rutina rutina:listaRutinas){
			if(rutina.getNomPackage().equals(packageName)){
				return true;
			}
		}
		return false;
	}
	
	public boolean existePalabraClave(String palabraClave, String nombrePackage){
		for(Rutina rutina:listaRutinas){
			if(rutina.getPalabraClave().equals(palabraClave.trim())
					&& !nombrePackage.equals(rutina.getNomPackage())){
				return true;
			}
		}
		return false;
	}
	
	public Rutina getRutinaByPalabraClave(String palabraClave){
		for(Rutina rutina:listaRutinas){
			if(rutina.getPalabraClave().trim().toUpperCase().equals(palabraClave.toUpperCase())){
				return rutina;
			}
		}
		return null;
	}
	
	public Rutina getRutinaByNombrePackage(String nombrePackage){
		for(Rutina rutina:listaRutinas){
			if(rutina.getNomPackage().trim().toUpperCase().equals(nombrePackage.toUpperCase())){
				return rutina;
			}
		}
		return null;
	}
	
	public List<Rutina> getListaRutinas() {
		return this.listaRutinas;
	}

	public void setListaRutinas(List<Rutina> listaRutinas) {
		this.listaRutinas = listaRutinas;
	}

	/**
	 * @param palabraClave
	 */
	public boolean eliminaRutinaByPalabraClave(String palabraClave) {
		for(Rutina rutina:listaRutinas){
			if(rutina.getPalabraClave().trim().toUpperCase().equals(palabraClave.toUpperCase())){
				listaRutinas.remove(rutina);
				actualizaCatalogo();
				return true;
			}
		}
		return false;
	}
	
	

}
