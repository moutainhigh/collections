<template>
	<div>
		<div class="container">
			<div class="row clearfix">
				<div class="col-md-12 column">
					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label for="code" class="col-sm-2 control-label">查找</label>
							<div class="col-sm-10 pos">
								<input type="text" class="form-control" autofocus="autofocus" v-model="latestPrice" v-on:input="inputFunc"
								 placeholder="输入代码或拼音" />
								<!-- <div class="col-sm-12">
									<div class="serachBox">
										<div @click="getCode(sts.code,sts.name)" class="p10" v-for="(sts,index) in goods">代码：【{{sts.code}}】 名称：【{{sts.name}}】</div>
										<div style="position: absolute;bottom: 0;right: 2px;" @click="closeDialg()">关闭</div>
									</div>
								</div> -->
							</div>
						</div>
					</form>

				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		props: ['reqURL'],
		data() {
			return {
				latestPrice: "",
				goods: [],
				isShowQuery:false,
				inputValue:""
			}
		},
		methods: {
			inputFunc: function(e) {
				this.inputValue = e.target.value;
				this.isShowQuery = true;
				 this.$emit('searchBox', this.inputValue)
			},
		},
		created: function() {

		}

	}
</script>

<style scoped="scoped">
	/* .serachBox {
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
	} */
</style>

<!-- https://www.cnblogs.com/caik13/p/6896890.html -->


