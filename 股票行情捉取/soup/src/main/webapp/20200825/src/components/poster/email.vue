<template>
	<div>
		<div class="panel panel-info">
			<div class="panel-heading">
				接收用户：{{total}}
			</div>
			<div class="panel-body">
				<div class="container">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="code" class="col-sm-2 control-label">邮箱地址</label>
							<div class="col-sm-4 pos" style="height: 80px;">
								<input type="text" class="form-control" autofocus="autofocus" v-model="recevier" placeholder="请输入邮箱地址" v-on:input="inputFunc" v-on:focus="clears"/>
								<query ref="queryCode" v-show="isShowQuery" v-on:isClose="isClose" v-on:getChildCode="getChildCode"></query>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-4">
								<a class="btn btn-primary" @click="addEmailReceiver()">添加收信人</a>
							</div>
						</div>
					</form>
				</div>
				<ul class="list-group" id="lists">
					<li class="list-group-item" v-for="(account,index) in receviers">
						<div class="container-fluid">
							<div class="row clearfix">
                <div class="col-md-3 column"></div>
								<div class="col-md-6 column systemInfo" style="min-height: 40px;">
									序号：【{{(index+1)<10? "0"+(index+1):index+1}}】， 邮箱地址：
									【<span @click="getSendLog(account.id)" :class="account.times>0?'red':''">{{account.address}}</span>】,名称：【{{account.name}}】，是否发送：【{{account.isSend>0?"是":"否"}}】,
                  操作： 【
                  <span @click="del(account.id,index)">删除</span>】 【
                  <span @click="setSend(account.id,index)">设置发送</span>】 【
                  <span @click="setNotSend(account.id,index)">取消发送</span>】
									<div class="alert alert-danger hide">
										   <button type="button" class="close" @click="closeLog(account.id)">&times;</button>
										   <div :id="account.id" style="min-height: 10rem;"></div>
									</div>

								</div>
                 <div class="col-md-3 column"></div>
								<!-- <div class="col-md-3 column opts"></div> -->
							</div>
						</div>
					</li>
				</ul>

			</div>

		</div>

		<div class="panel panel-primary">
			<div class="panel-heading">
				发信人配置：
			</div>
			<edit :edmail="edmail" v-show="isshow" ref="dataForm" v-on:closeWins="getInfosMsg"></edit>
			<div class="panel-body">
				<a class="btn btn-primary" @click="addSender()">添加发信人</a>
				<ul class="list-group" id="lists">
					<li class="list-group-item" v-for="(account,index) in senders">
						<div class="container-fluid">
							<div class="row clearfix">
								<div class="col-md-3 column opts">邮箱地址：【<span @click="addSender(account.id,account.address,account.port,account.protocol,account.host,account.pwd)">{{account.address}}</span>】</div>
								<div class="col-md-3 column">
									端口：【{{account.port }}】， 协议：【{{account.protocol}}】，
                  服务器地址：【{{account.host}}】，
                  默认行情通知发信人【{{account.flag>0?"是":"否"}}】,
                  默认买卖通知发信人【{{account.buy_sell_annouce>0?"是":"否"}}】，
                  下次需要更新密钥时间：【{{account.endDate}}】
								</div>
								<div class="col-md-3 column opts">
									操作： 【
									<span @click="delSender(account.id,index)">删除</span>】 【
									<span @click="setDefaultSender(account.id,index)">设置为默认发信人</span>】
									<!--【 <span @click="setNotDefaultSender(account.address,index)">撤销为默认发信人</span>】 -->

                  【 <span @click="updateBuySellSender(account.id,index)">默认买卖通知发信人</span>】

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
	import query from './queryBox.vue'
	import edit from './edit';
	import {
		Toast
	} from 'mint-ui';
	import {
		MessageBox
	} from 'mint-ui';
	export default {
		components: {
			edit,query
		},
		data() {
			return {
				receviers: [],
				senders: [],
				recevier: "",
				sender: "",
				edmail: {},
				isshow: false,
				total: "",
				isShowQuery:false,
			}

		},

		methods: {
			clears:function(){
				this.recevier = "";
			},

			inputFunc: function(e) {
				this.inputValue = e.target.value;
				this.isShowQuery = true;
				this.$refs.queryCode.query(this.inputValue);
			},

			isClose:function(msg){
				this.isShowQuery = false;
			},
			getChildCode:function(code){
				this.recevier= code;
				this.isShowQuery = false;
				console.log(code)
			},

			addEmailReceiver: function() {
				MessageBox.confirm('确定添加收件人吗?').then(action => {
					var url = this.baseURL.apisafe + '/addEmailRecevier.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('address', this.recevier);
					this.$ajax.post(url, params).then((response) => {
						var tempRec = {};
						tempRec.address = this.recevier;
						tempRec.name = "";
						tempRec.isSend = "1";
						tempRec.count = "";
						this.receviers.push(tempRec);
						this.total = this.receviers.length;
						Toast("添加成功");
					}).catch((response) => {
						Toast(response);
					})
				}, () => {

				});
			},

			getEmailReceviersList: function() {
				var url = this.baseURL.apisafe + '/getEmailRecevierList.do?rand=' + Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				this.$ajax.post(url, params).then((response) => {
					this.receviers = response.data.pageList;
					if(this.receviers != undefined) {
						this.total = this.receviers.length;
					}

					var msg = response.data.msg;
					if(msg == "用户未登录") {
						Toast(msg)
					}

				}).catch((response) => {
					Toast(response);
				})
			},

			del: function(id, index) {
				MessageBox.confirm('确定执行此删除操作?').then(action => {
					var url = this.baseURL.apisafe + '/delEmailRecevier.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('id', id);
					this.$ajax.post(url, params).then((response) => {
						this.receviers.splice(index, 1);
						Toast("删除成功");
					}).catch((response) => {
						Toast(response);
					})
				}, () => {

				});
			},

			delSender: function(id, index) {
				MessageBox.confirm('确定执行此删除操作?').then(action => {
					var url = this.baseURL.apisafe + '/delEmailSender.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('id', id);
					this.$ajax.post(url, params).then((response) => {
						this.senders.splice(index, 1);
						Toast("删除成功");
					}).catch((response) => {
						Toast(response);
					})
				}, () => {

				});
			},

			setSend: function(id, index) {
				MessageBox.confirm('确定设置发送吗?').then(action => {
					var url = this.baseURL.apisafe + '/addEmailRecevier.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('id', id);
					params.append('isSend', "1");
					this.$ajax.post(url, params).then((response) => {
						Toast("设置成功");
						this.getEmailReceviersList();
					}).catch((response) => {
						Toast(response);
					})
				}, () => {

				});
			},

			setNotSend: function(id, index) {
				MessageBox.confirm('确定取消发送吗?').then(action => {
					var url = this.baseURL.apisafe + '/addEmailRecevier.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('id', id);
					params.append('isSend', "0");
					this.$ajax.post(url, params).then((response) => {
						Toast("取消成功");
						this.getEmailReceviersList();
					}).catch((response) => {
						Toast(response);
					})
				}, () => {

				});
			},

			addSender: function(id,address, port, protocol, host, pwd) {
				this.isshow = true;
				var tempObj = {};
        tempObj.id = id;
				tempObj.address = address;
				tempObj.port = port;
				tempObj.protocol = protocol;
				tempObj.host = host;
				tempObj.pwd = pwd;

				this.edmail = tempObj;

				this.$refs.dataForm.loadFormData();

			},

			getInfosMsg: function(data) {
				this.isshow = data;
			},
			setDefaultSender: function(id, index) {
				MessageBox.confirm('确定设置为默认发送人吗?').then(action => {
					var url = this.baseURL.apisafe + '/updateDefaultSender.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('id', id);
					params.append('flag', "1");
					this.$ajax.post(url, params).then((response) => {
						Toast("设置成功");
						this.getEmailSenderList();
					}).catch((response) => {
						Toast(response);
					})
				}, () => {

				});
			},
			setNotDefaultSender: function(id, index) {
				MessageBox.confirm('确定设置发送吗?').then(action => {
					var url = this.baseURL.apisafe + '/updateDefaultSender.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('id', id);
					params.append('flag', "0");
					this.$ajax.post(url, params).then((response) => {
						Toast("设置成功");
						this.getEmailSenderList();
					}).catch((response) => {
						Toast(response);
					})
				}, () => {

				});
			},

      updateBuySellSender: function(id, index) {
      	MessageBox.confirm('确定设置为买卖通知的默认发送人吗?').then(action => {
      		var url = this.baseURL.apisafe + '/updateBuySellSender.do?rand=' + Math.random();
      		var token = this.cookie.getCookie("token");
      		var params = new URLSearchParams();
      		params.append('token', token);
      		params.append('id', id);
      		params.append('flag', "0");
      		this.$ajax.post(url, params).then((response) => {
      			Toast("设置成功");
      			this.getEmailSenderList();
      		}).catch((response) => {
      			Toast(response);
      		})
      	}, () => {

      	});
      },



			getEmailSenderList: function() {
				var url = this.baseURL.apisafe + '/getEmailSenderList.do?rand=' + Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				this.$ajax.post(url, params).then((response) => {
					this.senders = response.data.pageList;
					var msg = response.data.msg;
					if(msg == "用户未登录") {
						Toast(msg)
					}

				}).catch((response) => {
					Toast(response);
				})
			},

			getSendLog: function(id) {

					var url = this.baseURL.apisafe + '/getLogByAddress.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
          document.getElementById(id).innerHTML="";
					params.append('token', token);
					params.append('id', id);
					params.append('isSend', "0");
					this.$ajax.post(url, params).then((response) => {
						var list = response.data;

            if(list.length >0){
              for(var i in list){
               // document.getElementById(address).innerHTML += "<div style='min-height:8rem'>"+list[i].msg+"</div>";
                //document.getElementById(address).parentNode.classList.remove("hide");

                Toast(list[i].msg);
              }
						}else{
							Toast("该收信人接收正常");
						}
					}).catch((response) => {
						Toast(response);
					})
			},
			 closeLog:function(id){
				  document.getElementById(id).parentNode.classList.add("hide");
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

  .alert-danger{
    min-height: 40px;
  }

</style>

<!--https://blog.csdn.net/houfengfei668/article/details/79843625-->

<!--https://blog.csdn.net/lander_xiong/article/details/79018737-->
<!--http://www.imooc.com/wenda/detail/472144-->
