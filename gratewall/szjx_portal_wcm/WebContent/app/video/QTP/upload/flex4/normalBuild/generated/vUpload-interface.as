
package 
{
import flash.accessibility.*;
import flash.debugger.*;
import flash.display.*;
import flash.errors.*;
import flash.events.*;
import flash.external.*;
import flash.filters.*;
import flash.geom.*;
import flash.media.*;
import flash.net.*;
import flash.printing.*;
import flash.profiler.*;
import flash.system.*;
import flash.text.*;
import flash.ui.*;
import flash.utils.*;
import flash.xml.*;
import mx.binding.*;
import mx.containers.HBox;
import mx.containers.Panel;
import mx.controls.Button;
import mx.controls.ProgressBar;
import mx.controls.Text;
import mx.core.Application;
import mx.core.ClassFactory;
import mx.core.DeferredInstanceFromClass;
import mx.core.DeferredInstanceFromFunction;
import mx.core.IDeferredInstance;
import mx.core.IFactory;
import mx.core.IPropertyChangeNotifier;
import mx.core.mx_internal;
import mx.styles.*;
import mx.containers.HBox;
import mx.core.Application;

public class vUpload extends mx.core.Application
{
	public function vUpload() {}

	[Bindable]
	public var panel : mx.containers.Panel;
	[Bindable]
	public var hbxSelect : mx.containers.HBox;
	[Bindable]
	public var bBrowse : mx.controls.Button;
	[Bindable]
	public var bUpload : mx.controls.Button;
	[Bindable]
	public var bProgress : mx.controls.ProgressBar;
	[Bindable]
	public var hbxInfo : mx.containers.HBox;
	[Bindable]
	public var bFileInfo : mx.controls.Text;

	mx_internal var _bindings : Array;
	mx_internal var _watchers : Array;
	mx_internal var _bindingsByDestination : Object;
	mx_internal var _bindingsBeginWithWord : Object;

include "./scripts/upload.as";

}}
