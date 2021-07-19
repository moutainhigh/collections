
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.HaloBorder;

[ExcludeClass]

public class _mx_containers_ApplicationControlBarStyle
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
        selector = new CSSSelector("mx.containers.ApplicationControlBar", conditions, selector);
        // mx.containers.ApplicationControlBar
        style = StyleManager.getStyleDeclaration("mx.containers.ApplicationControlBar");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "applicationControlBar";
                this.paddingTop = 5;
                this.docked = false;
                this.dropShadowEnabled = true;
                this.shadowDistance = 5;
                this.borderSkin = mx.skins.halo.HaloBorder;
                this.cornerRadius = 5;
                this.fillColors = [0xffffff, 0xffffff];
                this.fillAlphas = [0, 0];
                this.paddingLeft = 8;
                this.paddingBottom = 4;
                this.paddingRight = 8;
            };
        }




    }
}

}
