

import flash.events.Event;
import flash.events.EventDispatcher;
import flash.events.IEventDispatcher;
import mx.core.IPropertyChangeNotifier;
import mx.events.PropertyChangeEvent;
import mx.utils.ObjectProxy;
import mx.utils.UIDUtil;

import mx.controls.DataGrid;
import mx.controls.Button;
import mx.controls.ProgressBar;
import mx.controls.Label;

class BindableProperty
{
	/*
	 * generated bindable wrapper for property browserBtn (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'browserBtn' moved to '_163567084browserBtn'
	 */

    [Bindable(event="propertyChange")]
    public function get browserBtn():mx.controls.Button
    {
        return this._163567084browserBtn;
    }

    public function set browserBtn(value:mx.controls.Button):void
    {
    	var oldValue:Object = this._163567084browserBtn;
        if (oldValue !== value)
        {
            this._163567084browserBtn = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "browserBtn", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property clearBtn (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'clearBtn' moved to '_790270159clearBtn'
	 */

    [Bindable(event="propertyChange")]
    public function get clearBtn():mx.controls.Button
    {
        return this._790270159clearBtn;
    }

    public function set clearBtn(value:mx.controls.Button):void
    {
    	var oldValue:Object = this._790270159clearBtn;
        if (oldValue !== value)
        {
            this._790270159clearBtn = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "clearBtn", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property dg (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'dg' moved to '_3203dg'
	 */

    [Bindable(event="propertyChange")]
    public function get dg():mx.controls.DataGrid
    {
        return this._3203dg;
    }

    public function set dg(value:mx.controls.DataGrid):void
    {
    	var oldValue:Object = this._3203dg;
        if (oldValue !== value)
        {
            this._3203dg = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "dg", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property msgLb (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'msgLb' moved to '_104191191msgLb'
	 */

    [Bindable(event="propertyChange")]
    public function get msgLb():mx.controls.Label
    {
        return this._104191191msgLb;
    }

    public function set msgLb(value:mx.controls.Label):void
    {
    	var oldValue:Object = this._104191191msgLb;
        if (oldValue !== value)
        {
            this._104191191msgLb = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "msgLb", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property pgBar (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'pgBar' moved to '_106569372pgBar'
	 */

    [Bindable(event="propertyChange")]
    public function get pgBar():mx.controls.ProgressBar
    {
        return this._106569372pgBar;
    }

    public function set pgBar(value:mx.controls.ProgressBar):void
    {
    	var oldValue:Object = this._106569372pgBar;
        if (oldValue !== value)
        {
            this._106569372pgBar = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "pgBar", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property removeBtn (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'removeBtn' moved to '_1282346808removeBtn'
	 */

    [Bindable(event="propertyChange")]
    public function get removeBtn():mx.controls.Button
    {
        return this._1282346808removeBtn;
    }

    public function set removeBtn(value:mx.controls.Button):void
    {
    	var oldValue:Object = this._1282346808removeBtn;
        if (oldValue !== value)
        {
            this._1282346808removeBtn = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "removeBtn", oldValue, value));
        }
    }

	/*
	 * generated bindable wrapper for property uploadBtn (public)
	 * - generated setter
	 * - generated getter
	 * - original public var 'uploadBtn' moved to '_1239067803uploadBtn'
	 */

    [Bindable(event="propertyChange")]
    public function get uploadBtn():mx.controls.Button
    {
        return this._1239067803uploadBtn;
    }

    public function set uploadBtn(value:mx.controls.Button):void
    {
    	var oldValue:Object = this._1239067803uploadBtn;
        if (oldValue !== value)
        {
            this._1239067803uploadBtn = value;
            this.dispatchEvent(mx.events.PropertyChangeEvent.createUpdateEvent(this, "uploadBtn", oldValue, value));
        }
    }



}
