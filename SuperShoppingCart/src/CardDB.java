import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.Card;
import model.Cart;
import productTools.DBUtil;


public class CardDB {
	public static List<Card> select() {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Card c";
		TypedQuery<Card> q = em.createQuery(query, Card.class);
		try {
			List<Card> card = q.getResultList();
			return card;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static List<Card> selectByUser(String email) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Card c WHERE c.shoppinguser.email = '" + email + "'";
		TypedQuery<Card> q = em.createQuery(query, Card.class);
		try {
			List<Card> card = q.getResultList();
			return card;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static void insert(Card card) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.persist(card);
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	
	public static void update(Card card) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.merge(card);
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	
	public static void delete(Card card) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.remove(em.merge(card));
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	
}
