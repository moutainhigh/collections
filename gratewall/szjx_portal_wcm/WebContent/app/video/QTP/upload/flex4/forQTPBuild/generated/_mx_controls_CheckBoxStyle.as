
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.CheckBoxSkin;

[ExcludeClass]

public class _mx_controls_CheckBoxStyle
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
        selector = new CSSSelector("mx.controls.CheckBox", conditions, selector);
        // mx.controls.CheckBox
        style = StyleManager.getStyleDeclaration("mx.controls.CheckBox");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.downSkin = null;
                this.iconColor = 0x2b333c;
                this.cornerRadius = 0;
                this.selectedDownIcon = null;
                this.selectedUpSkin = null;
                this.overIcon = null;
                this.skin = null;
                this.upSkin = null;
                this.selectedDownSkin = null;
                this.selectedOverIcon = null;
                this.selectedDisabledIcon = null;
                this.textAlign = "left";
                this.horizontalGap = 3;
                this.paddingBottom = -1;
                this.downIcon = null;
                this.icon = mx.skins.spark.CheckBoxSkin;
                this.overSkin = null;
                this.disabledIcon = null;
                this.paddingTop = -1;
                this.selectedDisabledSkin = null;
                this.upIcon = null;
                this.paddingLeft = 0;
                this.paddingRight = 0;
                this.fontWeight = "normal";
                this.selectedUpIcon = null;
                this.labelVerticalOffset = 1;
                this.disabledSkin = null;
                this.selectedOverSkin = null;
            };
        }




    }
}

}
