
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.ProgressIndeterminateSkin;
import mx.skins.spark.ProgressMaskSkin;
import mx.skins.spark.ProgressBarTrackSkin;
import mx.skins.spark.ProgressBarSkin;

[ExcludeClass]

public class _mx_controls_ProgressBarStyle
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
        selector = new CSSSelector("mx.controls.ProgressBar", conditions, selector);
        // mx.controls.ProgressBar
        style = StyleManager.getStyleDeclaration("mx.controls.ProgressBar");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
                this.trackColors = [0xe7e7e7, 0xffffff];
                this.leading = 0;
                this.barSkin = mx.skins.spark.ProgressBarSkin;
                this.trackSkin = mx.skins.spark.ProgressBarTrackSkin;
                this.indeterminateMoveInterval = 14;
                this.maskSkin = mx.skins.spark.ProgressMaskSkin;
                this.indeterminateSkin = mx.skins.spark.ProgressIndeterminateSkin;
            };
        }




    }
}

}
