package validation;

import javax.ejb.ConcurrencyManagementType;

import com.entities.Constancia;
import com.entities.Evento;
import com.entities.enums.EstadoSolicitudes;

public class ValidacionesConstancia {

	public static ValidationObject validarConstancia(Constancia constancia) {
		
		if(constancia == null)
			return new ValidationObject("La entidad no puede ser nula");
		
		ValidationObject valid = validarDetalle(constancia.getDetalle());
		if(!valid.isValid())
			return valid;
		
		valid = validarEstadoUsuario(constancia.getEstado());
		if(!valid.isValid())
			return valid;
		
		valid = validarEvento(constancia.getEvento());
		if(!valid.isValid())
			return valid;
		
		if (constancia.getEvento() == null) 
			return new ValidationObject("El Evento es obligatorio");
		
		if (constancia.getEstudiante() == null) 
			return new ValidationObject("El Estudiante es obligatorio");
		
		return ValidationObject.VALID;
	}
	
	public static ValidationObject validarDetalle(String detalle) {
		return Validaciones.ValidarLargo(detalle, 50) ? ValidationObject.VALID
				: new ValidationObject("El detalle no puede estar vacio y debe tener un maximo de 50 caracteres");
	}
	
	public static ValidationObject validarEstadoUsuario(EstadoSolicitudes estado) {
		return estado != null ? ValidationObject.VALID : new ValidationObject("El estado obligatorio");
	}
	
	public static ValidationObject validarEvento(Evento evento) {
		return evento != null ? ValidationObject.VALID : new ValidationObject("El evento obligatorio");
	}
}
