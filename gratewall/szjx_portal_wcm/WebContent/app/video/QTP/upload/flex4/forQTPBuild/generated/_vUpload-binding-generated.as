

import flash.events.Event;
import flash.events.EventDispatcher;
import flash.events.IEventDispatcher;
import mx.core.IPropertyChangeNotifier;
import mx.events.PropertyChangeEvent;
import mx.utils.ObjectProxy;
import mx.utils.UIDUtil;

import flash.filters.*;
import flash.profiler.*;
import mx.containers.HBox;
import mx.containers.Panel;
import flash.external.*;
import flash.display.*;
import flash.net.*;
import flash.debugger.*;
import mx.controls.Text;
import flash.utils.*;
import flash.printing.*;
import flash.text.*;
import flash.geom.*;
import flash.events.*;
import flash.accessibility.*;
import mx.binding.*;
import mx.controls.Button;
import mx.controls.ProgressBar;
import flash.ui.*;
import flash.media.*;
import flash.xml.*;
import mx.styles.*;
import flash.system.*;
import flash.errors.*;

class BindableProperty
{
	/*
	 * generated bindable wrapper for property bBrowse (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'bBrowse' moved to '_1220722292bBrowse'
	 */

    [Bindable(event="propertyChange")]
    public function get bBrowse():mx.controls.Button
    {
        return this._1220722292bBrowse;
    }

    public function set bBrowse(value:mx.controls.Button):void
    {
    	var oldValue:Object = this._1220722292bBrowse;
        if (oldValue !== value)
        {
            this._1220722292bBrowse = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "bBrowse", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property bFileInfo (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'bFileInfo' moved to '_1708092724bFileInfo'
	 */

    [Bindable(event="propertyChange")]
    public function get bFileInfo():mx.controls.Text
    {
        return this._1708092724bFileInfo;
    }

    public function set bFileInfo(value:mx.controls.Text):void
    {
    	var oldValue:Object = this._1708092724bFileInfo;
        if (oldValue !== value)
        {
            this._1708092724bFileInfo = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "bFileInfo", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property bProgress (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'bProgress' moved to '_1973312337bProgress'
	 */

    [Bindable(event="propertyChange")]
    public function get bProgress():mx.controls.ProgressBar
    {
        return this._1973312337bProgress;
    }

    public function set bProgress(value:mx.controls.ProgressBar):void
    {
    	var oldValue:Object = this._1973312337bProgress;
        if (oldValue !== value)
        {
            this._1973312337bProgress = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "bProgress", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property bUpload (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'bUpload' moved to '_678713085bUpload'
	 */

    [Bindable(event="propertyChange")]
    public function get bUpload():mx.controls.Button
    {
        return this._678713085bUpload;
    }

    public function set bUpload(value:mx.controls.Button):void
    {
    	var oldValue:Object = this._678713085bUpload;
        if (oldValue !== value)
        {
            this._678713085bUpload = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "bUpload", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property hbxInfo (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'hbxInfo' moved to '_729865356hbxInfo'
	 */

    [Bindable(event="propertyChange")]
    public function get hbxInfo():mx.containers.HBox
    {
        return this._729865356hbxInfo;
    }

    public function set hbxInfo(value:mx.containers.HBox):void
    {
    	var oldValue:Object = this._729865356hbxInfo;
        if (oldValue !== value)
        {
            this._729865356hbxInfo = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "hbxInfo", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property hbxSelect (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'hbxSelect' moved to '_1599090010hbxSelect'
	 */

    [Bindable(event="propertyChange")]
    public function get hbxSelect():mx.containers.HBox
    {
        return this._1599090010hbxSelect;
    }

    public function set hbxSelect(value:mx.containers.HBox):void
    {
    	var oldValue:Object = this._1599090010hbxSelect;
        if (oldValue !== value)
        {
            this._1599090010hbxSelect = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "hbxSelect", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property panel (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'panel' moved to '_106433028panel'
	 */

    [Bindable(event="propertyChange")]
    public function get panel():mx.containers.Panel
    {
        return this._106433028panel;
    }

    public function set panel(value:mx.containers.Panel):void
    {
    	var oldValue:Object = this._106433028panel;
        if (oldValue !== value)
        {
            this._106433028panel = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "panel", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property fileInfo (private)
	 * - generated setter
	 * - generated getter
	 * - original private var 'fileInfo' moved to '_735858614fileInfo'
	 */

    [Bindable(event="propertyChange")]
    private function get fileInfo():String
    {
        return this._735858614fileInfo;
    }

    private function set fileInfo(value:String):void
    {
    	var oldValue:Object = this._735858614fileInfo;
        if (oldValue !== value)
        {
            this._735858614fileInfo = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "fileInfo", oldValue, value));
        }
    }



}
