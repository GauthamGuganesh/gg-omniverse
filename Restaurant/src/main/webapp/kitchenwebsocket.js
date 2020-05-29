
var socket = new WebSocket("ws://localhost:8080/KitchenManagement");//Triggers OnOpen method in WebSocket in server.
socket.onmessage = onMessage; // Whenever server sends any message, socket.onmessage(which has been assigned the function "onMessage") will run the 
							// function defined under it.

function onMessage(event) {

    var order = JSON.parse(event.data);  
    if (order.action == "ADD") {
        displayOrder(order);
    }
    if (order.action == "REMOVE") {
        removeOrder(order);
    }
}

function displayOrder(order) {
    var content = document.getElementById("content");
    var div = document.createElement("div");
    div.setAttribute("id", order.id);
    
    var html = "<h3>Order " + order.id + "</h3>";
    html += "<p>Last update :" + order.time + "</p>";
    html += "<p>Current status : " + order.status + "</p>";
    html += "<p>Contents : " + order.content + "</p>";
    
    html += "<form id='form" + order.id + "'>";
    html+= "<input type='hidden' name='id' value	='" + order.id + "' />";
	html+= "<select name='status'>";
	html+= "<option value='order accepted' selected>order accepted</option>";
	html+= "<option value='payment received'>payment received</option>";
	html+= "<option value='being prepared'>being prepared</option>";
	html+= "<option value='ready for collection'>ready for collection</option>"; 
	html+= "</select><input type='button' value='Update' onClick='sendUpdate(\"" + order.id + "\");'/></form>";

    div.innerHTML= html;
    
    content.appendChild(div);
}

function removeOrder(order) {
 
    var div = document.getElementById(order.id);
    div.remove();
}

function sendUpdate(id) {
	var form = document.getElementById("form"+id);
    var status = form.elements["status"].value;
    var message = { 
    		"id" : id,
    		"status" : status
    };
    socket.send(JSON.stringify(message)); //Client sending message back to the server. 'OnMessage' annotation triggered in Server WebSocket.
}