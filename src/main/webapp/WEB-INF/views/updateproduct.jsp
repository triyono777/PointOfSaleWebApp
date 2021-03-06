<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Product</title>
<link rel="stylesheet" href="resources/style.css">
</head>
<body>

<divnav>
	<form name="home" class="navprofile" action="http://localhost:8080/pointofsale/" method="get">
		<input type="submit" class="inside" value="Home">
	</form>
	
	<form name="login" class="navprofile" action="login" method="post">
		<input type="submit" class="inside" value="Login">
	</form>
	
	<form name="customerSearch" class="navprofile" action="searchcustomer" method="post">
		<input type="submit" class="inside" value="Customer Search">
	</form>
	
	<form name="ownerPage" class="navprofile" action="ownerpage" method="post">
		<input type="submit" class="inside" value="Owner Page">
	</form>
</divnav>

<h1>Update Product</h1>

<div>
	<form name="updateProduct" onsubmit="return validation()" action="ownerpage" method="post">
	
		<input type="hidden" name="productID" class="inside" value="${product.productID}">
		<br>Product Name: <input type="text" name="productNameUp" class="inside" value="${product.productName}"> 
		<br>Price: <input type="text" name="price" class="inside" value="${product.price}"> 
		<br>Type: <input type="text" name="type" class="inside" value="${product.type }"> 
		<br>Description: <input type="text" name="description" class="inside" value="${product.description }">
		<br><br>
		<input type="submit" class="inside" value="Update Product">	
	</form>
</div>

<script>
	function prodValidation() {
		
		var productName = document.forms["addProduct"]["productName"].value;
		var price = document.forms["addProduct"]["price"].value;
		var type = document.forms["addProduct"]["type"].value;
		var description = document.forms["addProduct"]["description"].value;
	
		var letters = /^[A-Za-z\s]+$/;
	
		if (productName.length === 0) {
			alert("Need to input a product name!");
			return false;
		}
		if (price.length === 0) {
			alert("Need to input a price!");
			return false;
		}
		if (/^(\d*([.,](?=\d{3}))?\d+)+((?!\2)[.,]\d\d)?$/.test(price)) {
			alert("Need to input proper price value!");
			return false;
		}
		if (type.length === 0) {
			alert("Need to input a type!");
			return false;
		}
		if (description.length === 0) {
			alert("Need to input a description!");
			return false;
		}
		
	}
</script>

</body>
</html>