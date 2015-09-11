import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.Cart;
import productTools.DBUtil;


public class CartDB {
	public static List<Cart> select() {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Cart c";
		TypedQuery<Cart> q = em.createQuery(query, Cart.class);
		try {
			List<Cart> cart = q.getResultList();
			return cart;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static Cart selectById(long id) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Cart c WHERE c.id = " + id;
		TypedQuery<Cart> q = em.createQuery(query, Cart.class);
		try {
			Cart cart = q.getSingleResult();
			return cart;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static List<Cart> selectByUser(String email) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Cart c WHERE c.shoppinguser.email = '" + email + "'";
		TypedQuery<Cart> q = em.createQuery(query, Cart.class);
		try {
			List<Cart> cart = q.getResultList();
			return cart;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static List<Cart> selectByStatus(int ordered) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Cart c WHERE c.ordered = " + ordered;
		TypedQuery<Cart> q = em.createQuery(query, Cart.class);
		try {
			List<Cart> cart = q.getResultList();
			return cart;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static List<Cart> selectByUserStatus(String email, int ordered) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Cart c WHERE c.shoppinguser.email = '" + email + "' AND c.ordered = " + ordered;
		TypedQuery<Cart> q = em.createQuery(query, Cart.class);
		try {
			List<Cart> cart = q.getResultList();
			return cart;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static void insert(List<Cart> cartList) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			for (Cart cart : cartList) {
				em.persist(cart);
			}
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}

	public static void updateSingle(Cart cart) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.merge(cart);
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	
	public static void updateList(List<Cart> cartList) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			for (Cart cart : cartList) {
				em.merge(cart);
			}			
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	
	public static void delete(Cart cart) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.remove(em.merge(cart));
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
	
}
