package com.gwssi.share.service.vo;

public class VoTableColumns
{
	String table_name_cn ;
	String table_name_en ;
	String column_name_en ;
	String column_name_cn ;
	String column_name_alias ;
	String column_type;
	String column_length ;

	
	
	public String get_column_length()
	{
		return column_length;
	}

	public void set_column_length( String value )
	{
		column_length = value;
	}
	
	public String get_column_type()
	{
		return column_type;
	}

	public void set_column_type( String value )
	{
		column_type = value;
	}
	
	public String get_column_name_alias()
	{
		return column_name_alias;
	}

	public void set_column_name_alias( String value )
	{
		column_name_alias = value;
	}
	
	public String get_column_name_cn()
	{
		return column_name_cn;
	}

	public void set_column_name_cn( String value )
	{
		column_name_cn = value;
	}
	
	public String get_table_name_cn()
	{
		return table_name_cn;
	}

	public void set_table_name_cn( String value )
	{
		table_name_cn = value;
	}
	
	public String get_table_name_en()
	{
		return table_name_en;
	}

	public void set_table_name_en( String value )
	{
		table_name_en = value;
	}
	
	public String get_column_name_en()
	{
		return column_name_en;
	}

	public void set_column_name_en( String value )
	{
		column_name_en = value;
	}
}
