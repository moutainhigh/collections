<template>
	<div class="wrap">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">做T编辑</h3>
			</div>
			<div class="panel-body">
				<ul class="list-group">
					<li class="list-group-item">
						<div class="container">
							<div class="row clearfix">
								<div class="col-md-12 column">
									<form class="form-horizontal" role="form">
										<div class="form-group">
											<label for="code" class="col-sm-2 control-label">代码</label>
											<div class="col-sm-10 pos">
												<input type="text" class="form-control" autofocus="autofocus" v-model="myCodeStr" v-on:input="inputFunc" placeholder="请输入代码或拼音" />
												 <query ref="queryCode" v-show="isShowQuery" v-on:isClose="isClose" v-on:getChildCode="getChildCode"></query>
											</div>
										</div>
										<div class="form-group">
											<label for="code" class="col-sm-2 control-label">当前价</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" autofocus="autofocus" v-model="latestPrice" readonly="readonly" />
											</div>
										</div>
										
										<div class="form-group">
											<label for="code" class="col-sm-2 control-label">名称</label>
											<div class="col-sm-10">
												<input type="text" class="form-control" autofocus="autofocus" v-model="stockName" readonly="readonly" />
											</div>
										</div>
										<div class="form-group">
											<label for="code" class="col-sm-2 control-label">挂买价</label>
											<div class="col-sm-10">
												<input type="text" class="form-control"  v-model="sellPrice" placeholder="请输入买价" />
											</div>
										</div>

										<div class="form-group">
											<div class="col-sm-offset-2 col-sm-10">
												<a class="btn btn-default" @click="addStock">保存</a>
											</div>
										</div>
									</form>
								</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		
		<div class="panel panel-info">
			<div class="panel-heading">
				做T买百分比：
			</div>
			<div class="panel-body">
				<label :for="'buy_'+sts.percent_value" class="radio" v-for="sts in buyList">
					<span class="radio-bg"></span>
					<input type="radio" name="buyPercent" :id="'buy_'+sts.percent_value" :value="sts.percent_value" :checked="sts.isdefault>0?'checked':''" />
					{{sts.text}}
					<span class="radio-on"></span>
				</label>

			</div>
			
				<a class="btn btn-warning btn-lg btn-block" @click="doSave">保存</a>
		</div>
		
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">做T买单列表</h3>
			</div>
			<div class="panel-body">
				<a class="btn btn-success delBuys" @click="delAll">删除所有买单</a>
				<ul class="list-group" id="lists">
					<li class="list-group-item" v-for="(sts,index) in list">
						<div class="container-fluid">
							<div class="row clearfix">
								<div class="col-md-2 column">代码：【{{sts.code}}】,名称：【{{sts.name}}】,
									地区：【{{sts.diQu}}】<span>,行业：【{{sts.hangye}}】</span><span>,流通股：【{{sts.liuTongGu}}】亿</span><span>,总股本：【{{sts.zongGuBen}}】亿</span></div>
								<div class="col-md-2 column">本日买价：【{{ sts.weiTuoPrice }} 】</div>
								<div class="col-md-2 column">预设价：【{{sts.yuSheBuyPrice}}】</div>
								<div class="col-md-4 column">操作：【
									<a href="javascript:;" @click="deleteBuy(sts.code,index)">删除</a>】</div>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</div>

</template>

