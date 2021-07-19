<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据记录展示</title>
<style>
/* css 重置 */
*{margin:0; padding:0; list-style:none; }
body{ background:#fff; font:normal 12px/22px 宋体;  }
img{ border:0;  }
a{ text-decoration:none; color:#333;  }
a:hover{ color:#1974A1;  }

/*当前对应的样式的内容*/
.slideTxtBox{ width:450px; border:1px solid #ddd; text-align:left;  }
.slideTxtBox .hd{ height:30px; line-height:30px; background:#f4f4f4; padding:0 10px 0 20px;   border-bottom:1px solid #ddd;  position:relative; }
.slideTxtBox .hd ul{ float:left; position:absolute; left:20px; top:-1px; height:32px;   }
.slideTxtBox .hd ul li{ float:left; padding:0 15px; cursor:pointer;  }
.slideTxtBox .hd ul li.on{ height:30px;  background:#fff; border:1px solid #ddd; border-bottom:2px solid #fff; }
.slideTxtBox .bd ul{ padding:15px;  zoom:1;  }
.slideTxtBox .bd li{ height:24px; line-height:24px;   }
.slideTxtBox .bd li .date{ float:right; color:#999;  }

/* 下面是前/后按钮代码，如果不需要删除即可 */
.slideTxtBox .arrow{  position:absolute; right:10px; top:0; }
.slideTxtBox .arrow a{ display:block;  width:5px; height:9px; float:right; margin-right:5px; margin-top:10px;  overflow:hidden;
	 cursor:pointer; background:url("../../page/integeration/arrow.png") 0 0 no-repeat; }
.slideTxtBox .arrow .next{ background-position:0 -50px;  }
.slideTxtBox .arrow .prevStop{ background-position:-60px 0; }
.slideTxtBox .arrow .nextStop{ background-position:-60px -50px; }
</style>

<script src="../../page/integeration/jquery-1.11.3.js"></script>
<script src="../../page/integeration/jquery.SuperSlide.2.1.1.js"></script>
</head>
<body>
<div style="width:450px;">当前仅显示每个记录数的前10条</div>
<div class="slideTxtBox">
			<div class="hd">

				<!-- 下面是前/后按钮代码，如果不需要删除即可 -->
				<span class="arrow"><a class="next"></a><a class="prev"></a></span>

				<ul>
					<li>用户</li>
					<li>角色</li>
					<li>功能</li>
				</ul>
			</div>
			<div class="bd">
				<ul>
					<li><span class="date">2017-4-18</span><a href="####" target="_blank">苏燕勋---普通查询角色</a></li>
				</ul>
				<ul>
					<li><span class="date">2017-4-18</span><a href="####" target="_blank">ZHCX_PER---综合查询人员批量查询</a></li>
				</ul>
				<ul>
					<li><span class="date">2017-4-18</span><a href="####" target="_blank">功能管理---普通添加了新功能角色</a></li>
				</ul>
			</div>
		</div>

		<script type="text/javascript">
			$(".slideTxtBox").slide({effect:"leftLoop",autoPlay:true,easing:"easeOutCirc",delayTime:500});
		</script>
</body>
</html>