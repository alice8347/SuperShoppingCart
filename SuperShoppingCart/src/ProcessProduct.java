import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cart;
import model.Product;

/**
 * Servlet implementation class ProcessProduct
 */
@WebServlet("/ProcessProduct")
public class ProcessProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String productListMsg;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProcessProduct() {
        super();
        productListMsg = "";
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		productListMsg = "";
		List<Product> productList = ProductDB.select();
		List<Cart> cartList = (List<Cart>) session.getAttribute("cartCheckout");
		boolean exist = false;
		int index = 0;
		
		if (!productList.isEmpty()) {
			if (session.getAttribute("email") != null) {
				productListMsg += "<th width=\"30%\">Price</th><th width=\"10%\">Quantity</th></tr></thead><tbody>";
			} else {
				productListMsg += "<th width=\"40%\">Price</th></tr></thead><tbody>";
			}
			
			for (int i = 0; i < productList.size(); i++) {
				if (session.getAttribute("email") != null) {
					if (cartList == null) {
						productListMsg += "<tr><td width=\"60%\"><a href=\"Details?productid=" + productList.get(i).getId() + "\">" + productList.get(i).getName() + "</a></td><td width=\"30%\">" + ProcessCart.formattedPrice(productList.get(i).getPrice()) + "</td><td width=\"10%\"><input type=\"text\" name=\"product" + i + "\" id=\"product" + i + "\"></td></tr>";
						continue;
					} else {
						for (int j = 0; j < cartList.size(); j++) {
							if (cartList.get(j).getPid() == productList.get(i).getId()) {
								exist = true;
								index = j;
								break;
							}
						}
						
						if (exist) {
							productListMsg += "<tr><td width=\"60%\"><a href=\"Details?productid=" + productList.get(i).getId() + "\">" + productList.get(i).getName() + "</a></td><td width=\"30%\">" + ProcessCart.formattedPrice(productList.get(i).getPrice()) + "</td><td width=\"10%\"><input type=\"text\" name=\"product" + i + "\" id=\"product" + i + "\" value=\"" + cartList.get(index).getPquantity() + "\"></td></tr>";
						} else {
							productListMsg += "<tr><td width=\"60%\"><a href=\"Details?productid=" + productList.get(i).getId() + "\">" + productList.get(i).getName() + "</a></td><td width=\"30%\">" + ProcessCart.formattedPrice(productList.get(i).getPrice()) + "</td><td width=\"10%\"><input type=\"text\" name=\"product" + i + "\" id=\"product" + i + "\"></td></tr>";
						}
						exist = false;
					}
				} else {
					productListMsg += "<tr><td width=\"60%\"><a href=\"Details?productid=" + productList.get(i).getId() + "\">" + productList.get(i).getName() + "</a></td><td width=\"30%\">" + ProcessCart.formattedPrice(productList.get(i).getPrice()) + "</td></tr>";
				}
			}
		}
		request.setAttribute("productListMsg", productListMsg);
		getServletContext().getRequestDispatcher("/ProductList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		List<Cart> cartCheckout = new ArrayList<Cart>();
		
		if (request.getParameter("checkout") != null) {
			List<Product> productList = ProductDB.select();
			for (int i = 0; i < productList.size(); i++) {
				if (!request.getParameter("product" + i).isEmpty()) {
					Cart productSelected = new Cart(UserDB.selectByEmail((String)session.getAttribute("email")), productList.get(i).getId(), productList.get(i).getName(), productList.get(i).getPrice(), Integer.parseInt(request.getParameter("product" + i)));
					cartCheckout.add(productSelected);
				}
			}
			session.setAttribute("cartCheckout", cartCheckout);
			getServletContext().getRequestDispatcher("/ShoppingCart.jsp").forward(request, response);
		}
	}

}