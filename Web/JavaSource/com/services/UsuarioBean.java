package com.services;

import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.MessagingException;
import javax.persistence.NoResultException;

import com.auth.TokenManagmentBean;
import com.daos.UsuariosDAO;
import com.entities.Analista;
import com.entities.Estudiante;
import com.entities.Itr;
import com.entities.Tutor;
import com.entities.Usuario;
import com.entities.enums.EstadoUsuario;
import com.exceptions.DAOException;
import com.exceptions.InvalidEntityException;
import com.exceptions.NotFoundEntityException;
import com.exceptions.ServiceException;
import com.password4j.Argon2Function;
import com.password4j.Password;
import com.password4j.types.Argon2;

import validation.ValidacionesUsuario;
import validation.ValidacionesUsuario.TipoUsuarioDocumento;
import validation.ValidacionesUsuarioEstudiante;
import validation.ValidacionesUsuarioTutor;
import validation.ValidationObject;

@Stateless
@LocalBean
public class UsuarioBean implements UsuarioBeanRemote {

	@EJB
	private UsuariosDAO dao;

	@EJB
	private TokenManagmentBean tokenBean;
	
	@EJB
	private MailBean mail;
	
	public UsuarioBean() {
	}

	final Argon2Function hashFunction = Argon2Function.getInstance(1024, 3, 2, 64, Argon2.ID);

	private String encriptar(String password) {	
		return Password.hash(password)
				.addRandomSalt(64)
				.addPepper(password)
				.with(hashFunction).getResult();
	}

	private boolean verificar(String userPassword, String hashedPassword) {
		return Password.check(userPassword, hashedPassword)
				.addPepper(userPassword)
				.with(hashFunction);
	}
	
	@Override
	public <T extends Usuario> T register(T usuario, TipoUsuarioDocumento tipoDocumento)
			throws ServiceException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(usuario, "Al registrar un Usuario, este no puede ser nulo");

			if (usuario.getIdUsuario() != null)
				throw new InvalidEntityException("Al registrar un Usuario, este no puede tener un ID asignado");

			if (usuario instanceof Estudiante) {
				Estudiante est = (Estudiante) usuario;
				ValidationObject error = ValidacionesUsuarioEstudiante.validarEstudiante(est, tipoDocumento);
				if (!error.isValid())
					throw new InvalidEntityException(error.getErrorMessage());

			} else if (usuario instanceof Tutor) {
				Tutor tut = (Tutor) usuario;
				ValidationObject error = ValidacionesUsuarioTutor.validarTutor(tut, tipoDocumento);
				if (!error.isValid())
					throw new InvalidEntityException(error.getErrorMessage());

			} else {
				Analista ana = (Analista) usuario;
				ValidationObject error = ValidacionesUsuario.ValidarUsuario(ana, tipoDocumento);
				if (!error.isValid())
					throw new InvalidEntityException(error.getErrorMessage());
			}

			if (dao.findByDocumento(Usuario.class, usuario.getDocumento()) != null) {
				throw new InvalidEntityException("Ya existe un Usuario con el Documento: " + usuario.getDocumento());
			}

			if (dao.findByEmailUtec(Usuario.class, usuario.getEmailUtec()) != null) {
				throw new InvalidEntityException("Ya existe un Usuario con el Email Utec: " + usuario.getEmailUtec());
			}

			if (dao.findByEmailPersonal(Usuario.class, usuario.getEmailPersonal()) != null) {
				throw new InvalidEntityException("Ya existe un Usuario con el Email Personal: " + usuario.getEmailPersonal());
			}
			
			// El nombre de usuario se obtiene a partir de email
			String nombreUsu = usuario.getEmailUtec().split("@")[0];
			usuario.setNombreUsuario(nombreUsu);
			
			if (dao.findByNombreUsuario(Usuario.class, usuario.getNombreUsuario()) != null) {
				throw new InvalidEntityException(
						"Ya existe un Usuario con el Nombre de Usuario: " + usuario.getNombreUsuario());
			}

