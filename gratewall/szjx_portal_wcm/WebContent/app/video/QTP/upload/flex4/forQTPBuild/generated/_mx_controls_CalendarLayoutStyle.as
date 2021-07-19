
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_controls_CalendarLayoutStyle
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
        selector = new CSSSelector("mx.controls.CalendarLayout", conditions, selector);
        // mx.controls.CalendarLayout
        style = StyleManager.getStyleDeclaration("mx.controls.CalendarLayout");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 6;
                this.todayColor = 0x818181;
                this.color = 0x2b333c;
                this.cellHeightPadding = 6;
                this.textAlign = "center";
                this.verticalGap = 6;
                this.horizontalGap = 7;
                this.cellWidthPadding = 6;
                this.paddingLeft = 6;
                this.paddingBottom = 10;
                this.paddingRight = 6;
            };
        }




    }
}

}
