package test;

import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class Prueba {
	DirContext connection;
	
	/* create connection during object creation */
	public void newConnection() {
		Properties env = new Properties();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL,  "ldap://192.168.0.110:389");
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		//env.put(Context.SECURITY_PRINCIPAL, "uid=Administrador,ou=Users");
		env.put(Context.SECURITY_PRINCIPAL, "CN=Administrador,CN=Users,DC=CETU,DC=EDU,DC=UY");
		env.put(Context.SECURITY_CREDENTIALS, "Admin123");
		try {
			connection = new InitialDirContext(env);
			System.out.println("Hello World!" + connection);
		} catch (AuthenticationException ex) {
			System.out.println(ex.getMessage());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* use this to authenticate any existing user */
	public static boolean authUser(String username, String password)
	{
		try {
			Properties env = new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, "ldap://192.168.0.110:389");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			//env.put(Context.SECURITY_PRINCIPAL, "cn=bruno gonzalez,ou=Analistas,ou=Usuarios,dc=CETU,dc=EDU,dc=UY");  //check the DN correctly
			env.put(Context.SECURITY_PRINCIPAL, "cn=bruno gonzalez,ou=Analistas,ou=Usuarios,dc=CETU,dc=EDU,dc=UY");  //check the DN correctly

			//env.put(Context.SECURITY_CREDENTIALS, password);
			env.put(Context.SECURITY_CREDENTIALS, "1234567Abcd.");
			DirContext con = new InitialDirContext(env);
			System.out.println("success");
			con.close();
			return true;
		}catch (Exception e) {
			System.out.println("failed: "+e.getMessage());
			return false;
		}
	}
	
	public void getAllUsers() throws NamingException {
		String searchFilter = "(objectClass=user)";
		String[] reqAtt = { "cn","mail" };
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(reqAtt);

		//NamingEnumeration users = connection.search("CN=Users,DC=CETU,DC=EDU,DC=UY", searchFilter, controls);
		NamingEnumeration users = connection.search("OU=Usuarios,DC=CETU,DC=EDU,DC=UY", searchFilter, controls);
		//System.out.println("ejecuta el search "+users.next());
		
		SearchResult result = null;
		while (users.hasMore()) {
			System.out.println("entra al while");
			result = (SearchResult) users.next();
			Attributes attr = result.getAttributes();
			String cn = attr.get("cn").get(0).toString();
			String mail = attr.get("mail").get(0).toString();
			System.out.println("status encontrsdo: " + mail.equalsIgnoreCase("bruno.gonzalez.f@cetu.edu.uy"));
		}
	}
	
	public static void main(String[] args) throws NamingException {

		Prueba app = new Prueba();
		app.newConnection();
		app.getAllUsers();
		//app.authUser("bruno.gonzalez.f","1234567Abcd.");
		  
	}
	
}