			usuario.setContrasena(encriptar(usuario.getContrasena()));
			usuario.setEstadoUsuario(EstadoUsuario.SIN_VALIDAR);
	
			return dao.insert(usuario);

		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends Usuario> T login(String nombreUsuario, String password, Class<T> tipoUsu)
			throws ServiceException, InvalidEntityException {
		
		T usu = dao.findByNombreUsuario(tipoUsu, nombreUsuario);
		if(usu == null) 
			throw new InvalidEntityException("El nombre o la contraseña del usuario son incorrectos");
		
		if(!verificar(password, usu.getContrasena()))
			throw new InvalidEntityException("El nombre o la contraseña del usuario son incorrectos");
		
		if(usu.getEstadoUsuario() == EstadoUsuario.SIN_VALIDAR || usu.getEstadoUsuario() == EstadoUsuario.ELIMINADO)
			throw new InvalidEntityException("Las crendenciales de usuario ingreasdas no tiene acceso al sistema, consulto con su Analista");
		
		if (usu instanceof Estudiante) {
			Estudiante est = (Estudiante) usu;
			if(!est.getEstado())
				throw new InvalidEntityException("Las crendenciales de usuario ingresadas no tiene acceso al sistema, consulte con su Analista");

		} else if (usu instanceof Tutor) {
			Tutor tut = (Tutor) usu;
			if(!tut.getEstado())
				throw new InvalidEntityException("Las crendenciales de usuario ingresadas no tiene acceso al sistema, consulte con su Analista");
		} else {
			Analista ana = (Analista) usu;
			if(!ana.getEstado())
				throw new InvalidEntityException("Las crendenciales de usuario ingresadas no tiene acceso al sistema, consulte con su Analista");
		}
		
		return usu;
	}

	@Override
	public <T extends Usuario> List<T> findAll(Class<T> tipoUsu) {
		return dao.findAll(tipoUsu);
	}

	@Override
	public <T extends Usuario> List<T> findAll(Class<T> tipoUsu, EstadoUsuario estado, Itr itr) {
		return dao.findAll(tipoUsu, estado, itr);
	}

