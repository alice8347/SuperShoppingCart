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
import model.Shoppinguser;

/**
 * Servlet implementation class User
 */
@WebServlet("/LoginSignup")
public class LoginSignup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String loginErr;
	private String signupErr;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginSignup() {
        super();
        loginErr = "";
        signupErr = "";
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (request.getParameter("logout") != null) {
			String logout = request.getParameter("logout");
			if (logout.equals("true")) {
				List<Cart> cartList = (List<Cart>) session.getAttribute("cartCheckout");
				List<Cart> cartDB = CartDB.selectByUserStatus((String) session.getAttribute("email"), 0);
				boolean existCart = false;
				
				if ((cartList == null) || cartList.isEmpty()) {
					for (Cart cart : cartDB) {
						CartDB.delete(cart);
					}
				} else {				
					for (int i = 0; i < cartDB.size(); i++) {
						for (int j = 0; j < cartList.size(); j++) {
							if ((cartDB.get(i).getPid() == cartList.get(j).getPid()) && (cartDB.get(i).getPquantity() != cartList.get(j).getPquantity())) {
								cartDB.get(i).setPquantity(cartList.get(j).getPquantity());
								CartDB.updateSingle(cartDB.get(i));
								existCart = true;
								break;
							} else if ((cartDB.get(i).getPid() == cartList.get(j).getPid()) && (cartDB.get(i).getPquantity() == cartList.get(j).getPquantity())) {
								existCart = true;
								break;
							}
						}
						
						if (!existCart) {
							CartDB.delete(cartDB.get(i));
						}
					}
					
					boolean existDB = false;
					
					for (int i = 0; i < cartList.size(); i++) {
						for (int j = 0; j < cartDB.size(); j++) {
							if (cartList.get(i).getPid() == cartDB.get(j).getPid()) {
								existDB = true;
								break;
							}
						}
						
						if (!existDB) {
							CartDB.updateSingle(cartList.get(i));
						}
					}
				}

				session.removeAttribute("email");
				session.removeAttribute("cartCheckout");
				getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
			}
		}
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		loginErr = "";
		if (request.getParameter("LoginSub") != null) {
			if (request.getParameter("email") != null) {
				String inputUserN = request.getParameter("email");
				if (UserDB.selectByEmail(inputUserN) == null) {
					loginErr += "<script type=\"text/javascript\">validateEmail()</script>";
					request.setAttribute("loginErr", loginErr);
					getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
				} else {
					if (UserDB.selectByEmail(inputUserN).getPassword().equals(request.getParameter("password"))) {
						session.setAttribute("email", inputUserN);
						session.setAttribute("cartCheckout", CartDB.selectByUserStatus(inputUserN, 0));
						response.sendRedirect("/SuperShoppingCart/ProcessProduct");
					} else {
						loginErr += "<script type=\"text/javascript\">validatePassword()</script>";
						request.setAttribute("loginErr", loginErr);
						getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
					}
				}
			}
		}
		
		signupErr = "";
		if (request.getParameter("SignupSub") != null) {
			if (request.getParameter("email") != null) {
				String inputUserN = request.getParameter("email");
				if (UserDB.selectByEmail(inputUserN) == null) {
					Shoppinguser user = new Shoppinguser();
					String email = request.getParameter("email");
					user.setEmail(email);
					String password = request.getParameter("password");
					user.setPassword(password);
					UserDB.insert(user);
					session.setAttribute("email", user.getEmail());
					response.sendRedirect("/SuperShoppingCart/ProcessProduct");
				} else {
					signupErr += "<script type=\"text/javascript\">validateEmail()</script>";
					request.setAttribute("signupErr", signupErr);
					getServletContext().getRequestDispatcher("/Signup.jsp").forward(request, response);
				}
			}
		}
	}

}
