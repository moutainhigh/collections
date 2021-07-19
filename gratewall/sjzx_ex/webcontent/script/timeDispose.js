
// getCurrentTime, Format"YYYY-MM-DD HH:MM"
function getCurrentTime()
{
	var time = new Date();
	var year = time.getYear();
	var month = time.getMonth() + 1;
	if (month.toString().length == 1)
	{
		month = "0" + month;
	}
	var date = time.getDate();
	if (date.toString().length == 1)
	{
		date = "0" + date;
	}
	var hours = time.getHours();
	if (hours.toString().length == 1)
	{
		hours = "0" + hours;
	}
	var minutes = time.getMinutes();
	if (minutes.toString().length == 1)
	{
		minutes = "0" + minutes;
	}
	
	var currentTime = year + '-' + month + '-' + date + ' ' + hours + ':' + minutes; 
	return currentTime;
}

function getLastMonthOfNow()
{
	var time = new Date();
	var year = time.getYear();
	var month = time.getMonth();
	if (month.toString().length == 1)
	{
		month = "0" + month;
	}
	if (month.toString() == "00")
	{
		year -= 1;
		month = "12";
	}
	var date = time.getDate();
	if (date.toString().length == 1)
	{
		date = "0" + date;
	}
	var hours = time.getHours();
	if (hours.toString().length == 1)
	{
		hours = "0" + hours;
	}
	var minutes = time.getMinutes();
	if (minutes.toString().length == 1)
	{
		minutes = "0" + minutes;
	}
	
	// if month is April, June, September, or November, They donot hava the data 31th
	if (month == "04" || month == "06" || month == "09" || month == "11")
	{
		if (date == "31")
		{
			date = "30";
		}
	}
	
	if (month == "02")
	{
		var yearTail = year.toString().substring(2,4);
		var leapYearFlag;
		if (yearTail == "00")
		{
			leapYearFlag = year%400;	
		}
		else
		{
			leapYearFlag = year%4;
		}
		
		// Leap Year
		if(leapYearFlag == "0")
		{
			if(date > 29)
			{
				date = 29;
			}
		}
		// Not a Leap Year
		else
		{
			if(date > 28)
			{
				date = 28;
			}
		}
	}
	
	var currentTime = year + '-' + month + '-' + date + ' ' + hours + ':' + minutes; 
	return currentTime;
}