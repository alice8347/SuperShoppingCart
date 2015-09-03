package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the COMMENTS database table.
 * 
 */
@Entity
@Table(name="COMMENTS", schema="TESTDB")
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COMMENTS_ID_GENERATOR", schema="TESTDB", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMMENTS_ID_GENERATOR")
	private long id;

	private String ccontent;

	@Temporal(TemporalType.DATE)
	private Date cdate;

	private String rating;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name="PID")
	private Product product;

	//bi-directional many-to-one association to Shoppinguser
	@ManyToOne
	@JoinColumn(name="USERID")
	private Shoppinguser shoppinguser;

	public Comment() {
	}
	
	public Comment(Product product, Shoppinguser user, String rating, String content, Date date) {
		this.product = product;
		this.shoppinguser = user;
		this.rating = rating;
		this.ccontent = content;
		this.cdate = date;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCcontent() {
		return this.ccontent;
	}

	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}

	public Date getCdate() {
		return this.cdate;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		String ratingStar = "";
		if (rating == 1) {
			ratingStar = "*";
		} else if (rating == 2) {
			ratingStar = "**";
		} else if (rating == 3) {
			ratingStar = "***";
		} else if (rating == 4) {
			ratingStar = "****";
		} else {
			ratingStar = "*****";
		}
		this.rating = ratingStar;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Shoppinguser getShoppinguser() {
		return this.shoppinguser;
	}

	public void setShoppinguser(Shoppinguser shoppinguser) {
		this.shoppinguser = shoppinguser;
	}

}