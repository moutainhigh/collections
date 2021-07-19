<template>
	<div class="col-sm-12">
		<div class="serachBox">
			<div @click="getCode(sts.code,sts.name)" class="p10" v-for="(sts,index) in goods">代码：【{{sts.code}}】 名称：【{{sts.name}}】</div>
			<div style="position: absolute;bottom: 0;right: 2px;" @click="closeDialg()">关闭</div>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				goods: []
			}
		},
		methods: {
			query: function(code) {
				this._loadData(code);
			},

			_loadData: function(code) {
				var url = this.baseURL.apidomain + '/findStock.do?code=' + code + '&rand=' + Math.random();
				var token = this.cookie.getCookie("token");
				var params = new URLSearchParams();
				//params.append('token', token);
				params.append('code', code);
				this.$ajax.get(url, params).then((response) => {
					this.goods =[];
					this.goods = response.data.list;
				}).catch((response) => {
					console.log(response);
				})
			},
			getCode: function(code, name) {
				this.$emit('getChildCode', code + "_" + name)
			},
			closeDialg: function() {
				this.$emit('isClose', 'close')
			}
		},

		created: function() {
			//this.query();
		}


	}
</script>

<style scoped="scoped">
	.serachBox {
		left: 0;
		position: absolute;
		background: #fff;
		width: 100%;
		z-index: 999;
		border: 1px solid #ccc;
		border-radius: 4px;
		height: 450px;
		overflow: hidden;
	}

	.p10 {
		padding: 4px;
	}
</style>
<!------https://blog.csdn.net/Hill_Kinsham/article/details/81985552-->
<!-- https://blog.csdn.net/jsxiaoshu/article/details/79058940 -->


