
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.MenuItemSkin;
import mx.skins.spark.ButtonSkin;

[ExcludeClass]

public class _mx_controls_MenuBarStyle
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
        selector = new CSSSelector("mx.controls.MenuBar", conditions, selector);
        // mx.controls.MenuBar
        style = StyleManager.getStyleDeclaration("mx.controls.MenuBar");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.itemSkin = mx.skins.spark.MenuItemSkin;
                this.backgroundSkin = mx.skins.spark.ButtonSkin;
                this.translucent = false;
            };
        }




    }
}

}
