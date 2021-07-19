
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.DataGridSortArrow;

[ExcludeClass]

public class _mx_controls_advancedDataGridClasses_AdvancedDataGridSortItemRendererStyle
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
        selector = new CSSSelector("mx.controls.advancedDataGridClasses.AdvancedDataGridSortItemRenderer", conditions, selector);
        // mx.controls.advancedDataGridClasses.AdvancedDataGridSortItemRenderer
        style = StyleManager.getStyleDeclaration("mx.controls.advancedDataGridClasses.AdvancedDataGridSortItemRenderer");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.icon = mx.skins.halo.DataGridSortArrow;
                this.paddingTop = 0;
                this.color = 0x0b333c;
                this.horizontalGap = 0;
                this.paddingLeft = 0;
                this.paddingBottom = 0;
                this.paddingRight = 0;
            };
        }




    }
}

}
