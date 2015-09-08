<jsp:include page="includes/header.jsp"/>
<div>
<ul class="nav navbar-nav navbar-right">
<li><a href="ProcessAdmin">View Users' Orders</a></li>
<li><a href="LoginSignup?logout=true">Logout</a></li>
</ul>
</div>
</div>
</nav>
<script type="text/javascript">
function validateForm() {
    var a = document.forms["creditForm"]["userId"].value;
    var b = document.forms["creditForm"]["credits"].value;
    if (a == null || a == "" || b == null || b == "") {
        alert("All fields must be filled out.");
        return false;
    }
}
function validateUserId() {
	alert("User does not exist.");
}
</script>

<div class="container">
<h3>Login</h3>
<br />
<form class="form-horizontal" role="form" name="creditForm" id="creditForm" onsubmit="return validateForm()" action="ProcessAdmin" method="post">
<div class="form-group">
<label class="control-label col-sm-2" for="userId">User ID:</label>
<div class="col-sm-10">
<input type="text" class="form-control" name="userId" id="userId" placeholder="Enter user ID">
</div>
</div>
<div class="form-group">
<label class="control-label col-sm-2" for="credits">Credits:</label>
<div class="col-sm-10">
<input type="text" class="form-control" name="credits" id="credits" placeholder="Enter credits">
</div>
</div>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-10">
<button type="submit" class="btn btn-default" name="issueCredits" id="issueCredits">Submit</button>
</div>
</div>
</form>
</div>
${creditMsg}
<jsp:include page="includes/footer.jsp"/>