
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_containers_TabNavigatorStyle
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
        selector = new CSSSelector("mx.containers.TabNavigator", conditions, selector);
        // mx.containers.TabNavigator
        style = StyleManager.getStyleDeclaration("mx.containers.TabNavigator");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "solid";
                this.paddingTop = 10;
                this.borderColor = 0x696969;
                this.backgroundColor = 0xffffff;
                this.horizontalAlign = "left";
                this.horizontalGap = -1;
                this.tabOffset = 0;
            };
        }




    }
}

}
