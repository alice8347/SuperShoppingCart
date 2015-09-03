import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Product;
import productTools.DBUtil;

public class ProductDB {
	public static List<Product> select() {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT p FROM Product p";
		TypedQuery<Product> q = em.createQuery(query, Product.class);
		try {
			List<Product> productList = q.getResultList();
			return productList;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public static Product selectById(long id) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String query = "SELECT p FROM Product p WHERE p.id = " + id;
		TypedQuery<Product> q = em.createQuery(query, Product.class);
		try {
			Product product = q.getSingleResult();
			return product;
		} catch (Exception e) {
			return null;
		} finally {
			em.close();
		}
	}
}