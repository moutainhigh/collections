<template>
	<div>
		<chose url='traddingMa5VolList' searchURL="findTraddingMa5Vol" getTimeURL="getTraddingMa5VolMaxTime" title="交易中5日成交量涨的"></chose>
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
