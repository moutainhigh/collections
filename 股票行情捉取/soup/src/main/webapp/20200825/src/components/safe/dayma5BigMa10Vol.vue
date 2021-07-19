<template>
	<div>
		<chose url='ma5BigVol' searchURL="findMa5BigVol" getTimeURL="getMa5BigVolMaxTime" title="5日成交量大于10日成交量涨的"></chose>
	</div>
</template>

<script>
	import chose from './chose';

	export default {
		components: {
			chose
		},
		data(){
			return {
				//list:[]
			}
		},
	}
</script>

<style>
</style>
