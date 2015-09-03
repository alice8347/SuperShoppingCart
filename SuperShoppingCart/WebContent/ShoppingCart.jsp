<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="includes/header.jsp"/>
<div>
<ul class="nav navbar-nav navbar-right">
<li><a href="LoginSignup?logout=true">Logout</a></li>
</ul>
</div>
</div>
</nav>
<div class="container">
<h3>Shopping Cart</h3>
<a class="btn btn-default" href="ProcessProduct" role="button">Continue Shopping</a>
</div>
<c:choose>
	<c:when test="${empty cartCheckout}">
		<div class="container">
		<div class="jumbotron">
		<h4>Your cart is empty.</h4>
		</div>
		</div>		
	</c:when>
	<c:otherwise>
		<div class="container">
		<br />
		<form class="form-horizontal" role="form" name="cart" id="cart" action="ProcessCart" method="post">
		<table class="table table-striped">
		<thead>
		<tr><th width="60%">Product Name</th><th width="30%">Price</th><th width="10%">Add to Cart</th></tr></thead>
		<tbody>
		<c:forEach var="cart" items="${cartCheckout}" varStatus="i">
			<tr>
			<td width="60%"><a href="ProductDetails?productid=${cart.pid}"><c:out value="${cart.pname}" /></a></td>
			<td width="30%"><fmt:formatNumber type="number" maxFractionDigits="2" value="${cart.pprice}" /></td>
			<td width="10%"><input type="text" name="product${i.index}" id="product${i.index}" value="${cart.pquantity}"></td>
			</tr>
			<c:set var="sum" value="${sum + cart.pquantity}" />
		</c:forEach>
		<c:choose>
			<c:when test="${sum == 0 || sum == 1}">
				<tr><td colspan="3" align="right">You have <c:out value="${sum}" /> item in your cart.</td></tr>
			</c:when>
			<c:otherwise>
				<tr><td colspan="3" align="right">You have <c:out value="${sum}" /> items in your cart.</td></tr>
			</c:otherwise>
		</c:choose>
		</tbody>
		</table>
		<div class="form-group">
		<div class="col-sm-offset-2 col-sm-10">
		<button type="submit" class="btn btn-default" name="update" id="update">Update</button>
		</div>
		<div class="col-sm-offset-2 col-sm-10">
		<button type="submit" class="btn btn-default" name="confirm" id="confirm">Confirm</button>
		</div>
		</div>
		</form>
		</div>
	</c:otherwise>
</c:choose>
<jsp:include page="includes/footer.jsp"/>