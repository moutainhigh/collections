
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.PopUpButtonSkin;
import mx.skins.halo.PopUpIcon;

[ExcludeClass]

public class _mx_controls_PopUpButtonStyle
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
        selector = new CSSSelector("mx.controls.PopUpButton", conditions, selector);
        // mx.controls.PopUpButton
        style = StyleManager.getStyleDeclaration("mx.controls.PopUpButton");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.popUpGap = 0;
                this.arrowButtonWidth = 18;
                this.popUpIcon = mx.skins.halo.PopUpIcon;
                this.skin = mx.skins.spark.PopUpButtonSkin;
                this.paddingLeft = 3;
                this.paddingRight = 3;
            };
        }




    }
}

}
