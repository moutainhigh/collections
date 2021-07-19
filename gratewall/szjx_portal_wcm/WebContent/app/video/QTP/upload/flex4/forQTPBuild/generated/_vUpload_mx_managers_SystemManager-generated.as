package
{

import flash.display.LoaderInfo;
import flash.text.Font;
import flash.text.TextFormat;
import flash.text.engine.TextBlock;
import flash.text.engine.TextLine;
import flash.system.ApplicationDomain;
import flash.system.Security
import flash.utils.Dictionary;
import flash.utils.getDefinitionByName;
import flashx.textLayout.compose.ITextLineCreator;
import mx.core.IFlexModule;
import mx.core.IFlexModuleFactory;
import mx.preloaders.DownloadProgressBar;
import mx.preloaders.SparkDownloadProgressBar;
import mx.core.FlexVersion;
import mx.managers.SystemManager;

public class _vUpload_mx_managers_SystemManager
    extends mx.managers.SystemManager
    implements IFlexModuleFactory, ITextLineCreator
{
    // Cause the CrossDomainRSLItem class to be linked into this application.
    import mx.core.CrossDomainRSLItem; CrossDomainRSLItem;

    public function _vUpload_mx_managers_SystemManager()
    {
        FlexVersion.compatibilityVersionString = "4.0.0";

        super();
    }

    override     public function callInContext(fn:Function, thisArg:*, argsArray:*, returns:Boolean=true):*
    {
        if (returns)
           return fn.apply(thisArg, argsArray);
        else
           fn.apply(thisArg, argsArray);
    }

    public function createTextLine(textBlock:TextBlock,
                      previousLine:TextLine = null,
                      width:Number = 1000000,
                      lineOffset:Number = 0.0,
                      fitSomething:Boolean = false):TextLine
    {
        return textBlock.createTextLine(previousLine, width, lineOffset, fitSomething);
    }

    public function recreateTextLine(textBlock:TextBlock,
                      textLine:TextLine,
                      previousLine:TextLine = null,
                      width:Number = 1000000,
                      lineOffset:Number = 0.0,
                      fitSomething:Boolean = false):TextLine
    {
        var recreateTextLine:Function = textBlock["recreateTextLine"];
        if (recreateTextLine == null)
            return null;
        return recreateTextLine(textLine, previousLine, width, lineOffset, fitSomething);
    }

    override     public function create(... params):Object
    {
        if (params.length > 0 && !(params[0] is String))
            return super.create.apply(this, params);

        var mainClassName:String = params.length == 0 ? "vUpload" : String(params[0]);
        var mainClass:Class = Class(getDefinitionByName(mainClassName));
        if (!mainClass)
            return null;

        var instance:Object = new mainClass();
        if (instance is IFlexModule)
            (IFlexModule(instance)).moduleFactory = this;
        return instance;
    }

    override    public function info():Object
    {
        return {
            cdRsls: [{"rsls":["http://fpdownload.adobe.com/pub/swz/flex/4.0.0.10485/textLayout_4.0.0.10485.swz","textLayout_4.0.0.10485.swz"],
"policyFiles":["http://fpdownload.adobe.com/pub/swz/crossdomain.xml",""]
,"digests":["1c74d7ac0b79b5975af35ec0476a3613fa9317da5fcb8ff0837725bedb3dde1f","1c74d7ac0b79b5975af35ec0476a3613fa9317da5fcb8ff0837725bedb3dde1f"],
"types":["SHA-256","SHA-256"],
"isSigned":[true,true]
},
{"rsls":["http://fpdownload.adobe.com/pub/swz/flex/4.0.0.10485/framework_4.0.0.10485.swz","framework_4.0.0.10485.swz"],
"policyFiles":["http://fpdownload.adobe.com/pub/swz/crossdomain.xml",""]
,"digests":["b02defb39f361a7f2cff00ff37e8ae5f69bbd086b788bd195d0cf4e2c1edb51c","b02defb39f361a7f2cff00ff37e8ae5f69bbd086b788bd195d0cf4e2c1edb51c"],
"types":["SHA-256","SHA-256"],
"isSigned":[true,true]
},
{"rsls":["http://fpdownload.adobe.com/pub/swz/flex/4.0.0.10485/spark_4.0.0.10485.swz","spark_4.0.0.10485.swz"],
"policyFiles":["http://fpdownload.adobe.com/pub/swz/crossdomain.xml",""]
,"digests":["e4e7877a8240620f334a0b19069715ed843b26d422255f5bbc701654b6cdd83e","e4e7877a8240620f334a0b19069715ed843b26d422255f5bbc701654b6cdd83e"],
"types":["SHA-256","SHA-256"],
"isSigned":[true,true]
},
{"rsls":["http://fpdownload.adobe.com/pub/swz/flex/4.0.0.10485/rpc_4.0.0.10485.swz","rpc_4.0.0.10485.swz"],
"policyFiles":["http://fpdownload.adobe.com/pub/swz/crossdomain.xml",""]
,"digests":["22c5b7f88cba5cb4e15df3105e0d43964c518472800fdfa01601359dd8413d8a","22c5b7f88cba5cb4e15df3105e0d43964c518472800fdfa01601359dd8413d8a"],
"types":["SHA-256","SHA-256"],
"isSigned":[true,true]
}]
,
            compiledLocales: [ "en_US" ],
            compiledResourceBundleNames: [ "SharedResources", "automation", "automation_agent", "charts", "collections", "components", "containers", "controls", "core", "datamanagement", "effects", "formatters", "layout", "olap", "skins", "styles" ],
            currentDomain: ApplicationDomain.currentDomain,
            height: "150",
            horizontalAlign: "center",
            layout: "vertical",
            mainClassName: "vUpload",
            mixins: [ "_vUpload_FlexInit", "_mx_controls_RadioButtonStyle", "_mx_controls_ButtonBarStyle", "_mx_containers_PanelStyle", "_mx_controls_CheckBoxStyle", "_mx_controls_FormItemLabelStyle", "_mx_controls_listClasses_AdvancedListBaseStyle", "_mx_containers_FormStyle", "_mx_controls_ComboBoxStyle", "_mx_controls_ProgressBarStyle", "_mx_controls_TreeStyle", "_mx_controls_ColorPickerStyle", "_mx_controls_DataGridStyle", "_mx_controls_LinkBarStyle", "_mx_controls_PopUpButtonStyle", "_mx_core_ScrollControlBaseStyle", "_mx_core_ApplicationStyle", "_mx_controls_VideoDisplayStyle", "_mx_controls_buttonBarClasses_ButtonBarButtonStyle", "_mx_controls_sliderClasses_SliderStyle", "_mx_controls_DateFieldStyle", "_mx_controls_ToolTipStyle", "_mx_controls_advancedDataGridClasses_AdvancedDataGridHeaderRendererStyle", "_mx_controls_MenuStyle", "_mx_controls_AdvancedDataGridStyle", "_mx_controls_AlertStyle", "_mx_containers_accordionClasses_AccordionHeaderStyle", "_mx_controls_CalendarLayoutStyle", "_mx_controls_colorPickerClasses_SwatchPanelStyle", "_mx_controls_tabBarClasses_TabStyle", "_mx_controls_listClasses_ListBaseStyle", "_mx_controls_SWFLoaderStyle", "_mx_containers_TabNavigatorStyle", "_mx_controls_ButtonStyle", "_mx_controls_MenuBarStyle", "_mx_containers_ApplicationControlBarStyle", "_mx_controls_dataGridClasses_DataGridItemRendererStyle", "_mx_containers_AccordionStyle", "_mx_controls_LinkButtonStyle", "_mx_controls_TabBarStyle", "_globalStyle", "_mx_core_ContainerStyle", "_mx_managers_CursorManagerStyle", "_mx_controls_scrollClasses_ScrollBarStyle", "_mx_controls_TextInputStyle", "_mx_containers_DividedBoxStyle", "_mx_controls_NumericStepperStyle", "_mx_controls_advancedDataGridClasses_AdvancedDataGridSortItemRendererStyle", "_mx_managers_DragManagerStyle", "_mx_controls_TextAreaStyle", "_mx_containers_ControlBarStyle", "_mx_controls_advancedDataGridClasses_AdvancedDataGridItemRendererStyle", "_mx_containers_FormItemStyle", "_mx_controls_DateChooserStyle", "mx.managers.systemClasses.ActiveWindowManager", "mx.automation.delegates.charts.PieSeriesAutomationImpl", "mx.automation.delegates.containers.PanelAutomationImpl", "mx.automation.delegates.charts.BubbleSeriesAutomationImpl", "mx.automation.delegates.controls.SWFLoaderAutomationImpl", "mx.automation.delegates.controls.DateChooserAutomationImpl", "mx.automation.delegates.core.ScrollControlBaseAutomationImpl", "mx.automation.delegates.containers.ViewStackAutomationImpl", "mx.automation.delegates.controls.SliderAutomationImpl", "mx.automation.delegates.controls.ListItemRendererAutomationImpl", "mx.automation.delegates.controls.ComboBaseAutomationImpl", "mx.automation.delegates.controls.ToggleButtonBarAutomationImpl", "mx.automation.delegates.controls.ListBaseContentHolderAutomationImpl", "mx.automation.delegates.advancedDataGrid.AdvancedDataGridGroupItemRendererAutomationImpl", "mx.automation.delegates.controls.TreeItemRendererAutomationImpl", "mx.automation.delegates.controls.NavBarAutomationImpl", "mx.automation.delegates.controls.MenuItemRendererAutomationImpl", "mx.automation.delegates.advancedDataGrid.AdvancedDataGridBaseExAutomationImpl", "mx.automation.delegates.advancedDataGrid.OLAPDataGridAutomationImpl", "mx.automation.delegates.advancedDataGrid.OLAPDataGridGroupRendererAutomationImpl", "mx.automation.delegates.charts.BarSeriesAutomationImpl", "mx.automation.delegates.charts.PlotSeriesAutomationImpl", "mx.automation.delegates.charts.LegendAutomationImpl", "mx.automation.delegates.charts.LegendItemAutomationImpl", "mx.automation.AutomationManager", "mx.automation.delegates.controls.MenuBarItemAutomationImpl", "mx.automation.delegates.advancedDataGrid.AdvancedListBaseAutomationImpl", "mx.automation.delegates.controls.CheckBoxAutomationImpl", "mx.automation.delegates.core.UIComponentAutomationImpl", "mx.automation.delegates.charts.ColumnSeriesAutomationImpl", "mx.automation.delegates.containers.CanvasAutomationImpl", "mx.automation.delegates.charts.SeriesAutomationImpl", "mx.automation.delegates.core.RepeaterAutomationImpl", "mx.automation.delegates.DragManagerAutomationImpl", "mx.automation.delegates.controls.AlertAutomationImpl", "mx.automation.delegates.advancedDataGrid.AdvancedListBaseContentHolderAutomationImpl", "mx.automation.delegates.controls.ButtonAutomationImpl", "mx.automation.delegates.controls.TextInputAutomationImpl", "mx.automation.delegates.controls.DataGridItemRendererAutomationImpl", "mx.automation.delegates.controls.RadioButtonAutomationImpl", "mx.automation.delegates.controls.LinkBarAutomationImpl", "mx.automation.delegates.controls.ScrollBarAutomationImpl", "mx.automation.delegates.controls.TileListItemRendererAutomationImpl", "mx.automation.delegates.charts.AreaSeriesAutomationImpl", "mx.automation.delegates.controls.ComboBoxAutomationImpl", "mx.automation.delegates.charts.LineSeriesAutomationImpl", "mx.automation.qtp.QTPAgent", "mx.automation.delegates.controls.MenuAutomationImpl", "mx.automation.delegates.charts.ChartBaseAutomationImpl", "mx.automation.delegates.controls.DataGridAutomationImpl", "mx.automation.delegates.containers.FormItemAutomationImpl", "mx.automation.delegates.controls.ButtonBarAutomationImpl", "mx.automation.delegates.containers.AccordionAutomationImpl", "mx.automation.delegates.controls.VideoDisplayAutomationImpl", "mx.automation.delegates.advancedDataGrid.AdvancedDataGridAutomationImpl", "mx.automation.delegates.controls.ColorPickerAutomationImpl", "mx.automation.delegates.controls.NumericStepperAutomationImpl", "mx.automation.delegates.controls.ListBaseAutomationImpl", "mx.automation.delegates.controls.AlertFormAutomationImpl", "mx.automation.delegates.controls.ListAutomationImpl", "mx.automation.delegates.core.UITextFieldAutomationImpl", "mx.automation.delegates.charts.CartesianChartAutomationImpl", "mx.automation.delegates.containers.BoxAutomationImpl", "mx.automation.delegates.containers.DividedBoxAutomationImpl", "mx.automation.delegates.advancedDataGrid.AdvancedDataGridItemRendererAutomationImpl", "mx.automation.delegates.controls.PopUpButtonAutomationImpl", "mx.automation.delegates.containers.FormAutomationImpl", "mx.automation.delegates.controls.MenuBarAutomationImpl", "mx.automation.delegates.containers.ApplicationAutomationImpl", "mx.automation.delegates.containers.TabNavigatorAutomationImpl", "mx.automation.delegates.charts.HLOCSeriesBaseAutomationImpl", "mx.automation.delegates.controls.TileBaseAutomationImpl", "mx.automation.delegates.controls.ToolTipAutomationImpl", "mx.automation.delegates.core.ContainerAutomationImpl", "mx.automation.delegates.charts.AxisRendererAutomationImpl", "mx.automation.delegates.controls.TreeAutomationImpl", "mx.automation.delegates.controls.DateFieldAutomationImpl", "mx.automation.delegates.controls.ProgressBarAutomationImpl", "mx.automation.delegates.controls.TextAreaAutomationImpl", "mx.automation.delegates.controls.LabelAutomationImpl", "mx.managers.systemClasses.MarshallingSupport", "mx.managers.marshalClasses.ToolTipManagerMarshalMixin", "mx.managers.marshalClasses.PopUpManagerMarshalMixin", "mx.managers.marshalClasses.CursorManagerMarshalMixin", "mx.managers.marshalClasses.FocusManagerMarshalMixin", "mx.managers.marshalClasses.DragManagerMarshalMixin", "mx.messaging.config.LoaderConfig" ],
            paddingBottom: "0",
            paddingLeft: "0",
            paddingRight: "0",
            paddingTop: "0",
            preloader: SparkDownloadProgressBar,
            verticalAlign: "middle",
            width: "400"
        }
    }


    /**
     *  @private
     */
    private var _preloadedRSLs:Dictionary; // key: LoaderInfo, value: RSL URL

    /**
     *  The RSLs loaded by this system manager before the application
     *  starts. RSLs loaded by the application are not included in this list.
     */
    override     public function get preloadedRSLs():Dictionary
    {
        if (_preloadedRSLs == null)
           _preloadedRSLs = new Dictionary(true);
        return _preloadedRSLs;
    }

    /**
     *  Calls Security.allowDomain() for the SWF associated with this IFlexModuleFactory
     *  plus all the SWFs assocatiated with RSLs preLoaded by this IFlexModuleFactory.
     *
     */
    override     public function allowDomain(... domains):void
    {
        Security.allowDomain(domains);

        for (var loaderInfo:Object in _preloadedRSLs)
        {
            if (loaderInfo.content && ("allowDomainInRSL" in loaderInfo.content))
            {
                loaderInfo.content["allowDomainInRSL"](domains);
            }
        }
    }

    /**
     *  Calls Security.allowInsecureDomain() for the SWF associated with this IFlexModuleFactory
     *  plus all the SWFs assocatiated with RSLs preLoaded by this IFlexModuleFactory.
     *
     */
    override     public function allowInsecureDomain(... domains):void
    {
        Security.allowInsecureDomain(domains);

        for (var loaderInfo:Object in _preloadedRSLs)
        {
            if (loaderInfo.content && ("allowInsecureDomainInRSL" in loaderInfo.content))
            {
                loaderInfo.content["allowInsecureDomainInRSL"](domains);
            }
        }
    }


}

}
