
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.BorderSkin;

[ExcludeClass]

public class _mx_controls_ButtonBarStyle
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
        selector = new CSSSelector("mx.controls.ButtonBar", conditions, selector);
        // mx.controls.ButtonBar
        style = StyleManager.getStyleDeclaration("mx.controls.ButtonBar");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.lastButtonStyleName = "buttonBarLastButtonStyle";
                this.borderSkin = mx.skins.spark.BorderSkin;
                this.firstButtonStyleName = "buttonBarFirstButtonStyle";
                this.textAlign = "center";
                this.horizontalAlign = "center";
                this.verticalAlign = "middle";
                this.verticalGap = 0;
                this.horizontalGap = -1;
            };
        }




    }
}

}
