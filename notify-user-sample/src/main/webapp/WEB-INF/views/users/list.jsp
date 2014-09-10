<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<spring:url value="/users" var="usersBaseUrl" />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Users List</title>
		
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
			<h2 class="page-header">User List</h2>
			<a href="${usersBaseUrl}?form=register" class="btn btn-primary">New User</a>
			<div class="table-responsive">
				<table class="table table-hover table-striped">
					<thead>
						<tr>
							<th>Username</th>
							<th>Full Name</th>
							<th>Email Address</th>
							<th></th>
						</tr>	
					</thead>
					<tbody>
						<c:forEach items="${users}" var="user">
							<tr>
								<td><a href="${usersBaseUrl}/${user.username}">${user.username}</a></td>
								<td>${user.fullName}</td>
								<td>${user.emailAddress}</td>
								<td>
									<div class="dropdown">
										<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">
											Actions <span class="caret"></span>
										</button>
										<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
											<li role="presentation"><a href="${usersBaseUrl}/${user.username}/password?reset=start" role="menuitem" tabindex="-1">Reset Password</a></li>
										</ul>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
		<script src="/assets/js/jquery.min.js"></script>
		<!-- Include all compiled plugins (below), or include individual files as needed -->
		<script src="/assets/js/bootstrap.min.js"></script>
	</body>
</html>