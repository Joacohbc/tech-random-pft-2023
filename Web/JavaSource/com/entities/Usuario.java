package com.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.entities.enums.Departamento;
import com.entities.enums.EstadoUsuario;
import com.entities.enums.Genero;

/**
 * The persistent class for the USUARIOS database table.
 * 
 */
@Entity
@Table(name = "USUARIOS")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_USUARIO")
	private Long idUsuario;

	@Column(nullable = false, unique = true)
	private String documento;

	@Column(unique = true, nullable = false, name = "NOMBRE_USUARIO")
	private String nombreUsuario;
	
	@Column(nullable = false)
	private String contrasena;

	@Column(nullable = false, unique = true, name = "EMAIL_UTEC")
	private String emailUtec;
	
	@Column(nullable = false, unique = true, name = "EMAIL_PERSONAL")
	private String emailPersonal;
	
	@Column(nullable = false)
	private String nombres;

	@Column(nullable = false)
	private String apellidos;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Genero genero;

	@Column(nullable = false, name = "FEC_NACIMIENTO")
	private LocalDate fecNacimiento;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Departamento departamento;
	
	@Column(nullable = false)
	private String localidad;
	
	@Column(nullable = false)
	private String telefono;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "ESTADO")
	private EstadoUsuario estadoUsuario;
	
	@ManyToOne
	@JoinColumn(name = "ID_ITR")
	private Itr itr;

	public Usuario() {
	}
	
	public int getEdad() {
		Period periodo = Period.between(getFecNacimiento(), LocalDate.now());
		return periodo.getYears();
	}

	public Long getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public Departamento getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public String getDocumento() {
		return this.documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getEmailUtec() {
		return emailUtec;
	}

	public void setEmailUtec(String emailUtec) {
		this.emailUtec = emailUtec;
	}

	public String getEmailPersonal() {
		return emailPersonal;
	}

	public void setEmailPersonal(String emailPersonal) {
		this.emailPersonal = emailPersonal;
	}

	public EstadoUsuario getEstadoUsuario() {
		return this.estadoUsuario;
	}

	public void setEstadoUsuario(EstadoUsuario estado) {
		this.estadoUsuario = estado;
	}

	public LocalDate getFecNacimiento() {
		return this.fecNacimiento;
	}

	public void setFecNacimiento(LocalDate fecNacimiento) {
		this.fecNacimiento = fecNacimiento;
	}

	public Genero getGenero() {
		return this.genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public String getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Itr getItr() {
		return this.itr;
	}

	public void setItr(Itr itr) {
		this.itr = itr;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(idUsuario, other.idUsuario);
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", documento=" + documento + ", nombreUsuario=" + nombreUsuario
				+ ", contrasena=" + contrasena + ", emailUtec=" + emailUtec + ", emailPersonal=" + emailPersonal
				+ ", nombres=" + nombres + ", apellidos=" + apellidos + ", genero=" + genero + ", fecNacimiento="
				+ fecNacimiento + ", departamento=" + departamento + ", localidad=" + localidad + ", telefono="
				+ telefono + ", estadoUsuario=" + estadoUsuario + ", itr=" + itr + "]";
	}

	
}