
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.ColorPickerSkin;

[ExcludeClass]

public class _mx_controls_ColorPickerStyle
{

    public static function init(fbs:IFlexModuleFactory):void
    {
        var conditions:Array = null;
        var condition:CSSCondition = null;
        var selector:CSSSelector = null;
        var style:CSSStyleDeclaration;
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.ColorPicker", conditions, selector);
        // mx.controls.ColorPicker
        style = StyleManager.getStyleDeclaration("mx.controls.ColorPicker");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.iconColor = 0x000000;
                this.fontSize = 11;
                this.verticalGap = 0;
                this.shadowColor = 0x4d555e;
                this.skin = mx.skins.spark.ColorPickerSkin;
                this.swatchBorderSize = 0;
            };
        }




    }
}

}
