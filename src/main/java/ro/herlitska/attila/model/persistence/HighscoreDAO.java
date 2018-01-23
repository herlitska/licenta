package ro.herlitska.attila.model.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

public class HighscoreDAO {

	public static List<Highscore> getHighscores() throws DBConnectionException {
		List<Highscore> highscores = null;

		EntityManager manager = null;
		try {
			manager = DBConnection.getEntityManager();
			highscores = manager.createQuery("SELECT h FROM Highscore h", Highscore.class).getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new DBConnectionException();

		} finally {
			closeEntityManager(manager);
		}

		return highscores;
	}

	public static void insertNewHighscore(Highscore highscore) throws DBConnectionException {
		EntityManager manager = null;
		try {
			manager = DBConnection.getEntityManager();
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();
			manager.persist(highscore);
			transaction.commit();
		} catch (PersistenceException e) {

			throw new DBConnectionException();
		} finally {
			closeEntityManager(manager);
		}
	}

	public static void deleteHighscore(int id) throws DBConnectionException {
		EntityManager manager = null;
		try {
			manager = DBConnection.getEntityManager();
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();
			Highscore highscore = manager.find(Highscore.class, id);
			manager.remove(highscore);
			transaction.commit();
		} catch (PersistenceException e) {

			throw new DBConnectionException();
		} finally {
			closeEntityManager(manager);
		}
	}

	private static void closeEntityManager(EntityManager entityManager) throws DBConnectionException {
		try {
			if (entityManager != null && entityManager.isOpen()) {
				entityManager.close();
			}
		} catch (PersistenceException e) {

			throw new DBConnectionException();
		}
	}

}
