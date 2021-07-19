
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.BorderSkin;

[ExcludeClass]

public class _mx_containers_AccordionStyle
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
        selector = new CSSSelector("mx.containers.Accordion", conditions, selector);
        // mx.containers.Accordion
        style = StyleManager.getStyleDeclaration("mx.containers.Accordion");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "solid";
                this.paddingTop = -1;
                this.backgroundColor = 0xffffff;
                this.borderSkin = mx.skins.spark.BorderSkin;
                this.verticalGap = -1;
                this.paddingLeft = -1;
                this.paddingBottom = -1;
                this.paddingRight = -1;
            };
        }




    }
}

}
