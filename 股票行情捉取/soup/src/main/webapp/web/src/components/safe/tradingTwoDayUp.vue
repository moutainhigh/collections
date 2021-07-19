<template>
	<div>
		<chose url='traddingTwoDayUpList' searchURL="findTraddingTwoDayUp" getTimeURL="getTraddingTwoDayUpMaxTime" title="交易中连续两日涨的"></chose>
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



