<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>Login</h1>

	<div id="login-error">${sessionScope['SPRING_SECURITY_LAST_EXCEPTION'].message}</div>

		<form action="${pageContext.request.contextPath }/j_spring_security_check" method="post">

<!-- 		    <p>
			    <label for="j_username">Username</label> <input id="j_username"
				    name="j_username" type="text" value="user" />
		    </p>

		    <p>
			    <label for="j_password">Password</label> <input id="j_password"
				    name="j_password" type="password"  value="user"/>	
		    </p> -->
		    <div class="pageHeader">

	<div class="searchBar">
		<table class="searchContent">
		    
		    <tr> 		  
		     <td>
			    <label for="j_username">Username</label> <input id="j_username"
				    name="j_username" type="text" value="admin" />
		    </td>		   
		   </tr>
		    <tr> 		  
		     <td>
			    <label for="j_password">Password</label> <input id="j_password"
				    name="j_password" type="text" value="123456" />
		    </td>

		    </tr>
		    <tr> 		  
		     <td>
			    <label for="j_submit">Login</label> <input id="j_submit"
				    name="j_submit" type="submit" value="Login" />
		    </td>		   
		   </tr>
		    
		    		</table>
	</div>
</div>
		    

		   	  
	   </form>

</body>
</html>