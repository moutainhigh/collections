
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_controls_advancedDataGridClasses_AdvancedDataGridHeaderRendererStyle
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
        selector = new CSSSelector("mx.controls.advancedDataGridClasses.AdvancedDataGridHeaderRenderer", conditions, selector);
        // mx.controls.advancedDataGridClasses.AdvancedDataGridHeaderRenderer
        style = StyleManager.getStyleDeclaration("mx.controls.advancedDataGridClasses.AdvancedDataGridHeaderRenderer");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 0;
                this.backgroundColor = 0xffffff;
                this.color = 0x0b333c;
                this.horizontalAlign = "center";
                this.verticalAlign = "middle";
                this.horizontalGap = 0;
                this.backgroundAlpha = 0.0;
                this.paddingLeft = 2;
                this.paddingBottom = 0;
                this.paddingRight = 2;
            };
        }




    }
}

}
