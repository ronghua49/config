
//前端的跨域请求
$(function () {

    var user = {
        "username": "HelloWorld"
    };

    $.ajax({
        url: "http://localhost:8080/Changyou/UserInfo",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "jsonp",  //json不支持跨域请求,只能使用jsonp
        data: {
            user: JSON.stringify(user)
        },
        jsonp: "callback",  //传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名,默认为callback
        jsonpCallback: "userHandler", （可以忽略） //自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名，也可以写"?"，jQuery会自动为你处理数据
        success: function (data) {
            $("#user_name")[0].innerHTML = data.user_name;
            $("#user_teleNum")[0].innerHTML = data.user_teleNum;
            $("#user_ID")[0].innerHTML = data.user_ID;
        },
        error: function () {
            alert("请求超时错误!");
        }
    })
});

//后端代码

protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json; charset=utf-8");
    String username = new String(request.getParameter("user").getBytes("ISO-8859-1"),"utf-8");
    String callback = new String(request.getParameter("callback").getBytes("ISO-8859-1"),"utf-8");
    System.out.println("接收到的数据：" + username);
    System.out.println("callback的值：" + callback);
    JSONObject user = JSONObject.fromObject(username);
    System.out.println("接收到的用户名：" + user.get("username"));
    JSONObject userinfo = new JSONObject();
    userinfo.put("user_name", "张鸣晓");
    userinfo.put("user_teleNum", "18810011111");
    userinfo.put("user_ID", "123456789098765432");
    PrintWriter out = response.getWriter();
    String backInfo = callback + "(" + userinfo.toString() + ")"; //将json数据封装在callback函数中提供给客户端
    out.print(backInfo);
    out.close();
}


下面我们来说一下getJSON(): 
getJSON()和get是一样的，都是get请求，这就决定了，发送的data数据量不能太多，否则造成url太长接收失败（getJSON方式是不可能有post方式递交的）。 
区别是，getJSON专门请求json数据的，而且getJSON可以实现跨域请求。语法格式如下： 
getJSON(url,[data],[callback]) 
url：string类型， 发送请求地址 
data ：可选参数， 待发送 Key/value 参数 ，同get，post类型的data 
callback ：可选参数，载入成功时回调函数，同get，post类型的callback
