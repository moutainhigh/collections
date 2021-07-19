<template>
	<div>
		<chose url='dayMa10BigMa20List' getTimeURL="getDayMa10BigMa20MaxTime"  searchURL="findDayMa10BigMa20"   title="短线(ma20日)"></chose>

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
