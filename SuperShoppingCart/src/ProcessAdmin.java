import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cart;

/**
 * Servlet implementation class ProcessAdmin
 */
@WebServlet("/ProcessAdmin")
public class ProcessAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String adminMsg;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessAdmin() {
        super();
        adminMsg = "";
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		adminMsg = "";
		List<Cart> currentCart = CartDB.selectByStatus(1);
		
		if (currentCart.isEmpty()) {
			adminMsg += "<div class=\"container\"><div class=\"jumbotron\"><h4>There is no order.</h4></div></div>";
		} else {
			adminMsg += "<div class=\"container\"><br /><table class=\"table table-striped\"><thead><tr><th>ID</th><th>User ID</th><th>Product ID</th><th>Product Name</th><th>Price</th><th>Quantity</th><tr></thead><tbody>";
			for (int i = 0; i < currentCart.size(); i++) {
				adminMsg += "<tr><td>" + currentCart.get(i).getId() + "</td><td>" + currentCart.get(i).getShoppinguser().getId() + "</td><td>" + currentCart.get(i).getPid() + "</td><td>" + currentCart.get(i).getPname() + "</td><td>" + currentCart.get(i).getPprice() + "</td><td>" + currentCart.get(i).getPquantity() + "</td></tr>";
			}
			adminMsg += "</tbody></table></div>";
		}
		
		request.setAttribute("adminMsg", adminMsg);
		getServletContext().getRequestDispatcher("/AdminOrders.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}