	@Override
	public void updateEstadoUsuario(Long id, EstadoUsuario estadoUsuario)
			throws ServiceException, NotFoundEntityException {
		try {
			ServicesUtils.checkNull(id, "Al registrar un Usuario, el ID no puede ser nulo");

			Usuario usu = findById(Usuario.class, id);
			if (usu == null)
				throw new NotFoundEntityException("No existe un usuario el ID: " + id);

			usu.setEstadoUsuario(estadoUsuario);
			dao.update(usu);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends Usuario> T findById(Class<T> tipoUsu, Long id) throws ServiceException {
		return dao.findById(tipoUsu, id);
	}

	@Override
	public List<Estudiante> findAllEstudiantes() {
		return dao.findAllEstudiante();
	}

	private void validarUpdate(Usuario newUsu, Usuario actual) throws DAOException, InvalidEntityException {

		if (!newUsu.getDocumento().equals(actual.getDocumento())) {
			if (dao.findByDocumento(Usuario.class, newUsu.getDocumento()) != null)
				throw new InvalidEntityException("Ya existe un Usuario con el Documento: " + newUsu.getDocumento());
		}

		if (!newUsu.getEmailUtec().equals(actual.getEmailUtec())) {
			if (dao.findByEmailUtec(Usuario.class, newUsu.getEmailUtec()) != null)
				throw new InvalidEntityException("Ya existe un Usuario con el Email Institucional: " + newUsu.getEmailUtec());
		}

		if (!newUsu.getEmailPersonal().equals(actual.getEmailPersonal())) {
			if (dao.findByEmailPersonal(Usuario.class, newUsu.getEmailPersonal()) != null)
				throw new InvalidEntityException("Ya existe un Usuario con el Email Personal: " + newUsu.getEmailPersonal());
		}

		if (!newUsu.getNombreUsuario().equals(actual.getNombreUsuario())) {
			if (dao.findByNombreUsuario(Usuario.class, newUsu.getNombreUsuario()) != null)
				throw new InvalidEntityException(
						"Ya existe un Usuario con el Nombre de Usuario: " + newUsu.getNombreUsuario());
		}
		
	    if (newUsu.getEstadoUsuario() == EstadoUsuario.SIN_VALIDAR && 
	            (actual.getEstadoUsuario() == EstadoUsuario.VALIDADO || actual.getEstadoUsuario() == EstadoUsuario.ELIMINADO)) {
	            throw new InvalidEntityException("No se puede cambiar el estado a 'Sin Validar' si el usuario está en estado 'Validado' o 'Eliminado'");
	        }
		
		
	}

	@Override
	public void updateEstudiante(Estudiante estudiante)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {

			ServicesUtils.checkNull(estudiante, "Al actualizar un usuario este no puede ser nulo");

			Estudiante estActual = dao.findById(Estudiante.class, estudiante.getIdUsuario());
			if (estActual == null)
				throw new NotFoundEntityException("No existe un usuario con el ID: " + estudiante.getIdUsuario());

			estudiante.setIdEstudiante(estActual.getIdEstudiante());
			validarUpdate(estudiante, estActual);
			
			if(estudiante.getEstadoUsuario() == EstadoUsuario.ELIMINADO) 
				estudiante.setEstado(false);
			
			dao.update(estudiante);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateAnalista(Analista analista)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(analista, "Al actualizar un usuario este no puede ser nulo");

			Analista anaActual = dao.findById(Analista.class, analista.getIdUsuario());
			if (anaActual == null)
				throw new NotFoundEntityException("No existe un usuario con el ID: " + analista.getIdUsuario());

			analista.setIdAnalista(anaActual.getIdAnalista());
			validarUpdate(analista, anaActual);
				
			if(analista.getEstadoUsuario() == EstadoUsuario.ELIMINADO) 
				analista.setEstado(false);
			
			dao.update(analista);
		} catch (DAOException e) {
			throw new ServiceException(e);
		} 
	}

	@Override
	public void updateTutor(Tutor tutor) throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(tutor, "Al actualizar un usuario este no puede ser nulo");

			Tutor tutActual = dao.findById(Tutor.class, tutor.getIdUsuario());
			if (tutActual == null)
				throw new NotFoundEntityException("No existe un usuario con el ID: "+tutor.getIdUsuario());

			tutor.setIdTutor(tutActual.getIdTutor());
			validarUpdate(tutor, tutActual);
			
			if(tutor.getEstadoUsuario() == EstadoUsuario.ELIMINADO) 
				tutor.setEstado(false);
			
			dao.update(tutor);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateEstadoEstudiante(Long id, boolean estado) throws ServiceException, NotFoundEntityException {
		try {
			ServicesUtils.checkNull(id, "Al actualizar un Usuario, su ID no puede ser nulo");

			Estudiante actual = dao.findById(Estudiante.class, id);
			if (actual == null)
				throw new NotFoundEntityException("No existe un usuario con el ID: " + id);
			
			actual.setEstado(estado);
			dao.update(actual);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateEstadoAnalista(Long id, boolean estado) throws ServiceException, NotFoundEntityException {
		try {
			ServicesUtils.checkNull(id, "Al actualizar un Usuario, su ID no puede ser nulo");

			Analista actual = dao.findById(Analista.class, id);
			if (actual == null)
				throw new NotFoundEntityException("No existe un usuario con el ID: " + id);
			
			actual.setEstado(estado);
			dao.update(actual);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateEstadoTutor(Long id, boolean estado) throws ServiceException, NotFoundEntityException {
		try {
			ServicesUtils.checkNull(id, "Al actualizar un Usuario, su ID no puede ser nulo");

			Tutor actual = dao.findById(Tutor.class, id);
			if (actual == null)
				throw new NotFoundEntityException("No existe un usuario con el ID: " + id);
			
			actual.setEstado(estado);
			dao.update(actual);
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public <T extends Usuario> T findByDocumento(Class<T> tipoUsu, String documento) {
		try {
			return dao.findByDocumento(tipoUsu, documento);
		}catch (NoResultException e) {
			return null;
		}
	}
	
	public void updateContrasenia(Long id, String nueva)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(id, "Al actualizar un Usuario, su ID no puede ser nulo");

			Usuario actual = dao.findById(Usuario.class, id);
			if (actual == null)
				throw new NotFoundEntityException("No existe un usuario con el ID: " + id);
			
			ValidationObject valid = ValidacionesUsuario.validarContrasena(nueva);
			if(!valid.isValid())
				throw new InvalidEntityException(valid.getErrorMessage());
			
			actual.setContrasena(encriptar(nueva));
			
			mail.enviarConGMail(actual.getEmailUtec(), "Cambio de Contraseña - CETU", "Se modifico la contraseña de su Usuario");
			mail.enviarConGMail(actual.getEmailPersonal(), "Cambio de Contraseña - CETU", "Se modifico la contraseña de su Usuario");
			dao.update(actual);
		} catch (DAOException | MessagingException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateContrasenia(Long id, String antigua, String nueva)
			throws ServiceException, NotFoundEntityException, InvalidEntityException {
		try {
			ServicesUtils.checkNull(id, "Al actualizar un Usuario, su ID no puede ser nulo");

			Usuario actual = dao.findById(Usuario.class, id);
			if (actual == null)
				throw new NotFoundEntityException("No existe un usuario con el ID: " + id);
			
			if(!verificar(actual.getContrasena(), encriptar(antigua))) {
				throw new InvalidEntityException("La contraseña antigua ingresada no es igual a la actual");
			}
			
			ValidationObject valid = ValidacionesUsuario.validarContrasena(nueva);
			if(!valid.isValid())
				throw new InvalidEntityException(valid.getErrorMessage());
			
			actual.setContrasena(encriptar(nueva));
			
			mail.enviarConGMail(actual.getEmailUtec(), "Cambio de Contraseña - CETU", "Se modifico la contraseña de su Usuario");
			mail.enviarConGMail(actual.getEmailPersonal(), "Cambio de Contraseña - CETU", "Se modifico la contraseña de su Usuario");
			dao.update(actual);
		} catch (DAOException | MessagingException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void olvideContrasenia(String nombreUsuario) throws ServiceException, NotFoundEntityException {
		
		Usuario usuario = dao.findByNombreUsuario(Usuario.class, nombreUsuario);
		if(usuario == null) 
			throw new NotFoundEntityException("No existe un usuario con el Nombre de Usuario: "+ nombreUsuario);
		
		try {			
//			Map<String, Object> claims = new HashMap<String, Object>();
//			claims.put("id", usuario.getIdUsuario());
//			claims.put("nombreUsuario", usuario.getNombreUsuario());
//			claims.put("nombres", usuario.getNombres());
//			claims.put("apellidos", usuario.getApellidos());
				
			String temporalToken = tokenBean.generarCustomToken(UUID.randomUUID().toString(), 1000l * 60l * 5l);
			tokenBean.addToken(temporalToken, usuario.getIdUsuario());
			
			String link = "http://localhost:8080/ProyectoInfra/pages/restablecerContrasenia.xhtml?token=" + temporalToken;

			mail.enviarConGMail(usuario.getEmailUtec(), "Contraseña Temporal - CETU" , "Ingrese a este link para restablecer la contraseña (vence en 10 minutos desde la llegada de este mensaje): " + link);
			mail.enviarConGMail(usuario.getEmailPersonal(), "Contraseña Temporal - CETU" , "Ingrese a este link para restablecer la contraseña: (vence en 10 minutos desde la llegada de este mensaje)" + link);
		} catch (DAOException e) {
			throw new ServiceException(e);
		} catch (MessagingException e) {
			throw new ServiceException("No se pudo enviar el correo con al nueva contraseña, intentelo mas tarde");
		}
		
	}

}
