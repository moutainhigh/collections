<template>
	<div>
		<chose url='weekMa5VolList' searchURL="findWeekMa5Vol" getTimeURL="getWeekMa5VolMaxTime" title="周线的5日成交量涨的"></chose>
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
