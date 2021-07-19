 /*	
	audioDom.addEventListener("loadedmetadata",function(e){
	/错误状态
	Media.error; //null:正常
	Media.error.code; //1.用户终止 2.网络错误 3.解码错误 4.URL无效
	Media.volume = 0.1-1;
	//网络状态
	 Media.currentSrc; //返回当前资源的URL
	   Media.src = value; //返回或设置当前资源的URL
	   Media.canPlayType(type); //是否能播放某种格式的资源
	   Media.networkState; //0.此元素未初始化  1.正常但没有使用网络  2.正在下载数据  3.没有找到资源
	   Media.load(); //重新加载src指定的资源
	   Media.buffered; //返回已缓冲区域，TimeRanges
	   Media.preload; //none:不预载 metadata:预载资源信息 auto:

	//准备状态
	   Media.readyState; //1:HAVE_NOTHING 2:HAVE_METADATA 3.HAVE_CURRENT_DATA 4.HAVE_FUTURE_DATA 5.HAVE_ENOUGH_DATA
	   Media.seeking; //是否正在seeking

	//回放状态
	   Media.currentTime = value; //当前播放的位置，赋值可改变位置
	   Media.startTime; //一般为0，如果为流媒体或者不从0开始的资源，则不为0
	   Media.duration; //当前资源长度 流返回无限
	   Media.paused; //是否暂停
	   Media.defaultPlaybackRate = value;//默认的回放速度，可以设置
	   Media.playbackRate = value;//当前播放速度，设置后马上改变
	   Media.played; //返回已经播放的区域，TimeRanges，关于此对象见下文
	   Media.seekable; //返回可以seek的区域 TimeRanges
	   Media.ended; //是否结束
	   Media.autoPlay; //是否自动播放
	   Media.loop; //是否循环播放
	   Media.play(); //播放
	   Media.pause(); //暂停 	
	 eventTester("loadstart"); //客户端开始请求数据
	 eventTester("progress"); //客户端正在请求数据
	 eventTester("suspend");  //延迟下载
	 eventTester("abort");  //客户端主动终止下载（不是因为错误引起），
	 eventTester("error");  //请求数据时遇到错误
	 eventTester("stalled");  //网速失速
	 eventTester("play");  //play()和autoplay开始播放时触发
	 eventTester("pause");  //pause()触发
	 eventTester("loadedmetadata"); //成功获取资源长度
	 eventTester("loadeddata"); //
	 eventTester("waiting");  //等待数据，并非错误
	 eventTester("playing");  //开始回放
	 eventTester("canplay");  //可以播放，但中途可能因为加载而暂停
	 eventTester("canplaythrough"); //可以播放，歌曲全部加载完毕
	 eventTester("seeking");  //寻找中
	 eventTester("seeked");  //寻找完毕
	 eventTester("timeupdate"); //播放时间改变
	 eventTester("ended");  //播放结束
	 eventTester("ratechange"); //播放速率改变
	 eventTester("durationchange"); //资源长度改变
	 eventTester("volumechange"); //音量改变
 */

var tzAudio = function (options){
	//这里有什么用.我是不是可以给一些默认，比如：是否自动播放，音乐的大小的默认值，播放的第一次的地址，控制是否循环播放等等.
	this.controls = options.controls||"";//显示控制播放器的展示
	this.targetId = "audiobox";//audio添加的目标
	//this.autoPlay = "autoPlay";//自动播放
	this.volume = 0.4;//默认音量
	this.loop = 1;//是否为单曲循环 1顺序播放 2 单曲循环 3 随机播放 4	其他
	this.options = options;//参数链接
	this.audioDom = document.createElement("audio");//当前这个audio永远只创建一次,效率问题
	this.audioDom.setAttribute("audioVolume",this.volume);//缓存音量
};

