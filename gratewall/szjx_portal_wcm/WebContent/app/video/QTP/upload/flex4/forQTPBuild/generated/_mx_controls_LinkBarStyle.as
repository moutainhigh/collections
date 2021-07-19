
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.BorderSkin;
import mx.skins.halo.LinkSeparator;

[ExcludeClass]

public class _mx_controls_LinkBarStyle
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
        selector = new CSSSelector("mx.controls.LinkBar", conditions, selector);
        // mx.controls.LinkBar
        style = StyleManager.getStyleDeclaration("mx.controls.LinkBar");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 2;
                this.borderSkin = mx.skins.spark.BorderSkin;
                this.linkButtonStyleName = "linkButtonStyle";
                this.separatorColor = 0xc4cccc;
                this.separatorWidth = 1;
                this.verticalGap = 8;
                this.separatorSkin = mx.skins.halo.LinkSeparator;
                this.horizontalGap = 8;
                this.paddingLeft = 2;
                this.paddingBottom = 2;
                this.paddingRight = 2;
            };
        }




    }
}

}
