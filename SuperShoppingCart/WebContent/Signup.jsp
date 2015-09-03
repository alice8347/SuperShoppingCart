<jsp:include page="includes/header.jsp"/>
<div>
<ul class="nav navbar-nav navbar-right">
<li><a href="Login.jsp">Login</a></li>
</ul>
</div>
</div>
</nav>
<script type="text/javascript">
function validateForm() {
    var a = document.forms["SignupForm"]["email"].value;
    var b = document.forms["SignupForm"]["password"].value;
    if (a == null || a == "" || b == null || b == "") {
        alert("All fields must be filled out.");
        return false;
    }
}
function validateEmail() {
	alert("User already exists.");
}
</script>

<div class="container">
<h3>Signup</h3>
<br />
<form class="form-horizontal" role="form" name="SignupForm" id="SignupForm" onsubmit="return validateForm()" action="LoginSignup" method="post">
<div class="form-group">
<label class="control-label col-sm-2" for="email">User Name / Email:</label>
<div class="col-sm-10">
<input type="text" class="form-control" name="email" id="email" placeholder="Enter email">
</div>
</div>
<div class="form-group">
<label class="control-label col-sm-2" for="password">Password:</label>
<div class="col-sm-10">
<input type="password" class="form-control" name="password" id="password" placeholder="Enter password">
</div>
</div>
<div class="form-group">
<div class="col-sm-offset-2 col-sm-10">
<button type="submit" class="btn btn-default" name="SignupSub" id="SignupSub">Submit</button>
</div>
</div>
</form>
</div>
${signupErr}

<jsp:include page="includes/footer.jsp"/>