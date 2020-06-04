package demo.service;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import demo.entity.Todo;

public class TodoService {

	private static EntityManagerFactory entityManagerFactory;

	public static EntityManager openEntityManager() {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory( "demoJPA" );
		}
		return entityManagerFactory.createEntityManager();
	}

	public static void closeEntityManager() {
		entityManagerFactory.close();
	}

	public void create() {
		EntityManager entityManager = openEntityManager();
		entityManager.getTransaction().begin();

		Todo todo = new Todo();
		todo.setTitle("Learning JUnit");
		todo.setCompleted(false);
		todo.setOrdering(1L);
		todo.setUrl("#");

		entityManager.persist(todo);
		System.out.println(todo);
		entityManager.getTransaction().commit();

	}

	public void searchAll() {
		EntityManager entityManager = openEntityManager();
		entityManager.getTransaction().begin();
		Query query = entityManager.createQuery( "Select o from Todo o");
		List<Todo> todoList = query.getResultList();
		todoList.forEach(System.out::println);
		entityManager.getTransaction().commit();

	}

	public static void main(String... args) {
		TodoService todoService = new TodoService();
		todoService.create();
		todoService.searchAll();
		closeEntityManager();
	}

}
