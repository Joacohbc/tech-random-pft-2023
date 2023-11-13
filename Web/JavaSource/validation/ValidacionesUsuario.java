package validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import com.entities.Itr;
import com.entities.Usuario;
import com.entities.enums.Departamento;
import com.entities.enums.EstadoUsuario;
import com.entities.enums.Genero;

import validation.Validaciones.ValidacionesFecha;

public class ValidacionesUsuario {

	public ValidacionesUsuario() {
	}

	public enum TipoUsuarioEmail {
		UTEC("UTEC"), GENERAL("General");

		private String tipo;

		@Override
		public String toString() {
			return tipo;
		}

		private TipoUsuarioEmail(String tipo) {
			this.tipo = tipo;
		}
	}

	public enum TipoUsuarioDocumento {
		URUGUAYO("Uruguayo"), NO_URUGAUYO("No Uruguayo");

		private String tipo;

		@Override
		public String toString() {
			return tipo;
		}

		private TipoUsuarioDocumento(String tipo) {
			this.tipo = tipo;
		}
	}

	public static ValidationObject ValidarUsuario(Usuario usuario, TipoUsuarioDocumento tipoDocumento) {

		if(usuario == null)
			return new ValidationObject("La entidad no puede ser nula");
		
		ValidationObject error;
		if (tipoDocumento == TipoUsuarioDocumento.URUGUAYO) {
			error = validarDocumentoUruguayo(usuario.getDocumento());
			if (!error.isValid()) {
				return error;
			}
		} else {
			error = validarDocumentoNoUruguayo(usuario.getDocumento());
			if (!error.isValid()) {
				return error;
			}
		}

		error = validarContrasena(usuario.getContrasena());
		if (!error.isValid()) {
			return error;
		}
		
		error = validarEmailUTEC(usuario.getEmailUtec());
		if (!error.isValid()) {
			return error;
		}
		
		error = validarEmail(usuario.getEmailPersonal());
		if (!error.isValid()) {
			return error;
		}

		error = validarNombres(usuario.getNombres());
		if (!error.isValid()) {
			return error;
		}

		error = validarApellido(usuario.getApellidos());
		if (!error.isValid()) {
			return error;
		}

		error = validarGenero(usuario.getGenero());
		if (!error.isValid()) {
			return error;
		}

		error = validarFechaNacimiento(usuario.getFecNacimiento());
		if (!error.isValid()) {
			return error;
		}

		error = validarDepartamento(usuario.getDepartamento());
		if (!error.isValid()) {
			return error;
		}

		error = validarLocalidad(usuario.getLocalidad());
		if (!error.isValid()) {
			return error;
		}

		error = validarTelefono(usuario.getTelefono());
		if (!error.isValid()) {
			return error;
		}

		error = validarEstadoUsuario(usuario.getEstadoUsuario());
		if (!error.isValid()) {
			return error;
		}

		error = validarItr(usuario.getItr());
		if (!error.isValid()) {
			return error;
		}

		return ValidationObject.VALID;
	}


	public static ValidationObject ValidarUsuarioSinContrasenia(Usuario usuario, TipoUsuarioDocumento tipoDocumento) {

		if(usuario == null)
			return new ValidationObject("La entidad no puede ser nula");
		
		ValidationObject error;
		if (tipoDocumento == TipoUsuarioDocumento.URUGUAYO) {
			error = validarDocumentoUruguayo(usuario.getDocumento());
			if (!error.isValid()) {
				return error;
			}
		} else {
			error = validarDocumentoNoUruguayo(usuario.getDocumento());
			if (!error.isValid()) {
				return error;
			}
		}
		
		error = validarEmailUTEC(usuario.getEmailUtec());
		if (!error.isValid()) {
			return error;
		}
		
		error = validarEmail(usuario.getEmailPersonal());
		if (!error.isValid()) {
			return error;
		}

		error = validarNombres(usuario.getNombres());
		if (!error.isValid()) {
			return error;
		}

		error = validarApellido(usuario.getApellidos());
		if (!error.isValid()) {
			return error;
		}

		error = validarGenero(usuario.getGenero());
		if (!error.isValid()) {
			return error;
		}

		error = validarFechaNacimiento(usuario.getFecNacimiento());
		if (!error.isValid()) {
			return error;
		}

		error = validarDepartamento(usuario.getDepartamento());
		if (!error.isValid()) {
			return error;
		}

		error = validarLocalidad(usuario.getLocalidad());
		if (!error.isValid()) {
			return error;
		}

		error = validarTelefono(usuario.getTelefono());
		if (!error.isValid()) {
			return error;
		}

		error = validarEstadoUsuario(usuario.getEstadoUsuario());
		if (!error.isValid()) {
			return error;
		}

		error = validarItr(usuario.getItr());
		if (!error.isValid()) {
			return error;
		}

		return ValidationObject.VALID;
	}


	public static ValidationObject validarDocumentoUruguayo(String documento) {
		return Validaciones.ValidarCedulaUruguaya(documento) ? ValidationObject.VALID
				: new ValidationObject("La cédula uruguaya debe contener los puntos, guiones y el dígito verificador válido");
	}

	public static ValidationObject validarDocumentoNoUruguayo(String documento) {
		return Validaciones.ValidarLargo(documento, 20) ? ValidationObject.VALID
				: new ValidationObject("El documento debe contener un máximo de 20 caracteres");
	}

