package validation;

import com.entities.enums.EstadoReclamo;
import com.entities.enums.EstadoSolicitudes;
import com.entities.Evento;
import com.entities.Reclamo;

public class ValidacionesReclamo {

	public static ValidationObject validarReclamo(Reclamo reclamo) {

		if (reclamo == null)
			return new ValidationObject("La entidad no puede ser nula");

		ValidationObject valid = validarDetalle(reclamo.getDetalle());
		if (!valid.isValid())
			return valid;

		valid = validarEstadoUsuario(reclamo.getEstado());
		if (!valid.isValid())
			return valid;

		valid = validarEvento(reclamo.getEvento());
		if (!valid.isValid())
			return valid;

		if (reclamo.getEvento() == null)
			return new ValidationObject("El Evento es obligatorio");

		if (reclamo.getEstudiante() == null)
			return new ValidationObject("El Estudiante es obligatorio");

		return ValidationObject.VALID;
	}

	public static ValidationObject validarDetalle(String detalle) {
		return Validaciones.ValidarLargo(detalle, 50) ? ValidationObject.VALID
				: new ValidationObject("El detalle no puede estar vacío y debe tener un máximo de 50 caracteres");
	}
	public static ValidationObject validarEstadoUsuario(EstadoReclamo estado) {
		return estado != null ? ValidationObject.VALID : new ValidationObject("El estado es obligatorio");
	}
	
	public static ValidationObject validarEvento(Evento evento) {
		return evento != null ? ValidationObject.VALID : new ValidationObject("El evento es obligatorio");
	}

}
