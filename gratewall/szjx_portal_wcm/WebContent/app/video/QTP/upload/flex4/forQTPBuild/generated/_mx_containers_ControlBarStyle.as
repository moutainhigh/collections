
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_containers_ControlBarStyle
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
        selector = new CSSSelector("mx.containers.ControlBar", conditions, selector);
        // mx.containers.ControlBar
        style = StyleManager.getStyleDeclaration("mx.containers.ControlBar");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.disabledOverlayAlpha = 0;
                this.borderStyle = "none";
                this.paddingTop = 11;
                this.verticalAlign = "middle";
                this.paddingLeft = 11;
                this.paddingBottom = 11;
                this.paddingRight = 11;
            };
        }




    }
}

}
