<template>
	<div>
		<div class="panel panel-info">
			<div class="panel-heading">
				接收用户：{{total}}
			</div>
			<div class="panel-body">
				
				<form class="form-horizontal" role="form">
					<div class="form-group">
						<label for="code" class="col-sm-2 control-label">手机号码</label>
						<div class="col-sm-10 pos">
							<input type="text" class="form-control" autofocus="autofocus"  v-model="recevier" placeholder="请输入手机号码" />
						</div>
					</div>
				
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-2">
							<a class="btn btn-primary"  @click="addEmailReceiver()">添加短信用户</a>
						</div>
					</div>
				</form>
				
				<ul class="list-group" id="lists">
					<li class="list-group-item" v-for="(account,index) in receviers">
						<div class="container-fluid">
							<div class="row clearfix">
								<div class="col-md-3 column systemInfo">
									序号:【{{(index+1)<10?"0"+(index+1):index+1}}】，
									手机号：
									【<span @click="getSendLog(account.phone)" :class="account.times>0?'red':''">{{account.phone}}</span>】
									<div class="alert alert-danger hide">
										   <button type="button" class="close" @click="closeLog(account.phone)">&times;</button>
										   <div :id="account.phone"></div>
									</div>
									
								 </div>
								<div class="col-md-3 column">名称：【{{account.name}}】</div>
								<div class="col-md-3 column">是否发送：【{{account.isSend>0?"是":"否"}}】</div>
								<div class="col-md-3 column opts">
									操作：
								【<span @click="del(account.phone,index)">删除</span>】
								【<span @click="setSend(account.phone,index)">设置发送</span>】
								【<span @click="setNotSend(account.phone,index)">取消发送</span>】
								</div>
							</div>
						</div>
					</li>
				</ul>

			</div>

		</div>
		
		
		
		<div class="panel panel-primary">
			<div class="panel-heading">
				短信配置：
			</div>
			<div class="panel-body">
				<ul class="list-group" id="lists">
					<li class="list-group-item" v-for="(account,index) in senders">
						<div class="container-fluid">
							<div class="row clearfix">
								<div class="col-md-4 column">短信编号：【{{account.appId}}】</div>
								<div class="col-md-4 column">
									端口：【{{account.sign }}】，
									余量：【{{account.times}}】，
									买模板编号：【{{account.templateIdForBuy}}】，
									卖模板编号【{{account.templateIdForSell}}】
								</div>
							</div>
						</div>
					</li>
				</ul>
		
			</div>
		
		</div>
	</div>
	</div>
</template>

<script>
	import {Toast} from 'mint-ui';
	import { MessageBox } from 'mint-ui';
	export default {
		data() {
			return {
				receviers: [],
				senders:[],
				recevier:"",
				total:""
			}

		},

		methods: {
			addEmailReceiver:function(){
				MessageBox.confirm('确定添加收件人吗?').then(action => {
					var url = this.baseURL.apisafe + '/addSMSRecevier.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('phone', this.recevier);
					this.$ajax.post(url, params).then((response) => {
						var tempRec = {};
						tempRec.phone = this.recevier;
						tempRec.name = "";
						tempRec.isSend="1";
						tempRec.count="";
						this.receviers.push(tempRec);
						this.total = this.receviers.length;
						Toast("添加成功");
					}).catch((response) => {
						Toast(response);
					})
				},()=>{
					
				});
			},
			
			
		del:function(address,index){
					MessageBox.confirm('确定执行此删除操作?').then(action => {
					var url = this.baseURL.apisafe + '/delSMSRecevier.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('phone', address);
					this.$ajax.post(url, params).then((response) => {
						this.receviers.splice(index, 1);
						Toast("删除成功");
					}).catch((response) => {
						Toast(response);
					})
				},()=>{
					
				});
			},
			setSend:function(address,index){
					MessageBox.confirm('确定设置发送吗?').then(action => {
					var url = this.baseURL.apisafe + '/addSMSRecevier.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('phone', address);
					params.append('isSend', "1");
					this.$ajax.post(url, params).then((response) => {
						Toast("设置成功");
						this.getEmailReceviersList();
					}).catch((response) => {
						Toast(response);
					})
				},()=>{
					
				});
			},
			
			
			setNotSend:function(address,index){
					MessageBox.confirm('确定取消发送吗?').then(action => {
					var url = this.baseURL.apisafe + '/addSMSRecevier.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('phone', address);
					params.append('isSend', "0");
					this.$ajax.post(url, params).then((response) => {
						Toast("取消成功");
						this.getEmailReceviersList();
					}).catch((response) => {
						Toast(response);
					})
				},()=>{
					
				});
			},
		getEmailReceviersList: function() {
				var url = this.baseURL.apisafe + '/getSMSRecevierList.do?rand=' + Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				this.$ajax.post(url, params).then((response) => {
					
					this.receviers = response.data.pageList;
					if(this.receviers!=undefined){
						this.total = this.receviers.length;
					}
					var msg = response.data.msg;
					if (msg == "用户未登录") {
						Toast(msg)
					}

				}).catch((response) => {
					Toast(response);
				})
			},
			

		getEmailSenderList: function() {
				var url = this.baseURL.apisafe + '/getSMSSenderList.do?rand=' + Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				this.$ajax.post(url, params).then((response) => {
					this.senders = response.data.pageList;
					var msg = response.data.msg;
					if (msg == "用户未登录") {
						Toast(msg)
					}

				}).catch((response) => {
					Toast(response);
				})
			},
			
			getSendLog: function([address]) {
					var url = this.baseURL.apisafe + '/getLogByPhone.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('phone', address);
					params.append('isSend', "0");
					this.$ajax.post(url, params).then((response) => {
						var list = response.data.list;
						if(list!=null){
							document.getElementById(address).innerHTML = list[0].msg;
							document.getElementById(address).parentNode.classList.remove("hide");
						}
					}).catch((response) => {
						Toast(response);
					})
			},
			 closeLog:function(address){
				document.getElementById(address).parentNode.classList.add("hide");
			}

		},
		created: function() {
			this.getEmailReceviersList();
			this.getEmailSenderList();
		}

	}
</script>

<style scoped="scoped">

.opts span {
		cursor: pointer;
		color: blue;
	}
	
	.systemInfo span {
		cursor: pointer;
		color: blue;
	}
	
	.systemInfo .red {
		color: red;
	}
</style>


// WEBPACK FOOTER //
// src/components/poster/sms.vue