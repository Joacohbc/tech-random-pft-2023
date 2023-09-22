package com.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;

import com.entities.enums.EstadoSolicitudes;

import java.util.Arrays;
import java.util.List;

/**
 * The persistent class for the CONSTANCIAS database table.
 * 
 */
@Entity
@Table(name = "CONSTANCIAS")
public class Constancia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_CONSTANCIAS")
	@Column(name = "ID_CONSTANCIA")
	private Long idConstancia;

	@Column(nullable = false)
	private String detalle;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private EstadoSolicitudes estado;

	@Column(nullable = false, name = "FECHA_HORA")
	private LocalDateTime fechaHora;

	@Lob
	@Column(nullable = true)
	private byte[] archivo;

	@OneToMany(mappedBy = "constancia", fetch = FetchType.EAGER)
	private List<AccionConstancia> accionConstancias;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_ESTUDIANTE", referencedColumnName = "ID_ESTUDIANTE")
	private Estudiante estudiante;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_EVENTO")
	private Evento evento;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TIPO_CONSTANCIA")
	private TipoConstancia tipoConstancia;

	public Constancia() {
	}

	public Long getIdConstancia() {
		return this.idConstancia;
	}

	public void setIdConstancia(Long idConstancia) {
		this.idConstancia = idConstancia;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public EstadoSolicitudes getEstado() {
		return this.estado;
	}

	public void setEstado(EstadoSolicitudes estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaHora() {
		return this.fechaHora;
	}

	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}

	public byte[] getArchivo() {
		return archivo;
	}

	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}

	public List<AccionConstancia> getAccionConstancias() {
		return this.accionConstancias;
	}

	public void setAccionConstancias(List<AccionConstancia> accionConstancias) {
		this.accionConstancias = accionConstancias;
	}

	public AccionConstancia addAccionConstancia(AccionConstancia accionConstancia) {
		getAccionConstancias().add(accionConstancia);
		accionConstancia.setConstancia(this);

		return accionConstancia;
	}

	public AccionConstancia removeAccionConstancia(AccionConstancia accionConstancia) {
		getAccionConstancias().remove(accionConstancia);
		accionConstancia.setConstancia(null);

		return accionConstancia;
	}

	public Estudiante getEstudiante() {
		return this.estudiante;
	}

	public void setEstudiante(Estudiante estudiante) {
		this.estudiante = estudiante;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public TipoConstancia getTipoConstancia() {
		return this.tipoConstancia;
	}

	public void setTipoConstancia(TipoConstancia tipoConstancia) {
		this.tipoConstancia = tipoConstancia;
	}

	@Override
	public String toString() {
		return "Constancia [idConstancia=" + idConstancia + ", detalle=" + detalle + ", estado=" + estado
				+ ", fechaHora=" + fechaHora + ", accionConstancias=" + accionConstancias + ", archivo="
				+ Arrays.toString(archivo) + ", estudiante=" + estudiante + ", evento=" + evento + ", tipoConstancia="
				+ tipoConstancia + "]";
	}

}