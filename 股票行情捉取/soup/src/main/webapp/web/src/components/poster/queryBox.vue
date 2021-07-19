<template>
	<div class="col-sm-12">
		<div class="serachBox">
			<div @click="getCode(sts)" class="p10 tianChong" v-for="(sts,index) in goods"><figcaption><span>{{sts.split("@")[0]}}</span>@{{sts.split("@")[1]}}</figcaption></div>
			<div style="position: absolute;bottom: 0;right: 2px;" @click="closeDialg()">关闭</div>
		</div>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				suffix:['qq.com','139.com','sina.com','163.com','189.cn'],
				goods: []
			}
		},
		methods: {
			query: function(code) {
				this._loadData(code);
			},

			_loadData: function(code) {
				this.goods = [];
				for (var i=0;i<this.suffix.length;i++) {
					var temps = this.suffix[i];
					var strs = code +"@"+ temps;
					this.goods.push(strs)
					
					console.log(strs)
				}
				
			},
			getCode: function(code, name) {
				this.$emit('getChildCode', code)
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
	
	
	.tianChong span{
		color: red;
	}
</style>



// WEBPACK FOOTER //
// src/components/poster/queryBox.vue