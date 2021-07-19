
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
import mx.controls.Button;
import mx.controls.DataGrid;
import mx.controls.Label;
import mx.controls.ProgressBar;
import mx.core.Application;
import mx.core.ClassFactory;
import mx.core.DeferredInstanceFromClass;
import mx.core.DeferredInstanceFromFunction;
import mx.core.IDeferredInstance;
import mx.core.IFactory;
import mx.core.IPropertyChangeNotifier;
import mx.core.mx_internal;
import mx.styles.*;
import mx.containers.ControlBar;
import mx.containers.HBox;
import mx.controls.dataGridClasses.DataGridColumn;
import mx.core.Application;

public class vMultiUpload extends mx.core.Application
{
	public function vMultiUpload() {}

	[Bindable]
	public var dg : mx.controls.DataGrid;
	[Bindable]
	public var msgLb : mx.controls.Label;
	[Bindable]
	public var pgBar : mx.controls.ProgressBar;
	[Bindable]
	public var browserBtn : mx.controls.Button;
	[Bindable]
	public var uploadBtn : mx.controls.Button;
	[Bindable]
	public var removeBtn : mx.controls.Button;
	[Bindable]
	public var clearBtn : mx.controls.Button;

	mx_internal var _bindings : Array;
	mx_internal var _watchers : Array;
	mx_internal var _bindingsByDestination : Object;
	mx_internal var _bindingsBeginWithWord : Object;

include "./scripts/multiUpload.as";

}}
