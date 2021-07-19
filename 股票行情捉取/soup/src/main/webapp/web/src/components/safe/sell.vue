<template>
	<div class="wrap">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">卖编辑</h3>
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
											<label for="code" class="col-sm-2 control-label">挂卖价</label>
											<div class="col-sm-10">
												<input type="text" class="form-control"  v-model="sellPrice" placeholder="请输入卖价" />
											</div>
										</div>
                    <div class="form-group">
                    	<label for="code" class="col-sm-2 control-label">口令</label>
                    	<div class="col-sm-10">
                    		<input type="text" class="form-control"  v-model="safekey" placeholder="请输入口令" />
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
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">当前卖单列表</h3>
			</div>
			<div class="panel-body">
				<a class="btn btn-success delBuys" @click="delAll">删除所有卖单</a>
				<ul class="list-group" id="lists">
					<li class="list-group-item" v-for="(sts,index) in list">
						<div class="container-fluid">
							<div class="row clearfix">
								<div class="col-md-2 column">代码：【 {{sts.code }}】</div>
								<div class="col-md-2 column">名称：【{{sts.name }} 】</div>
								<div class="col-md-2 column">本日卖价：【{{ sts.weiTuoTodaySellPrice }} 】</div>
								<div class="col-md-2 column">委托买入价：【{{ sts.buyPrice }} 】  ,预设价：【{{sts.yuSheSellPrice}}】</div>
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
				sellPrice:"",
				latestPrice:"",
				myCodeStr:"",
				isShowQuery:false,
				stockName:"",
        safekey:""
			}
		},
		components: {
			query
		},

		methods: {
			inital: function() {
				var url = this.baseURL.apisafe + '/sellList.do?rand=' + Math.random();
				var token = this.cookie.getCookie("token");
				var params = new URLSearchParams();
				params.append('token', token);
				this.$ajax.post(url, params).then((response) => {
					this.list = response.data.pageList;
					var msg = response.data.msg;
					 if(msg=="用户未登录"){
						Toast(msg)
					}

				}).catch((response) => {
					Toast(response);
				})
			},

			inputFunc: function(e) {
				this.inputValue = e.target.value;
				this.isShowQuery = true;
				this.$refs.queryCode.query(this.inputValue);
			},

			deleteBuy: function(code, index) {
				MessageBox.confirm('确定执行此操作?').then(action => {
					var url = this.baseURL.apisafe + '/delSell.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('code', code);
					this.$ajax.post(url, params).then((response) => {
						this.list.splice(index, 1);
					}).catch((response) => {
						Toast(response);
					})
				},()=>{

				});


			},
			addStock: function() {
				let stockCode = this.myCodeStr;
				var url = this.baseURL.apisafe + '/editSell.do?rand=' + Math.random();
				var params = new URLSearchParams();
				params.append('code', stockCode);
				params.append('name', this.stockName);
				params.append('shu_ru_price', this.sellPrice);
				params.append('safekey', this.safekey);
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
					var url = this.baseURL.apisafe + '/truncateSell.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					this.$ajax.post(url, params).then((response) => {
						this.list=[];
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
		}
	}
</script>

<style>

</style>
<!--https://www.jianshu.com/p/99278065d1df 和Watch监听-->

<!--https://www.cnblogs.com/daiwenru/p/6694530.html-->
<!--http://www.vogin.top/view/48-->
