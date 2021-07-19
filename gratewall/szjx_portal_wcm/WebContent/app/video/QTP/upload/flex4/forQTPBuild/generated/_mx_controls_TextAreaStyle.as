
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_controls_TextAreaStyle
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
        selector = new CSSSelector("mx.controls.TextArea", conditions, selector);
        // mx.controls.TextArea
        style = StyleManager.getStyleDeclaration("mx.controls.TextArea");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "solid";
                this.verticalScrollBarStyleName = "textAreaVScrollBarStyle";
                this.backgroundColor = 0xffffff;
                this.horizontalScrollBarStyleName = "textAreaHScrollBarStyle";
                this.backgroundDisabledColor = 0xdddddd;
            };
        }




    }
}

}
