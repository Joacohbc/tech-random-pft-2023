package com.utec.proyectofinaltecnicatura.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class Formatos {

	public static final DateTimeFormatter DateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	public static final DateTimeFormatter DateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	// Retorna el texto con formato de titulo (mayuscula en cada palabra)
	public static String ToTitle(String texto) {
		String[] words = texto.trim().split(" ");
		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].toUpperCase();
		}

		String text = words[0].charAt(0) + words[0].substring(1).toLowerCase();
		for (int i = 1; i < words.length; i++) {
			text += " " + words[i].charAt(0) + words[i].substring(1).toLowerCase();
		}

		return text;
	}

	public static String ToFormatedString(LocalDate date) throws DateTimeParseException {
		return date.format(DateFormat);
	}

	public static String ToFormatedString(LocalDateTime date) throws DateTimeParseException {
		return date.format(DateTimeFormat);
	}

	public static LocalDate ToLocalDate(String date) throws DateTimeParseException {
		return LocalDate.parse(date, DateFormat);
	}

	public static LocalDate ToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static LocalDateTime ToLocalDateTime(String date) throws DateTimeParseException {
		return LocalDateTime.parse(date, DateTimeFormat);
	}
	
	public static LocalDateTime ToLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
}
