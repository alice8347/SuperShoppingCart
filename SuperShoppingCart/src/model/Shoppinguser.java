package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SHOPPINGUSERS database table.
 * 
 */
@Entity
@Table(name="SHOPPINGUSERS", schema="TESTDB")
@NamedQuery(name="Shoppinguser.findAll", query="SELECT s FROM Shoppinguser s")
public class Shoppinguser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SHOPPINGUSERS_ID_GENERATOR", schema="TESTDB", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SHOPPINGUSERS_ID_GENERATOR")
	private long id;

	private double credits;

	private String email;

	private String password;

	//bi-directional many-to-one association to Cart
	@OneToMany(mappedBy="shoppinguser")
	private List<Cart> carts;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="shoppinguser")
	private List<Comment> comments;

	public Shoppinguser() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getCredits() {
		return this.credits;
	}

	public void setCredits(double credits) {
		this.credits = credits;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Cart> getCarts() {
		return this.carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public Cart addCart(Cart cart) {
		getCarts().add(cart);
		cart.setShoppinguser(this);

		return cart;
	}

	public Cart removeCart(Cart cart) {
		getCarts().remove(cart);
		cart.setShoppinguser(null);

		return cart;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setShoppinguser(this);

		return comment;
	}

	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setShoppinguser(null);

		return comment;
	}

}