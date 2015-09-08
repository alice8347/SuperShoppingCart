import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.Shoppinguser;
import productTools.DBUtil;


public class UserDB {
	public static Shoppinguser select() {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT s FROM Shoppinguser s";
		TypedQuery<Shoppinguser> q = em.createQuery(query, Shoppinguser.class);
		try {
			Shoppinguser user = q.getSingleResult();
			return user;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static Shoppinguser selectByUserId(long userId) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT s FROM Shoppinguser s WHERE s.id = " + userId;
		TypedQuery<Shoppinguser> q = em.createQuery(query, Shoppinguser.class);
		try {
			Shoppinguser user = q.getSingleResult();
			return user;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static Shoppinguser selectByEmail(String email) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT s FROM Shoppinguser s WHERE s.email = '" + email + "'";
		TypedQuery<Shoppinguser> q = em.createQuery(query, Shoppinguser.class);
		try {
			Shoppinguser user = q.getSingleResult();
			return user;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static void insert(Shoppinguser user) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.persist(user);
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	
	public static void update(Shoppinguser user) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.merge(user);
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	
}