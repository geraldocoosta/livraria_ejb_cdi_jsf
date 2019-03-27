package br.com.ultcode.livraria.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.ultcode.livraria.modelo.Livro;

@Stateless
public class LivroDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager manager;
	private DAO<Livro> dao;

	public void atualiza(Livro t) {
		dao.atualiza(t);
	}

	public Livro busca(Integer id) {
		return dao.busca(id);
	}

	public Livro buscaComEscritores(Livro livro) {
		return manager.createQuery("select l from Livro l left join fetch l.autor where l = :pLivro", Livro.class)
				.setParameter("pLivro", livro).getSingleResult();
	}

	public List<Livro> buscaTodos() {
		return dao.buscaTodos();
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	@PostConstruct
	private void init() {
		dao = new DAO<>(manager, Livro.class);
	}

	public List<Livro> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

	public List<Livro> listaTodosPaginada(int firstResult, int maxResults, Map<String, String> colunaValor) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Livro> query = criteriaBuilder.createQuery(Livro.class);
		Root<Livro> root = query.from(Livro.class);

		String titulo = colunaValor.get("titulo");

		long preco = 0;

		try {
			preco = Long.parseLong(colunaValor.get("preco"));
		} catch (NumberFormatException e) {
			System.out.println("Não foi um numero que foi inserido");
		}

		if (titulo != null && !titulo.isEmpty()) {
			query = query.where(criteriaBuilder.like(root.<String>get("titulo"), titulo + "%"));
		}

		if (preco != 0) {
			query = query.where(criteriaBuilder.le(root.<Long>get("preco"), preco));
		}

		List<Livro> lista = manager.createQuery(query).setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();

		return lista;
	}

	public void persist(Livro t) {
		dao.persist(t);
	}

	public void remove(Livro t) {
		dao.remove(t);
	}

}
