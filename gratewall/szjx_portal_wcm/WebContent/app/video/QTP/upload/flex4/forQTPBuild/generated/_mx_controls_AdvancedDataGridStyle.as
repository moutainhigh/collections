
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.halo.DataGridColumnDropIndicator;
import mx.skins.halo.DataGridSortArrow;
import mx.skins.halo.DataGridHeaderBackgroundSkin;
import mx.skins.halo.AdvancedDataGridHeaderHorizontalSeparator;
import mx.skins.halo.DataGridColumnResizeSkin;
import mx.skins.halo.DataGridHeaderSeparator;

[ExcludeClass]

public class _mx_controls_AdvancedDataGridStyle
{
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='cursorStretch', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='32', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/datavisualization.swc$defaults.css')]
    private static var _embed_css_Assets_swf_cursorStretch_32358787:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeNodeIcon', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='36', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/datavisualization.swc$defaults.css')]
    private static var _embed_css_Assets_swf_TreeNodeIcon_700580835:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeDisclosureClosed', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='34', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/datavisualization.swc$defaults.css')]
    private static var _embed_css_Assets_swf_TreeDisclosureClosed_2009078475:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeFolderClosed', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='37', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/datavisualization.swc$defaults.css')]
    private static var _embed_css_Assets_swf_TreeFolderClosed_748703364:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeFolderOpen', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='38', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/datavisualization.swc$defaults.css')]
    private static var _embed_css_Assets_swf_TreeFolderOpen_1966678378:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeDisclosureOpen', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='35', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/datavisualization.swc$defaults.css')]
    private static var _embed_css_Assets_swf_TreeDisclosureOpen_251874921:Class;

    public static function init(fbs:IFlexModuleFactory):void
    {
        var conditions:Array = null;
        var condition:CSSCondition = null;
        var selector:CSSSelector = null;
        var style:CSSStyleDeclaration;
        selector = null;
        conditions = null;
        conditions = null;
        selector = new CSSSelector("mx.controls.AdvancedDataGrid", conditions, selector);
        // mx.controls.AdvancedDataGrid
        style = StyleManager.getStyleDeclaration("mx.controls.AdvancedDataGrid");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.sortFontSize = 10;
                this.sortArrowSkin = mx.skins.halo.DataGridSortArrow;
                this.stretchCursor = _embed_css_Assets_swf_cursorStretch_32358787;
                this.folderOpenIcon = _embed_css_Assets_swf_TreeFolderOpen_1966678378;
                this.headerStyleName = "advancedDataGridStyles";
                this.paddingLeft = 2;
                this.verticalGridLineColor = 0xcccccc;
                this.paddingRight = 0;
                this.columnDropIndicatorSkin = mx.skins.halo.DataGridColumnDropIndicator;
                this.disclosureOpenIcon = _embed_css_Assets_swf_TreeDisclosureOpen_251874921;
                this.folderClosedIcon = _embed_css_Assets_swf_TreeFolderClosed_748703364;
                this.columnResizeSkin = mx.skins.halo.DataGridColumnResizeSkin;
                this.alternatingItemColors = [0xf7f7f7, 0xffffff];
                this.sortFontStyle = "normal";
                this.disclosureClosedIcon = _embed_css_Assets_swf_TreeDisclosureClosed_2009078475;
                this.headerColors = [0xffffff, 0xe6e6e6];
                this.headerBackgroundSkin = mx.skins.halo.DataGridHeaderBackgroundSkin;
                this.headerSeparatorSkin = mx.skins.halo.DataGridHeaderSeparator;
                this.headerDragProxyStyleName = "headerDragProxyStyle";
                this.sortFontWeight = "normal";
                this.defaultLeafIcon = _embed_css_Assets_swf_TreeNodeIcon_700580835;
                this.headerHorizontalSeparatorSkin = mx.skins.halo.AdvancedDataGridHeaderHorizontalSeparator;
                this.sortFontFamily = "Verdana";
            };
        }




    }
}

}
