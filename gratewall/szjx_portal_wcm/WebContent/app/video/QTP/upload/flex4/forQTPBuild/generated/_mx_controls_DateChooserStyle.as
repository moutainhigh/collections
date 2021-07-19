
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.DateChooserPrevMonthSkin;
import mx.skins.spark.DateChooserPrevYearSkin;
import mx.skins.spark.DateChooserTodayIndicatorSkin;
import mx.skins.spark.DateChooserRollOverIndicatorSkin;
import mx.skins.spark.DateChooserNextYearSkin;
import mx.skins.spark.DateChooserSelectionIndicatorSkin;
import mx.skins.spark.DateChooserNextMonthSkin;

[ExcludeClass]

public class _mx_controls_DateChooserStyle
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
        selector = new CSSSelector("mx.controls.DateChooser", conditions, selector);
        // mx.controls.DateChooser
        style = StyleManager.getStyleDeclaration("mx.controls.DateChooser");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.nextYearSkin = mx.skins.spark.DateChooserNextYearSkin;
                this.backgroundColor = 0xffffff;
                this.todayStyleName = "todayStyle";
                this.rollOverIndicatorSkin = mx.skins.spark.DateChooserRollOverIndicatorSkin;
                this.cornerRadius = 0;
                this.nextMonthSkin = mx.skins.spark.DateChooserNextMonthSkin;
                this.selectionIndicatorSkin = mx.skins.spark.DateChooserSelectionIndicatorSkin;
                this.prevMonthSkin = mx.skins.spark.DateChooserPrevMonthSkin;
                this.todayColor = 0xc6d0db;
                this.weekDayStyleName = "weekDayStyle";
                this.headerColors = [0xffffff, 0xd8d8d8];
                this.todayIndicatorSkin = mx.skins.spark.DateChooserTodayIndicatorSkin;
                this.prevYearSkin = mx.skins.spark.DateChooserPrevYearSkin;
            };
        }




    }
}

}
