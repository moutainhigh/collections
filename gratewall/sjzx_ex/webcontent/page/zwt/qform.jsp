<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>深圳市市场和质量监督管理委员会数据交换平台-热力图</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<script type="text/javascript" src="/page/zwt/Charts/FusionCharts.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/lib/jquery171.js"></script>
<style type="text/css">
#t4 ul.pack-list li a span{/* display:none; */}
#modal_window, #searchDiv{width:400px; }
.cont{width: 400px; height: 400px; overflow-y: auto; overflow-x: hidden; padding: 0; border: 1px solid #def; }
.albar{background: rgb(41, 140, 206) !important;margin:0px; text-align: center; line-height: 20px; height: 20px; color: white;}
.letter-list, .letter-list li{padding: 0; margin: 0; display: inline; text-align: center; color: #fff;}
.letter-list li:hover, .letter-list .on{background: rgb(102, 200, 232); cursor: default;}
.letter-list a{color: #fff; text-decoration: none; margin: 0 5px;}
.letter-list a:hover, .letter-list a:visited, .letter-list a:linked{text-decoration: underline; color: #fff;}
.cont .cont-area{ color: #036; font-weight: bold; margin-left: 5px;}
.cont .item{color:#333; padding-left:2px; cursor: pointer; height: 25px; line-height: 25px; border: 1px solid rgb(207,207,254); margin-bottom: 1px; font-size: 12px;}
.cont .item-hover{background: #cef;}
.cont .item .tnum{color: #888;}
.nodata{display: none;}
</style>
</head>
<script type="text/javascript" src="/script/lib/jquery171.js"></script>
<script type="text/javascript" src="/script/lib/jquery/gwssi.qform.js"></script>
<script type="text/javascript" src="/script/lib/pinyin.js"></script>
<jsp:include page="/page/share/common/top_datepicker.html"></jsp:include>
<script type="text/javascript">
var itfName = [{"data":[{"title":"12315登记基本信息接口（区县）","key":"534104dd002747c68f28f0ed1a239164"},{"title":"案件复议信息(广告)","key":"870343308162491084983fc1b1274c60"},{"title":"案件基本信息","key":"44e8b94de8c34fe5bb83be26e257c05e"},{"title":"案件基本信息(广告新)","key":"ee3b9deb869044db8a2b99d7bc808cb9"},{"title":"案件基本信息-农资监管（批量）","key":"5b18998479184df69cd94457252dd173"},{"title":"案件基本信息-市场监管（商户）","key":"d86673107b704b4a9e4f7dc3748c825c"},{"title":"案件基本信息-市场监管（市场）","key":"748c397163f3469c8c2d882a718066ce"},{"title":"地税变更信息-信用二期","key":"879c30ab015d418893befa918dd66d84"},{"title":"地税查询时间段内企业股权转让信息","key":"42bd8a9582f544abb567fb80bf9000a6"},{"title":"地税基本信息-信用二期","key":"3cbc2f61e8794f28b4560c4cba5bd3f5"},{"title":"个体变更信息","key":"5c0f66186b034c63a9739811069e1db3"},{"title":"个体基本信息","key":"e9845e8246974184ae3ff573d30d9216"},{"title":"个体基本信息(单表)","key":"b8a6a451085045feb6f0c5337d6954d7"},{"title":"个体信息(附表+户口）","key":"58257ff2d7dc42348f628c867dfe13a9"},{"title":"股权冻结信息","key":"002b2f29d87a4e86822efe882c90ae93"},{"title":"股权质押信息","key":"eef258fe9e2f412a9eb5ed2c1be93c9b"},{"title":"股权转让信息","key":"a1dc911ebaf540d8acad00a5d06699ee"},{"title":"广告经营监管信息","key":"886d08762f33441cbf37de44214bd4e2"},{"title":"国家总局黑牌企业基本信息","key":"ff665bde6ad6442e8836064d23817a21"},{"title":"国家总局一人公司基本信息","key":"8d4ed94392024938b57a3f7cc263cec5"},{"title":"国家总局一人有限公司(自然人)","key":"60f83171a29f4aaea3699273b2801da0"},{"title":"监管基本信息(批量)","key":"f517d4c770cb4d52a368fa6e8fee9885"},{"title":"监管日常检查","key":"3ee447256e6049028f6a080750573582"},{"title":"监管日常检查--网格管理(检查)-手机","key":"d167e9ca94f04436b3d974c46d0fbd54"},{"title":"隶属企业信息","key":"25d3c99cd02a45eb84cdc412d7f5b55a"},{"title":"名称登记主题-区县","key":"866989454e0244ecab3dc65fc87976b8"},{"title":"内资企业及附属信息(全匹配)","key":"b89870bed2f44ebf9e3f6b6cd8e79397"},{"title":"年检验照信息","key":"80856ae2cbbf4b49b3f544f05a76fdf0"},{"title":"年检验照信息(内资法人)","key":"d4b10df7944d46678fec1c5f478b2b03"},{"title":"年检验照信息(外资法人)","key":"23b9a0059c11469e94660a3f11864435"},{"title":"企业变更历史","key":"ae922ec52d7b493398260df3d7bf3f51"},{"title":"企业分支机构信息","key":"e4f9f65ab47a43d4b0aaf71e45ab859b"},{"title":"企业附属信息(附表-证照-隶属企业)","key":"6c672c2a026c4ed1b900d5b7d6674bae"},{"title":"企业股东信息（单户）","key":"12a2baa5b5fe49129fec4567ec46d38e"},{"title":"企业基本登记事项","key":"123f455bac694f3c90d69ccb5524e2ac"},{"title":"企业基本信息(+外资附表)","key":"32e35507096a4649b19f9eab15be5631"},{"title":"企业基本信息(+外资附表和地理信息)","key":"324441d792974791af5ad7e53442b1f7"},{"title":"企业基本信息(批量)-市场监管系统(商户)","key":"2f6c6a4cf60f4576b83ff7ef27ba637f"},{"title":"企业基本信息(批量)-市场监管系统(市场)","key":"43dbbd7259f849e2a08368f3a2bbc2f1"},{"title":"企业基本信息-农资系统(单户)","key":"ec3a9ba9921c4722b3dbd213edd1d47e"},{"title":"企业机构历史-区县","key":"4280b45210b34ed2850795a8449fd850"},{"title":"企业机构删除表","key":"96b973f771204f7cb9610b00231b57b9"},{"title":"企业联系人","key":"7fad9ecfb08943aa94b8b1c4f8905969"},{"title":"企业年检信息","key":"ac4d6c99c05048f8935919385dea778e"},{"title":"企业迁移信息-区县","key":"d8fa57c4d70e472ba5ed912247d6f4bf"},{"title":"企业投资人信息","key":"cb7c88d22f264b04a3fb29c20c75e083"},{"title":"企业信息(附表（内外）+户口)","key":"3e89ce1c10be414f90917019938918d8"},{"title":"企业许可证信息","key":"f2727d66a6304dc4bf0e222e057bd286"},{"title":"企业证照信息","key":"8538507736254ffb8c224770eae237d0"},{"title":"企业主要人员职务信息","key":"ec2fd255eb2342ddb899e8fa7b43323a"},{"title":"清算信息","key":"803c190477fc4dab8c54f802405de511"},{"title":"申诉信息(单户)—企业E网通","key":"b2496a73537f4e8c99207eb59d7aecdc"},{"title":"食品许可证-补证","key":"62957f50b72a4addb7393b339ad1b89a"},{"title":"食品许可证-撤销","key":"46ed9d4e66fc4c028bc098ce8d26f5fa"},{"title":"食品许可证-吊销","key":"2dba19014e50436284f34d27b01bd722"},{"title":"食品许可证基本信息（区县）","key":"743dd41948c949c9a9289e1a40784ecb"},{"title":"市场控制企业退市信息-监管信息","key":"fdadecd03e7c4bcf9e8e73f92462c6a4"},{"title":"市场控制暂停销售信息","key":"08180a49342f43f6b11450fc397f7514"},{"title":"市场控制暂停销售信息(新)","key":"6c601c8734a746ee8266c2068d6ce65b"},{"title":"市场控制暂停销售信息-监管系统","key":"020f02e59915462e99e9d3de75d7ccfe"},{"title":"市场控制暂停销售信息-监管系统(新)","key":"5ae38c8a18b343e19ce6bab547f0bd11"},{"title":"停止销售商品信息","key":"8a322ccccc2546d8b867d17c42ff58e6"},{"title":"停止销售商品信息（新）","key":"5086244c3b0840448c868fef61265f81"},{"title":"投资人信息(单户)","key":"14029395dcaf4cc68a304247bddd44a8"},{"title":"投资人信息（主表+投资人）","key":"7592eb99eb3543b7b920b9dd3dd09cdd"},{"title":"外商(机构)投资企业投资","key":"918092358c8c4cebbff0b8a084de9e7c"},{"title":"外资代表机构附属信息","key":"cfd07699c8fb4842a122d39e1478d63d"},{"title":"外资企业(机构)基本信息","key":"a35e9519c20e418892e1e633e32f5c19"},{"title":"质监扩展信息-信用二期","key":"589609dd2c4b46608251d07a89552c2d"},{"title":"主要人员信息(单户)","key":"3a50e14a84214729b10a877f6fb41563"},{"title":"总局登记企业信息（含投资表）","key":"b426f0ae462e4a7fba376cc49ea8d98f"},{"title":"总局登记企业信息（含主要人员）","key":"8b94be1495f74eb39cc6dfdc1c6cd968"}],"hot":[{"title":"12315登记基本信息接口（区县）","key":"534104dd002747c68f28f0ed1a239164"},{"title":"案件复议信息(广告)","key":"870343308162491084983fc1b1274c60"},{"title":"案件基本信息","key":"44e8b94de8c34fe5bb83be26e257c05e"},{"title":"案件基本信息(广告新)","key":"ee3b9deb869044db8a2b99d7bc808cb9"},{"title":"案件基本信息-农资监管（批量）","key":"5b18998479184df69cd94457252dd173"},{"title":"案件基本信息-市场监管（商户）","key":"d86673107b704b4a9e4f7dc3748c825c"},{"title":"案件基本信息-市场监管（市场）","key":"748c397163f3469c8c2d882a718066ce"},{"title":"地税变更信息-信用二期","key":"879c30ab015d418893befa918dd66d84"},{"title":"地税查询时间段内企业股权转让信息","key":"42bd8a9582f544abb567fb80bf9000a6"},{"title":"地税基本信息-信用二期","key":"3cbc2f61e8794f28b4560c4cba5bd3f5"}]}];


function bulidForm(){
	var datahtml = itfName[0].data;
    var $e = $(this).parent();
    $('#mask').css({
        'display': 'inline',
         opacity: 0
    });
    //fade in the mask to opacity 0.8 
    $('#mask').fadeTo(500, 0.4);
    $('#mask').height(document.body.availHeight);
    $('#mask').width(document.body.availWidth);
    //show the modal window
    $('#modal_window').html('');

    if ($('#searchDiv').length == 0) {
        $('#modal_window').before('<div id="searchDiv" class="albar" style="text-align:center;font-size:14px; width: 400px;" >'
            //+ '<input value="" style="" type="text" id="searchBar" />' + '<span style="">(输入关键词进行检索)</span>' + '<span style="" id="windowClose">[关闭]</span>'+
            + '<ul class="letter-list"><li class="on"><a href="#A">A-E</a></li><li><a href="#F">F-J</a></li><li><a href="#K">K-O</a></a></li><li><a href="#P">P-T</a></li><li><a href="#U">U-Z</a></li> <li><a href="#other">其他</a></li> </ul><div id="windowClose">[关闭]</div></div>');
    } else {
        $('#searchDiv').show();
    }
    //alert($('#modal_window').css('top')+ " - - - - " +$('#modal_window').height());
    //$('#searchDiv').css('top', ($('#modal_window').css('top')-$('#modal_window').height()));
    $('#modal_window').html('');
    var datahtml = buildHtml(itfName[0].data);
    if (datahtml) {
        $('#modal_window').html(datahtml);
    }
    $('#searchDiv a').bind('click', function() {
        $(this).parent().addClass('on');
        $(this).parent().siblings().removeClass('on');
    })
    $('#modal_window .item').bind('click', function() {
        //$(this).parent().addClass('selected');
        //$(this).parent().siblings().removeClass('selected');
        var id = $(this).attr('id').replace('_', "");
        var mc = $(this).text();
        $el.find('.move2topSel').html('<a id="' + id + '" class="inner_a" href="javascript:;">' + mc + '</a>');
        $('#modal_window').data('name', mc).data('value', id);
        $el.queryform('getSelectedNode');
    }).bind('mouseover', function(){
        $(this).addClass('item-hover');
    }).bind('mouseout', function(){
        $(this).removeClass('item-hover');
    })
    //点击选中事件
    $('#modal_window .pack-list li a').each(function() {
        $(this).bind("click", function() {
            var indes = $(this).text();
            var id = $(this).parents('ul').attr("id").replace(/_modal/g, '');
            $("#" + id).find("li:gt(1)").each(function() {
                // console.log($(this).find('a').text() +" = = = "+ indes );
                if ($(this).find('a').text() == indes) {
                    // console.log($(this).find('a'));
                    $(this).find('a')[0].click();
                    $('.close_modal')[0].click();
                    return false;
                }
            })
            if ($(this).parent().hasClass('active')) {
                $('.close_modal')[0].click();
            }
        })
    })
    //if($('#searchDiv').length==0){
    $('#searchBar').keyup(
        function() {
            $("#modal_window .pack-list li:gt(1)").not('.pull-out').hide().filter(
                ":contains('" + ($(this).val()) + "')").show();
        }).keyup();
    $('#windowClose').click(function() {
    	$('#mask').fadeOut(500);
        $('#modal_window').fadeOut(500);
        $('#searchDiv').fadeOut(500);
    })
    //}

    //$('#modal_window .pack-list li:lt('+(2+options.maxsize)+')').hide();
    if ($('#modal_window').outerHeight() >= $('body').height() * 0.6) {
        $('#modal_window').css("overflow-y", "hidden");
        $('#modal_window').height($('body').height() * 0.75);
    }
    var left = ($(window).width() - $('#modal_window').outerWidth()) / 2;
    var top = ($(window).height() - $('#modal_window').outerHeight()) / 2;
    //$('#searchDiv').css('top', ($('#modal_window').css('top').replace('px', '')-$('#modal_window').height()));
    top = top + 20;
    if (top <= 0) {
        top = 25;
    };
    $('#modal_window').css({
        "top": top,
        "left": left,
        "position":"absolute",
        "background-color":"#fff",
        "overflow-x":"hidden"
    });

    $('#searchDiv').css('top', (top - $('#searchDiv').height() - 10));
    $('#searchDiv').css('left', left);
    $('#windowClose').css('left', $('#modal_window').width() - $('#windowClose').width() - 5);
    $('#modal_window').fadeIn("fast");
    //$el.height(32);
     
	
}
</script>

</head>
<body style="background: #ebf0f5;">
	<input type="button" value="弹出" onclick="bulidForm()">
    <div id="mark"></div>
    <div id="modal_window"></div>


</body>
</html>