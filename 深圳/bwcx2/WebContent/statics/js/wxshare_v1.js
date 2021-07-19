//获取分享图片属性
scripts = document.getElementsByTagName('script');
currentScript = scripts[scripts.length - 1];
imgurl = currentScript.getAttribute('imgurl');
link = currentScript.getAttribute('link');
desc = currentScript.getAttribute('desc');
title = currentScript.getAttribute('title');

//加载微信js-sdk
var head = document.getElementsByTagName('head')[0];
var script = document.createElement('script');
script.type = 'text/javascript';
script.onload = script.onreadystatechange = function () {
    if (!this.readyState || this.readyState === "loaded" || this.readyState === "complete") {
        if(typeof $ != "function"){
            //alert("is function");
            $ = jQuery;
        } 
        setTimeout(share(),300);
        // Handle memory leak in IE 
        script.onload = script.onreadystatechange = null;
    }
};
script.src = 'statics/js/jweixin-1.0.0.js';
head.appendChild(script);




//设置朋友圈和微信分享
function share() {
    var url = window.location.href;
    if (url.indexOf('#') > -1) {
        url = url.split('#')[0];
    }
    var sharetitle = $('title').text().replace("\n","");
    var sharelink = url + '?' + new Date().getTime();
    var shareimgUrl = "http://news.sznews.com/123781.files/fx20170331.jpg";

    if (title != null) {
        sharetitle = title;
    }
    if (imgurl != null) {
        shareimgUrl = imgurl;
    }
    if (link != null) {
        sharelink = link;
    }
    var sharedesc = $('title').text().replace("\n","");;
    if (desc != null) {
        sharedesc = desc;
    }
    var shareTimeline = $('title').text().replace("\n","");;
    //console.log(shareimgUrl);
    $.ajax({
        url: 'https://v2.sznews.com/weitest/index.php?s=Wxshare/getwx.html',
        type: 'post',
        data: {
            url:url.replace(/&/g, "@")
        },
        success: function (msg) {

            msg = JSON.parse(msg);
            // console.log(msg);
            wx.config({
                debug: false,//值为true时进入debug模式，可以打出状态值
                appId: msg.appId,
                timestamp: msg.timestamp,
                nonceStr: msg.nonceStr,
                signature: msg.signature,
                jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage'
                  // 所有要调用的 API 都要加到这个列表中
                ]
            });
            wx.ready(function () {
                //alert(123);
                wx.checkJsApi({
                    jsApiList: ['onMenuShareTimeline', 'onMenuShareAppMessage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
                    success: function (res) {
                        // 以键值对的形式返回，可用的api值true，不可用为false
                        // console.log("api success");
                        //console.log(res);

                    }
                });

                wx.error(function (res) {
                })
                //获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
                wx.onMenuShareTimeline({
                    title: sharedesc, // 分享标题
                    link: sharelink, // 分享链接
                    imgUrl: shareimgUrl, // 分享图标
                    success: function () {
                        // 用户确认分享后执行的回调函数

                        // console.log("share1 success");
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                    },
                    fail: function () {
                        //alert("share1 fail");
                    }
                });
                //获取“分享给朋友”按钮点击状态及自定义分享内容接口
                wx.onMenuShareAppMessage({
                    title: sharetitle, // 分享标题
                    desc: sharedesc, // 分享描述
                    link: sharelink, // 分享链接
                    imgUrl: shareimgUrl, // 分享图标
                    type: 'link', // 分享类型,music、video或link，不填默认为link
                    dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                    success: function () {
                        // 用户确认分享后执行的回调函数

                        // console.log("share2 success");
                    },
                    cancel: function () {
                        // 用户取消分享后执行的回调函数
                    },
                    fail: function () {
                        //alert("share2 fail");
                    }
                });
            });
        }
    });
}