<script>
	import query from './query.vue'
	import {Toast} from 'mint-ui';
	import { MessageBox } from 'mint-ui';
	export default {
		data() {
			return {
				list: [],
				findlist: [],
				inputValue: "",
				sellPrice:"0",
				latestPrice:"",
				myCodeStr:"",
				isShowQuery:false,
				stockName:"",
				buyList: [],
			}
		},
		components: {
			query
		},

		methods: {
			
			loadBuyData: function() {
				var token = this.cookie.getCookie("token");
				var url = this.baseURL.apisafe + '/getSpBuySettingList.do?rand=' + Math.random();
				this.$ajax.get(url,{
					params:{'token':token}
				}).then((response) => {
					this.buyList = response.data.list;
				}).catch((response) => {
					Toast(response);
				})

			},
			upate: function(percent) {
				var url = this.baseURL.apisafe + '/updateSpSetting.do?rand=' + Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				params.append('percent', percent);
				this.$ajax.post(url,params).then((response) => {
					Toast(response.data.msg);
				}).catch((response) => {
					Toast(response);
				})
			},
			doSave: function() {
				let radios = document.getElementsByName("buyPercent");
				
				var value = '';
				for (var i = 0; i < radios.length; i++) {
					if (radios[i].checked == true) {
						value += radios[i].value ;
					}
				}
				
				//value = value.substring(0, value.length - 1);
				this.upate(value)
			},

			
			inital: function() {
				var url = this.baseURL.apisafe + '/getSpBuyList.do?rand=' + Math.random();
				var token = this.cookie.getCookie("token");
				var params = new URLSearchParams();
				params.append('token', token);
				this.$ajax.post(url, params).then((response) => {
					var msg = response.data.msg;
					 if(msg=="用户未登录"){
						Toast(msg)
					}
					this.list = response.data.pageList;
				}).catch((response) => {
					Toast(response.data);
				})
			},
			
			inputFunc: function(e) {
				this.inputValue = e.target.value;
				this.isShowQuery = true;
				this.$refs.queryCode.query(this.inputValue);
			},
			
			deleteBuy: function(code, index) {
				MessageBox.confirm('确定执行此操作?').then(action => {
					var url = this.baseURL.apisafe + '/delSpBuy.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('code', code);
					this.$ajax.post(url, params).then((response) => {
						this.list.splice(index, 1);
						Toast("删除成功");
					}).catch((response) => {
						Toast(response);
					})
				},()=>{
					
				});
								
				
			},
			addStock: function() {
				let stockCode = this.myCodeStr;
				var url = this.baseURL.apisafe + '/editSpBuy.do?rand=' + Math.random();
				var params = new URLSearchParams();
				params.append('code', stockCode);
				params.append('name', this.stockName);
				params.append('yuSheBuyPrice', this.sellPrice);
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				this.$ajax.post(url, params).then((response) => {
					this.msg = response.data.msg;
					Toast(this.msg);
					this.inital();
				}).catch((response) => {
					Toast(response);
				})
			},
			delAll:function(){
					MessageBox.confirm('确定将所有删除吗?').then(action => {
					var url = this.baseURL.apisafe + '/truncateSpBuy.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					this.$ajax.post(url, params).then((response) => {
						this.list=[];
						Toast("删除成功");
					}).catch((response) => {
						Toast(response);
					})
				},()=>{
					
				});
			},
			isClose:function(msg){
				this.isShowQuery = false;
			},
			getChildCode:function(code){
				/* this.myCodeStr = code;
				this.isShowQuery = false;
				this.stockName = name;
				console.log(name) */
				let codeStr = code.split("_")[0];
				let name = code.split("_")[1];
				this.myCodeStr= codeStr;
				this.isShowQuery = false;
				this.stockName = name;
				this.queryLastestPrice(codeStr);
			},
			queryLastestPrice: function(code) {
				var url = this.baseURL.apidomain + '/getLatest.do?rand=' + Math.random();
				var token = this.cookie.getCookie("token");
				var params = new URLSearchParams();
				//params.append('token', token);
				params.append('code', code);
				this.$ajax.post(url, params).then((response) => {
					this.latestPrice = response.data.price;
				}).catch((response) => {
					console.log(response);
				})
			}

		},
		created: function() {
			this.inital();
			this.loadBuyData();
		}
	}
</script>


<style scoped="scoped">
	.radio {
		display: inline-block;
		position: relative;
		line-height: 18px;
		margin-right: 10px;
		cursor: pointer;
	}

	.radio input {
		display: none;
	}

	.radio .radio-bg {
		display: inline-block;
		height: 18px;
		width: 18px;
		margin-right: 5px;
		padding: 0;
		/* background-color: #45bcb8; */
		background-color: #f5f4f4;
		border-radius: 100%;
		vertical-align: top;
		box-shadow: 0 1px 15px rgba(0, 0, 0, 0.1) inset, 0 1px 4px rgba(0, 0, 0, 0.1) inset, 1px -1px 2px rgba(0, 0, 0, 0.1);
		cursor: pointer;
		transition: all 0.2s ease;
	}

	.radio .radio-on {
		display: none;
	}

	.radio input:checked+span.radio-on {
		width: 10px;
		height: 10px;
		position: absolute;
		border-radius: 100%;
		background: #FFFFFF;
		top: 4px;
		left: 4px;
		box-shadow: 0 2px 5px 1px rgba(0, 0, 0, 0.3), 0 0 1px rgba(255, 255, 255, 0.4) inset;
		background-image: linear-gradient(#ea0505 0, #ea0505 100%);
		transform: scale(0, 0);
		transition: all 0.2s ease;
		transform: scale(1, 1);
		display: inline-block;
	}
</style>

<!--https://www.jianshu.com/p/99278065d1df 和Watch监听-->

<!--https://www.cnblogs.com/daiwenru/p/6694530.html-->
<!--http://www.vogin.top/view/48-->

