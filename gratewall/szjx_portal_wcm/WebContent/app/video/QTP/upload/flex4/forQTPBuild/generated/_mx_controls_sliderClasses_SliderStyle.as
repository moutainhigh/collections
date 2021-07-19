
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.SliderTrackHighlightSkin;
import mx.skins.spark.SliderThumbSkin;
import mx.skins.spark.SliderTrackSkin;

[ExcludeClass]

public class _mx_controls_sliderClasses_SliderStyle
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
        selector = new CSSSelector("mx.controls.sliderClasses.Slider", conditions, selector);
        // mx.controls.sliderClasses.Slider
        style = StyleManager.getStyleDeclaration("mx.controls.sliderClasses.Slider");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderColor = 0x919999;
                this.tickColor = 0x6f7777;
                this.trackHighlightSkin = mx.skins.spark.SliderTrackHighlightSkin;
                this.tickThickness = 1;
                this.showTrackHighlight = false;
                this.thumbSkin = mx.skins.spark.SliderThumbSkin;
                this.thumbOffset = 0;
                this.slideDuration = 300;
                this.labelOffset = -10;
                this.trackColors = [0xe7e7e7, 0xe7e7e7];
                this.dataTipOffset = 16;
                this.trackSkin = mx.skins.spark.SliderTrackSkin;
                this.dataTipPrecision = 2;
                this.tickOffset = -6;
            };
        }




    }
}

}
