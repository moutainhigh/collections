<template>
	<div id="app">
		<router-view />
    <div class="backTo" v-show="isShow">
        <button @click="prev" class="btn btn-warning btn-lg btn-block">返回上一页</button>
    </div>
	</div>
</template>

<script>
	export default {
		name: 'App',
		data() {
			return {
				isShow: true
			}
		},
		created() {
			this.getUrl();
		},
		methods: {
			getUrl: function() {
				var path = this.$route.path;
				if (path == "/") {
					this.isShow = false;
				}

			},
			prev() {
				this.$router.go(-1)
			},
		},
		watch: {
			'$route': function(now, old) {
				//监控路由变换，控制返回按钮的显示
				if (now.path == "/" || now.path == "") {
					this.isShow = false;
				} else {
					this.isShow = true;
				}
			}
		},
	}
</script>
<style>

</style>



// WEBPACK FOOTER //
// src/App.vue
