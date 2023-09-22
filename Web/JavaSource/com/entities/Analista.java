package com.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ANALISTAS database table.
 * 
 */
@Entity
@Table(name="ANALISTAS")
public class Analista extends Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/*
	 * Las clases Tutor, Estudiante y Analista no posse un ID declarada con @Id
	 * debido a que extienden de Usuario y solo la super clase Usuario puede
	 * tener el ID en una Herencia JOINED.
	 * 
	 * Por tanto, se le agrega el ID de Analista (en este caso) como UNIQUE y 
	 * NOT NULL y se le asigna un valor con un Trigger desde la Base de Datos.
	 * 
	 * Hibernate puede leer el IdAnalista desde la base de datos pero no escribe
	 * sobre el ya que lo considera un atributo no primario, por tanto tenemos
	 * que escribirlo nosotros.
	 * */
	@Column(name = "ID_ANALISTA", nullable = false, unique = true)
	private Long idAnalista;
	
	@Column(nullable = false)
	private Boolean estado;

	@OneToMany(mappedBy="analista")
	private List<AccionConstancia> accionConstancias;

	@OneToMany(mappedBy="analista")
	private List<AccionJustificacion> accionJustificaciones;

	@OneToMany(mappedBy="analista")
	private List<AccionReclamo> accionReclamos;

	@OneToMany(mappedBy="analista")
	private List<Gestor> gestores;

	public Analista() {
	}

	public Long getIdAnalista() {
		return idAnalista;
	}

	public void setIdAnalista(Long idAnalista) {
		this.idAnalista = idAnalista;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public List<AccionConstancia> getAccionConstancias() {
		return this.accionConstancias;
	}

	public void setAccionConstancias(List<AccionConstancia> accionConstancias) {
		this.accionConstancias = accionConstancias;
	}

	public AccionConstancia addAccionConstancia(AccionConstancia accionConstancia) {
		getAccionConstancias().add(accionConstancia);
		accionConstancia.setAnalista(this);

		return accionConstancia;
	}

	public AccionConstancia removeAccionConstancia(AccionConstancia accionConstancia) {
		getAccionConstancias().remove(accionConstancia);
		accionConstancia.setAnalista(null);

		return accionConstancia;
	}

	public List<AccionJustificacion> getAccionJustificaciones() {
		return this.accionJustificaciones;
	}

	public void setAccionJustificaciones(List<AccionJustificacion> accionJustificaciones) {
		this.accionJustificaciones = accionJustificaciones;
	}

	public AccionJustificacion addAccionJustificacione(AccionJustificacion accionJustificacione) {
		getAccionJustificaciones().add(accionJustificacione);
		accionJustificacione.setAnalista(this);

		return accionJustificacione;
	}

	public AccionJustificacion removeAccionJustificacione(AccionJustificacion accionJustificacione) {
		getAccionJustificaciones().remove(accionJustificacione);
		accionJustificacione.setAnalista(null);

		return accionJustificacione;
	}

	public List<AccionReclamo> getAccionReclamos() {
		return this.accionReclamos;
	}

	public void setAccionReclamos(List<AccionReclamo> accionReclamos) {
		this.accionReclamos = accionReclamos;
	}

	public AccionReclamo addAccionReclamo(AccionReclamo accionReclamo) {
		getAccionReclamos().add(accionReclamo);
		accionReclamo.setAnalista(this);

		return accionReclamo;
	}

	public AccionReclamo removeAccionReclamo(AccionReclamo accionReclamo) {
		getAccionReclamos().remove(accionReclamo);
		accionReclamo.setAnalista(null);

		return accionReclamo;
	}

	public List<Gestor> getGestores() {
		return this.gestores;
	}

	public void setGestores(List<Gestor> gestores) {
		this.gestores = gestores;
	}

	public Gestor addGestore(Gestor gestor) {
		getGestores().add(gestor);
		gestor.setAnalista(this);

		return gestor;
	}

	public Gestor removeGestore(Gestor gestor) {
		getGestores().remove(gestor);
		gestor.setAnalista(null);

		return gestor;
	}

	@Override
	public String toString() {
		return "Analista [idAnalista=" + idAnalista + ", estado=" + estado + ", getIdUsuario()=" + getIdUsuario()
				+ ", getApellidos()=" + getApellidos() + ", getContrasena()=" + getContrasena() + ", getDepartamento()="
				+ getDepartamento() + ", getDocumento()=" + getDocumento() + ", getEmail()=" + getEmailUtec()
				+ ", getEstadoUsuario()=" + getEstadoUsuario() + ", getFecNacimiento()=" + getFecNacimiento()
				+ ", getGenero()=" + getGenero() + ", getLocalidad()=" + getLocalidad() + ", getNombreUsuario()="
				+ getNombreUsuario() + ", getNombres()=" + getNombres() + ", getTelefono()=" + getTelefono()
				+ ", getItr()=" + getItr() + "]";
	}

}