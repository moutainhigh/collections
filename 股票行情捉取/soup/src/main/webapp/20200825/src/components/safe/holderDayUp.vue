<template>
	<div>
		<chose url='chiCangDayUpList' searchURL="findChiCangDayUp" getTimeURL="getChiCangDayUpMaxTime"  title="持有中大于5日线收阳的"></chose>

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


