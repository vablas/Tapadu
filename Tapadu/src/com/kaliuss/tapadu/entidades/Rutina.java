package com.kaliuss.tapadu.entidades;


public class Rutina {
	String nombre;
	String nomPackage;
	String palabraClave;
	
	public Rutina() {
		super();
	}

	/**
	 * @param nombre
	 * @param nomPackage
	 * @param palabraClave
	 * @param estado
	 * @param categoria
	 * @param idCatalogo
	 * @param listaEtiquetas
	 */
	public Rutina(String nombre, String nomPackage, String palabraClave) {
		super();
		this.nombre = nombre;
		this.nomPackage = nomPackage;
		this.palabraClave = palabraClave;
	}



	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNomPackage() {
		return nomPackage;
	}
	
	public void setNomPackage(String nomPackage) {
		this.nomPackage = nomPackage;
	}
	
	public String getPalabraClave() {
		return palabraClave;
	}
	
	public void setPalabraClave(String palabraClave) {
		this.palabraClave = palabraClave;
	}

}
