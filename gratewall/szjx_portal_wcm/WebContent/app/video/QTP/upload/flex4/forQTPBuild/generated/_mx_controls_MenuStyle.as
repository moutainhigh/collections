
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.MenuCheckDisabled;
import mx.skins.spark.MenuRadioDisabled;
import mx.skins.spark.MenuArrowDisabled;
import mx.skins.halo.ListDropIndicator;
import mx.skins.spark.MenuArrow;
import mx.skins.spark.MenuCheck;
import mx.skins.spark.MenuRadio;
import mx.skins.spark.MenuSeparatorSkin;

[ExcludeClass]

public class _mx_controls_MenuStyle
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
        selector = new CSSSelector("mx.controls.Menu", conditions, selector);
        // mx.controls.Menu
        style = StyleManager.getStyleDeclaration("mx.controls.Menu");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.radioIcon = mx.skins.spark.MenuRadio;
                this.borderStyle = "menuBorder";
                this.paddingTop = 1;
                this.rightIconGap = 15;
                this.branchIcon = mx.skins.spark.MenuArrow;
                this.checkDisabledIcon = mx.skins.spark.MenuCheckDisabled;
                this.verticalAlign = "middle";
                this.backgroundAlpha = 0.95;
                this.paddingLeft = 1;
                this.paddingRight = 0;
                this.checkIcon = mx.skins.spark.MenuCheck;
                this.radioDisabledIcon = mx.skins.spark.MenuRadioDisabled;
                this.dropShadowVisible = true;
                this.branchDisabledIcon = mx.skins.spark.MenuArrowDisabled;
                this.dropIndicatorSkin = mx.skins.halo.ListDropIndicator;
                this.separatorSkin = mx.skins.spark.MenuSeparatorSkin;
                this.horizontalGap = 6;
                this.leftIconGap = 18;
                this.paddingBottom = 1;
            };
        }




    }
}

}
