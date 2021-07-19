
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.EditableComboBoxSkin;
import mx.skins.spark.ComboBoxSkin;

[ExcludeClass]

public class _mx_controls_ComboBoxStyle
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
        selector = new CSSSelector("mx.controls.ComboBox", conditions, selector);
        // mx.controls.ComboBox
        style = StyleManager.getStyleDeclaration("mx.controls.ComboBox");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.disabledIconColor = 0x919999;
                this.paddingTop = -1;
                this.dropdownStyleName = "comboDropdown";
                this.leading = 0;
                this.arrowButtonWidth = 18;
                this.editableSkin = mx.skins.spark.EditableComboBoxSkin;
                this.skin = mx.skins.spark.ComboBoxSkin;
                this.paddingLeft = 5;
                this.paddingBottom = -2;
                this.paddingRight = 5;
            };
        }




    }
}

}
