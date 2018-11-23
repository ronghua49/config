
//前端的跨域请求
$(function () {

   var user={name:"rose",age:23};
    $.ajax({
        url: "http://localhost:8080/Changyou/UserInfo",
        type: "POST",
        data: { 
        },
       success: function (data) {
			var data_obj = JSON.parse(data);//将json字符串转换为js对象
			  var li="<li>"+data_obj.name+"</li>";
			  var li2="<li>"+data_obj.age+"</li>";
			 $("ul").append(li).append(li2); 
		},
        error: function () {
            alert("请求超时错误!");
        }
    })
});

//后端代码

@GetMapping("/accross")
	@ResponseBody
	@CrossOrigin//跨域注解
	public String accrossArea(@RequestParam String user) {
		return user;
	}
	


