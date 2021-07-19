<template>
	<div>
		<chose url='traddingMa5VolBigMa10VolList' searchURL="findTraddingMa5VolBigMa10Vol" getTimeURL="getTraddingMa5VolBigMa10VolMaxTime" title="5日成交量涨的"></chose>
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
