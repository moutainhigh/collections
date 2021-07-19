
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.ContainerBorderSkin;

[ExcludeClass]

public class _mx_core_ContainerStyle
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
        selector = new CSSSelector("mx.core.Container", conditions, selector);
        // mx.core.Container
        style = StyleManager.getStyleDeclaration("mx.core.Container");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "none";
                this.borderSkin = mx.skins.spark.ContainerBorderSkin;
                this.cornerRadius = 0;
            };
        }




    }
}

}
