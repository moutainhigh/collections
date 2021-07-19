
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.TextInputBorderSkin;

[ExcludeClass]

public class _mx_controls_TextInputStyle
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
        selector = new CSSSelector("mx.controls.TextInput", conditions, selector);
        // mx.controls.TextInput
        style = StyleManager.getStyleDeclaration("mx.controls.TextInput");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 2;
                this.backgroundColor = 0xffffff;
                this.borderSkin = mx.skins.spark.TextInputBorderSkin;
                this.backgroundDisabledColor = 0xdddddd;
                this.paddingLeft = 2;
                this.paddingRight = 2;
            };
        }




    }
}

}
