import demo.entity.Todo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class JPATestCase {

    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    public static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory( "demoJPA" );
    }

    @AfterAll
    public static void destroy() {
        entityManagerFactory.close();
    }


    @Test
    public void create() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Todo todo = new Todo();
        //todo.setId(1L);
        todo.setTitle("Learning JUnit");
        todo.setCompleted(false);
        todo.setOrdering(1L);
        todo.setUrl("#");

        entityManager.persist(todo);

        System.out.println(todo);

        entityManager.getTransaction().commit();
        entityManager.close();
    }


    @Test
    public void createWithEntityConstructor() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Todo todo = new Todo(false, 1L, "Learning JUnit","#");

        entityManager.persist(todo);

        System.out.println(todo);

        entityManager.getTransaction().commit();
        entityManager.close();
    }


    @Test
    public void searchById() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Todo todo = entityManager.find(Todo.class, 2L);
        System.out.println(todo);


        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void searchByIdWithQuery() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("Select o from Todo o where o.id=:id");
        query.setParameter("id", 2L);

        Todo todo = (Todo)query.getSingleResult();
        System.out.println(todo);


        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void searchAll() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery("Select o from Todo o");

        List<Todo> todoList = query.getResultList();

        todoList.forEach(System.out::println);


        entityManager.getTransaction().commit();
        entityManager.close();

        assertTrue(!todoList.isEmpty());


    }


    @Test
    @DisplayName("Updating TODO id=2")
    public void update() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Todo todo = entityManager.find(Todo.class, 2L);
        todo.setCompleted(true);
        todo = entityManager.merge(todo);


        Todo todoUptaded = entityManager.find(Todo.class, 2L);

        entityManager.getTransaction().commit();
        entityManager.close();

        assertEquals(todoUptaded.getCompleted(), true);
    }


    @Test
    @DisplayName("Deleting TODO id=1")
    public void delete() throws Exception {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        Todo todo = entityManager.find(Todo.class, 1L);
        todo.setCompleted(true);
        entityManager.remove(todo);

        entityManager.getTransaction().commit();
        entityManager.close();

    }

}
