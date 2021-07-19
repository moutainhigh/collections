
/**
 *  Generated by mxmlc 4.0
 *
 *  Package:    
 *  Class:      vMultiUpload
 *  Source:     F:\workspaceF4\UploadSample\source\vMultiUpload.mxml
 *  Template:   flex2/compiler/mxml/gen/ClassDef.vm
 *  Time:       2009.12.02 11:23:15 CST
 */

package 
{

import flash.accessibility.*;
import flash.debugger.*;
import flash.display.*;
import flash.errors.*;
import flash.events.*;
import flash.external.*;
import flash.filters.*;
import flash.geom.*;
import flash.media.*;
import flash.net.*;
import flash.printing.*;
import flash.profiler.*;
import flash.system.*;
import flash.text.*;
import flash.ui.*;
import flash.utils.*;
import flash.xml.*;
import mx.binding.*;
import mx.containers.ControlBar;
import mx.containers.HBox;
import mx.controls.Button;
import mx.controls.DataGrid;
import mx.controls.Label;
import mx.controls.ProgressBar;
import mx.controls.dataGridClasses.DataGridColumn;
import mx.core.Application;
import mx.core.ClassFactory;
import mx.core.DeferredInstanceFromClass;
import mx.core.DeferredInstanceFromFunction;
import mx.core.IDeferredInstance;
import mx.core.IFactory;
import mx.core.IPropertyChangeNotifier;
import mx.core.UIComponentDescriptor;
import mx.core.mx_internal;
import mx.events.FlexEvent;
import mx.styles.*;


[SWF( backgroundColor='12632256', height='236', width='462')]
[Frame(extraClass="_vMultiUpload_FlexInit")]

[Frame(factoryClass="_vMultiUpload_mx_managers_SystemManager")]


public class vMultiUpload
    extends mx.core.Application
{

    [Bindable]
	/**
 * @private
 **/
    public var browserBtn : mx.controls.Button;

    [Bindable]
	/**
 * @private
 **/
    public var clearBtn : mx.controls.Button;

    [Bindable]
	/**
 * @private
 **/
    public var dg : mx.controls.DataGrid;

    [Bindable]
	/**
 * @private
 **/
    public var msgLb : mx.controls.Label;

    [Bindable]
	/**
 * @private
 **/
    public var pgBar : mx.controls.ProgressBar;

    [Bindable]
	/**
 * @private
 **/
    public var removeBtn : mx.controls.Button;

    [Bindable]
	/**
 * @private
 **/
    public var uploadBtn : mx.controls.Button;




private var _documentDescriptor_ : mx.core.UIComponentDescriptor = 
new mx.core.UIComponentDescriptor({
  type: mx.core.Application
  ,
  propertiesFactory: function():Object { return {
    width: 462,
    height: 236,
    childDescriptors: [
      new mx.core.UIComponentDescriptor({
        type: mx.controls.DataGrid
        ,
        id: "dg"
        ,
        stylesFactory: function():void {
          this.textAlign = "center";
          this.fontSize = 12;
        }
        ,
        propertiesFactory: function():Object { return {
          id: "dg",
          width: 460,
          height: 170,
          columns: [_vMultiUpload_DataGridColumn1_c(), _vMultiUpload_DataGridColumn2_c(), _vMultiUpload_DataGridColumn3_c()]
        }}
      })
    ,
      new mx.core.UIComponentDescriptor({
        type: mx.controls.Label
        ,
        id: "msgLb"
        ,
        stylesFactory: function():void {
          this.fontSize = 14;
        }
        ,
        propertiesFactory: function():Object { return {
          id: "msgLb",
          x: 110,
          y: 200,
          width: 110,
          text: "上传完毕!"
        }}
      })
    ,
      new mx.core.UIComponentDescriptor({
        type: mx.controls.ProgressBar
        ,
        id: "pgBar"
        ,
        stylesFactory: function():void {
          this.trackHeight = 10;
        }
        ,
        propertiesFactory: function():Object { return {
          id: "pgBar",
          width: 460,
          height: 10,
          mode: "manual",
          label: "",
          y: 173
        }}
      })
    ,
      new mx.core.UIComponentDescriptor({
        type: mx.containers.ControlBar
        ,
        propertiesFactory: function():Object { return {
          x: 207,
          y: 187,
          childDescriptors: [
            new mx.core.UIComponentDescriptor({
              type: mx.containers.HBox
              ,
              propertiesFactory: function():Object { return {
                childDescriptors: [
                  new mx.core.UIComponentDescriptor({
                    type: mx.controls.Button
                    ,
                    id: "browserBtn"
                    ,
                    propertiesFactory: function():Object { return {
                      id: "browserBtn",
                      label: "浏览"
                    }}
                  })
                ,
                  new mx.core.UIComponentDescriptor({
                    type: mx.controls.Button
                    ,
                    id: "uploadBtn"
                    ,
                    propertiesFactory: function():Object { return {
                      id: "uploadBtn",
                      label: "上传"
                    }}
                  })
                ,
                  new mx.core.UIComponentDescriptor({
                    type: mx.controls.Button
                    ,
                    id: "removeBtn"
                    ,
                    propertiesFactory: function():Object { return {
                      id: "removeBtn",
                      label: "删除"
                    }}
                  })
                ,
                  new mx.core.UIComponentDescriptor({
                    type: mx.controls.Button
                    ,
                    id: "clearBtn"
                    ,
                    propertiesFactory: function():Object { return {
                      id: "clearBtn",
                      label: "清空"
                    }}
                  })
                ]
              }}
            })
          ]
        }}
      })
    ]
  }}
})

    /**
     * @private
     **/
    public function vMultiUpload()
    {
        super();

        mx_internal::_document = this;



        // our style settings
		//	initialize component styles
		if (!this.styleDeclaration)
		{
			this.styleDeclaration = new CSSStyleDeclaration();
		}

		this.styleDeclaration.defaultFactory = function():void
		{
			this.backgroundColor = 12632256;
		};


        // ambient styles
        mx_internal::_vMultiUpload_StylesInit();

        // layer initializers

       
        // properties
        this.layout = "absolute";
        this.width = 462;
        this.height = 236;


        // events
        this.addEventListener("creationComplete", ___vMultiUpload_Application1_creationComplete);



    }

    /**
     * @private
     **/
    override public function initialize():void
    {
        mx_internal::setDocumentDescriptor(_documentDescriptor_);


        super.initialize();
    }


include "./scripts/multiUpload.as";



    //	supporting function definitions for properties, events, styles, effects
/**
 * @private
 **/
public function ___vMultiUpload_Application1_creationComplete(event:mx.events.FlexEvent):void
{
	init();
}

private function _vMultiUpload_DataGridColumn1_c() : mx.controls.dataGridClasses.DataGridColumn
{
	var temp : mx.controls.dataGridClasses.DataGridColumn = new mx.controls.dataGridClasses.DataGridColumn();
	temp.dataField = "name";
	temp.headerText = "视频文件";
	temp.dataTipField = "name";
	temp.showDataTips = true;
	mx.binding.BindingManager.executeBindings(this, "temp", temp);
	return temp;
}

private function _vMultiUpload_DataGridColumn2_c() : mx.controls.dataGridClasses.DataGridColumn
{
	var temp : mx.controls.dataGridClasses.DataGridColumn = new mx.controls.dataGridClasses.DataGridColumn();
	temp.dataField = "type";
	temp.headerText = "类型";
	temp.width = 50;
	mx.binding.BindingManager.executeBindings(this, "temp", temp);
	return temp;
}

private function _vMultiUpload_DataGridColumn3_c() : mx.controls.dataGridClasses.DataGridColumn
{
	var temp : mx.controls.dataGridClasses.DataGridColumn = new mx.controls.dataGridClasses.DataGridColumn();
	temp.dataField = "size";
	temp.headerText = "大小";
	temp.width = 150;
	temp.labelFunction = bytesToKilobytes;
	mx.binding.BindingManager.executeBindings(this, "temp", temp);
	return temp;
}




	mx_internal static var _vMultiUpload_StylesInit_done:Boolean = false;

	mx_internal function _vMultiUpload_StylesInit():void
	{
		//	only add our style defs to the StyleManager once
		if (mx_internal::_vMultiUpload_StylesInit_done)
			return;
		else
			mx_internal::_vMultiUpload_StylesInit_done = true;
			
			        var style:CSSStyleDeclaration;
			        var effects:Array;
        var conditions:Array;
        var condition:CSSCondition;
        var selector:CSSSelector;
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("Button", conditions, selector);
        // Button
        style = StyleManager.getStyleDeclaration("Button");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.factory == null)
        {
            style.factory = function():void
            {
                this.fontSize = 12;
            };
        }


        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("ToolTip", conditions, selector);
        // ToolTip
        style = StyleManager.getStyleDeclaration("ToolTip");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.factory == null)
        {
            style.factory = function():void
            {
                this.fontSize = 12;
            };
        }


        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("Alert", conditions, selector);
        // Alert
        style = StyleManager.getStyleDeclaration("Alert");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.factory == null)
        {
            style.factory = function():void
            {
                this.fontSize = 12;
            };
        }



		StyleManager.mx_internal::initProtoChainRoots();
	}




}

}