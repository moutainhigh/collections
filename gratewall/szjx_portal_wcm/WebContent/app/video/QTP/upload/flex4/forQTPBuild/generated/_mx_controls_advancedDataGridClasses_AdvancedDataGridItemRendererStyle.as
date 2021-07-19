
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_controls_advancedDataGridClasses_AdvancedDataGridItemRendererStyle
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
        selector = new CSSSelector("mx.controls.advancedDataGridClasses.AdvancedDataGridItemRenderer", conditions, selector);
        // mx.controls.advancedDataGridClasses.AdvancedDataGridItemRenderer
        style = StyleManager.getStyleDeclaration("mx.controls.advancedDataGridClasses.AdvancedDataGridItemRenderer");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingLeft = 5;
            };
        }




    }
}

}
