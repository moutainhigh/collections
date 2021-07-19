package {
import flash.utils.*;
import mx.core.IFlexModuleFactory;
import mx.styles.StyleManagerImpl;
import mx.managers.systemClasses.ChildManager;
import mx.core.TextFieldFactory; TextFieldFactory;
import flash.system.*
import mx.accessibility.AlertAccImpl;
import mx.accessibility.PanelAccImpl;
import mx.accessibility.DataGridAccImpl;
import mx.accessibility.ListBaseAccImpl;
import mx.accessibility.ButtonAccImpl;
import mx.accessibility.UIComponentAccProps;
import flash.net.registerClassAlias;
import flash.net.getClassByAlias;
import mx.collections.ArrayCollection;
import mx.collections.ArrayList;
import mx.utils.ObjectProxy;
import mx.effects.EffectManager;
import mx.core.mx_internal;
[ResourceBundle("collections")]
[ResourceBundle("components")]
[ResourceBundle("containers")]
[ResourceBundle("controls")]
[ResourceBundle("core")]
[ResourceBundle("effects")]
[ResourceBundle("layout")]
[ResourceBundle("skins")]
[ResourceBundle("styles")]

[Mixin]
public class _vMultiUpload_FlexInit
{
   public function _vMultiUpload_FlexInit()
   {
       super();
   }
   public static function init(fbs:IFlexModuleFactory):void
   {
       new ChildManager(fbs);
       new StyleManagerImpl(fbs);
      EffectManager.mx_internal::registerEffectTrigger("addedEffect", "added");
      EffectManager.mx_internal::registerEffectTrigger("completeEffect", "complete");
      EffectManager.mx_internal::registerEffectTrigger("creationCompleteEffect", "creationComplete");
      EffectManager.mx_internal::registerEffectTrigger("focusInEffect", "focusIn");
      EffectManager.mx_internal::registerEffectTrigger("focusOutEffect", "focusOut");
      EffectManager.mx_internal::registerEffectTrigger("hideEffect", "hide");
      EffectManager.mx_internal::registerEffectTrigger("mouseDownEffect", "mouseDown");
      EffectManager.mx_internal::registerEffectTrigger("mouseUpEffect", "mouseUp");
      EffectManager.mx_internal::registerEffectTrigger("moveEffect", "move");
      EffectManager.mx_internal::registerEffectTrigger("removedEffect", "removed");
      EffectManager.mx_internal::registerEffectTrigger("resizeEffect", "resize");
      EffectManager.mx_internal::registerEffectTrigger("resizeEndEffect", "resizeEnd");
      EffectManager.mx_internal::registerEffectTrigger("resizeStartEffect", "resizeStart");
      EffectManager.mx_internal::registerEffectTrigger("rollOutEffect", "rollOut");
      EffectManager.mx_internal::registerEffectTrigger("rollOverEffect", "rollOver");
      EffectManager.mx_internal::registerEffectTrigger("showEffect", "show");
      // trace("Flex accessibility startup: " + Capabilities.hasAccessibility);
      if (Capabilities.hasAccessibility) {
         mx.accessibility.AlertAccImpl.enableAccessibility();
         mx.accessibility.PanelAccImpl.enableAccessibility();
         mx.accessibility.DataGridAccImpl.enableAccessibility();
         mx.accessibility.ListBaseAccImpl.enableAccessibility();
         mx.accessibility.ButtonAccImpl.enableAccessibility();
         mx.accessibility.UIComponentAccProps.enableAccessibility();
      }
      try {
      if (flash.net.getClassByAlias("flex.messaging.io.ArrayCollection") == null){
          flash.net.registerClassAlias("flex.messaging.io.ArrayCollection", mx.collections.ArrayCollection);}
      } catch (e:Error) {
          flash.net.registerClassAlias("flex.messaging.io.ArrayCollection", mx.collections.ArrayCollection); }
      try {
      if (flash.net.getClassByAlias("flex.messaging.io.ArrayList") == null){
          flash.net.registerClassAlias("flex.messaging.io.ArrayList", mx.collections.ArrayList);}
      } catch (e:Error) {
          flash.net.registerClassAlias("flex.messaging.io.ArrayList", mx.collections.ArrayList); }
      try {
      if (flash.net.getClassByAlias("flex.messaging.io.ObjectProxy") == null){
          flash.net.registerClassAlias("flex.messaging.io.ObjectProxy", mx.utils.ObjectProxy);}
      } catch (e:Error) {
          flash.net.registerClassAlias("flex.messaging.io.ObjectProxy", mx.utils.ObjectProxy); }
      var styleNames:Array = ["lineHeight", "unfocusedTextSelectionColor", "kerning", "iconColor", "textRollOverColor", "digitCase", "inactiveTextSelectionColor", "justificationRule", "textDecoration", "dominantBaseline", "fontThickness", "trackingRight", "blockProgression", "leadingModel", "selectionDisabledColor", "textAlignLast", "baseColor", "textAlpha", "letterSpacing", "rollOverColor", "fontSize", "baselineShift", "focusedTextSelectionColor", "paragraphEndIndent", "fontWeight", "breakOpportunity", "leading", "renderingMode", "symbolColor", "barColor", "fontSharpness", "modalTransparencyDuration", "paragraphStartIndent", "justificationStyle", "footerColors", "contentBackgroundColor", "paragraphSpaceAfter", "contentBackgroundAlpha", "textRotation", "fontAntiAliasType", "cffHinting", "direction", "errorColor", "locale", "horizontalGridLineColor", "digitWidth", "backgroundDisabledColor", "modalTransparencyColor", "ligatureLevel", "firstBaselineOffset", "textIndent", "themeColor", "verticalGridLineColor", "fontLookup", "tabStops", "modalTransparency", "paragraphSpaceBefore", "textAlign", "headerColors", "fontFamily", "textSelectedColor", "lineThrough", "whiteSpaceCollapse", "labelWidth", "fontGridFitType", "alignmentBaseline", "trackingLeft", "fontStyle", "dropShadowColor", "disabledColor", "selectionColor", "disabledIconColor", "modalTransparencyBlur", "textJustify", "focusColor", "color", "alternatingItemColors", "typographicCase"];

      import mx.styles.StyleManager;

      for (var i:int = 0; i < styleNames.length; i++)
      {
         StyleManager.registerInheritingStyle(styleNames[i]);
      }
   }
}  // FlexInit
}  // package