//初始化
tzAudio.prototype.init = function(src){
	var $this = this;
	var $audioDom = $this.audioDom;
	$audioDom.src = src;
	$audioDom.controls = $this.controls;
	$audioDom.autoPlay = $this.autoPlay;
	$audioDom.volume = this.volume;//声音的范围[0.1-1]
	document.getElementById($this.targetId).appendChild($audioDom);
	//事件监听,监听主要监听一些动态改变的事件就变得有意义了,
	//loadedmetadata loadeddata:成功获取资源长度 
	$audioDom.addEventListener("loadedmetadata",function(){
		var totalTime = this.duration;//总时长
		var m = parseInt(totalTime / 60);//分钟
		var s = parseInt(totalTime % 60);//秒
		var ms = ((m < 10) ? ("0"+m) : m ) +":"+ ((s < 10) ? ("0"+s) : s );
		if(!$audioDom.pasued && $this.options.timecallback)$this.options.timecallback($audioDom,totalTime,ms);
	});
	
	$audioDom.addEventListener("timeupdate",function(){
		var totalTime = this.duration;//总时长
		var currentTime = this.currentTime;
		var stime = totalTime - currentTime; 
		var m = parseInt(currentTime / 60);//分钟
		var s = parseInt(currentTime % 60);//秒
		var ms = ((m < 10) ? ("0"+m) : m ) +":"+ ((s < 10) ? ("0"+s) : s );
		var mm = parseInt(stime / 60);//分钟
		var ss = parseInt(stime % 60);//秒
		var sms = ((mm < 10) ? ("0"+mm) : mm ) +":"+ ((ss < 10) ? ("0"+ss) : ss );
		if($this.options.timeupdate)$this.options.timeupdate($this,$audioDom,totalTime,currentTime,ms,sms);
	});
};

//更改播放地址
tzAudio.prototype.playLink = function(src){
	if(this.audioDom.played){//判断当前是播放的情况下，才暂停
		this.audioDom.pause();
	}
	this.currentTime = 0;
	this.init(src);
	this.play();
};

//改变音量的
tzAudio.prototype.changeVolume = function(volume){
	this.volume = volume;
	this.audioDom.setAttribute("audiovolume",volume);
	this.audioDom.volume = volume;
};

//静音
tzAudio.prototype.staticVolume = function(callback){
	if(this.volume==0){
		var dataVolume = this.audioDom.getAttribute("audiovolume");
		this.volume = dataVolume;
		this.audioDom.volume = dataVolume;
		if(callback)callback(dataVolume);
	}else{
		this.volume = 0;
		this.audioDom.volume = 0;
		if(callback)callback(0);
	}
};

//播放
tzAudio.prototype.play = function(){
	this.audioDom.play();
};

//暂停
tzAudio.prototype.pause = function(){
	this.audioDom.pause();
};

//暂停
tzAudio.prototype.loops = function(loop){
	this.loop = loop;
};




//参数列表
var audioOptions = {
	src : $("#playlist").find(".playitems").data("src"),
	timecallback : function(audioDom,totaltime,ms){
		$("#time").text(ms);
		$(".load").mousedown(function(e){
			var x = e.clientX;
			var left = $(this).offset().left;
			var w = x - left;
			var tw =  $(this).width();
			var percent = w  / tw ;
			var current = totaltime * percent;
			audioDom.currentTime = current;
			$("#play").trigger("click");//触发播放按钮
		});
		
		//音量的调节
		$(".m_volume_load").mousedown(function(e){
			var x = e.clientX;
			var left = $(this).offset().left;
			var w = x - left;
			var tw =  $(this).width();
			var percent = w  / tw ;
			$(this).next().width((percent * 100)+"%");
			audio.changeVolume(percent<0.01?0:percent);
		});
	},
	
	timeupdate : function(audioobj,$audioDom,totalTime,currentTime,ms,sms){
		$("#stime").text(ms);
		$("#ctime").text(sms);
		//进度条
		var percent = (currentTime / totalTime) * 100;
		$(".percent").width(percent+"%");
		if(percent>=100){//顺序播放
			if(audioobj.loop){
				$("#next").trigger("click");
			}else {
				//单曲循环或者随机播放(产生一个随机数(0-length))
				$audioDom.currentTime = 0;
				$(".percent").width("0%");
			}
		}
	}
};	
//audio初始化
var audio = new tzAudio(audioOptions);
