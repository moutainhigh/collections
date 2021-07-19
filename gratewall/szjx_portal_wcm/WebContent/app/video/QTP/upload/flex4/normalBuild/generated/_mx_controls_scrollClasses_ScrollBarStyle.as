
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.ScrollBarThumbSkin;
import mx.skins.spark.ScrollBarDownButtonSkin;
import mx.skins.spark.ScrollBarUpButtonSkin;
import mx.skins.spark.ScrollBarTrackSkin;

[ExcludeClass]

public class _mx_controls_scrollClasses_ScrollBarStyle
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
        selector = new CSSSelector("mx.controls.scrollClasses.ScrollBar", conditions, selector);
        // mx.controls.scrollClasses.ScrollBar
        style = StyleManager.getStyleDeclaration("mx.controls.scrollClasses.ScrollBar");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.thumbOffset = 0;
                this.paddingTop = 0;
                this.borderColor = 0xb7babc;
                this.trackColors = [0x94999b, 0xe7e7e7];
                this.trackSkin = mx.skins.spark.ScrollBarTrackSkin;
                this.downArrowSkin = mx.skins.spark.ScrollBarDownButtonSkin;
                this.upArrowSkin = mx.skins.spark.ScrollBarUpButtonSkin;
                this.paddingLeft = 0;
                this.paddingBottom = 0;
                this.thumbSkin = mx.skins.spark.ScrollBarThumbSkin;
                this.paddingRight = 0;
            };
        }




    }
}

}
