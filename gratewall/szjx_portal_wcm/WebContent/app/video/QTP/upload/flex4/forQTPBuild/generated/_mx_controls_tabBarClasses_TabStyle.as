
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.TabSkin;

[ExcludeClass]

public class _mx_controls_tabBarClasses_TabStyle
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
        selector = new CSSSelector("mx.controls.tabBarClasses.Tab", conditions, selector);
        // mx.controls.tabBarClasses.Tab
        style = StyleManager.getStyleDeclaration("mx.controls.tabBarClasses.Tab");
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
                this.paddingTop = 1;
                this.selectedUpSkin = null;
                this.disabledSkin = null;
                this.skin = mx.skins.spark.TabSkin;
                this.paddingBottom = 1;
                this.selectedOverSkin = null;
            };
        }




    }
}

}
