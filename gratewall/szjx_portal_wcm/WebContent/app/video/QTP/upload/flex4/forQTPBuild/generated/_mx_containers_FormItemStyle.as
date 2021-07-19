
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_containers_FormItemStyle
{
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.containers.FormItem.Required', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='768', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf_mx_containers_FormItem_Required_276317009:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
        var conditions:Array = null;
        var condition:CSSCondition = null;
        var selector:CSSSelector = null;
        var style:CSSStyleDeclaration;
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.FormItem", conditions, selector);
        // mx.containers.FormItem
        style = StyleManager.getStyleDeclaration("mx.containers.FormItem");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.indicatorSkin = _embed_css_Assets_swf_mx_containers_FormItem_Required_276317009;
            };
        }




    }
}

}
