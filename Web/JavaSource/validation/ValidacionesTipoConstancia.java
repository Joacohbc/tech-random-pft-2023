package validation;

import com.entities.TipoConstancia;

public class ValidacionesTipoConstancia {

	
	public static ValidationObject validarTipoContancia(TipoConstancia tipo) {
		
		if(tipo == null)
			return new ValidationObject("La entidad no puede ser nula");
		
		ValidationObject error = validarTipo(tipo.getTipo());
		if(!error.isValid()) 
			return error;
		
		error = validarPlantilla(tipo.getPlantilla());
		if(!error.isValid()) 
			return error;
		
		return ValidationObject.VALID;
	}
	
	public static ValidationObject validarTipo(String tipo) {
		return Validaciones.ValidarLargo(tipo, 10, 50) ? ValidationObject.VALID :
			new ValidationObject("El Tipo debete tener entre 10 y 50 caracteres");
	}
	
	public static ValidationObject validarPlantilla(byte[] plantilla) {
		if(plantilla == null) {
			return new ValidationObject("La plantilla no puede ser nula");
		}
		
		if(plantilla.length == 0) {
			return new ValidationObject("La plantilla no puede estar vacia");
		}
		
		return ValidationObject.VALID;
	}
}
