package validation;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.entities.Estudiante;

import validation.ValidacionesUsuario.TipoUsuarioDocumento;
import validation.ValidacionesUsuario.TipoUsuarioEmail;

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

		valid = validarGeneracion(estudiante.getGeneracion().toString());
		if (!valid.isValid())
			return valid;

		return ValidationObject.VALID;
	}

	public static ValidationObject validarEstudianteSinContrasenia(Estudiante estudiante, TipoUsuarioDocumento tipoDocumento) {
		
		if(estudiante == null)
				return new ValidationObject("La entidad no puede ser nula");
		
		ValidationObject valid = ValidacionesUsuario.ValidarUsuarioSinContrasenia(estudiante, tipoDocumento);
		if (!valid.isValid())
			return valid;

		valid = validarGeneracion(estudiante.getGeneracion().toString());
		if (!valid.isValid())
			return valid;

		return ValidationObject.VALID;
	}
	
	public static ValidationObject validarGeneracion(String generacion) {
		if(!Validaciones.ValidarNoVacio(generacion)) {
			return new ValidationObject("La generacion es obligatoria");
		}
		
		if (!Validaciones.ValidarSoloNumeros(generacion, false)) {
			return new ValidationObject("La generacion solo puede contener numeros positivos");

		} 
	
		if (generacion.length() != 4) {
			return new ValidationObject("La generacion debe tener 4 digitos");
		}
		
		return ValidationObject.VALID;
	}
}
