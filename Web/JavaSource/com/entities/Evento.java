package com.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import com.entities.enums.Modalidad;

import java.util.List;


/**
 * The persistent class for the EVENTOS database table.
 * 
 */
@Entity
@Table(name="EVENTOS")
public class Evento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator = "SEQ_EVENTOS")
	@Column(name="ID_EVENTO")
	private Long idEvento;
	
	@Column(nullable = false, unique = true)
	private String titulo;
	
	@Column(nullable = false, name = "FECHA_INICIO")
	private LocalDate fechaInicio;
	
	@Column(nullable = false, name = "FECHA_FIN")
	private LocalDate fechaFin;
	
	@Column(nullable = false)
	private Boolean estado;
	
	@OneToMany(mappedBy="evento")
	private List<Asistencia> asistencias;
	
	// TODO: La bi-direccionaldiad es incesaria? No necesito saber cuales
	// constancias se hicieron de este evento
	
	@OneToMany(mappedBy="evento")
	private List<Constancia> constancias;

	@Column(nullable = false)
	private String localizacion;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Modalidad modalidad;
	
	@ManyToOne
	@JoinColumn(name = "ID_ITR")
	private Itr itr;
	
	@ManyToOne
	@JoinColumn(name="ID_TIPO_EVENTO")
	private TipoEvento tipoEvento;

	@OneToMany(mappedBy="evento")
	private List<Gestor> gestores;

	@OneToMany(mappedBy="evento")
	private List<Justificacion> justificaciones;

	@OneToMany(mappedBy="evento")
	private List<Reclamo> reclamos;

	@OneToMany(mappedBy="evento")
	private List<Responsable> responsables;

	public Evento() {
	}

	public Long getIdEvento() {
		return this.idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public LocalDate getFechaFin() {
		return this.fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public LocalDate getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(LocalDate fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public List<Asistencia> getAsistencias() {
		return this.asistencias;
	}

	public void setAsistencias(List<Asistencia> asistencias) {
		this.asistencias = asistencias;
	}

	public Asistencia addAsistencia(Asistencia asistencia) {
		getAsistencias().add(asistencia);
		asistencia.setEvento(this);

		return asistencia;
	}

	public Asistencia removeAsistencia(Asistencia asistencia) {
		getAsistencias().remove(asistencia);
		asistencia.setEvento(null);

		return asistencia;
	}

	public List<Constancia> getConstancias() {
		return this.constancias;
	}

	public void setConstancias(List<Constancia> constancias) {
		this.constancias = constancias;
	}

	public Constancia addConstancia(Constancia constancia) {
		getConstancias().add(constancia);
		constancia.setEvento(this);

		return constancia;
	}

	public Constancia removeConstancia(Constancia constancia) {
		getConstancias().remove(constancia);
		constancia.setEvento(null);

		return constancia;
	}

	public TipoEvento getTipoEvento() {
		return this.tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public List<Gestor> getGestores() {
		return this.gestores;
	}

	public void setGestores(List<Gestor> gestores) {
		this.gestores = gestores;
	}

	public Gestor addGestore(Gestor gestore) {
		getGestores().add(gestore);
		gestore.setEvento(this);

		return gestore;
	}

	public Gestor removeGestore(Gestor gestore) {
		getGestores().remove(gestore);
		gestore.setEvento(null);

		return gestore;
	}

	public List<Justificacion> getJustificaciones() {
		return this.justificaciones;
	}

	public void setJustificaciones(List<Justificacion> justificaciones) {
		this.justificaciones = justificaciones;
	}

	public Justificacion addJustificacione(Justificacion justificacione) {
		getJustificaciones().add(justificacione);
		justificacione.setEvento(this);

		return justificacione;
	}

	public Justificacion removeJustificacione(Justificacion justificacione) {
		getJustificaciones().remove(justificacione);
		justificacione.setEvento(null);

		return justificacione;
	}

	public List<Reclamo> getReclamos() {
		return this.reclamos;
	}

	public void setReclamos(List<Reclamo> reclamos) {
		this.reclamos = reclamos;
	}

	public Reclamo addReclamo(Reclamo reclamo) {
		getReclamos().add(reclamo);
		reclamo.setEvento(this);

		return reclamo;
	}

	public Reclamo removeReclamo(Reclamo reclamo) {
		getReclamos().remove(reclamo);
		reclamo.setEvento(null);

		return reclamo;
	}

	public List<Responsable> getResponsables() {
		return this.responsables;
	}

	public void setResponsables(List<Responsable> responsables) {
		this.responsables = responsables;
	}

	public Responsable addResponsable(Responsable responsable) {
		getResponsables().add(responsable);
		responsable.setEvento(this);

		return responsable;
	}

	public Responsable removeResponsable(Responsable responsable) {
		getResponsables().remove(responsable);
		responsable.setEvento(null);

		return responsable;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public Modalidad getModalidad() {
		return modalidad;
	}

	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}

	public Itr getItr() {
		return itr;
	}

	public void setItr(Itr itr) {
		this.itr = itr;
	}
}