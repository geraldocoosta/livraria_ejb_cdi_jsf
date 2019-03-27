package br.com.ultcode.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.ultcode.livraria.modelo.Autor;

@Stateless
public class AutorDao implements Serializable {

	private static final long serialVersionUID = 7237372867940712873L;

	@PersistenceContext
	private EntityManager manager;
	private DAO<Autor> dao;

	public void atualiza(Autor t) {
		dao.atualiza(t);
	}

	public Autor busca(Integer id) {
		return dao.busca(id);
	}

	public List<Autor> buscaTodos() {
		return dao.buscaTodos();
	}

	@PostConstruct
	private void init() {
		dao = new DAO<>(manager, Autor.class);
	}

	public void persist(Autor t) {
		dao.persist(t);
	}

	public void remove(Autor t) {
		dao.remove(t);
	}

}
