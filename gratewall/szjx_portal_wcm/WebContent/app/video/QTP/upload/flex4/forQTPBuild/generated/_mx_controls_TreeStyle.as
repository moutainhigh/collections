
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;

[ExcludeClass]

public class _mx_controls_TreeStyle
{
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeNodeIcon', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1633', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf_TreeNodeIcon_700580835:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeDisclosureClosed', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1634', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf_TreeDisclosureClosed_2009078475:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeFolderClosed', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1636', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf_TreeFolderClosed_748703364:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeFolderOpen', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1637', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
    private static var _embed_css_Assets_swf_TreeFolderOpen_1966678378:Class;
    [Embed(_resolvedSource='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', symbol='TreeDisclosureOpen', source='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$Assets.swf', original='Assets.swf', _line='1635', _pathsep='true', _file='F:/adobe/Adobe Flash Builder Plug-in Beta 2/sdks/4.0.0/frameworks/libs/framework.swc$defaults.css')]
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
        selector = new CSSSelector("mx.controls.Tree", conditions, selector);
        // mx.controls.Tree
        style = StyleManager.getStyleDeclaration("mx.controls.Tree");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.disclosureOpenIcon = _embed_css_Assets_swf_TreeDisclosureOpen_251874921;
                this.folderClosedIcon = _embed_css_Assets_swf_TreeFolderClosed_748703364;
                this.folderOpenIcon = _embed_css_Assets_swf_TreeFolderOpen_1966678378;
                this.disclosureClosedIcon = _embed_css_Assets_swf_TreeDisclosureClosed_2009078475;
                this.verticalAlign = "middle";
                this.defaultLeafIcon = _embed_css_Assets_swf_TreeNodeIcon_700580835;
                this.paddingLeft = 2;
                this.paddingRight = 0;
            };
        }




    }
}

}
