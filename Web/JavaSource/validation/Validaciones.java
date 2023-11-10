package validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;

@LocalBean
@Singleton
public final class Validaciones {
	
	public static String Trim(String s) {
		return s.trim();
	}
	
	public static boolean ValidarNoVacio(String s) {
		if (s == null)
			return false;

		return !s.isBlank();
	}

	public static boolean ValidarLargo(String s, int largoMax) {
		if (!ValidarNoVacio(s))
			return false;

		return s.length() <= largoMax;
	}

	public static boolean ValidarLargo(String s, int largoMin, int largoMax) {
		if (!ValidarNoVacio(s)) {
			return false;
		}
		
		return s.length() <= largoMax && s.length() >= largoMin;
	}
	
	public static boolean ValidarRegex(String s, String regex) {
		return ValidarNoVacio(s) && Pattern.matches(regex, s);
	}

	public static boolean ValidarSoloLetras(String s, boolean espacios) {
		if (!ValidarNoVacio(s))
			return false;
		if (espacios) {
			return Pattern.matches("[a-zA-ZáéíóúýÁÉÍÓÚÝñÑ ]+", s);
		} else {
			return Pattern.matches("[a-zA-ZáéíóúýÁÉÍÓÚÝñÑ]+", s);
		}
	}

	public static boolean ValidarSoloNumeros(String s, boolean espacios) {
		if (!ValidarNoVacio(s))
			return false;

		if (espacios) {
			return Pattern.matches("[0-9 ]+", s);
		} else {
			return Pattern.matches("[0-9]+", s);
		}
	}

	public static boolean ValidarNumerosYLetras(String s, boolean espacios) {
		if (!ValidarNoVacio(s))
			return false;

		if (espacios) {
			return Pattern.matches("[0-9a-zA-ZáéíóúýÁÉÍÓÚÝñÑ ]+", s);
		} else {
			return Pattern.matches("[0-9a-zA-ZáéíóúýÁÉÍÓÚÝñÑ]+", s);
		}
	}
	
	// Validacion de Mail 
	public static boolean ValidarMail(String s) {
		if(!ValidarNoVacio(s))
			return false;
		
		// RFC 5322
		String regx = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
		return Pattern.matches(regx, s);
	}	
	
	// Validaciones de Fechas
	public enum ValidacionesFecha {
		ESTRICTAMENTE, NO_ESTRICTAMENTE;
	}

	public static boolean ValidarFechaMin(LocalDate date, LocalDate fechaMin, ValidacionesFecha validacion) {
		if (date == null || fechaMin == null)
			return false;

		// Si la validacion no es menor estrictamente
		// entoces igual sirve
		if (validacion == ValidacionesFecha.NO_ESTRICTAMENTE) {
			if (date.isEqual(fechaMin))
				return true;
		}

		return date.isAfter(fechaMin);
	}

	public static boolean ValidarFechaMax(LocalDate date, LocalDate fechaMax, ValidacionesFecha validacion) {
		if (date == null || fechaMax == null)
			return false;

		// Si la validacion no es mayor estrictamente
		// entoces igual sirve
		if (validacion == ValidacionesFecha.NO_ESTRICTAMENTE) {
			if (date.isEqual(fechaMax))
				return true;
		}
		
		return date.isBefore(fechaMax);
	}

	public static boolean ValidarFecha(LocalDate date, LocalDate fechaMin, LocalDate fechaMax,
			ValidacionesFecha validacion) {
		
		if (date == null || fechaMin == null || fechaMax == null)
			return false;

		if (validacion == ValidacionesFecha.NO_ESTRICTAMENTE) {
			if (date.isEqual(fechaMin) || date.isEqual(fechaMax))
				return true;
		}

		return ValidarFechaMin(date, fechaMin, validacion) && ValidarFechaMax(date, fechaMax, validacion);
	}
	
	public static boolean IsValid(String date) {
		try {
			LocalDate.parse(date, Formatos.DateFormat);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public static boolean ValidarCedulaUruguaya(String s) {
		if (!ValidarNoVacio(s))
			return false;

		return (Pattern.matches("[0-9](\\.)[0-9][0-9][0-9](\\.)[0-9][0-9][0-9](-)[0-9]", s)
				|| Pattern.matches("[0-9][0-9][0-9](\\.)[0-9][0-9][0-9](-)[0-9]", s)) && ValidarDigitoVerificadorCedulaUruguaya(s);
	}
	
	public static boolean ValidarDigitoVerificadorCedulaUruguaya(String s) {
        String digitos = s.replaceAll("[^0-9]", "").substring(0, 7); // Estraigo los digitos 1.234.567
        String digitoVerificadorIngresado = s.replaceAll("[^0-9]", "").substring(7,8);

        int[] factoresCalculo = {2, 9, 8, 7, 6, 3, 4};

        int suma = 0;
        for (int i = 0; i < digitos.length(); i++) {
            int digit = Character.getNumericValue(digitos.charAt(i));
            suma += digit * factoresCalculo[i];
        }

        int modulo = suma % 10;
        int digitoVerificadorCaluclado = (10 - modulo) % 10;
        return Integer.parseInt(digitoVerificadorIngresado) == digitoVerificadorCaluclado;
	}
}
