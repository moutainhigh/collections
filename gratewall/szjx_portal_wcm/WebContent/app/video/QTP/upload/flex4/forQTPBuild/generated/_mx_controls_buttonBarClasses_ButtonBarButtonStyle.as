
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.ButtonBarFirstButtonSkin;
import mx.skins.spark.ButtonBarLastButtonSkin;
import mx.skins.spark.ButtonBarMiddleButtonSkin;

[ExcludeClass]

public class _mx_controls_buttonBarClasses_ButtonBarButtonStyle
{

    public static function init(fbs:IFlexModuleFactory):void
    {
        var conditions:Array = null;
        var condition:CSSCondition = null;
        var selector:CSSSelector = null;
        var style:CSSStyleDeclaration;
        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "buttonBarFirstButtonStyle");
        conditions.push(condition); 
        selector = new CSSSelector("mx.controls.buttonBarClasses.ButtonBarButton", conditions, selector);
        // mx.controls.buttonBarClasses.ButtonBarButton.buttonBarFirstButtonStyle
        style = StyleManager.getStyleDeclaration("mx.controls.buttonBarClasses.ButtonBarButton.buttonBarFirstButtonStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.skin = mx.skins.spark.ButtonBarFirstButtonSkin;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "buttonBarLastButtonStyle");
        conditions.push(condition); 
        selector = new CSSSelector("mx.controls.buttonBarClasses.ButtonBarButton", conditions, selector);
        // mx.controls.buttonBarClasses.ButtonBarButton.buttonBarLastButtonStyle
        style = StyleManager.getStyleDeclaration("mx.controls.buttonBarClasses.ButtonBarButton.buttonBarLastButtonStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.skin = mx.skins.spark.ButtonBarLastButtonSkin;
            };
        }


        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.buttonBarClasses.ButtonBarButton", conditions, selector);
        // mx.controls.buttonBarClasses.ButtonBarButton
        style = StyleManager.getStyleDeclaration("mx.controls.buttonBarClasses.ButtonBarButton");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.upSkin = null;
                this.selectedDownSkin = null;
                this.overSkin = null;
                this.downSkin = null;
                this.selectedDisabledSkin = null;
                this.selectedUpSkin = null;
                this.disabledSkin = null;
                this.horizontalGap = 1;
                this.skin = mx.skins.spark.ButtonBarMiddleButtonSkin;
                this.selectedOverSkin = null;
            };
        }




    }
}

}
