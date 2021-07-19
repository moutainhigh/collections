<template>
	<div>
		<chose url='weekMa5VolBigMa10lVolList' searchURL="findWeekMa5VolBigMa10lVol" getTimeURL="getWeekMa5VolBigMa10lVolMaxTime" title="周线5日成交量大于10日成交量涨的"></chose>
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
