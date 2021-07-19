<template>
	<div>
		<chose url='shouYangOneList' searchURL="findShouYangOne" getTimeURL="getShouYangOneMaxTime" title="每日收阳"></chose>
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


