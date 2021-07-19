
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.ApplicationBackground;

[ExcludeClass]

public class _mx_core_ApplicationStyle
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
        selector = new CSSSelector("mx.core.Application", conditions, selector);
        // mx.core.Application
        style = StyleManager.getStyleDeclaration("mx.core.Application");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.backgroundSize = "100%";
                this.paddingTop = 24;
                this.backgroundColor = 0xffffff;
                this.backgroundImage = mx.skins.spark.ApplicationBackground;
                this.horizontalAlign = "center";
                this.backgroundGradientAlphas = [1, 1];
                this.paddingLeft = 24;
                this.paddingBottom = 24;
                this.paddingRight = 24;
            };
        }




    }
}

}
