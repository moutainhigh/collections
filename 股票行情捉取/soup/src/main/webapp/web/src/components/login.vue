<template>
	<div class="login">
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="inputEmail3" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" v-model="uname" autofocus="autofocus" />
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-10">
								<input type="password" class="form-control" v-model="upwd" />
							</div>
						</div>

						<!-- <div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<a class="btn btn-default" @click="dologin">登录</a>
						</div>
					</div> -->
					</form>
					<div class="alert alert-success" v-show="isShow" role="alert">{{msg}}</div>
				</div>
			</div>
		</div>
		<a class="btn btn-danger btn-lg btn-block" @click="dologin">登录</a>
	</div>
</template>

<script>
	export default {
		name: 'login',
		data() {
			return {
				uname: "admin",
				upwd: "root",
				msg: "",
				isShow: false
			}
		},
		mounted: function() {
			setInterval(() => {
				this.isShow = false;
			}, 3000);
		},
		methods: {
			dologin: function() {
				var name = this.uname;
				var pwd = this.upwd;
				if (name == "") {
					this.msg = "用户名不能为空";
					this.isShow = true;
					document.getElementsByTagName("input")[0].focus();
				} else if (pwd == "") {
					this.msg = "密码不能为空";
					this.isShow = true;
					document.getElementsByTagName("input")[1].focus();
				} else {
					/*this.msg="用户名或密码不正确";
					document.getElementsByTagName("input")[1].focus();
					document.getElementsByTagName("input")[1].select();*/
					var url = this.baseURL.apidomain + '/dologin.do?rand=' + Math.random();
					var params = new URLSearchParams();
					params.append('name', name);
					params.append('pwd', pwd);
					/*this.$ajax({
						method: "post",
						headers: {
							'Content-type': 'application/x-www-form-urlencoded'
						},
						url: url,
						params:params
					}).then((res) => {
						console.log(res.data);
					})*/
					this.$ajax.post(url, params).then((response) => {
						this.msg = response.data.code;
						this.isShow = true;
						if (this.msg == "登录成功") {
							let token = response.data.token;
							this.cookie.setCookieExpireByMin("token", token, 30);
							//this.setKeys(name,"");
							//window.location ="/";
							this.$router.push({
								path: '/'
							})
							//this.$rooter.go(-1)

						}
					}).catch((response) => {
						Toast(response);
					})

				}
			}

			/*setKeys: function(name,value){
				alert(1)
				var Days = 30;
				var exp = new Date();
				exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
				document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
			}*/

		}
	}

	//vue 操作cookie  https://www.jb51.net/article/119692.htm
	//https://blog.csdn.net/weixin_41753291/article/details/80619523
	//https://blog.csdn.net/weixin_41753291/article/details/80619523
	//https://www.jb51.net/article/143910.htm
	//https://segmentfault.com/a/1190000008558024
	//https://www.cnblogs.com/coolslider/p/7074715.html

	//https://segmentfault.com/a/1190000008558024

	//https://segmentfault.com/a/1190000008558024
	//https://blog.csdn.net/csdn_yudong/article/details/79668655
	//https://blog.csdn.net/wopelo/article/details/78783442
	//https://segmentfault.com/q/1010000016837027?utm_source=tag-newest
	//https://blog.csdn.net/guoscy/article/details/78953532
</script>

<style scoped="scoped">
	body{
		background: #FAEBCC;
	}
	
	.login{	
		margin-top: 15%;
	}
	
.btn-block{
	margin-bottom: 14px;;
	
}
</style>



