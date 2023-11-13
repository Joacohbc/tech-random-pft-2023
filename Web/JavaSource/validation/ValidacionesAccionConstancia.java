package validation;

import com.entities.AccionConstancia;
import com.entities.Analista;
import com.entities.Constancia;

public class ValidacionesAccionConstancia {
	
	public static ValidationObject validatAccionConstancia(AccionConstancia constancia) {
		
		if(constancia == null)
			return new ValidationObject("La entidad no puede ser nula");
		
		ValidationObject valid = validarDetalle(constancia.getDetalle());
		if(!valid.isValid())
			return valid;

		valid = validarConstancia(constancia.getConstancia());
		if(!valid.isValid())
			return valid;

		valid = validarAnalista(constancia.getAnalista());
		if(!valid.isValid())
			return valid;
		
		return ValidationObject.VALID;
	}
	
	public static ValidationObject validarDetalle(String detalle) {
		return Validaciones.ValidarLargo(detalle, 50) ? ValidationObject.VALID
				: new ValidationObject("El detalle no puede estar vacío y debe tener un máximo de 50 caracteres");
	}
	
	public static ValidationObject validarAnalista(Analista analista) {
		return analista != null ? ValidationObject.VALID : new ValidationObject("El analista es  obligatorio");
	}
	
	public static ValidationObject validarConstancia(Constancia constancia) {
		return constancia != null ? ValidationObject.VALID : new ValidationObject("La constancia es  obligatoria");
	}
}
