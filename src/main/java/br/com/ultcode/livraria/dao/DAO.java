package br.com.ultcode.livraria.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Class<T> classe;

	EntityManager em;

	public DAO(EntityManager manager, Class<T> classe) {
		this.classe = classe;
		em = manager;
	}

	public void atualiza(T t) {
		em.merge(t);
	}

	public T busca(Integer id) {
		return em.find(classe, id);
	}

	public List<T> buscaTodos() {
		return em.createQuery("from " + classe.getSimpleName(), classe).getResultList();
	}

	public int contaTodos() {
		long result = (Long) em.createQuery("select count(n) from " + classe.getSimpleName() + " n").getSingleResult();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();

		return lista;
	}

	public void persist(T t) {
		em.persist(t);
	}

	public void remove(T t) {
		em.remove(em.merge(t));
	}

}
