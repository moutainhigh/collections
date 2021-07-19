
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.DefaultButtonSkin;
import mx.skins.spark.ButtonSkin;

[ExcludeClass]

public class _mx_controls_ButtonStyle
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
        selector = new CSSSelector("mx.controls.Button", conditions, selector);
        // mx.controls.Button
        style = StyleManager.getStyleDeclaration("mx.controls.Button");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.textAlign = "center";
                this.labelVerticalOffset = 1;
                this.emphasizedSkin = mx.skins.spark.DefaultButtonSkin;
                this.verticalGap = 2;
                this.horizontalGap = 2;
                this.skin = mx.skins.spark.ButtonSkin;
                this.paddingLeft = 6;
                this.paddingRight = 6;
            };
        }




    }
}

}
