package {
import flash.utils.*;
import mx.core.IFlexModuleFactory;
import mx.styles.StyleManagerImpl;
import mx.managers.systemClasses.ChildManager;
import mx.core.TextFieldFactory; TextFieldFactory;
import flash.system.*
import mx.accessibility.ColorPickerAccImpl;
import mx.accessibility.ComboBoxAccImpl;
import mx.accessibility.PanelAccImpl;
import mx.accessibility.TabBarAccImpl;
import mx.accessibility.AdvancedDataGridAccImpl;
import mx.accessibility.ListBaseAccImpl;
import mx.accessibility.DateFieldAccImpl;
import mx.accessibility.AccordionHeaderAccImpl;
import mx.accessibility.TreeAccImpl;
import mx.accessibility.ListAccImpl;
import mx.accessibility.DateChooserAccImpl;
import mx.accessibility.AlertAccImpl;
import mx.accessibility.MenuAccImpl;
import mx.accessibility.RadioButtonAccImpl;
import mx.accessibility.LinkButtonAccImpl;
import mx.accessibility.DataGridAccImpl;
import mx.accessibility.SliderAccImpl;
import mx.accessibility.MenuBarAccImpl;
import mx.accessibility.CheckBoxAccImpl;
import mx.accessibility.ButtonAccImpl;
import mx.accessibility.UIComponentAccProps;
import mx.accessibility.ComboBaseAccImpl;
import flash.net.registerClassAlias;
import flash.net.getClassByAlias;
import mx.collections.ArrayCollection;
import mx.collections.ArrayList;
import mx.messaging.messages.AcknowledgeMessage;
import mx.messaging.messages.AcknowledgeMessageExt;
import mx.messaging.messages.AsyncMessage;
import mx.messaging.messages.AsyncMessageExt;
import mx.messaging.messages.ErrorMessage;
import mx.utils.ObjectProxy;
import mx.effects.EffectManager;
import mx.core.mx_internal;
[ResourceBundle("SharedResources")]
[ResourceBundle("collections")]
[ResourceBundle("components")]
[ResourceBundle("containers")]
[ResourceBundle("controls")]
[ResourceBundle("core")]
[ResourceBundle("effects")]
[ResourceBundle("formatters")]
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
      EffectManager.mx_internal::registerEffectTrigger("hideDataEffect", "hideData");
      EffectManager.mx_internal::registerEffectTrigger("hideEffect", "hide");
      EffectManager.mx_internal::registerEffectTrigger("itemsChangeEffect", "itemsChange");
      EffectManager.mx_internal::registerEffectTrigger("mouseDownEffect", "mouseDown");
      EffectManager.mx_internal::registerEffectTrigger("mouseUpEffect", "mouseUp");
      EffectManager.mx_internal::registerEffectTrigger("moveEffect", "move");
      EffectManager.mx_internal::registerEffectTrigger("removedEffect", "removed");
      EffectManager.mx_internal::registerEffectTrigger("resizeEffect", "resize");
      EffectManager.mx_internal::registerEffectTrigger("resizeEndEffect", "resizeEnd");
      EffectManager.mx_internal::registerEffectTrigger("resizeStartEffect", "resizeStart");
      EffectManager.mx_internal::registerEffectTrigger("rollOutEffect", "rollOut");
      EffectManager.mx_internal::registerEffectTrigger("rollOverEffect", "rollOver");
      EffectManager.mx_internal::registerEffectTrigger("showDataEffect", "showData");
      EffectManager.mx_internal::registerEffectTrigger("showEffect", "show");
      // trace("Flex accessibility startup: " + Capabilities.hasAccessibility);
      if (Capabilities.hasAccessibility) {
         mx.accessibility.ColorPickerAccImpl.enableAccessibility();
         mx.accessibility.ComboBoxAccImpl.enableAccessibility();
         mx.accessibility.PanelAccImpl.enableAccessibility();
         mx.accessibility.TabBarAccImpl.enableAccessibility();
         mx.accessibility.AdvancedDataGridAccImpl.enableAccessibility();
         mx.accessibility.ListBaseAccImpl.enableAccessibility();
         mx.accessibility.DateFieldAccImpl.enableAccessibility();
         mx.accessibility.AccordionHeaderAccImpl.enableAccessibility();
         mx.accessibility.TreeAccImpl.enableAccessibility();
         mx.accessibility.ListAccImpl.enableAccessibility();
         mx.accessibility.DateChooserAccImpl.enableAccessibility();
         mx.accessibility.AlertAccImpl.enableAccessibility();
         mx.accessibility.MenuAccImpl.enableAccessibility();
         mx.accessibility.RadioButtonAccImpl.enableAccessibility();
         mx.accessibility.LinkButtonAccImpl.enableAccessibility();
         mx.accessibility.DataGridAccImpl.enableAccessibility();
         mx.accessibility.SliderAccImpl.enableAccessibility();
         mx.accessibility.MenuBarAccImpl.enableAccessibility();
         mx.accessibility.CheckBoxAccImpl.enableAccessibility();
         mx.accessibility.ButtonAccImpl.enableAccessibility();
         mx.accessibility.UIComponentAccProps.enableAccessibility();
         mx.accessibility.ComboBaseAccImpl.enableAccessibility();
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
      if (flash.net.getClassByAlias("flex.messaging.messages.AcknowledgeMessage") == null){
          flash.net.registerClassAlias("flex.messaging.messages.AcknowledgeMessage", mx.messaging.messages.AcknowledgeMessage);}
      } catch (e:Error) {
          flash.net.registerClassAlias("flex.messaging.messages.AcknowledgeMessage", mx.messaging.messages.AcknowledgeMessage); }
      try {
      if (flash.net.getClassByAlias("DSK") == null){
          flash.net.registerClassAlias("DSK", mx.messaging.messages.AcknowledgeMessageExt);}
      } catch (e:Error) {
          flash.net.registerClassAlias("DSK", mx.messaging.messages.AcknowledgeMessageExt); }
      try {
      if (flash.net.getClassByAlias("flex.messaging.messages.AsyncMessage") == null){
          flash.net.registerClassAlias("flex.messaging.messages.AsyncMessage", mx.messaging.messages.AsyncMessage);}
      } catch (e:Error) {
          flash.net.registerClassAlias("flex.messaging.messages.AsyncMessage", mx.messaging.messages.AsyncMessage); }
      try {
      if (flash.net.getClassByAlias("DSA") == null){
          flash.net.registerClassAlias("DSA", mx.messaging.messages.AsyncMessageExt);}
      } catch (e:Error) {
          flash.net.registerClassAlias("DSA", mx.messaging.messages.AsyncMessageExt); }
      try {
      if (flash.net.getClassByAlias("flex.messaging.messages.ErrorMessage") == null){
          flash.net.registerClassAlias("flex.messaging.messages.ErrorMessage", mx.messaging.messages.ErrorMessage);}
      } catch (e:Error) {
          flash.net.registerClassAlias("flex.messaging.messages.ErrorMessage", mx.messaging.messages.ErrorMessage); }
      try {
      if (flash.net.getClassByAlias("flex.messaging.io.ObjectProxy") == null){
          flash.net.registerClassAlias("flex.messaging.io.ObjectProxy", mx.utils.ObjectProxy);}
      } catch (e:Error) {
          flash.net.registerClassAlias("flex.messaging.io.ObjectProxy", mx.utils.ObjectProxy); }
      var styleNames:Array = ["sortFontSize", "lineHeight", "unfocusedTextSelectionColor", "kerning", "iconColor", "markerWidth", "textDecoration", "dominantBaseline", "adjustedRadius", "fontThickness", "depthColors", "blockProgression", "textAlignLast", "textAlpha", "rollOverColor", "itemRollOverColor", "fontSize", "shadowColor", "paragraphEndIndent", "fontWeight", "indicatorGap", "breakOpportunity", "leading", "symbolColor", "renderingMode", "paragraphStartIndent", "footerColors", "contentBackgroundColor", "paragraphSpaceAfter", "digitWidth", "ligatureLevel", "firstBaselineOffset", "itemDisabledColor", "fontLookup", "todayColor", "paragraphSpaceBefore", "separatorWidth", "fontFamily", "labelPlacement", "lineThrough", "alignmentBaseline", "trackingLeft", "separatorColor", "fontStyle", "dropShadowColor", "selectionColor", "dropdownBorderColor", "disabledIconColor", "textJustify", "focusColor", "alternatingItemColors", "typographicCase", "highlightColor", "textRollOverColor", "digitCase", "shadowCapColor", "inactiveTextSelectionColor", "justificationRule", "dividerColor", "trackingRight", "itemSelectionColor", "leadingModel", "selectionDisabledColor", "baseColor", "letterSpacing", "focusedTextSelectionColor", "baselineShift", "axisTitleStyleName", "fontSharpness", "barColor", "sortFontStyle", "modalTransparencyDuration", "justificationStyle", "contentBackgroundAlpha", "fontAntiAliasType", "textRotation", "errorColor", "direction", "cffHinting", "horizontalGridLineColor", "locale", "backgroundDisabledColor", "modalTransparencyColor", "textIndent", "verticalGridLineColor", "themeColor", "modalTransparency", "tabStops", "markerHeight", "headerColors", "textAlign", "sortFontWeight", "textSelectedColor", "labelWidth", "whiteSpaceCollapse", "fontGridFitType", "proposedColor", "disabledColor", "modalTransparencyBlur", "color", "sortFontFamily"];

      import mx.styles.StyleManager;

      for (var i:int = 0; i < styleNames.length; i++)
      {
         StyleManager.registerInheritingStyle(styleNames[i]);
      }
   }
}  // FlexInit
}  // package
