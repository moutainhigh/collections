
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_controls_colorPickerClasses_SwatchPanelStyle
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
        selector = new CSSSelector("mx.controls.colorPickerClasses.SwatchPanel", conditions, selector);
        // mx.controls.colorPickerClasses.SwatchPanel
        style = StyleManager.getStyleDeclaration("mx.controls.colorPickerClasses.SwatchPanel");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.swatchGridBackgroundColor = 0x000000;
                this.previewHeight = 22;
                this.borderColor = 0xa5a9ae;
                this.paddingTop = 4;
                this.swatchWidth = 12;
                this.backgroundColor = 0xe5e6e7;
                this.highlightColor = 0xffffff;
                this.textFieldStyleName = "swatchPanelTextField";
                this.swatchHighlightSize = 1;
                this.swatchHeight = 12;
                this.fontSize = 11;
                this.previewWidth = 45;
                this.verticalGap = 0;
                this.shadowColor = 0x4d555e;
                this.paddingLeft = 5;
                this.swatchBorderSize = 1;
                this.paddingRight = 5;
                this.swatchBorderColor = 0x000000;
                this.swatchGridBorderSize = 0;
                this.columnCount = 20;
                this.textFieldWidth = 72;
                this.swatchHighlightColor = 0xffffff;
                this.horizontalGap = 0;
                this.paddingBottom = 5;
            };
        }




    }
}

}
