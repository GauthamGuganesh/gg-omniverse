<html>
<style>
	#container
	{
		text-align: center;
		box-shadow: 10px 10px 10px rgba(0,0,0,0.23);
		border-style: solid;
	}
	
	#jsp
	{
		font-size: 20px;
		font-weight: 800;
	}
	
</style>

<body>

<div id = "container">
	<h2> Squares of numbers </h2>

<div id = "jsp">
<% for(int i = 0; i < 7 ; i++)
	{
		out.println(i*i);
	}
%>
</div>

</body>
</html>