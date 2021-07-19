<template>
	<div>
		<chose url='shouYangTwoList' searchURL="findShouYangTwo" title="连续两日收阳的" getTimeURL="getShouYangTwoMaxTime"></chose>
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


