<template>
	<div>
		<chose url='ma5VolList' searchURL="findMa5Vol" getTimeURL="getMa5VolMaxTime" title="5日成交量涨的"></chose>
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



