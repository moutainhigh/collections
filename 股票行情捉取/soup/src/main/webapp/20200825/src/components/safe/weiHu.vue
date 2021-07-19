<template>
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">股东信息维护</h3>
		</div>
		<div class="panel-body">
			<!-- <a class="btn btn-primary"  @click="addAccount">添加账户</a> -->
			
			<div class="container"> 
			   <div class="row clearfix"> 
				<div class="col-md-12 column"> 
				 <form class="form-horizontal" role="form"> 
				  
				  <div class="form-group"> 
				  		<label for="code" class="col-sm-2 control-label">股东账号</label> 
						<div class="col-sm-10 pos"> 
				  			<input type="text" class="form-control" autofocus="autofocus" v-model="acccount" placeholder="请输入股东账号" /> 
						</div> 
				  </div> 
				  
				    <div class="form-group"> 
				  		<label for="code" class="col-sm-2 control-label">股东名称</label> 
				  						<div class="col-sm-10 pos"> 
				  			<input type="text" class="form-control" autofocus="autofocus" v-model="acccountName" placeholder="请输入股东名称" /> 
				  						</div> 
				  </div> 
				  	<div class="form-group">
				  	<div class="col-sm-offset-2 col-sm-10">
				  		<a class="btn btn-default" @click="addAccountToDb">保存</a>
				  	</div>
				  </div>
				 </form> 
				</div> 
			   </div> 
			  </div>
			
			 <ul class="list-group" id="lists">
				<li class="list-group-item" v-for="account in list">
				<div class="container-fluid">
						<div class="row clearfix">
							<div class="col-md-3 column">股东账号：【{{account.account}} 】【<span class="dels" @click="delAccount(account.aid)">删除</span>】</div>
							<div class="col-md-3 column">名称：【{{account.name}}】</div>
							<div class="col-md-3 column">账户可用金额：【{{account.keYongZiJing}}】</div>
							<div class="col-md-3 column">
								<form class="form-horizontal" role="form">
									<div class="form-group">
										<label for="code" class="col-sm-3 control-label">tid信息</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" :id="'tid_'+account.aid" autofocus="autofocus">
											<a class="btn btn-default" @click="doTidSubmit(account.aid)">保存</a>
										</div>
									</div>
									<!-- <div class="form-group">
										<label for="code" class="col-sm-3 control-label">uid信息</label>
										<div class="col-sm-9">
											<input type="text" class="form-control" :id="'uid_'+account.aid" autofocus="autofocus">
											<a class="btn btn-default" @click="doUidSubmit(account.aid)">保存</a>
										</div>
									</div> -->
								</form>
							</div>
						</div>
					</div>
				</li>
			</ul> 
			
		</div>
	</div>
</template>

<script>
	import {Toast} from 'mint-ui';
	export default {
		name: 'HelloWorld',
		data() {
			return {
				list: [],
				acccountName:"",
				acccount:""
				
			}
		},
		methods: {
			addAccount:function(){
				alert(1)
			},
			addAccountToDb:function(){
				var acccount = this.acccount;
				var acccountName = this.acccountName;
				var url = this.baseURL.apisafe+ '/addAccount.do?rand='+Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				params.append('account', acccount);
				params.append('name', acccountName);
				this.$ajax.post(url,params).then((response) => {
					Toast(response.data.msg);
				//	inputsPwd.value="";
					this.initMenu();
				}).catch((response) => {
					Toast(response);
				});
			},
			
			delAccount:function(acc){
				var url = this.baseURL.apisafe+ '/delAccount.do?rand='+Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				params.append('aid', acc);
				this.$ajax.post(url,params).then((response) => {
					Toast(response.data.msg);
					this.initMenu();
				//	inputsPwd.value="";
				}).catch((response) => {
					Toast(response);
				});
				
			},
			
			initMenu: function() {
				var url = this.baseURL.apisafe+ '/getAccountList.do?rand='+Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				this.$ajax.post(url,params).then((response) => {
					 this.list=response.data.pageList;
					 var msg = response.data.msg;
					 if(msg=="用户未登录"){
						Toast(msg)
					 }
					 
				}).catch((response) => {
					Toast(response);
				})
			},
			doTidSubmit:function(acc){
				var url = this.baseURL.apisafe+ '/updateAccount.do?rand='+Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				params.append('aid', acc);
				var inputsPwd = document.getElementById('tid_'+acc);
				params.append('tokenId', inputsPwd.value);
				this.$ajax.post(url,params).then((response) => {
					Toast(response.data.msg);
					inputsPwd.value="";
				}).catch((response) => {
					Toast(response);
				});
				
			},
			
		},
		created: function() {
			this.initMenu();
		}

	}
	//https://www.cnblogs.com/posthxl/p/8126601.html
	//https://blog.csdn.net/dahuzix/article/details/56009454
	//https://www.cnblogs.com/guomin/p/9073042.html
	//https://segmentfault.com/q/1010000006890647
</script>

<style scoped="scoped">
	a{text-decoration: none;}
	
	.dels{
		color: blue;
	}
	
	/*@import url("../../../static/bt/css/bootstrap.min.css");*/
</style>


