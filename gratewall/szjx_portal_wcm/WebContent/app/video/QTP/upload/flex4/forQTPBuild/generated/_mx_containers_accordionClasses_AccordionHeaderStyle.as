
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.AccordionHeaderSkin;

[ExcludeClass]

public class _mx_containers_accordionClasses_AccordionHeaderStyle
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
        selector = new CSSSelector("mx.containers.accordionClasses.AccordionHeader", conditions, selector);
        // mx.containers.accordionClasses.AccordionHeader
        style = StyleManager.getStyleDeclaration("mx.containers.accordionClasses.AccordionHeader");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.downSkin = null;
                this.overSkin = null;
                this.paddingTop = 0;
                this.selectedDisabledSkin = null;
                this.selectedUpSkin = null;
                this.paddingLeft = 5;
                this.skin = mx.skins.spark.AccordionHeaderSkin;
                this.paddingRight = 5;
                this.upSkin = null;
                this.selectedDownSkin = null;
                this.textAlign = "left";
                this.disabledSkin = null;
                this.horizontalGap = 2;
                this.paddingBottom = 0;
                this.selectedOverSkin = null;
            };
        }




    }
}

}
