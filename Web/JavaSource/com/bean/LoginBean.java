package com.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.flow.SwitchCase;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Tutor;
import com.entities.enums.EstadoUsuario;
import com.entities.enums.Rol;
import com.services.UsuarioBean;

@Named("loginBean")
@ViewScoped
public class LoginBean implements Serializable { 

	@EJB
	private UsuarioBean user;
	
	@Inject
	private EnumJSF enums;
	
	@Inject
	private AuthJWTBean auth;
	
	private String nombreUsuario;
	private String contrasenia;
	private Rol rol;
	
	private DirContext connection;
	
	public LoginBean() {}

	public void restablecerContrasenia() {
		
		if(nombreUsuario.isBlank()) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el nombre de usuario");
			return;
		}
		
		JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Si existe un usuario con estas crendeciales, se le recibirá un e-mail para reestablecer la contraseña");
		try {
			user.olvideContrasenia(nombreUsuario);
		} catch (Exception e) {
		}
	}
	
	public void login() {
		try {
			auth.borrar();
			if(rol == Rol.ANALISTA) {
				Analista usu = user.login(nombreUsuario, contrasenia, Analista.class);
				auth.generar(usu.getIdUsuario(), usu.getIdAnalista(), nombreUsuario, rol, usu);
			} else if(rol == Rol.ESTUDIANTE) {
				Estudiante usu = user.login(nombreUsuario, contrasenia, Estudiante.class);
				auth.generar(usu.getIdUsuario(), usu.getIdEstudiante(), nombreUsuario, rol, usu);
			} else if(rol == Rol.TUTOR) {
				Tutor usu = user.login(nombreUsuario, contrasenia, Tutor.class);
				auth.generar(usu.getIdUsuario(), usu.getIdTutor(), nombreUsuario, rol, usu);
			}

			JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Incio de sesión exitoso", "");
			FacesContext.getCurrentInstance().getExternalContext().redirect("inicio.xhtml");
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al iniciar sesión:", e.getMessage());
		}
	}
	public void newActiveDirectoryConnection() {
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL,  "ldap://192.168.0.110:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, "CN=Administrador,CN=Users,DC=CETU,DC=EDU,DC=UY");
		env.put(Context.SECURITY_CREDENTIALS, "Admin123");
		try {
			connection = new InitialDirContext(env);
			System.out.println("Hello World!" + connection);
		} catch (AuthenticationException ex) {
			System.out.println(ex.getMessage());
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	public HashMap<String, String> getAllUsers() throws NamingException {
		HashMap<String, String> salida = new HashMap<String, String>();
		String searchFilter = "(objectClass=user)";
		String[] reqAtt = { "cn","mail" };
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(reqAtt);

		NamingEnumeration users = connection.search("OU=Usuarios,DC=CETU,DC=EDU,DC=UY", searchFilter, controls);
		
		SearchResult result = null;
		while (users.hasMore()) {
			System.out.println("entra al while");
			result = (SearchResult) users.next();
			Attributes attr = result.getAttributes();
			salida.put(attr.get("cn").get(0).toString(), attr.get("mail").get(0).toString());

		}
		return salida;
	}

	public void loginActiveDirectory() {
		
		try {
			
			auth.borrar();
			newActiveDirectoryConnection();
			HashMap<String, String> usuarios = getAllUsers();
			
			
			if(rol == Rol.ANALISTA) {
				Analista usu = user.login(nombreUsuario, contrasenia, Analista.class);
				
				if(usuarios.containsValue(usu.getEmailUtec())) {
					auth.generar(usu.getIdUsuario(), usu.getIdAnalista(), nombreUsuario, rol, usu);
					JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Incio de sesión exitoso", "");
					FacesContext.getCurrentInstance().getExternalContext().redirect("inicio.xhtml");
				}else {
					JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al validar usuario en ActiveDirectory. Contáctese con el analista de carrera en el ITR.");
				}
					

			} else if(rol == Rol.ESTUDIANTE) {
				Estudiante usu = user.login(nombreUsuario, contrasenia, Estudiante.class);
				
				if(usuarios.containsValue(usu.getEmailUtec())) {
					auth.generar(usu.getIdUsuario(), usu.getIdEstudiante(), nombreUsuario, rol, usu);
					JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Incio de sesión exitoso", "");
					FacesContext.getCurrentInstance().getExternalContext().redirect("inicio.xhtml");
				}else {
					JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al validar usuario en ActiveDirectory. Contáctese con el analista de carrera en el ITR.");
				}
				

			} else if(rol == Rol.TUTOR) {
				Tutor usu = user.login(nombreUsuario, contrasenia, Tutor.class);
				if(usuarios.containsValue(usu.getEmailUtec())) {
					auth.generar(usu.getIdUsuario(), usu.getIdTutor(), nombreUsuario, rol, usu);
					JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Incio de sesión exitoso", "");
					FacesContext.getCurrentInstance().getExternalContext().redirect("inicio.xhtml");
				}else {
					JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al validar usuario en ActiveDirectory. Contáctese con el analista de carrera en el ITR.");
				}
				
			}
		} catch (Exception e) {
			JSFUtils.addMessage(FacesMessage.SEVERITY_ERROR, "Error al iniciar sesión:", e.getMessage());
		}
	}

	/*
	public static boolean authUserAD(String username,Rol rol, String password)
	{
		String rolConvertido = "";
		if(rol == Rol.TUTOR) {
			rolConvertido="Tutores";
		}else if(rol==Rol.ANALISTA) {
			rolConvertido="Analistas";
		}else {
			rolConvertido="Estudiantes";
		}		
		try {
			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://192.168.0.110:389");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, "cn="+username+",ou="+rolConvertido+",ou=Usuarios,dc=CETU,dc=EDU,dc=UY");

			env.put(Context.SECURITY_CREDENTIALS, "1234567Abcd.");
			DirContext con = new InitialDirContext(env);
			con.close();
			return true;
		}catch (Exception e) {
			System.out.println("failed: "+e.getMessage());
			return false;
		}
	}
	*/
	
	public void register() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("register.xhtml");
	}
	
	public void info() {
		if(!auth.yaGenerado()) return;
		JSFUtils.addMessage(FacesMessage.SEVERITY_INFO, "Información de Usuario:", auth.getRol().toString() + " " + auth.getNombreUsuario() + " " + auth.getIdUsuario() + " " + auth.getIdRol());
	}
    
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
}
