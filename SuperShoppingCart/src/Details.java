import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cart;
import model.Comment;
import model.Product;

/**
 * Servlet implementation class Details
 */
@WebServlet("/Details")
public class Details extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String details;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Details() {
        super();
        details = "";
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		details = "";
		List<Cart> cart = (List<Cart>) session.getAttribute("cartCheckout");
		long productid = Long.parseLong(request.getParameter("productid"));
		session.setAttribute("productid", productid);
		boolean exist = false;
		int cartIndex = 0;
		
		Product product = ProductDB.selectById(productid);
		
		details += "<div class=\"container\"><div class=\"jumbotron\"><h4>Product Name: " + product.getName() + "</h4><h4>Description: <br />" + product.getDescription() + "</h4><h4>Price: $" + ProcessCart.formattedPrice(product.getPrice()) + "</h4></div></div>";
		
		if (cart != null) {
			for (int i = 0; i < cart.size(); i++) {
				if (cart.get(i).getPid() == productid) {
					cartIndex = i;
					exist = true;
				}
			}
		}

		if (session.getAttribute("email") != null) {
			if (exist) {
				details += "<div class=\"container\"><br /><form class=\"form-horizontal\" role=\"form\" name=\"singleProd\" id=\"singleProd\" action=\"Details\" method=\"post\">"
						+ "<input type=\"hidden\" name=\"productid\" value=\"" + productid + "\">"
						+ "<label class=\"control-label col-sm-2\" for=\"product" + productid + "\">Quantity:</label>"
						+ "<input type=\"text\" name=\"product" + productid + "\" id=\"product" + productid + "\" value=\"" + cart.get(cartIndex).getPquantity() + "\">"
						+ "<button type=\"submit\" class=\"btn btn-default\" name=\"singleUpd\" id=\"singleUpd\">Add to Cart</button></form>";
			} else {
				details += "<div class=\"container\"><br /><form class=\"form-horizontal\" role=\"form\" name=\"singleProd\" id=\"singleProd\" action=\"Details\" method=\"post\">"
						+ "<input type=\"hidden\" name=\"productid\" value=\"" + productid + "\">"
						+ "<label class=\"control-label col-sm-2\" for=\"product" + productid + "\">Quantity:</label>"
						+ "<input type=\"text\" name=\"product" + productid + "\" id=\"product" + productid + "\">"
						+ "<button type=\"submit\" class=\"btn btn-default\" name=\"singleUpd\" id=\"singleUpd\">Add to Cart</button></form>";
			}
			
			details += "<div class=\"container\"><br /><h4>Write a Comment</h4><form class=\"form-horizontal\" role=\"form\" name=\"writeComment\" id=\"writeComment\" action=\"Details\" method=\"post\">"
					+ "<input type=\"hidden\" name=\"productid\" value=\"" + productid + "\">"
					+ "<div class=\"form-group\"><label for=\"rating\">Rating:</label><select class=\"form-control\" name=\"rating\" id=\"rating\"><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select></div>"
					+ "<div class=\"form-group\"><label for=\"comment\">Comment:</label><textarea class=\"form-control\" rows=\"5\" name=\"comment\" id=\"comment\"></textarea></div>"
					+ "<div class=\"form-group\"><button type=\"submit\" class=\"btn btn-default\" name=\"addComment\" id=\"addComment\">Send</button></div></form></div>";
		}
		
		List<Comment> comments = CommentDB.selectByProduct(productid);
		for (int i = 0; i < comments.size(); i++) {
			details += "<div class=\"container\"><br /><table class=\"table table-striped\"><thead><tr><th width=\"40%\">" + comments.get(i).getShoppinguser().getEmail() + "</th><th width=\"40%\">" + comments.get(i).getRating() + "</th><th width=\"20%\">" + comments.get(i).getCdate() + "</th></tr></thead>";
			details += "<tbody><tr><td colspan=\"4\">" + comments.get(i).getCcontent() + "</td></tr></tbody></table></div>";
		}

		request.setAttribute("details", details);
		getServletContext().getRequestDispatcher("/ProductDetails.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Cart> cart = (List<Cart>) session.getAttribute("cartCheckout");
		boolean exist = false;
		long productid = Long.parseLong(request.getParameter("productid"));
		session.setAttribute("productid", productid);
		
		if (request.getParameter("singleUpd") != null) {
			for (int i = 0; i < cart.size(); i++) {
				if (cart.get(i).getPid() == productid) {
					cart.get(i).setPquantity(Integer.parseInt(request.getParameter("product" + productid)));
					exist = true;
				}
			}
			
			if (!exist) {
				Product product = ProductDB.selectById(productid);
				Cart newItem = new Cart(UserDB.selectByEmail((String) session.getAttribute("email")), product.getId(), product.getName(), product.getPrice(), Integer.parseInt(request.getParameter("product" + productid)));
				cart.add(newItem);
			}
			
			session.setAttribute("cartCheckout", cart);
			response.sendRedirect("/SuperShoppingCart/ProcessProduct");
		}
		
		if (request.getParameter("addComment") != null) {
			Date date = new Date();
			Comment newComment = new Comment();
			newComment.setProduct(ProductDB.selectById((long) session.getAttribute("productid")));
			newComment.setShoppinguser(UserDB.selectByEmail((String) session.getAttribute("email")));
			newComment.setRating(Integer.parseInt(request.getParameter("rating")));
			newComment.setCcontent(request.getParameter("comment"));
			newComment.setCdate(date);
			CommentDB.insert(newComment);
			doGet(request, response);
		}
	}

}