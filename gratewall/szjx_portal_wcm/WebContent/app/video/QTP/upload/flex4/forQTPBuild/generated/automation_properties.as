package 
{

import mx.resources.ResourceBundle;

[ExcludeClass]

public class en_US$automation_properties extends ResourceBundle
{

    public function en_US$automation_properties()
    {
		 super("en_US", "automation");
    }

    override protected function getContent():Object
    {
        var content:Object =
        {
            "notReplayable": "Cannot replay KeyboardEvent with key code '{0}'."
        };
        return content;
    }
}



}
