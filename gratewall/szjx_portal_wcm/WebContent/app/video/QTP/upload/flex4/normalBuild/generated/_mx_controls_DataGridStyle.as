
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.DataGridColumnDropIndicator;
import mx.skins.spark.DataGridHeaderBackgroundSkin;
import mx.skins.spark.DataGridHeaderSeparatorSkin;
import mx.skins.halo.DataGridColumnResizeSkin;
import mx.skins.spark.DataGridSortArrow;

[ExcludeClass]

public class _mx_controls_DataGridStyle
{
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='cursorStretch', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='592', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf_cursorStretch_32358787:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
        var conditions:Array = null;
        var condition:CSSCondition = null;
        var selector:CSSSelector = null;
        var style:CSSStyleDeclaration;
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.DataGrid", conditions, selector);
        // mx.controls.DataGrid
        style = StyleManager.getStyleDeclaration("mx.controls.DataGrid");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.sortArrowSkin = mx.skins.spark.DataGridSortArrow;
                this.columnDropIndicatorSkin = mx.skins.halo.DataGridColumnDropIndicator;
                this.columnResizeSkin = mx.skins.halo.DataGridColumnResizeSkin;
                this.stretchCursor = _embed_css_Assets_swf_cursorStretch_32358787;
                this.alternatingItemColors = [0xeff3fa, 0xffffff];
                this.headerSeparatorSkin = mx.skins.spark.DataGridHeaderSeparatorSkin;
                this.headerBackgroundSkin = mx.skins.spark.DataGridHeaderBackgroundSkin;
                this.headerColors = [0xffffff, 0xe6e6e6];
                this.headerDragProxyStyleName = "headerDragProxyStyle";
                this.verticalGridLineColor = 0x696969;
            };
        }




    }
}

}
