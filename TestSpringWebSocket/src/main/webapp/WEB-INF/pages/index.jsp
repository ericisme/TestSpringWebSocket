<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
	<head>
		<title>多人聊天室</title>	
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.0.js"></script>
		<script type="text/javascript" src="<%=basePath%>lib/sockjs-0.3.4.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>lib/stomp.js"></script>
	</head>
	<body>
		<h2>Hello World!2</h2>
		<div id="chatMainContent">
		</div>
		<select id="chatName" name="chatName">
			<option>张三</option>
			<option>李四</option>
			<option>王五</option>
		</select>
		<input type="text" id="chatContent" name="chatContent" />
		<input type="button" value="发送" onclick="sendName();"/>
	</body>
<script type="text/javascript">
var sessionId = '<%=request.getSession().getId()%>';
var stompClient = null;
//连接服务器的函数
function connect(){
    var socket = new SockJS('/testSpringWebSocket');
    stompClient = Stomp.over(socket);    
    stompClient.connect('', '', function (frame) {
        //用户聊天订阅
        stompClient.subscribe('/userChat/chat' + '1', function (chat) {
            showChat(JSON.parse(chat.body));
        });
        //初始化聊天记录;
    	stompClient.subscribe('/app/init/userChatqueue', function (initData) {
    	    var body = JSON.parse(initData.body);
    	    var chat = body.chat;
    	    for(var i = 0;i<chat.length;i++){
    	    	showChat(chat[i]);
    	    }
    	});
    }, function () {
        connect();
    });  
}
 //发送聊天信息
function sendName() {
    var input = $('#chatContent');
    var inputValue = input.val();
    input.val("");
    stompClient.send("/app/userChat", {}, JSON.stringify({
        'name': encodeURIComponent($('#chatName').val()),
        'chatContent': encodeURIComponent(inputValue),
        'sessionId': sessionId
    }));
} 
//显示聊天信息  
function showChat(message) {  
	$('#chatMainContent').html($('#chatMainContent').html()+ '<br/>'+decodeURIComponent(message.name) + ':' + decodeURIComponent(message.chatContent) );
}  
connect();
//initChatContent();
</script>
</html>
