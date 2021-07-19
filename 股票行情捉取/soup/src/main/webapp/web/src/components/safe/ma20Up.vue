<template>
	<div>
		<chose url='closePriceMa20List' searchURL="findClosePriceMa20"  getTimeURL="getclosePriceMa20MaxTime"  title="股价大于20日均线"></chose>

	</div>
</template>

<script>
	import chose from './chose';

	export default {
		components: {
			chose
		}

	}
</script>

<style>
</style>
