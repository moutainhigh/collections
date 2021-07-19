package 
{

import mx.resources.ResourceBundle;

[ExcludeClass]

public class en_US$olap_properties extends ResourceBundle
{

    public function en_US$olap_properties()
    {
		 super("en_US", "olap");
    }

    override protected function getContent():Object
    {
        var content:Object =
        {
            "duplicateHierarchyOnAxes": "'{0}' appears on Axis '{1}' and on Axis '{2}'",
            "crossJoinSameHierarchyError": "'{0}' found in both sets.",
            "dimensionProcessingMessage": "Processing dimension: {0}",
            "noAttributeForLevel": "No attribute has been specified for a level in '{0}' hierarchy.",
            "noMeasures": "No measures have been specified for the cube",
            "multipleHierarchies": "Dimension contains multiple hierarchies. A hierarchy should be specified explicitly.",
            "multipleHierarchiesNotSupported": "Attempt to add multiple user defined hierarchies to the dimension '{0}'. Only a single user defined hierarchy is supported in this version.",
            "zeroElementsOnAxis": "No members found on Axis '{0}'.",
            "unionError": "Sets do not have same dimensionality. Union cannot be peformed.",
            "finalizingMessage": "Finalizing...Please wait.",
            "invalidAttributeName": "A attribute with the name '{0}' is not available on the dimension.",
            "progressMessage": "Processing data Row: {0} of {1}",
            "nullMemberOnAxis": "Attempt was made to add a null member to the Axis '{1}'",
            "invalidAggregator": "Invalid aggregator value '{0}'. The possible values are 'SUM', 'AVG', 'MIN', 'MAX' and 'COUNT'."
        };
        return content;
    }
}



}
