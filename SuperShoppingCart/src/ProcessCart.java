import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cart;

/**
 * Servlet implementation class ProcessCart
 */
@WebServlet("/ProcessCart")
public class ProcessCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String shoppingCartMsg;
	private String order;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessCart() {
        super();
        shoppingCartMsg = "";
        order = "";
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		order = "";
		if (request.getParameter("update") != null) {
			List<Cart> preCartCheckout = (List<Cart>) session.getAttribute("cartCheckout");
			List<Cart> cartCheckout = new ArrayList<Cart>();

			for (int i = 0; i < preCartCheckout.size(); i++) {
				if ((!request.getParameter("product" + i).isEmpty()) && (Integer.parseInt(request.getParameter("product" + i)) > 0)) {
					Cart productSelected = new Cart(UserDB.selectByEmail((String) session.getAttribute("email")), preCartCheckout.get(i).getPid(), preCartCheckout.get(i).getPname(), preCartCheckout.get(i).getPprice(), Integer.parseInt(request.getParameter("product" + i)));
					cartCheckout.add(productSelected);
				}
			}
			session.setAttribute("cartCheckout", cartCheckout);
			getServletContext().getRequestDispatcher("/ShoppingCart.jsp").forward(request, response);
		}
		
		if (request.getParameter("confirm") != null) {
			order += displayOrder(session);
			request.setAttribute("order", order);
			List<Cart> cartList = (List<Cart>) session.getAttribute("cartCheckout");

			for (int i = 0; i < cartList.size(); i++) {
				cartList.get(i).setOrdered(1);
				CartDB.updateSingle(cartList.get(i));
			}

			session.removeAttribute("cartCheckout");
			getServletContext().getRequestDispatcher("/OrderConfirmation.jsp").forward(request, response);
		}
	}
	
//	private static String displayCart(HttpSession session) {
//		String msg = "";
//		List<Cart> cart = (List<Cart>) session.getAttribute("cartCheckout");
//		if (cart != null && !cart.isEmpty()) {
//			msg += "<div class=\"container\"><br /><form class=\"form-horizontal\" role=\"form\" name=\"cart\" id=\"cart\" action=\"ProcessCart\" method=\"post\"><table class=\"table table-striped\"><thead><tr><th width=\"60%\">Product Name</th><th width=\"30%\">Price</th><th width=\"10%\">Add to Cart</th></tr></thead><tbody>";
//			for (int i = 0; i < cart.size(); i++) {
//				msg += "<tr><td width=\"60%\"><a href=\"ProductDetails?productid=" + cart.get(i).getPid() + "\">" + cart.get(i).getPname() + "</a></td><td width=\"30%\">" + formattedPrice(cart.get(i).getPprice()) + "</td><td width=\"10%\"><input type=\"text\" name=\"product" + i + "\" id=\"product" + i + "\" value=\"" + cart.get(i).getPquantity() + "\"></td></tr>";
//			}
//			msg += "</tbody></table><div class=\"form-group\"><div class=\"col-sm-offset-2 col-sm-10\"><button type=\"submit\" class=\"btn btn-default\" name=\"update\" id=\"update\">Update</button></div><div class=\"col-sm-offset-2 col-sm-10\"><button type=\"submit\" class=\"btn btn-default\" name=\"confirm\" id=\"confirm\">Confirm</button></div></div></form></div>";
//		} else {
//			msg += "<div class=\"container\"><div class=\"jumbotron\"><h4>Your cart is empty.</h4></div></div>";
//		}
//		return msg;
//	}
	
	private static String displayOrder(HttpSession session) {
		String msg = "";
		double subTotal = 0.0;
		List<Cart> cart = (List<Cart>) session.getAttribute("cartCheckout");
		if (cart != null && !cart.isEmpty()) {
			msg += "<div class=\"container\"><div class=\"jumbotron\"><h4>Your order is received.</h4><h4>Thank you for shopping with us!</h4></div></div>";
			msg += "<div class=\"container\"><br /><table class=\"table table-striped\"><thead><tr><th width=\"60%\">Product Name</th><th width=\"30%\">Price</th><th width=\"10%\">Add to Cart</th></tr></thead><tbody>";
			for (int i = 0; i < cart.size(); i++) {
				if (cart.get(i).getPquantity() > 0) {
					msg += "<tr><td width=\"60%\">" + cart.get(i).getPname() + "</td><td width=\"30%\">" + formattedPrice(cart.get(i).getPprice()) + "</td><td width=\"10%\">" + cart.get(i).getPquantity() + "</td></tr>";
					subTotal += cart.get(i).getPprice() * cart.get(i).getPquantity();
				}
			}
			msg += "<tr><td colspan=\"3\" align=\"right\">Subtotal: $" + formattedPrice(subTotal) + "</td></tr>";
			msg += "<tr><td colspan=\"3\" align=\"right\">Tax (6%): $" + formattedPrice(subTotal * 0.06) + "</td></tr>";
			msg += "<tr><td colspan=\"3\" align=\"right\">Total: $" + formattedPrice(subTotal * 1.06) + "</td></tr></tbody></table></div>";
		}
		return msg;
	}
	
	public static String formattedPrice(double total) {
		DecimalFormat myFormatter = new DecimalFormat("###,##0.00");
		String output = myFormatter.format(total);
		return output;
	}

}
