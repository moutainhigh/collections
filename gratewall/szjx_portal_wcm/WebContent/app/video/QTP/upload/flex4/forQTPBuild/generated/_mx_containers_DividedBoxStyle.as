
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_containers_DividedBoxStyle
{
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.cursor.HBoxDivider', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='690', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf_mx_skins_cursor_HBoxDivider_456888573:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.BoxDividerSkin', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='688', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf_mx_skins_BoxDividerSkin_724516070:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='mx.skins.cursor.VBoxDivider', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='692', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf_mx_skins_cursor_VBoxDivider_373733061:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
        var conditions:Array = null;
        var condition:CSSCondition = null;
        var selector:CSSSelector = null;
        var style:CSSStyleDeclaration;
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.containers.DividedBox", conditions, selector);
        // mx.containers.DividedBox
        style = StyleManager.getStyleDeclaration("mx.containers.DividedBox");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.dividerThickness = 3;
                this.dividerColor = 0x6f7777;
                this.dividerAffordance = 6;
                this.verticalDividerCursor = _embed_css_Assets_swf_mx_skins_cursor_VBoxDivider_373733061;
                this.dividerSkin = _embed_css_Assets_swf_mx_skins_BoxDividerSkin_724516070;
                this.horizontalDividerCursor = _embed_css_Assets_swf_mx_skins_cursor_HBoxDivider_456888573;
                this.dividerAlpha = 0.75;
                this.verticalGap = 10;
                this.horizontalGap = 10;
            };
        }




    }
}

}
