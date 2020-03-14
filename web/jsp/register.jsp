<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head></head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员注册</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

<style>
  body{
   margin-top:20px;
   margin:0 auto;
 }
 .carousel-inner .item img{
	 width:100%;
	 height:300px;
 }
 .container .row div{
	 /* position:relative;
	 float:left; */
 }

font {
    color: #3164af;
    font-size: 18px;
    font-weight: normal;
    padding: 0 10px;
}
 </style>
</head>
<body>


<%--菜单栏  导航条--%>
<%@include file="header.jsp"%>


<div class="container" style="width:100%;background:url('${pageContext.request.contextPath}/img/regist_bg.jpg');">
<div class="row">

	<div class="col-md-2"></div>




	<div class="col-md-8" style="background:#fff;padding:40px 80px;margin:30px;border:7px solid #ccc;">
		<font>会员注册</font>USER REGISTER
		<form class="form-horizontal" style="margin-top:5px;" action="${pageContext.request.contextPath}/UserServlet?method=UserRegist" method="post">
			 <div class="form-group">
			    <label for="username" class="col-sm-2 control-label">用户名</label>
			    <div class="col-sm-6">
			      <input type="text" name="username" class="form-control" id="username" placeholder="请输入用户名">
					<span id="sl"></span>
					<script type="text/javascript">
                        $(function(){
                            $("#username").blur(function(){
                                //获得文本框值
                                var val = $(this).val();
                                //发送数据
                                if(val != ""){
                                    var url = "${ pageContext.request.contextPath}/UserServlet";
                                    var params = {"method":"checkUsername","username":val};
                                    $.post(url,params,function(data){
                                        if(data == 1){
                                            $("#sl").html("用户名可以使用").css("color","#0f0");//s1是用户名后面的span的id
                                            $("#regBut").attr("disabled",false);//将注册按钮调整为可用
								         } else if(data == 2){
									        $("#sl").html("用户名已被注册").css("color","#f00");
                                           $("#regBut").attr("disabled",true);//将注册按钮调整为不可用
                                         }
							         });
                                 }
                            });
                        });
					</script>
			    </div>
			  </div>
			   <div class="form-group">
			    <label for="inputPassword3" class="col-sm-2 control-label">密码</label>
			    <div class="col-sm-6">
			      <input type="password" name="password" class="form-control" id="inputPassword3" placeholder="请输入密码">
			    </div>
			  </div>
			   <div class="form-group">
			    <label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
			    <div class="col-sm-6">
			      <input type="password" class="form-control" id="confirmpwd" placeholder="请输入确认密码">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
			    <div class="col-sm-6">
			      <input type="email" name="email" class="form-control" id="inputEmail3" placeholder="Email">
			    </div>
			  </div>
			 <div class="form-group">
			    <label for="usercaption" class="col-sm-2 control-label">姓名</label>
			    <div class="col-sm-6">
			      <input type="text" name="name" class="form-control" id="usercaption" placeholder="请输入姓名">
			    </div>
			 </div>
			 <div class="form-group opt">
				 <label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
				 	<div class="col-sm-6">
						<label class="radio-inline">
				  			<input type="radio" name="sex" id="inlineRadio1" value="男" checked="checked"> 男
						</label>
						<label class="radio-inline">
			  				<input type="radio" name="sex" id="inlineRadio2" value="女"> 女
						</label>
					</div>
			 </div>
			<div class="form-group">
			    <label for="date" class="col-sm-2 control-label">出生日期</label>
			    <div class="col-sm-6">
			      <input type="date" class="form-control" name="birthday" id="date"  >
			    </div>
			</div>
			<div class="form-group">
				<label for="telephone" class="col-sm-2 control-label">电话号码</label>
				<div class="col-sm-6">
					<input type="text" class="form-control" name="telephone" id="telephone"  >
				</div>
			</div>



			  <div class="form-group">
			    <label for="check" class="col-sm-2 control-label">验证码</label>
			    <div class="col-sm-3">
			      <input type="text" class="form-control"  id="check" >
			    </div>
			    <div class="col-sm-2">
			    <img src="${pageContext.request.contextPath}/img/captcha.jhtml"/>
			    </div>
			  </div>

			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <input type="submit"  width="100" value="注册" name="submit" border="0"
				    style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
				    height:35px;width:100px;color:white;" id="regBut" class="form-control">
			    </div>
			  </div>
			</form>
	</div>

	<div class="col-md-2"></div>

</div>
</div>




			<!--
               描述：页脚部分
           -->
			<%@include file="footer.jsp"%>

</body></html>




