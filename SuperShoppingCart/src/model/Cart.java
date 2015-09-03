package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the CART database table.
 * 
 */
@Entity
@Table(name="Cart", schema="TESTDB")
@NamedQuery(name="Cart.findAll", query="SELECT c FROM Cart c")
public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CART_ID_GENERATOR", schema="TESTDB", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CART_ID_GENERATOR")
	private long id;

	private int ordered;

	private long pid;

	private String pname;

	private double pprice;

	private int pquantity;

	//bi-directional many-to-one association to Shoppinguser
	@ManyToOne
	@JoinColumn(name="USERID")
	private Shoppinguser shoppinguser;

	public Cart() {
	}
	
	public Cart(Shoppinguser user, long pId, String pName, double pPrice, int pQuantity) {
		this.shoppinguser = user;
		this.pid = pId;
		this.pname = pName;
		this.pprice = pPrice;
		this.pquantity = pQuantity;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getOrdered() {
		return this.ordered;
	}

	public void setOrdered(int ordered) {
		this.ordered = ordered;
	}

	public long getPid() {
		return this.pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getPname() {
		return this.pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public double getPprice() {
		return this.pprice;
	}

	public void setPprice(double pprice) {
		this.pprice = pprice;
	}

	public int getPquantity() {
		return this.pquantity;
	}

	public void setPquantity(int pquantity) {
		this.pquantity = pquantity;
	}

	public Shoppinguser getShoppinguser() {
		return this.shoppinguser;
	}

	public void setShoppinguser(Shoppinguser shoppinguser) {
		this.shoppinguser = shoppinguser;
	}

}