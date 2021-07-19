<template>
	<div>
		<chose url='wuRiShiRiChongHeList' searchURL="findWuRiShiRiChongHe"  getTimeURL="getWuRiShiRiChongHeMaxTime"  title="短线(10日均线向上)"></chose>

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
