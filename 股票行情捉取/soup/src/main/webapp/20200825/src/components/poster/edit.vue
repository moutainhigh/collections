<template>
	<div class="container" style="padding-top: 0.4rem;">
		<form class="form-horizontal" role="form">
			<div class="form-group">
				<label for="floor1" class="col-sm-2 control-label">发信人地址</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="floor1" v-model="address" placeholder="请输入发信人地址">
				</div>
			</div>
			<div class="form-group">
				<label for="floor2" class="col-sm-2 control-label">端口</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="floor2" v-model="port" placeholder="请输入端口">
				</div>
			</div>
			<div class="form-group">
				<label for="floor3" class="col-sm-2 control-label">服务器地址</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="floor3" v-model="host" placeholder="请输入服务器地址">
				</div>
			</div>
			<div class="form-group">
				<label for="floor4" class="col-sm-2 control-label">协议</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="floor4" v-model="protocol" placeholder="请输入协议">
				</div>
			</div>
			<div class="form-group">
				<label for="floor5" class="col-sm-2 control-label">邮箱密码</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="floor5" v-model="pwd" placeholder="请输入邮箱密码">
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<a class="btn btn-default" @click="saveFormData()">保存并关闭</a>
				</div>
			</div>
		</form>
	</div>
</template>

<script>
	import {
		Toast
	} from 'mint-ui';
	import {
		MessageBox
	} from 'mint-ui';
	export default {
		props: {
			edmail: {}
		},
		data() {
			return {
				address: "",
				port: "",
				host: "",
				protocol: "",
				pwd: "",
        id:""

			}

		},
		methods: {
			loadFormData: function() {
        this.id = this.$parent.edmail.id;
				this.address = this.$parent.edmail.address;
				this.port = this.$parent.edmail.port;
				this.host = this.$parent.edmail.host;
				this.protocol = this.$parent.edmail.protocol;
				this.pwd = this.$parent.edmail.pwd;
			},
			sendMsgToParent:function(){
				this.$emit('closeWins',false)
			},
			submitInfo:function () {
				MessageBox.confirm('确定要保存吗?').then(action => {

					this.sendAjax();
				}, () => {

				});
			},
			sendAjax:function(){
        var id = this.id;
				var address = this.address;
				var port = this.port;
				var host = this.host;
				var protocol = this.protocol;
				var pwd = this.pwd;
				if(address!=undefined&&port!=undefined&&host!=undefined&&protocol!=undefined&&pwd!=undefined){
					var url = this.baseURL.apisafe + '/addEmailSender.do?rand=' + Math.random();
					var token = this.cookie.getCookie("token");
					var params = new URLSearchParams();
					params.append('token', token);
          params.append('id', id);
					params.append('address', address);
					params.append('port', port);
					params.append('host', host);
					params.append('protocol', protocol);
					params.append('pwd', pwd);
					this.$ajax.post(url, params).then((response) => {
						Toast("保存成功");
						this.sendMsgToParent();
						 this.$parent.getEmailSenderList();
					}).catch((response) => {
						Toast(response);
					})
				}else{
					Toast("发信人地址，端口等相关信息不能为空");
				}


			},



			saveFormData:function () {
				this.submitInfo();
			},


		}

	}
</script>

<style scoped="scoped">
</style>
