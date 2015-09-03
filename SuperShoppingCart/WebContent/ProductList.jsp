<jsp:include page="includes/header.jsp"/>
<div>
<ul class="nav navbar-nav navbar-right">
<% if (session.getAttribute("email") == null) { %>
<li><a href="Signup.jsp">Signup</a></li>
<li><a href="Login.jsp">Login</a></li>
<% } else if (((String) session.getAttribute("email")).equals("Admin")){ %>
<li><a href="ProcessAdmin">View Users' Orders</a></li>
<li><a href="LoginSignup?logout=true">Logout</a></li>
<% } else { %>
<li><a href="ShoppingCart.jsp">View Cart</a></li>
<li><a href="LoginSignup?logout=true">Logout</a></li>
<% } %>
</ul>
</div>
</div>
</nav>

<div class="container">
<br />
<% if (session.getAttribute("email") != null) { %>
<form class="form-horizontal" role="form" name="addProduct" id="addProduct" action="ProcessProduct" method="post">
<% } %>
<table class="table table-striped">
<thead>
<tr>
<th width="60%">Product Name</th>
${productListMsg}
</tbody>
</table>
<% if (session.getAttribute("email") != null) { %>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-10">
<button type="submit" class="btn btn-default" name="checkout" id="checkout">Checkout</button>
</div>
</div>
</form>
<% } %>
</div>

<jsp:include page="includes/footer.jsp"/>