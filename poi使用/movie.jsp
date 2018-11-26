<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>电影列表</title>
<link rel="stylesheet" href="/static/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="/static/bootstrap/css/font-awesome.min.css" />
<link rel="stylesheet" href="/static/bootstrap/font/FontAwesome.otf" />
<link rel="stylesheet" href="/static/bootstrap/css/bootstrap-table.css" />
</head>
<body>


	<nav class="navbar navbar-light bg-light justify-content-between">
		<a class="navbar-brand">Navbar</a>
		<form class="form-inline" id="search">
			<input class="form-control mr-sm-2" type="search"
				placeholder="请输入关键字查询" aria-label="Search" name="key"
				value="${param.key}" id="INPUT_KEY">
			<button class="btn btn-outline-success my-2 my-sm-0"  id="BUTTON_SEARCH">Search</button>
		</form>
	</nav>

	<button id="PO_PLAN_EXPORT" type="button"
		class="btn btn-primary pull-right">
		<i class="icon-upload-alt"></i> 导出
	</button>

<button id="accross">发起跨域请求</button>
<ul style="list-style:none">

</ul>

	<div class="box">
		<div class="box-body">
			<table class="table" id="table">
			</table>
			
			 <select name="select" id="select">
                <option value="0">请选择</option>
                <option value="1">北京</option>
                <option value="2">上海</option>
                <option value="3">焦作</option>
            </select>
			
		</div>
	</div>

	<script src="/static/dist/js/jquery-3.3.1.js"></script>
	
	<script src="/static/bootstrap/js/bootstrap.min.js"></script>
	<script src="/static/bootstrap/js/bootstrap-table.js"></script>
	<script src="/static/bootstrap/js/bootstrap-table-zh-CN.min.js"></script>
	<script src="/static/bootstrap/js/jquery.tabletojson.js"></script>
	<script src="/static/dist/js/jquery.twbsPagination.min.js"></script>
	<script src="/static/plugins/moment/moment.js"></script>
	<script src="/static/plugins/moment/moment.locals.js"></script>
	<script>
		$(function() {
			//阻止表单默认提交行为
			$("form").submit(function(e){
			    e.preventDefault();
			  });
		
			
			//跨域请求
			$("#accross").click(function(){
				
				var user={name:"rose",age:23};
				
				 $.ajax({
				        url: "http://localhost/accross",
				        type: "GET",
				        data: {
				            user:JSON.stringify(user)
				        },
				       
				        success: function (data) {
				        	var data_obj = JSON.parse(data);
				        	  var li="<li>"+data_obj.name+"</li>";
				        	  var li2="<li>"+data_obj.age+"</li>";
				        	 $("ul").append(li).append(li2); 
				        },
				        error: function () {
				            alert("请求超时错误!");
				        }
				    })
				
				
			});
			
			
			 var queryConditions = {"select":"","marital":"","key":""};
			 
			 //添加选择参数到数组
	         
		        $("#INPUT_KEY").change(function(){
		          
		        	queryConditions.key=$(this).val();
		           
		            
		        });
			 
			$("#BUTTON_SEARCH").click(function(event){
				
				
			 	var url = "<c:url value='/movie/home?1=1'/>";
				
				url+="&param="+JSON.stringify(queryConditions);
				
				 $("#BUTTON_SEARCH").attr({"disabled":"true"}).addClass("disabled");
				  
				 $('#table').bootstrapTable('refresh', {url: url}).on('load-success.bs.table', function() {
					 
				 $("#BUTTON_SEARCH").removeAttr("disabled").removeClass("disabled");});
			});
			
			
	       
	        
			
			//根据选择条件导出（当选择条件时，把所有条件封装在一个数组中）
			$("#PO_PLAN_EXPORT").click(function() {
				
				
				window.location.href="/movie/export?param="+JSON.stringify(queryConditions); 
			});

			
			$(function() {
				//初始化Table
				var movieTable = new TableInit();
				movieTable.Init();
			});

			var TableInit = function() {
				var oTableInit = new Object();
				//初始化Table
				oTableInit.Init = function() {

					$('#table').bootstrapTable({
						url : "/movie/home",
						method : "get",
						toolbar : '#toolbar', //工具按钮用哪个容器
						striped : true, //是否显示行间隔色
						cache : false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
						pagination : true, //是否显示分页（*）
						sortable : false, //是否启用排序
						sortOrder : "asc", //排序方式
						//queryParams : oTableInit.queryParams,//传递参数（*）
						sidePagination : "server", //分页方式：client客户端分页，server服务端分页（*）
						queryParamsType : '',//必须指定类型参数类型  ''
						pageNumber : 1, //初始化加载第一页，默认第一页
						pageSize : 10, //每页的记录行数（*）
						pageList : [ 10, 25, 50, 100 ], //可供选择的每页的行数（*）
						search : true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
						strictSearch : true,
						showColumns : true, //是否显示所有的列
						showRefresh : true, //是否显示刷新按钮
						minimumCountColumns : 2, //最少允许的列数
						clickToSelect : true, //是否启用点击选中行
						height : 500, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
						uniqueId : "ID", //每一行的唯一标识，一般为主键列
						showToggle : true, //是否显示详细视图和列表视图的切换按钮
						cardView : false, //是否显示详细视图
						detailView : false, //是否显示父子表
						locale : "zh-CN", //中文支持
						queryParams : function queryParams(params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的  
							return {//这里的params是table提供的  
								//offset : params.offset,//从数据库第几条记录开始  
								//limit : params.limit,//取几条数据
								
								limit : params.pageSize, //每页多少条数据
								pageNo : params.pageNumber,
							};
						},

						responseHandler : responseHandler,
						//列名
						columns : [ {
							checkbox : true
						}, {
							field : 'movieName',
							title : '电影名'
						}, {
							field : 'directorName',
							title : '导演名'
						}, {
							field : 'area',
							title : '区域'
						}, {
							field : 'year',
							title : '年份'
						}, {
							field : 'simpleContent',
							title : '简介',
							halign : 'center',
							align : "center"
						}, {
							field : 'createTime',
							title : '创建时间',
							halign : 'center',
							align : "center",
							formatter : timeFormatter
						}, {
							field : 'scanNum',
							title : '浏览数'
						}, {
							field : 'replyNum',
							title : '回复数'
						} ]
					});

				}
				
				responseHandler = function(res) {
					return {
						"total" : res.data.total,//总页数
						"rows" : res.data.rows
					//数据
					};
				};

				function timeFormatter(value, row, index) {
					return moment(row.createTime)
							.format("YYYY年MM月DD日 HH:mm:ss");
				};
				return oTableInit;
			}

		});
	</script>



</body>
</html>