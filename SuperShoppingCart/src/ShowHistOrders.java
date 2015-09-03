import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cart;

/**
 * Servlet implementation class ShowHistOrders
 */
@WebServlet("/ShowHistOrders")
public class ShowHistOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String histOrders;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowHistOrders() {
        super();
        histOrders = "";
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		histOrders = "";
		HttpSession session = request.getSession();
		histOrders += displayPastOrders(session);
		request.setAttribute("histOrders", histOrders);
		getServletContext().getRequestDispatcher("/HistoryOrders.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	private static String displayPastOrders(HttpSession session) {
		String msg = "";
		List<Cart> orders = CartDB.selectByUserStatus((String) session.getAttribute("email"), 1);
		if (orders != null && !orders.isEmpty()) {
			msg += "<div class=\"container\"><h3>Historical Orders</h3><table class=\"table table-striped\"><thead><tr><th width=\"60%\">Product Name</th><th width=\"30%\">Price</th><th width=\"10%\">Quantity</th></tr></thead><tbody>";
			for (int i = 0; i < orders.size(); i++) {
				msg += "<tr><td width=\"60%\"><a href=\"ProductDetails?productid=" + orders.get(i).getPid() + "\">" + orders.get(i).getPname() + "</a></td><td width=\"30%\">" + ProcessCart.formattedPrice(orders.get(i).getPprice()) + "</td><td width=\"10%\">" + orders.get(i).getPquantity() + "</td></tr>";
			}
			msg += "</tbody></table></div>";
		} else {
			msg += "<div class=\"container\"><h3>Historical Orders</h3><div class=\"jumbotron\"><h4>No historical order.</h4></div></div>";
		}
		return msg;
	}

}
