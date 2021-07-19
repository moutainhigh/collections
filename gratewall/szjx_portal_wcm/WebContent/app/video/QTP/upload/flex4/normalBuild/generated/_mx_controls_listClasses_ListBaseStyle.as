
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.ListDropIndicator;

[ExcludeClass]

public class _mx_controls_listClasses_ListBaseStyle
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
        selector = new CSSSelector("mx.controls.listClasses.ListBase", conditions, selector);
        // mx.controls.listClasses.ListBase
        style = StyleManager.getStyleDeclaration("mx.controls.listClasses.ListBase");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "solid";
                this.paddingTop = 2;
                this.backgroundColor = 0xffffff;
                this.backgroundDisabledColor = 0xdddddd;
                this.dropIndicatorSkin = mx.skins.halo.ListDropIndicator;
                this.paddingLeft = 2;
                this.paddingBottom = 2;
                this.paddingRight = 0;
            };
        }




    }
}

}
