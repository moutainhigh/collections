<template>
	<div>
		<chose url='zhouAndDayList' searchURL="finZhouAndDayShouYangOne" title="周和日收阳" getTimeURL="getZhouAndDayShouYangMaxTime"></chose>
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



