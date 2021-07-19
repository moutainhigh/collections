package com.gwssi.advancedSearch.model;

import java.util.Date;

import com.gwssi.optimus.core.persistence.annotation.Entity;
import com.gwssi.optimus.core.persistence.annotation.Table;
@Entity
@Table(name = "DC_TABLE")
public class DcTable {
	//该表对应db_dc用户中的DC_TABLE表
	private String 	pk_dc_table;
	private String 	pk_dc_data_source;
	private String 	table_name_en;
	private String 	table_name_cn;
	private String 	table_code;
	private String 	table_type;
	private Integer first_record_count;
	private Integer last_record_count;
	private Date 	first_load_time;
	private Date 	last_load_time;
	private String 	table_use_desc;
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
	private String 	is_share;			
	private String 	is_query;			//是否可查询
	private String 	topic;				//所属主题
	public DcTable(String pk_dc_table, String pk_dc_data_source, String table_name_en, String table_name_cn,
			String table_code, String table_type, Integer first_record_count, Integer last_record_count,
			Date first_load_time, Date last_load_time, String table_use_desc, String is_check, Integer order_no,
			String remarks, String effective_marker, String creater_id, String creater_name, Date creater_time,
			String modifier_id, String modifier_name, Date modifier_time, String is_share, String is_query,
			String topic) {
		this.pk_dc_table = pk_dc_table;
		this.pk_dc_data_source = pk_dc_data_source;
		this.table_name_en = table_name_en;
		this.table_name_cn = table_name_cn;
		this.table_code = table_code;
		this.table_type = table_type;
		this.first_record_count = first_record_count;
		this.last_record_count = last_record_count;
		this.first_load_time = first_load_time;
		this.last_load_time = last_load_time;
		this.table_use_desc = table_use_desc;
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
		this.is_share = is_share;
		this.is_query = is_query;
		this.topic = topic;
	}
	public String getPk_dc_table() {
		return pk_dc_table;
	}
	public void setPk_dc_table(String pk_dc_table) {
		this.pk_dc_table = pk_dc_table;
	}
	public String getPk_dc_data_source() {
		return pk_dc_data_source;
	}
	public void setPk_dc_data_source(String pk_dc_data_source) {
		this.pk_dc_data_source = pk_dc_data_source;
	}
	public String getTable_name_en() {
		return table_name_en;
	}
	public void setTable_name_en(String table_name_en) {
		this.table_name_en = table_name_en;
	}
	public String getTable_name_cn() {
		return table_name_cn;
	}
	public void setTable_name_cn(String table_name_cn) {
		this.table_name_cn = table_name_cn;
	}
	public String getTable_code() {
		return table_code;
	}
	public void setTable_code(String table_code) {
		this.table_code = table_code;
	}
	public String getTable_type() {
		return table_type;
	}
	public void setTable_type(String table_type) {
		this.table_type = table_type;
	}
	public Integer getFirst_record_count() {
		return first_record_count;
	}
	public void setFirst_record_count(Integer first_record_count) {
		this.first_record_count = first_record_count;
	}
	public Integer getLast_record_count() {
		return last_record_count;
	}
	public void setLast_record_count(Integer last_record_count) {
		this.last_record_count = last_record_count;
	}
	public Date getFirst_load_time() {
		return first_load_time;
	}
	public void setFirst_load_time(Date first_load_time) {
		this.first_load_time = first_load_time;
	}
	public Date getLast_load_time() {
		return last_load_time;
	}
	public void setLast_load_time(Date last_load_time) {
		this.last_load_time = last_load_time;
	}
	public String getTable_use_desc() {
		return table_use_desc;
	}
	public void setTable_use_desc(String table_use_desc) {
		this.table_use_desc = table_use_desc;
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
	public String getIs_share() {
		return is_share;
	}
	public void setIs_share(String is_share) {
		this.is_share = is_share;
	}
	public String getIs_query() {
		return is_query;
	}
	public void setIs_query(String is_query) {
		this.is_query = is_query;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	
	
}
