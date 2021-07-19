package com.gwssi.advancedSearch.model;

import java.util.Date;

import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
@Entity
@Table(name = "DC_COLUMN")
public class DcColumn {

	private String 	pk_dc_column;
	private String 	pk_dc_data_source;
	private String 	pk_dc_table;
	private String 	column_name_en;
	private String 	column_name_cn;
	private String 	column_code;
	private String 	column_type;
	private Integer column_length;
	private String 	is_null;
	private String 	is_primary_key;
	private String	is_index;
	private String 	is_order_by;
	private String 	is_check;
	private Integer order_no;
	private String 	remarks;
	private String 	effective_marker;
	private String 	creater_id;
	private String 	creater_name;
	private Date 	creater_time;
	private String 	modifier_id;
	private String 	modifier_name;
	private Date 	modifier_time;
	private String 	is_standard;
	private String 	standard_code;
	private String 	is_codedata;
	private String 	pk_dc_standard_dataelement;
	private String 	pk_dc_standar_codeindex;
	private String 	dc_dm_id;
	public DcColumn(String pk_dc_column, String pk_dc_data_source, String pk_dc_table, String column_name_en,
			String column_name_cn, String column_code, String column_type, Integer column_length, String is_null,
			String is_primary_key, String is_index, String is_order_by, String is_check, Integer order_no,
			String remarks, String effective_marker, String creater_id, String creater_name, Date creater_time,
			String modifier_id, String modifier_name, Date modifier_time, String is_standard, String standard_code,
			String is_codedata, String pk_dc_standard_dataelement, String pk_dc_standar_codeindex, String dc_dm_id) {
		this.pk_dc_column = pk_dc_column;
		this.pk_dc_data_source = pk_dc_data_source;
		this.pk_dc_table = pk_dc_table;
		this.column_name_en = column_name_en;
		this.column_name_cn = column_name_cn;
		this.column_code = column_code;
		this.column_type = column_type;
		this.column_length = column_length;
		this.is_null = is_null;
		this.is_primary_key = is_primary_key;
		this.is_index = is_index;
		this.is_order_by = is_order_by;
		this.is_check = is_check;
		this.order_no = order_no;
		this.remarks = remarks;
		this.effective_marker = effective_marker;
		this.creater_id = creater_id;
		this.creater_name = creater_name;
		this.creater_time = creater_time;
		this.modifier_id = modifier_id;
		this.modifier_name = modifier_name;
		this.modifier_time = modifier_time;
		this.is_standard = is_standard;
		this.standard_code = standard_code;
		this.is_codedata = is_codedata;
		this.pk_dc_standard_dataelement = pk_dc_standard_dataelement;
		this.pk_dc_standar_codeindex = pk_dc_standar_codeindex;
		this.dc_dm_id = dc_dm_id;
	}
	public String getPk_dc_column() {
		return pk_dc_column;
	}
	public void setPk_dc_column(String pk_dc_column) {
		this.pk_dc_column = pk_dc_column;
	}
	public String getPk_dc_data_source() {
		return pk_dc_data_source;
	}
	public void setPk_dc_data_source(String pk_dc_data_source) {
		this.pk_dc_data_source = pk_dc_data_source;
	}
	public String getPk_dc_table() {
		return pk_dc_table;
	}
	public void setPk_dc_table(String pk_dc_table) {
		this.pk_dc_table = pk_dc_table;
	}
	public String getColumn_name_en() {
		return column_name_en;
	}
	public void setColumn_name_en(String column_name_en) {
		this.column_name_en = column_name_en;
	}
	public String getColumn_name_cn() {
		return column_name_cn;
	}
	public void setColumn_name_cn(String column_name_cn) {
		this.column_name_cn = column_name_cn;
	}
	public String getColumn_code() {
		return column_code;
	}
	public void setColumn_code(String column_code) {
		this.column_code = column_code;
	}
	public String getColumn_type() {
		return column_type;
	}
	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}
	public Integer getColumn_length() {
		return column_length;
	}
	public void setColumn_length(Integer column_length) {
		this.column_length = column_length;
	}
	public String getIs_null() {
		return is_null;
	}
	public void setIs_null(String is_null) {
		this.is_null = is_null;
	}
	public String getIs_primary_key() {
		return is_primary_key;
	}
	public void setIs_primary_key(String is_primary_key) {
		this.is_primary_key = is_primary_key;
	}
	public String getIs_index() {
		return is_index;
	}
	public void setIs_index(String is_index) {
		this.is_index = is_index;
	}
	public String getIs_order_by() {
		return is_order_by;
	}
	public void setIs_order_by(String is_order_by) {
		this.is_order_by = is_order_by;
	}
	public String getIs_check() {
		return is_check;
	}
	public void setIs_check(String is_check) {
		this.is_check = is_check;
	}
	public Integer getOrder_no() {
		return order_no;
	}
	public void setOrder_no(Integer order_no) {
		this.order_no = order_no;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getEffective_marker() {
		return effective_marker;
	}
	public void setEffective_marker(String effective_marker) {
		this.effective_marker = effective_marker;
	}
	public String getCreater_id() {
		return creater_id;
	}
	public void setCreater_id(String creater_id) {
		this.creater_id = creater_id;
	}
	public String getCreater_name() {
		return creater_name;
	}
	public void setCreater_name(String creater_name) {
		this.creater_name = creater_name;
	}
	public Date getCreater_time() {
		return creater_time;
	}
	public void setCreater_time(Date creater_time) {
		this.creater_time = creater_time;
	}
	public String getModifier_id() {
		return modifier_id;
	}
	public void setModifier_id(String modifier_id) {
		this.modifier_id = modifier_id;
	}
	public String getModifier_name() {
		return modifier_name;
	}
	public void setModifier_name(String modifier_name) {
		this.modifier_name = modifier_name;
	}
	public Date getModifier_time() {
		return modifier_time;
	}
	public void setModifier_time(Date modifier_time) {
		this.modifier_time = modifier_time;
	}
	public String getIs_standard() {
		return is_standard;
	}
	public void setIs_standard(String is_standard) {
		this.is_standard = is_standard;
	}
	public String getStandard_code() {
		return standard_code;
	}
	public void setStandard_code(String standard_code) {
		this.standard_code = standard_code;
	}
	public String getIs_codedata() {
		return is_codedata;
	}
	public void setIs_codedata(String is_codedata) {
		this.is_codedata = is_codedata;
	}
	public String getPk_dc_standard_dataelement() {
		return pk_dc_standard_dataelement;
	}
	public void setPk_dc_standard_dataelement(String pk_dc_standard_dataelement) {
		this.pk_dc_standard_dataelement = pk_dc_standard_dataelement;
	}
	public String getPk_dc_standar_codeindex() {
		return pk_dc_standar_codeindex;
	}
	public void setPk_dc_standar_codeindex(String pk_dc_standar_codeindex) {
		this.pk_dc_standar_codeindex = pk_dc_standar_codeindex;
	}
	public String getDc_dm_id() {
		return dc_dm_id;
	}
	public void setDc_dm_id(String dc_dm_id) {
		this.dc_dm_id = dc_dm_id;
	}
	
	
	
}
