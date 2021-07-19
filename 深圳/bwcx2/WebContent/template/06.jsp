<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--6征求意见-->
<style>
.form-control{margin-bottom:10px;display:block;width:98%;height:34px;padding:6px 12px;font-size:14px;line-height:1.42857143;color:#999999;background-image:none;border:1px solid #ccc;border-radius:4px;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075);box-shadow:inset 0 1px 1px rgba(0,0,0,.075);-webkit-transition:border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;-o-transition:border-color ease-in-out .15s,box-shadow ease-in-out .15s;transition:border-color ease-in-out .15s,box-shadow ease-in-out .15s}
 
 
 .form-control:focus{border-color:#66afe9;outline:0;-webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);box-shadow:inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)}.form-control::-moz-placeholder{color:#999;opacity:1}.form-control:-ms-input-placeholder{color:#999}.form-control::-webkit-input-placeholder{color:#999}
 
 .form-control::-ms-expand{background-color:transparent;border:0}.form-control[disabled],.form-control[readonly],fieldset[disabled] .form-control{background-color:#eee;opacity:1}.form-control[disabled],fieldset[disabled] .form-control{cursor:not-allowed}textarea.form-control{height:auto}input[type=search]{-webkit-appearance:none}@media screen and (-webkit-min-device-pixel-ratio:0){input[type=date].form-control,input[type=time].form-control,input[type=datetime-local].form-control,input[type=month].form-control{line-height:34px}.input-group-sm input[type=date],.input-group-sm input[type=time],.input-group-sm input[type=datetime-local],.input-group-sm input[type=month],input[type=date].input-sm,input[type=time].input-sm,input[type=datetime-local].input-sm,input[type=month].input-sm{line-height:30px}.input-group-lg input[type=date],.input-group-lg input[type=time],.input-group-lg input[type=datetime-local],.input-group-lg input[type=month],input[type=date].input-lg,input[type=time].input-lg,input[type=datetime-local].input-lg,input[type=month].input-lg{line-height:46px}}


.btn {
    display: inline-block;
    padding: 6px 12px;
    margin-bottom: 0;
    font-size: 14px;
    font-weight: 400;
    line-height: 1.42857143;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
    background-image: none;
    border: 1px solid transparent;
    border-radius: 4px
}

.btn.active.focus,.btn.active:focus,.btn.focus,.btn:active.focus,.btn:active:focus,.btn:focus {
    outline: 5px auto -webkit-focus-ring-color;
    outline-offset: -2px
}

.btn.focus,.btn:focus,.btn:hover {
    text-decoration: none
}

.btn.active,.btn:active {
    background-image: none;
    outline: 0;
    -webkit-box-shadow: inset 0 3px 5px rgba(0,0,0,.125);
    box-shadow: inset 0 3px 5px rgba(0,0,0,.125)
}

.btn.disabled,.btn[disabled],fieldset[disabled] .btn {
    cursor: not-allowed;
    filter: alpha(opacity=65);
    -webkit-box-shadow: none;
    box-shadow: none;
    opacity: .65
}

a.btn.disabled,fieldset[disabled] a.btn {
    pointer-events: none
}


.btn-primary {

    color: #fff;
    background-color: #337ab7;
    border-color: #2e6da4
}

.btn-primary.focus,.btn-primary:focus {
    color: #fff;
    background-color: #286090;
    border-color: #122b40
}

.btn-primary:hover {
    color: #fff;
    background-color: #286090;
    border-color: #204d74
}

.btn-primary.active,.btn-primary:active,.open>.dropdown-toggle.btn-primary {
    color: #fff;
    background-color: #286090;
    border-color: #204d74
}

.btn-primary.active.focus,.btn-primary.active:focus,.btn-primary.active:hover,.btn-primary:active.focus,.btn-primary:active:focus,.btn-primary:active:hover,.open>.dropdown-toggle.btn-primary.focus,.open>.dropdown-toggle.btn-primary:focus,.open>.dropdown-toggle.btn-primary:hover {
    color: #fff;
    background-color: #204d74;
    border-color: #122b40
}

.btn-primary.active,.btn-primary:active,.open>.dropdown-toggle.btn-primary {
    background-image: none
}
</style>
<div class="modb mb50">
	<div class="hd">
		<a href="javascript:;" class="colTit" target="_blank">【&nbsp;&nbsp;&nbsp;征求意见&nbsp;&nbsp;&nbsp;】</a>
	</div>
	<div class="bd mb15" style="padding:25px; margin:0 auto;">
          <div class="form-group" style="display:none;">
              <input type="text" class="form-control" id="department" name="department" placeholder="填写单位*:">
          </div>
          <div class="form-group">
              <input type="text" class="form-control" id="name" name="name" placeholder="填写用户名*:">
          </div>
          <div class="form-group" style="display:none;">
              <input type="text" class="form-control" id="phone" name="phone" placeholder="填写电话*:">
          </div>
          <div class="form-group">
              <textarea class="form-control" rows="3" placeholder="填写留言*:" name="content"></textarea>
          </div>
          <a href="javascript:;" class="btn btn-primary" onclick="submit()" style='display: table;margin: 0 auto;'>提交</a>
  </div>
</div>
<script>
	function submit(){
		alert("感谢您的意见")
	}

</script>