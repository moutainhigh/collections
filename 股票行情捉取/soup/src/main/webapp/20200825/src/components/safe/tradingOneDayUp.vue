<template>
	<div>
		<chose url='tradingShouYangOneList' searchURL="findTradingShouYangOne" getTimeURL="getTradingShouYangOneMaxTime" title="交易中1日涨且上十日线的"></chose>
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
