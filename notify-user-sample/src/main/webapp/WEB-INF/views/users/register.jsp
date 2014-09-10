<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="/users" var="usersBaseUrl" />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Register User</title>
		
		<!-- Bootstrap -->
		<link href="/assets/css/bootstrap.min.css" rel="stylesheet">
		
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
		<div class="container">
			<h2 class="page-header">Register User</h2>
			<form:form method="post" role="form" commandName="command">
				<div class="row">
					<div class="form-group col-sm-6">
						<label for="fullName">Full Name</label>
						<form:input path="fullName" id="fullName" type="text" class="form-control" placeholder="Full Name" />
					</div>
					<div class="form-group col-sm-6">
						<label for="emailAddress">Email Address</label>
						<form:input path="emailAddress" id="emailAddress" type="email" class="form-control" placeholder="Email Address" />
					</div>
				</div>
				<div class="row">
					<div class="form-group col-sm-6">
						<label for="username">Username</label>
						<form:input path="username" id="username" type="text" class="form-control" placeholder="Username" />
					</div>
					<div class="form-group col-sm-6">
						<label for="password">Password</label>
						<p class="form-control-static">Generated by System</p>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-sm-6">
						<label for="emailAddress">Roles</label>
						<form:select path="roles" id="roles" class="form-control" items="${roles}" itemValue="id" itemLabel="label" />
					</div>
				</div>
				<button type="submit" class="btn btn-primary">Register</button>
				<a href="${usersBaseUrl}" class="btn btn-warning">Cancel</a>
			</form:form>
		</div>


	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="/assets/js/jquery.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="/assets/js/bootstrap.min.js"></script>
	</body>
</html>