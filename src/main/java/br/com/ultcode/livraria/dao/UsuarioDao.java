package br.com.ultcode.livraria.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.ultcode.livraria.modelo.Usuario;

@Stateless
public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;

	public Usuario confereInformacoes(Usuario usuario) {

		TypedQuery<Usuario> query = em.createQuery(
				"select u from Usuario u where u.email = :pUserEmail and u.senha = :pUserSenha", Usuario.class);
		query.setParameter("pUserEmail", usuario.getEmail());
		query.setParameter("pUserSenha", usuario.getSenha());
		Usuario user;
		try {
			user = query.getSingleResult();
			System.out.println(user);
			return user;
		} catch (Exception e) {
			return null;
		}
	}
}
