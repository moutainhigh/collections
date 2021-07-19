<template>
	<div>
		<div class="panel panel-info">
			<div class="panel-heading">
				买百分比：
			</div>
			<div class="panel-body">
				<label :for="'buy_'+sts.percent_value" class="radio" v-for="sts in buyList">
					<span class="radio-bg"></span>
					<input type="radio" name="buyPercent" :id="'buy_'+sts.percent_value" :value="sts.percent_value" :checked="sts.isdefault>0?'checked':''" />
					{{sts.text}}
					<span class="radio-on"></span>
				</label>

			</div>
		</div>


		<div class="panel panel-info">
			<div class="panel-heading">
				卖百分比：
			</div>
			<div class="panel-body">
				<label :for="'sell_'+sts.percent_value" class="radio" v-for="sts in sellList">
					<span class="radio-bg"></span>
					<input type="radio" name="sellPercent" :id="'sell_'+sts.percent_value" :value="sts.percent_value" :checked="sts.isdefault>0?'checked':''" />
					{{sts.text}}
					<span class="radio-on"></span>
				</label>

			</div>
		</div>


	
		<!-- <label for="p2" class="radio">
			<span class="radio-bg"></span>
			<input type="radio" name="percent" id="p2" value="20" /> 20%
			<span class="radio-on"></span>
		</label>-->
		<a class="btn btn-primary btn-lg btn-block" @click="doSave">保存</a>
		<br />
		<div class="panel panel-info">
			<div class="panel-heading">
				股票账户买卖操作：
			</div>
			<div class="panel-body">
				<ul class="list-group" id="lists">
					<li class="list-group-item" v-for="(account,index) in list">
						<div class="container-fluid">
							<div class="row clearfix">
								<div class="col-md-3 column">股东账号：【{{account.account}}】</div>
								<div class="col-md-3 column">名称：【{{account.name}}】</div>
								
								<div class="col-md-3 column">基金操作：
										<label :for="'optJiJing_'+sts.id+index" class="radio" v-for="(sts,index) in account.optionsJiJing">
												<span class="radio-bg"></span>
												<input type="radio" :name="'optionsJiJing_'+account.aid" :id="'optJiJing_'+sts.id+index" :value="sts.operationType"	 :checked="sts.isdefault>0?'checked':''" />
												{{sts.operationType}}
												<span class="radio-on"></span>
											</label>
											
											<a class="btn btn-danger btn-lg btn-block" @click="doSubmitJiJing(account.aid)">保存</a>
								</div>
								
								<div class="col-md-3 column">
									股票操作：
									<label :for="'opt_'+sts.id+index" class="radio" v-for="(sts,index) in account.options">
										<span class="radio-bg"></span>
										<input type="radio" :name="'options_'+account.aid" :id="'opt_'+sts.id+index" :value="sts.operationType"	 :checked="sts.isdefault>0?'checked':''" />
										{{sts.operationType}}
										<span class="radio-on"></span>
									</label>
									
									<a class="btn btn-primary btn-lg btn-block" @click="doSubmit(account.aid)">保存</a>
								</div>
							</div>
						</div>
					</li>
				</ul>

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
				buyList: [],
				sellList: [],
				list: [],
			}

		},

		methods: {
			loadSellData: function() {
				var token = this.cookie.getCookie("token");
				var url = this.baseURL.apisafe + '/getSellSettingList.do?rand=' + Math.random();
				this.$ajax.get(url,{
					params:{'token':token}
				}).then((response) => {
					this.sellList = response.data.list;
				}).catch((response) => {
					Toast(response);
				})

			},

			loadBuyData: function() {
				var token = this.cookie.getCookie("token");
				var url = this.baseURL.apisafe + '/getBuySettingList.do?rand=' + Math.random();
				this.$ajax.get(url,{
					params:{'token':token}
				}).then((response) => {
					this.buyList = response.data.list;
				}).catch((response) => {
					Toast(response);
				})

			},

			loadGudong: function() {
				var url = this.baseURL.apisafe + '/getAccountList.do?rand=' + Math.random();
				var params = new URLSearchParams();
				var token = this.cookie.getCookie("token");
				params.append('token', token);
				this.$ajax.post(url, params).then((response) => {
					this.list = response.data.pageList;
					var msg = response.data.msg;
					if (msg == "用户未登录") {
						Toast(msg)
					}

				}).catch((response) => {
					Toast(response);
				})
			},
			upate: function(percent) {
				var url = this.baseURL.apisafe + '/updateSetting.do?rand=' + Math.random();
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
				//let radios = document.getElementsByTagName("input");
				let radios = document.getElementsByName("buyPercent");
				let buyRadios = document.getElementsByName("sellPercent");
				var value = '';
				for (var i = 0; i < radios.length; i++) {
					if (radios[i].checked == true) {
						value += radios[i].value ;
					}
				}
				
				for (var i = 0; i < buyRadios.length; i++) {
					if (buyRadios[i].checked == true) {
						value += "," +buyRadios[i].value  ;
					}
				}
				
				//value = value.substring(0, value.length - 1);
				this.upate(value)
			},
			doSubmit: function(key) {
				//https://blog.csdn.net/xiejunna/article/details/77309943
				//https://zhidao.baidu.com/question/557395960498389092.html
				var radio_tag ='options_' + key;
				let amount = this.getSelect(radio_tag);
				//console.log("amount==" + amount + "   --->" + key);
				this.updateOpt(key,amount)
			},
			
			
			
			
			doSubmitJiJing: function(key) {
				var radio_tag ='optionsJiJing_' + key;
				let amount = this.getSelect(radio_tag);
				this.updateOptJiJing(key,amount)
				
			},
			
			updateOpt:function(key,opt){
				MessageBox.confirm('确定执行此操作?').then(action => {
					var url = this.baseURL.apisafe + '/updateGudongOpt.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('account', key);
					params.append('opt', opt);
					this.$ajax.post(url, params).then((response) => {
						let msg = response.data.msg;
						Toast(msg);
					}).catch((response) => {
						Toast(response);
					})
				},()=>{
					
				});
				
				
			},
			updateOptJiJing:function(key,opt){
				MessageBox.confirm('确定执行此操作?').then(action => {
					var url = this.baseURL.apisafe + '/updateGudongJiJingOpt.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
					params.append('account', key);
					params.append('opt', opt);
					this.$ajax.post(url, params).then((response) => {
						let msg = response.data.msg;
						Toast(msg);
					}).catch((response) => {
						Toast(response);
					})
				},()=>{
					
				});
				
				
			},

			getSelect: function(tagNameAttr) {
				var radio_tag = document.getElementsByName(tagNameAttr);
				for (var i = 0; i < radio_tag.length; i++) {
					if (radio_tag[i].checked) {
						var checkvalue = radio_tag[i].value;
						return checkvalue;
					}
				}
			}

		},
		created: function() {
			this.loadBuyData();
			this.loadSellData();
			this.loadGudong();
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

<!-- https://www.cnblogs.com/sakura-panda/p/7065449.html -->
<!-- https://blog.csdn.net/weixin_36517727/article/details/79541705
https://segmentfault.com/q/1010000008941634/a-1020000008941668
https://blog.csdn.net/qq_36947128/article/details/79398301
https://blog.csdn.net/zuorishu/article/details/84992194 -->
<!-- http://www.php.cn/css-tutorial-374742.html -->

