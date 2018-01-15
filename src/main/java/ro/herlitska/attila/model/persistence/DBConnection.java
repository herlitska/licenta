package ro.herlitska.attila.model.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

public class DBConnection {
	private static EntityManagerFactory entityManagerFactory;
	private static List<EntityManager> entityManagers = new ArrayList<>();

	static EntityManager getEntityManager() throws PersistenceException {
		if (entityManagerFactory == null) {
			entityManagerFactory = Persistence.createEntityManagerFactory("db");

		}
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManagers.add(entityManager);
		return entityManager;
	}

	public static void close() {
		try {
			entityManagers.stream().filter(em -> em.isOpen()).forEach(em -> em.close());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
				entityManagerFactory.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