	public static ValidationObject validarNombreUsuario(String nombreUsuario) {
		if (!Validaciones.ValidarLargo(nombreUsuario, 6, 64)) {
			return new ValidationObject("El nombre de usuario debe tener un mínimo de 6 caracteres y un máximo de 64");
		}

		if (!Pattern.matches("[a-z]+(\\.)[a-z]+", nombreUsuario)
				&& !Pattern.matches("[a-z]+(\\.)[a-z]+(\\.)[a-z]+", nombreUsuario)) {
			return new ValidationObject("El nombre de usuario es inválido, debe tener el formato \"nombre.apellido\"");
		}
		
		return ValidationObject.VALID;
	}

	public static ValidationObject validarContrasena(String contrasena) {
		if (!Validaciones.ValidarLargo(contrasena, 8, 128)) 
			return new ValidationObject("La contraseña debe tener un mínimo de 8 caracteres y un máximo de 128 caracteres");
		
		// .* = Cualquier caracter zero o mas veces
		
		if(!Pattern.matches(".*[a-z]+.*", contrasena)) {
			return new ValidationObject("La contraseña debe contener por lo menos una letra minúscula");
		}
		
		if(!Pattern.matches(".*[A-Z]+.*", contrasena)) {
			return new ValidationObject("La contraseña debe contener por lo menos un número, una letra mayúscula y una letra minúscula");
		}
		
		if(!Pattern.matches(".*[1-9]+.*", contrasena)) {
			return new ValidationObject("La contraseña debe contener por lo menos un número");
		}
		
		return ValidationObject.VALID;
	}

	public static ValidationObject validarNombres(String nombre) {
		if (!Validaciones.ValidarSoloLetras(nombre, true)) {
			return new ValidationObject("Los nombres solo deben contener letras y/o espacios");
		}

		if (!Validaciones.ValidarLargo(nombre, 100)) {
			return new ValidationObject("Los nombres deben tener un máximo de 100 caracteres");
		}

		return ValidationObject.VALID;
	}

	public static ValidationObject validarApellido(String apellido) {
		if (!Validaciones.ValidarSoloLetras(apellido, true)) {
			return new ValidationObject("Los apellidos solo deben contener letras y/o espacios");
		}

		if (!Validaciones.ValidarLargo(apellido, 100)) {
			return new ValidationObject("Los apellidos deben tener un máximo de 100 caracteres");
		}

		return ValidationObject.VALID;
	}

	public static ValidationObject validarFechaNacimiento(LocalDate fecNacimiento) {
		if(fecNacimiento == null) return new ValidationObject("La fecha de nacimiento es un campo obligatorio");
		
		if (LocalDate.now().compareTo(fecNacimiento) < 17) {
			return new ValidationObject("La fecha de nacimiento debe ser de mayoría de edad (o 17 años inclusive)");
		}

		return Validaciones.ValidarFechaMax(fecNacimiento, LocalDate.now(), ValidacionesFecha.NO_ESTRICTAMENTE)
				? ValidationObject.VALID
				: new ValidationObject("La fecha de nacimiento debe ser menor a la fecha actual");
	}

	public static ValidationObject validarFechaNacimiento(String fecNacimiento) {
		try {
			LocalDate fecha = Formatos.ToLocalDate(fecNacimiento);
			return validarFechaNacimiento(fecha);
		} catch (DateTimeParseException e) {
			return new ValidationObject("La fecha debe seguir el formato de \"día-mes-año\"");
		}
	}

	public static ValidationObject validarTelefono(String telefono) {
		if (!Validaciones.ValidarLargo(telefono, 3, 20)) {
			return new ValidationObject("El teléfono debe tener un máximo de 20 caracteres");
		}

		if (!Pattern.matches("[+-]?[0-9]+", telefono)) {
			return new ValidationObject("El teléfono solo debe contener números y tambien símbolo de \'+\' o \'-\'");
		}

		return ValidationObject.VALID;
	}

	public static ValidationObject validarEmailUTEC(String email) {
		if (!Validaciones.ValidarMail(email)) {
			return new ValidationObject("El email de UTEC tiene un formato inválido");
		}

		String[] partes = email.split("@");
		
		ValidationObject valid = validarNombreUsuario(partes[0]);
		if(!valid.isValid()) {
			return new ValidationObject("El email de UTEC debe contener un nombre de usuario (delante del @) en el formato: \"nombre.apellido\"");
		}
		
		if (!partes[1].endsWith("utec.edu.uy")) {
			return new ValidationObject("El email de UTEC debe pertener al dominio utec.edu.uy");
		}
		
		return ValidationObject.VALID;
	}

	public static ValidationObject validarEmail(String email) {
		if (!Validaciones.ValidarMail(email)) {
			return new ValidationObject("El email personal tiene un formato inválido");
		}

		return ValidationObject.VALID;
	}

	public static ValidationObject validarLocalidad(String localidad) {
		if (!Validaciones.ValidarLargo(localidad, 100)) {
			return new ValidationObject("La localidad debe tener un máximo de 100 caracteres");
		}

		return ValidationObject.VALID;
	}

	public static ValidationObject validarGenero(Genero genero) {
		return genero != null ? ValidationObject.VALID : new ValidationObject("El género es obligatorio");
	}

	public static ValidationObject validarDepartamento(Departamento departamento) {
		return departamento != null ? ValidationObject.VALID : new ValidationObject("El departamento es obligatorio");
	}

	public static ValidationObject validarEstadoUsuario(EstadoUsuario estado) {
		return estado != null ? ValidationObject.VALID : new ValidationObject("El estado es obligatorio");
	}

	public static ValidationObject validarItr(Itr itr) {
		return itr != null ? ValidationObject.VALID : new ValidationObject("El ITR es obligatorio");
	}
}
