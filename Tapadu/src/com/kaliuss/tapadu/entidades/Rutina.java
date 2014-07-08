package com.kaliuss.tapadu.entidades;


public class Rutina {
	String nombre;
	String nomPackage;
	String palabraClave;
	String estado;
	String categoria;
	String idCatalogo;
	String listaEtiquetas;


	public Rutina() {
		super();
		idCatalogo = "";
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
	public Rutina(String nombre, String nomPackage, String palabraClave,
			String estado, String categoria, String idCatalogo,
			String listaEtiquetas) {
		super();
		this.nombre = nombre;
		this.nomPackage = nomPackage;
		this.palabraClave = palabraClave;
		this.estado = estado;
		this.categoria = categoria;
		this.idCatalogo = idCatalogo;
		this.listaEtiquetas = listaEtiquetas;
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

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getIdCatalogo() {
		return this.idCatalogo;
	}

	public void setIdCatalogo(String idCatalogo) {
		this.idCatalogo = idCatalogo;
	}

	public String getListaEtiquetas() {
		return this.listaEtiquetas;
	}

	public void setListaEtiquetas(String listaEtiquetas) {
		this.listaEtiquetas = listaEtiquetas;
	}

}
