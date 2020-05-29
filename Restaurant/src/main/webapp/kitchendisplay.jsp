<html>
	<head>
		<script>
		
            var connectionOpen = false; //Global variables
            var nextOrder = 1; //Increment afer every response to start polling for next response. Fetches already present orders quickly
            																					// and waits for new one.
            
            //GetOrders gets called again and again.
            
            function getOrders(){   // Opening connection to server
            	
                if(!connectionOpen){ // Polling every 500ms to check if connection is open. If open, doesn't do anything. 
                	
                	connectionOpen = true;  // If connection closed, fires a new connection and starts polling again, till receiving response.
                	
                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange=function(){
                    								//status==200 to avoid timeout response and check if there is an actual response
                        if (xmlhttp.readyState==4 && xmlhttp.status==200) {  //Closing connection if received response.
                        	connectionOpen = false;
                        	nextOrder++; //To retrieve next order
                            var ordersDiv = document.getElementById("orders");
                            ordersDiv.innerHTML = xmlhttp.responseText + ordersDiv.innerHTML;
                        }
                    }										//parameter 'size' taken by servlet in server side.
                    xmlhttp.open("GET", "/kitchenAsyncServlet?size=" + nextOrder, true);
                    xmlhttp.send();
                }
            }
            setInterval(getOrders, 500);  // Polling every 500ms if connection is open. 
        </script>
</head>


<body>
	<h1>Ricky's Restaurant - Live orders</h1>

	<div id="orders"></div>


</body>
</html>