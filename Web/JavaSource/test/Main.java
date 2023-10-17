package test;

import com.auth.JWTUtils;
import com.auth.UserDetails;
import com.entities.Tutor;
import com.entities.enums.Rol;
import com.password4j.Argon2Function;
import com.password4j.BcryptFunction;
import com.password4j.Password;
import com.password4j.types.Argon2;
import com.services.UsuarioBean;

public class Main {

	public static void main(String[] args) {
		
		JWTUtils jwtUtils = new JWTUtils();
//		
//		UserDetails details = new UserDetails();
//		details.setIdRol(0l);
//		details.setIdUsuario(0l);
//		details.setNombreUsuario("");
//		details.setRol(Rol.TUTOR);
//		System.out.println(jwtUtils.generateToken(details));
//		System.out.println(jwtUtils.generateToken(details));
//		System.out.println(jwtUtils.generateToken(details));
//		System.out.println(jwtUtils.generateToken(details));
//		System.out.println(jwtUtils.generateToken(details));
//		System.out.println(jwtUtils.generateToken(details));
		
		System.out.println(jwtUtils.isTokenExpired("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiMWVlYzJkZS0zNjMyLTRkZWYtODQzNi01MDQ2OWFjNjFjODQiLCJleHAiOjE2OTcyMDU5MTIsImlhdCI6MTY5NzIwNTYxMn0.vP8cR7flqbYgXkPfT5plNHKsKh4PgyRjodihgoM2Xxe6Ww4p3LaTvVQObTCvvEnvKyNP8qj9BaFJIYNpV71r7g"));
		
//		System.out.println(jwtUtils.getUserDetails("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJKb2FxdWluIiwiaWRSb2wiOjEsImlkVXN1YXJpbyI6MSwiZXhwIjoxNjg4NTE1MTUxLCJpYXQiOjE2ODg1MTE1NTEsInJvbCI6IlRVVE9SIn0.e5rjNlMRVHmK-jldVJzdR3xHinhtrD2mA2WI44-OjlewBvT3VIvQAmXX4Q3_Xf-ruFpifD4kpjsFLzsi1Fsbqw"));
		
//		final String userPassword = "Mieqweqwmega epicae123123wdeqwe qwewqfgeqqweqw321312ljeraglsareml brem lgml ;ea;ktkqerwerewrdsgnaerll";
//
////		
//		final Argon2Function hashFunction = Argon2Function.getInstance(1024, 3, 2, 64, Argon2.ID);
////		
//		String hash = Password.hash("1234567Ab")
//				.addRandomSalt(64)
//				.addPepper("1234567Ab")
//				.with(hashFunction).getResult();
//		
//		System.out.println(hash);
//		
//		System.out.println(Password.check("1234567Ab", hash)
//				.addPepper("1234567Ab")
//				.with(hashFunction));
//		System.out.println(Password.check(userPassword, hash)
//				.addPepper(userPassword)
//				.with(hashFunction));
		
	}
	


}