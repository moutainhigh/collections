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

public class _vMultiUpload_mx_managers_SystemManager
    extends mx.managers.SystemManager
    implements IFlexModuleFactory, ITextLineCreator
{
    // Cause the CrossDomainRSLItem class to be linked into this application.
    import mx.core.CrossDomainRSLItem; CrossDomainRSLItem;

    public function _vMultiUpload_mx_managers_SystemManager()
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

        var mainClassName:String = params.length == 0 ? "vMultiUpload" : String(params[0]);
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
            backgroundColor: "0xC0C0C0",
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
            compiledResourceBundleNames: [ "collections", "components", "containers", "controls", "core", "effects", "layout", "skins", "styles" ],
            creationComplete: "init();",
            currentDomain: ApplicationDomain.currentDomain,
            height: "236",
            layout: "absolute",
            mainClassName: "vMultiUpload",
            mixins: [ "_vMultiUpload_FlexInit", "_mx_controls_ToolTipStyle", "_mx_controls_ButtonStyle", "_mx_containers_PanelStyle", "_mx_controls_ProgressBarStyle", "_mx_controls_AlertStyle", "_mx_controls_dataGridClasses_DataGridItemRendererStyle", "_mx_managers_DragManagerStyle", "_mx_controls_DataGridStyle", "_mx_core_ScrollControlBaseStyle", "_mx_containers_ControlBarStyle", "_globalStyle", "_mx_core_ContainerStyle", "_mx_core_ApplicationStyle", "_mx_controls_listClasses_ListBaseStyle", "_mx_controls_scrollClasses_ScrollBarStyle", "_mx_managers_CursorManagerStyle", "_mx_controls_TextInputStyle", "mx.managers.systemClasses.ActiveWindowManager" ],
            preloader: SparkDownloadProgressBar,
            width: "462"
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
