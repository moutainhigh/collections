
var SwordSearch = new Class({
    Implements: [Options, Events],
     $family: {name: 'SwordSearch'}
    ,targetContainer:null
    ,listContainer:null
    ,css:{
        'searchContainer':'searchContainer'
        ,'infoContainer':'infoContainer'
        ,'search_main':'search_item'
        ,'search_title':'search_title'
        ,'search_docket':'search_docket'
        ,'search_titlePath':'search_titlePath'
        ,'search_path':'search_path'

    }
    ,sum:0

	,options: {
         inputID:''
        ,searchBtnID:''
        ,listType:'frame'
        ,searchSrc:''

        /*
        目標展現容器
         */
        ,targetContainer:''
        /*
        摘要长度
         */
        ,docketLength:'400'
        /*
        向前距离
         */
        ,maxFrontLength: '100'
        ,isFenye:false
        ,rowNum:'10'

        ,onInitBefore:$empty()
        ,onClickBefore:$empty()
        ,onClickTitle:$empty()
    }

    ,initElement:function() {
        SwordSearch.Div = new Element("div");
        this.listContainer = new Element("iframe", {
            'name': "search_frame",
            'frameborder': 0,
            'border': 0
        });
        this.listContainer.setStyle('display', 'none');
        this.listContainer.setProperty('src', this.options.searchSrc);
        this.listContainer.inject(window.document.body);    
        this.addEvent("onIframeLoaded",function(){
            $(this.options.searchBtnID).set("disabled","");
        });
        if(!Browser.Engine.trident) { //if not IE
            this.listContainer.addEvent('load', function(){
                this.fireEvent("onIframeLoaded",this);
            }.bind(this));
        } else {
            this.listContainer.addEvent('readystatechange', function(){
                if (this.listContainer.readyState == "complete"){
                    this.fireEvent("onIframeLoaded",this);
                }
            }.bind(this));
        }

    }

    ,initialize: function(options) {
        this.setOptions(options);
        this.initElement();
        if($chk(this.options.targetContainer)){
             this.targetContainer = $(this.options.targetContainer);
        }else{
             this.targetContainer = SwordSearch.Div.clone();
             this.targetContainer.set("id","_targetContainer");
             this.targetContainer.inject(document.body);
             
        }

        this.options.docketLength = this.options.docketLength.toInt();
        this.options.maxFrontLength = this.options.maxFrontLength.toInt();

        this.build();
       
    }
    ,build:function(){
        $(this.options.searchBtnID).addEvent("click",function(){
              var res ;
              if($defined( this.$events["clickBefore"])){
                  res = this.$events["clickBefore"][0].create({'bind': this, 'arguments': $(this.options.inputID).get("value").toLowerCase()})();
              }
              if(!$defined(res)){ res = true};
              if(res){
                   this.sum = 0;
                   this.search($(this.options.inputID).get("value").toLowerCase());
              }
            //todo  写死了。
            $('ss').removeClass('ss');
            $('ss').addClass('ss2');
         }.bind(this));
         $(this.options.inputID).addEvent("keydown",function(e){
                 if(e.key=="enter"){
                    $(this.options.searchBtnID).fireEvent("click") ;
                 }
         }.bind(this));

    }
    ,initSearchDiv:function(){

        this.searchContainer = new Element("div",{'id':'_searchContainer'});
        this.searchContainer.addClass(this.css.searchContainer);
        this.topDiv = new Element("div",{'id':'_searchTop'});
        this.ptDiv = new Element("div",{'id':'_searchPt'});
        this.topDiv.inject(this.searchContainer);
        this.ptDiv.inject(this.searchContainer);

        this.searchContainer.inject(this.targetContainer);
        this.infoContainer = new Element("div",{'id':'_infoContainer'});
        this.infoContainer.addClass(this.css.infoContainer);
        this.infoContainer.inject(this.targetContainer);
        this.infoSum = SwordSearch.Div.clone();
        this.infoSum.set("id","sum");
        this.infoSum.inject(this.infoContainer);

    }
    ,search:function(searchText){

        if(!this.searchContainer){
            this.initSearchDiv();
        }
        this.fireEvent("onInitBefore");
        this[this.options.listType].run(searchText,this);
    }


   // 应获取所有 div
    //计算匹配度
    //匹配度高的放前面

    ,frame:function(searchText){

        var search = searchText.split(/\s/);
        var tfS = "div";
        var pfS = "div";
        for(var i=0;i<search.length;i++){
            tfS += "[title*="+search[i]+"]";
            pfS += "[:contains("+search[i]+")]";
        }

        var tf = function(tfS){
           
            var list = $(this.listContainer.contentWindow.document.body).getElements(tfS);
            this.topDiv.set("html","");
            this.sumSearch(list);
            list.each(function(item,index){
                var docket = this.createDocket(item.get("html"));
                this.createContent(this.topDiv,{'title':item.get("title"),'docket':docket
                    ,'titlePath':item.get("titlePath")
                    ,'path':item.get("url")});
            }.bind(this));
        }.bind(this);
        var pf = function(pfS){
            var list = $(this.listContainer.contentWindow.document.body).getElements(pfS);
            this.ptDiv.set("html","");
            this.sumSearch(list);
            list.each(function(item,index){
                var docket = this.createDocket(item.get("html"));
                this.createContent(this.ptDiv,{'title':item.get("title"),'docket':docket
                    ,'titlePath':item.get("titlePath")
                    ,'path':item.get("url")});
            }.bind(this));
        }.bind(this);
        tf.delay(0,this,[tfS]);
        pf.delay(100,this,[pfS]);
    }

    ,sumSearch:function(list){
         if(list && list.length>0){
              if(this.sum==0){
                  this.sum = list.length;
              }else{
                  this.sum=this.sum+list.length;
              }
         }
        this.infoSum.set("html","共【"+this.sum+"】条");
    }

    ,createDocket:function(html){

        var res = "";
        var spT=""; //前置

        if((html||"").length<=this.options.docketLength){
            res =this.createHighlight(html,$(this.options.inputID).get("value").toLowerCase());
        }else{
            var spT_start = 0; //前置位置
            var spT_end = 0;//后置位置
            var index = html.indexOf($(this.options.inputID).get("value").toLowerCase());
            if(index>0){
                var sp =  html.substring(index-this.options.maxFrontLength,index).lastIndexOf(" ");
                if(sp>=this.options.maxFrontLength){
                    spT_start = index-this.options.maxFrontLength;
                    spT_end = index+(this.options.docketLength - this.options.maxFrontLength);
                }else{
                    spT_start = index - sp;
                    spT_end = index +(this.options.docketLength - (index-spT_start));
                }
                if(spT_end > html.length){
                    spT_end = html.length;
                }
                res = html.substring(spT_start,spT_end);
                res =this.createHighlight(res,$(this.options.inputID).get("value").toLowerCase());
               }else{
                res = html.substring(0,html.length>this.options.maxFrontLength?this.options.maxFrontLength:html.length);
            }
            res +="...";
        }
        return res;

    }

    ,createHighlight:function(res,rep){
        var search = rep.split(/\s/);
        for(var i=0;i<search.length;i++){
            var se = "/"+search[i]+"/g";
            res = res.replace(eval(se),"<span style='color:#C60A00;font-weight:bold;'  >"+search[i]+"</span>");
        }
        return res;
    }

    ,createContent:function(contain,content){
        var main = SwordSearch.Div.clone();
        var title =  SwordSearch.Div.clone();
        var docket = SwordSearch.Div.clone();
        var titlePath =  SwordSearch.Div.clone();
        var path = SwordSearch.Div.clone();
        title.set("html",content['title']+"    【路径："+content['titlePath']+"】"); title.inject(main);  title.addEvent("click",function(){this.fireEvent("onClickTitle",content);  }.bind(this));
        docket.set("html",content['docket']||"");docket.inject(main);
//        titlePath.set("html","路径："+content['titlePath']||"");titlePath.inject(main);
        path.set("html","详细位置: "+content['path']||"");path.inject(main);
        main.addClass(this.css.search_main);
        title.addClass(this.css.search_title);
        docket.addClass(this.css.search_docket);
        titlePath.addClass(this.css.search_titlePath);
        path.addClass(this.css.search_path);
        main.inject(contain);
    }

});