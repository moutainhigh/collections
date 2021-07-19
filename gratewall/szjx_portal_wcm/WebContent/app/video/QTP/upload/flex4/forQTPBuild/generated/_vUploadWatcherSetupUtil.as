






package
{
import mx.core.IFlexModuleFactory;
import mx.binding.ArrayElementWatcher;
import mx.binding.FunctionReturnWatcher;
import mx.binding.IWatcherSetupUtil2;
import mx.binding.PropertyWatcher;
import mx.binding.RepeaterComponentWatcher;
import mx.binding.RepeaterItemWatcher;
import mx.binding.StaticPropertyWatcher;
import mx.binding.XMLWatcher;
import mx.binding.Watcher;

[ExcludeClass]
public class _vUploadWatcherSetupUtil
    implements mx.binding.IWatcherSetupUtil2
{
    public function _vUploadWatcherSetupUtil()
    {
        super();
    }

    public static function init(fbs:IFlexModuleFactory):void
    {
        import vUpload;
        (vUpload).watcherSetupUtil = new _vUploadWatcherSetupUtil();
    }

    public function setup(target:Object,
                          propertyGetter:Function,
                          staticPropertyGetter:Function,
                          bindings:Array,
                          watchers:Array):void
    {
        import mx.core.UIComponentDescriptor;
        import mx.containers.Panel;
        import mx.core.DeferredInstanceFromClass;
        import flash.events.IOErrorEvent;
        import mx.controls.Text;
        import flash.events.SecurityErrorEvent;
        import com.trs.util.FileUtil;
        import __AS3__.vec.Vector;
        import mx.binding.IBindingClient;
        import flash.events.MouseEvent;
        import flash.external.ExternalInterface;
        import flash.net.URLRequest;
        import mx.core.ClassFactory;
        import flash.net.FileReference;
        import flash.events.DataEvent;
        import mx.core.IFactory;
        import mx.controls.Button;
        import mx.controls.ProgressBar;
        import mx.core.DeferredInstanceFromFunction;
        import flash.events.EventDispatcher;
        import mx.core.Application;
        import flash.events.HTTPStatusEvent;
        import flash.net.URLLoader;
        import com.trs.util.URLUtils;
        import mx.controls.Alert;
        import mx.containers.HBox;
        import mx.binding.BindingManager;
        import mx.core.IDeferredInstance;
        import com.adobe.serialization.json.JSON;
        import mx.core.IPropertyChangeNotifier;
        import flash.events.IEventDispatcher;
        import com.trs.util.Format;
        import mx.core.mx_internal;
        import flash.net.FileFilter;
        import flash.events.Event;

        // writeWatcher id=0 shouldWriteSelf=true class=flex2.compiler.as3.binding.PropertyWatcher shouldWriteChildren=true
        watchers[0] = new mx.binding.PropertyWatcher("fileInfo",
                                                                             {
                propertyChange: true
            }
,
                                                                         // writeWatcherListeners id=0 size=1
        [
        bindings[0]
        ]
,
                                                                 propertyGetter
);


        // writeWatcherBottom id=0 shouldWriteSelf=true class=flex2.compiler.as3.binding.PropertyWatcher
        watchers[0].updateParent(target);

 





    }
}

}
