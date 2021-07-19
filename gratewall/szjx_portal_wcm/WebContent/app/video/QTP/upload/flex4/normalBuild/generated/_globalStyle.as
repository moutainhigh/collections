
package 
{

import mx.core.IFlexModuleFactory;
import mx.core.mx_internal;
import mx.styles.CSSCondition;
import mx.styles.CSSSelector;
import mx.styles.CSSStyleDeclaration;
import mx.styles.StyleManager;
import mx.skins.spark.BorderSkin;
import mx.core.UITextField;
import mx.skins.halo.HaloFocusRect;

[ExcludeClass]

public class _globalStyle
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
        selector = new CSSSelector("global", conditions, selector);
        // global
        style = StyleManager.getStyleDeclaration("global");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.lineHeight = "120%";
                this.unfocusedTextSelectionColor = 0xe8e8e8;
                this.kerning = "default";
                this.iconColor = 0x111111;
                this.horizontalAlign = "left";
                this.filled = true;
                this.textDecoration = "none";
                this.columnCount = "auto";
                this.dominantBaseline = "auto";
                this.fontThickness = 0;
                this.focusBlendMode = "normal";
                this.blockProgression = "tb";
                this.buttonColor = 0x6f7777;
                this.indentation = 17;
                this.textAlignLast = "start";
                this.paddingTop = 0;
                this.textAlpha = 1.0;
                this.rollOverColor = 0xcedbef;
                this.bevel = true;
                this.fontSize = 12;
                this.shadowColor = 0xeeeeee;
                this.columnGap = 20;
                this.paddingLeft = 0;
                this.paragraphEndIndent = 0;
                this.fontWeight = "normal";
                this.indicatorGap = 14;
                this.focusSkin = mx.skins.halo.HaloFocusRect;
                this.breakOpportunity = "auto";
                this.leading = 2;
                this.symbolColor = 0x000000;
                this.renderingMode = "cff";
                this.borderThickness = 1;
                this.paragraphStartIndent = 0;
                this.contentBackgroundColor = 0xffffff;
                this.backgroundSize = "auto";
                this.paragraphSpaceAfter = 0;
                this.borderColor = 0x696969;
                this.shadowDistance = 2;
                this.stroked = false;
                this.digitWidth = "default";
                this.verticalAlign = "top";
                this.ligatureLevel = "common";
                this.firstBaselineOffset = "auto";
                this.fillAlphas = [0.6, 0.4, 0.75, 0.65];
                this.version = "4.0.0";
                this.shadowDirection = "center";
                this.fontLookup = "auto";
                this.lineBreak = "toFit";
                this.openDuration = 0;
                this.repeatInterval = 35;
                this.paragraphSpaceBefore = 0;
                this.fontFamily = "Arial";
                this.paddingBottom = 0;
                this.strokeWidth = 1;
                this.lineThrough = false;
                this.textFieldClass = mx.core.UITextField;
                this.alignmentBaseline = "useDominantBaseline";
                this.trackingLeft = 0;
                this.verticalGridLines = true;
                this.fontStyle = "normal";
                this.dropShadowColor = 0x000000;
                this.selectionColor = 0xa8c6ee;
                this.focusRoundedCorners = "tl tr bl br";
                this.paddingRight = 0;
                this.borderSides = "left top right bottom";
                this.disabledIconColor = 0x999999;
                this.textJustify = "interWord";
                this.focusColor = 0x70b2ee;
                this.borderVisible = true;
                this.selectionDuration = 250;
                this.typographicCase = "default";
                this.highlightAlphas = [0.3, 0];
                this.fillColor = 0xffffff;
                this.textRollOverColor = 0;
                this.digitCase = "default";
                this.shadowCapColor = 0xd5dddd;
                this.inactiveTextSelectionColor = 0xe8e8e8;
                this.backgroundAlpha = 1.0;
                this.justificationRule = "auto";
                this.roundedBottomCorners = true;
                this.dropShadowVisible = false;
                this.trackingRight = 0;
                this.fillColors = [0xffffff, 0xcccccc, 0xffffff, 0xeeeeee];
                this.horizontalGap = 8;
                this.borderCapColor = 0x919999;
                this.leadingModel = "auto";
                this.selectionDisabledColor = 0xdddddd;
                this.closeDuration = 50;
                this.baseColor = 0xcccccc;
                this.embedFonts = false;
                this.letterSpacing = 0;
                this.focusAlpha = 0.55;
                this.focusedTextSelectionColor = 0xa8c6ee;
                this.borderAlpha = 1.0;
                this.baselineShift = 0;
                this.borderSkin = mx.skins.spark.BorderSkin;
                this.fontSharpness = 0;
                this.modalTransparencyDuration = 100;
                this.justificationStyle = "auto";
                this.borderStyle = "inset";
                this.contentBackgroundAlpha = 1;
                this.textRotation = "auto";
                this.fontAntiAliasType = "advanced";
                this.errorColor = 0xfe0000;
                this.direction = "ltr";
                this.cffHinting = "horizontalStem";
                this.horizontalGridLineColor = 0xf7f7f7;
                this.locale = "en";
                this.cornerRadius = 2;
                this.modalTransparencyColor = 0xdddddd;
                this.disabledAlpha = 0.5;
                this.textIndent = 0;
                this.verticalGridLineColor = 0xd5dddd;
                this.themeColor = 0x70b2ee;
                this.tabStops = null;
                this.modalTransparency = 0.5;
                this.columnWidth = "auto";
                this.textAlign = "start";
                this.textSelectedColor = 0;
                this.whiteSpaceCollapse = "collapse";
                this.fontGridFitType = "pixel";
                this.horizontalGridLines = false;
                this.useRollOver = true;
                this.repeatDelay = 500;
                this.focusThickness = 2;
                this.verticalGap = 6;
                this.disabledColor = 0xaab3b3;
                this.modalTransparencyBlur = 3;
                this.color = 0x000000;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "activeButtonStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .activeButtonStyle
        style = StyleManager.getStyleDeclaration(".activeButtonStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "activeTabStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .activeTabStyle
        style = StyleManager.getStyleDeclaration(".activeTabStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "comboDropdown");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .comboDropdown
        style = StyleManager.getStyleDeclaration(".comboDropdown");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.shadowDirection = "center";
                this.fontWeight = "normal";
                this.leading = 0;
                this.backgroundColor = 0xffffff;
                this.dropShadowVisible = true;
                this.shadowDistance = 1;
                this.cornerRadius = 0;
                this.borderThickness = 0;
                this.paddingLeft = 5;
                this.paddingRight = 5;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "dateFieldPopup");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .dateFieldPopup
        style = StyleManager.getStyleDeclaration(".dateFieldPopup");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.backgroundColor = 0xffffff;
                this.dropShadowVisible = true;
                this.borderThickness = 1;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "errorTip");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .errorTip
        style = StyleManager.getStyleDeclaration(".errorTip");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
                this.borderStyle = "errorTipRight";
                this.paddingTop = 4;
                this.borderColor = 0xce2929;
                this.color = 0xffffff;
                this.fontSize = 10;
                this.shadowColor = 0x000000;
                this.paddingLeft = 4;
                this.paddingBottom = 4;
                this.paddingRight = 4;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "headerDragProxyStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .headerDragProxyStyle
        style = StyleManager.getStyleDeclaration(".headerDragProxyStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "linkButtonStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .linkButtonStyle
        style = StyleManager.getStyleDeclaration(".linkButtonStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 2;
                this.paddingLeft = 2;
                this.paddingBottom = 2;
                this.paddingRight = 2;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "opaquePanel");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .opaquePanel
        style = StyleManager.getStyleDeclaration(".opaquePanel");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderColor = 0xffffff;
                this.backgroundColor = 0xffffff;
                this.headerColors = [0xe7e7e7, 0xd9d9d9];
                this.footerColors = [0xe7e7e7, 0xc7c7c7];
                this.borderAlpha = 1;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "plain");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .plain
        style = StyleManager.getStyleDeclaration(".plain");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.paddingTop = 0;
                this.backgroundColor = 0xffffff;
                this.backgroundImage = "";
                this.horizontalAlign = "left";
                this.paddingLeft = 0;
                this.paddingBottom = 0;
                this.paddingRight = 0;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "popUpMenu");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .popUpMenu
        style = StyleManager.getStyleDeclaration(".popUpMenu");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "normal";
                this.textAlign = "left";
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "richTextEditorTextAreaStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .richTextEditorTextAreaStyle
        style = StyleManager.getStyleDeclaration(".richTextEditorTextAreaStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "swatchPanelTextField");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .swatchPanelTextField
        style = StyleManager.getStyleDeclaration(".swatchPanelTextField");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.borderStyle = "inset";
                this.borderColor = 0xd5dddd;
                this.highlightColor = 0xc4cccc;
                this.backgroundColor = 0xffffff;
                this.shadowCapColor = 0xd5dddd;
                this.shadowColor = 0xd5dddd;
                this.paddingLeft = 5;
                this.buttonColor = 0x6f7777;
                this.borderCapColor = 0x919999;
                this.paddingRight = 5;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "textAreaVScrollBarStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .textAreaVScrollBarStyle
        style = StyleManager.getStyleDeclaration(".textAreaVScrollBarStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "textAreaHScrollBarStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .textAreaHScrollBarStyle
        style = StyleManager.getStyleDeclaration(".textAreaHScrollBarStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "todayStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .todayStyle
        style = StyleManager.getStyleDeclaration(".todayStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.color = 0;
                this.textAlign = "center";
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "weekDayStyle");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .weekDayStyle
        style = StyleManager.getStyleDeclaration(".weekDayStyle");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
                this.textAlign = "center";
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "windowStatus");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .windowStatus
        style = StyleManager.getStyleDeclaration(".windowStatus");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.color = 0x666666;
            };
        }


        selector = null;
        conditions = null;
        conditions = [];
        condition = new CSSCondition("class", "windowStyles");
        conditions.push(condition); 
        selector = new CSSSelector("", conditions, selector);
        // .windowStyles
        style = StyleManager.getStyleDeclaration(".windowStyles");
        if (!style)
        {
            style = new CSSStyleDeclaration(selector);
        }

        if (style.defaultFactory == null)
        {
            style.defaultFactory = function():void
            {
                this.fontWeight = "bold";
            };
        }




    }
}

}
