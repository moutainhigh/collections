
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.StepperIncrButtonSkin;
import mx.skins.spark.StepperDecrButtonSkin;

[ExcludeClass]

public class _mx_controls_NumericStepperStyle
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
        selector = new CSSSelector("mx.controls.NumericStepper", conditions, selector);
        // mx.controls.NumericStepper
        style = StyleManager.getStyleDeclaration("mx.controls.NumericStepper");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.downArrowSkin = mx.skins.spark.StepperDecrButtonSkin;
                this.upArrowSkin = mx.skins.spark.StepperIncrButtonSkin;
                this.focusRoundedCorners = "tr br";
            };
        }




    }
}

}
