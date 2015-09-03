import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import model.Comment;
import model.Shoppinguser;
import productTools.DBUtil;


public class CommentDB {
	public static List<Comment> select() {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Comment c";
		TypedQuery<Comment> q = em.createQuery(query, Comment.class);
		try {
			List<Comment> comments = q.getResultList();
			return comments;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static List<Comment> selectByProduct(long productId) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT c FROM Comment c WHERE c.product.id = " + productId;
		TypedQuery<Comment> q = em.createQuery(query, Comment.class);
		try {
			List<Comment> comments = q.getResultList();
			return comments;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static void insert(Comment comment) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try {
			em.persist(comment);
			trans.commit();
		} catch (Exception e) {
			System.out.println(e);
			trans.rollback();
		} finally {
			em.close();
		}
	}
}