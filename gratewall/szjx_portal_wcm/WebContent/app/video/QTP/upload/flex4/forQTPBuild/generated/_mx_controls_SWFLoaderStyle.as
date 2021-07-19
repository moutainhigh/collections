
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.BrokenImageBorderSkin;

[ExcludeClass]

public class _mx_controls_SWFLoaderStyle
{
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='__brokenImage', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1412', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf___brokenImage_1450342906:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
        var conditions:Array = null;
        var condition:CSSCondition = null;
        var selector:CSSSelector = null;
        var style:CSSStyleDeclaration;
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.SWFLoader", conditions, selector);
        // mx.controls.SWFLoader
        style = StyleManager.getStyleDeclaration("mx.controls.SWFLoader");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "none";
                this.brokenImageSkin = _embed_css_Assets_swf___brokenImage_1450342906;
                this.brokenImageBorderSkin = mx.skins.halo.BrokenImageBorderSkin;
            };
        }




    }
}

}
