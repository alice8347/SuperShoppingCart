package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the CARDS database table.
 * 
 */
@Entity
@Table(name="CARDS", schema="TESTDB")
@NamedQuery(name="Card.findAll", query="SELECT c FROM Card c")
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CARDS_ID_GENERATOR", schema="TESTDB", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CARDS_ID_GENERATOR")
	private long id;

	private String billaddress;

	private long cardnum;

	@Temporal(TemporalType.DATE)
	private Date expdate;

	private String holdername;

	private int securitycode;

	//bi-directional many-to-one association to Shoppinguser
	@ManyToOne
	@JoinColumn(name="USERID")
	private Shoppinguser shoppinguser;

	//bi-directional many-to-one association to Cart
	@OneToMany(mappedBy="card")
	private List<Cart> carts;

	public Card() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBilladdress() {
		return this.billaddress;
	}

	public void setBilladdress(String billaddress) {
		this.billaddress = billaddress;
	}

	public long getCardnum() {
		return this.cardnum;
	}

	public void setCardnum(long cardnum) {
		this.cardnum = cardnum;
	}

	public Date getExpdate() {
		return this.expdate;
	}

	public void setExpdate(Date expdate) {
		this.expdate = expdate;
	}

	public String getHoldername() {
		return this.holdername;
	}

	public void setHoldername(String holdername) {
		this.holdername = holdername;
	}

	public int getSecuritycode() {
		return this.securitycode;
	}

	public void setSecuritycode(int securitycode) {
		this.securitycode = securitycode;
	}

	public Shoppinguser getShoppinguser() {
		return this.shoppinguser;
	}

	public void setShoppinguser(Shoppinguser shoppinguser) {
		this.shoppinguser = shoppinguser;
	}

	public List<Cart> getCarts() {
		return this.carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public Cart addCart(Cart cart) {
		getCarts().add(cart);
		cart.setCard(this);

		return cart;
	}

	public Cart removeCart(Cart cart) {
		getCarts().remove(cart);
		cart.setCard(null);

		return cart;
	}

}