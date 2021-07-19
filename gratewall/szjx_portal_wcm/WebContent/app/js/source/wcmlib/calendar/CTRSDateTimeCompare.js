Date.date2str = function(oDateTime, isNoTime){
	if(oDateTime instanceof String) return oDateTime;
	var aDate = [
		[
			oDateTime.getYear(),
			oDateTime.getMonth() + 1,
			oDateTime.getDate()
		].join("-")
	];
	if(!isNoTime){
		aDate.push(
			[
				oDateTime.getHours(),
				oDateTime.getMinutes(),
				oDateTime.getSeconds()
			].join(":")
		);
	}
	return aDate.join(" ");
};

Date.str2date = function(sDateTime){
	if(sDateTime instanceof Date) return sDateTime;
	var date = new Date();
	date.setMonth(0);
	date.setDate(1);
	var aDateTime = sDateTime.split(" ");
	var aDateInfo = aDateTime[0].split("-");
	date.setYear(parseInt(aDateInfo[0], 10));
	date.setMonth(parseInt(aDateInfo[1], 10) - 1);
	date.setDate(parseInt(aDateInfo[2], 10));

	aDateTime[1] = aDateTime[1] || "0:0:0";
	var aTimeInfo = aDateTime[1].split(":");
	aTimeInfo[0] = aTimeInfo[0] || 0;
	date.setHours(parseInt(aTimeInfo[0], 10));	
	aTimeInfo[1] = aTimeInfo[1] || 0;
	date.setMinutes(parseInt(aTimeInfo[1], 10));
	aTimeInfo[2] = aTimeInfo[2] || 0;
	date.setSeconds(parseInt(aTimeInfo[2], 10));
	date.setMilliseconds(0);
	return date;
};

//Date.milliSecondsPerDay = 24*60*60*1000;

Date.dateDiff = function(date1, date2){
	date1 = new Date(date1.getTime());
	date2 = new Date(date2.getTime());
	return (date1 - date2) ; 
};