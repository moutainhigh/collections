
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.PanelBorderSkin;
import mx.core.UIComponent;

[ExcludeClass]

public class _mx_containers_PanelStyle
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
        selector = new CSSSelector("mx.containers.Panel", conditions, selector);
        // mx.containers.Panel
        style = StyleManager.getStyleDeclaration("mx.containers.Panel");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.statusStyleName = "windowStatus";
                this.borderStyle = "default";
                this.paddingTop = 0;
                this.borderColor = 0;
                this.backgroundColor = 0xffffff;
                this.cornerRadius = 0;
                this.titleBackgroundSkin = mx.core.UIComponent;
                this.borderAlpha = 0.5;
                this.borderThicknessTop = 2;
                this.paddingLeft = 0;
                this.resizeEndEffect = "Dissolve";
                this.paddingRight = 0;
                this.titleStyleName = "windowStyles";
                this.roundedBottomCorners = false;
                this.borderThicknessRight = 10;
                this.resizeStartEffect = "Dissolve";
                this.dropShadowVisible = true;
                this.borderSkin = mx.skins.spark.PanelBorderSkin;
                this.borderThickness = 0;
                this.borderThicknessLeft = 10;
                this.paddingBottom = 0;
            };
        }

        var effects:Array = style.mx_internal::effects;
        if (!effects)
        {
            effects = style.mx_internal::effects = [];
        }
        effects.push("resizeEndEffect");
        effects.push("resizeStartEffect");



    }
}

}
