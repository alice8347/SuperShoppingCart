<jsp:include page="includes/header.jsp"/>
<div>
<ul class="nav navbar-nav navbar-right">
<% if (session.getAttribute("email") == null) { %>
<li><a href="Signup.jsp">Signup</a></li>
<li><a href="Login.jsp">Login</a></li>
<% } else if (((String) session.getAttribute("email")).equals("Admin")){ %>
<li><a href="ProcessAdmin">View Users' Orders</a></li>
<li><a href="LoginSignup?logout=true">Logout</a></li>
<% } else {%>
<li><a href="ShowHistOrders">View Historical Orders</a></li>
<li><a href="ShoppingCart.jsp">View Cart</a></li>
<li><a href="LoginSignup?logout=true">Logout</a></li>
<% } %>
</ul>
</div>
</div>
</nav>
${details}

<jsp:include page="includes/footer.jsp"/>