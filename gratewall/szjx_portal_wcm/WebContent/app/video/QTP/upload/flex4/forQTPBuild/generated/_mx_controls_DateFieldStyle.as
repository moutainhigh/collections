
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_controls_DateFieldStyle
{
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$assets/CalendarIcon.png', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$assets/CalendarIcon.png', original='assets/CalendarIcon.png', _line='661', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_assets_CalendarIcon_png_428564250:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
        var conditions:Array = null;
        var condition:CSSCondition = null;
        var selector:CSSSelector = null;
        var style:CSSStyleDeclaration;
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.DateField", conditions, selector);
        // mx.controls.DateField
        style = StyleManager.getStyleDeclaration("mx.controls.DateField");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.upSkin = _embed_css_assets_CalendarIcon_png_428564250;
                this.overSkin = _embed_css_assets_CalendarIcon_png_428564250;
                this.downSkin = _embed_css_assets_CalendarIcon_png_428564250;
                this.dateChooserStyleName = "dateFieldPopup";
                this.disabledSkin = _embed_css_assets_CalendarIcon_png_428564250;
            };
        }




    }
}

}
