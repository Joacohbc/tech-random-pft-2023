package validation;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.entities.Estudiante;

import validation.ValidacionesUsuario.TipoUsuarioDocumento;

/**
 * Session Bean implementation class ValidacionesUsuarioEstudiante
 */
@Stateless
@LocalBean
public class ValidacionesUsuarioEstudiante {


	public static ValidationObject validarEstudiante(Estudiante estudiante, TipoUsuarioDocumento tipoDocumento) {
		
		if(estudiante == null)
				return new ValidationObject("La entidad no puede ser nula");
		
		ValidationObject valid = ValidacionesUsuario.ValidarUsuario(estudiante, tipoDocumento);
		if (!valid.isValid())
			return valid;
		
		valid = validarEmailUtecEstudiante(estudiante.getEmailUtec());
		if (!valid.isValid())
			return valid;		

		valid = validarGeneracion(String.valueOf(estudiante.getGeneracion()));
		if (!valid.isValid())
			return valid;

		return ValidationObject.VALID;
	}

	public static ValidationObject validarEmailUtecEstudiante(String email) {
		
		if (!Validaciones.ValidarMail(email)) {
			return new ValidationObject("El email de UTEC tiene un formato inválido");
		}

		String[] partes = email.split("@");
		
		ValidationObject valid = ValidacionesUsuario.validarNombreUsuario(partes[0]);
		if(!valid.isValid()) {
			return new ValidationObject("El email de UTEC debe contener un nombre de usuario (delante del @) en el formato: \"nombre.apellido\"");
		}
		
		if (!partes[1].equals("estudiantes.utec.edu.uy")) {
			return new ValidationObject("El email de UTEC de un estudiante debe ser del dominio (después del @) \"estudiantes.utec.edu.uy\"");
		}

		return ValidationObject.VALID;
	}
	
	public static ValidationObject validarEstudianteSinContrasenia(Estudiante estudiante, TipoUsuarioDocumento tipoDocumento) {
		
		if(estudiante == null)
				return new ValidationObject("La entidad no puede ser nula");
		
		ValidationObject valid = ValidacionesUsuario.ValidarUsuarioSinContrasenia(estudiante, tipoDocumento);
		if (!valid.isValid())
			return valid;

		valid = validarGeneracion(String.valueOf(estudiante.getGeneracion()));
		if (!valid.isValid())
			return valid;

		return ValidationObject.VALID;
	}
	
	public static ValidationObject validarGeneracion(String generacion) {
		if(!Validaciones.ValidarNoVacio(generacion)) {
			return new ValidationObject("La generación es obligatoria");
		}
		
		if (!Validaciones.ValidarSoloNumeros(generacion, false)) {
			return new ValidationObject("La generación solo puede contener números positivos");

		} 
	
		if (generacion.length() != 4) {
			return new ValidationObject("La generación debe tener 4 dígitos");
		}
		
		return ValidationObject.VALID;
	}
}
