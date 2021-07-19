package com.ly.pojo;

//https://code.juhe.cn/docs/2002

//https://www.liangzl.com/get-article-detail-14585.html
//https://wenku.baidu.com/view/aa3ebcb827284b73f24250d8.html
//https://blog.csdn.net/liuyukuan/article/details/53560278
//https://blog.csdn.net/metal1/article/details/44357895

/*新增


DROP TRIGGER IF EXISTS insert_WeiTuo;
CREATE TRIGGER insert_WeiTuo BEFORE insert ON WeiTuo
 FOR EACH ROW
BEGIN
if new.`code` LIKE '000%' or new.code like '001%' THEN
set new.marketType ='4609';
set new.exchange_type='2';
elseif new.`code` LIKE '002%' THEN
set new.marketType ='4614';
set new.exchange_type='2';
elseif new.`code` LIKE '300%' THEN
set new.marketType ='4621';
set new.exchange_type='2';
elseif new.`code` LIKE '60%' THEN
set new.marketType ='4353';
set new.exchange_type='1';
end if;
end




 */

/*更新
DROP TRIGGER IF EXISTS update_WeiTuo;
CREATE TRIGGER update_WeiTuo BEFORE update ON WeiTuo
 FOR EACH ROW
BEGIN
if new.`code` LIKE '000%' THEN
set new.marketType ='4609';
set new.exchange_type='2';
elseif new.`code` LIKE '002%' THEN
set new.marketType ='4614';
set new.exchange_type='2';
elseif new.`code` LIKE '300%' THEN
set new.marketType ='4621';
set new.exchange_type='2';
elseif new.`code` LIKE '60%' THEN
set new.marketType ='4353';
set new.exchange_type='1';
end if;
end
*/

public class ChiCang  implements java.io.Serializable {
	private static final long serialVersionUID = -3859526119096611813L;

	private String code;
	private String name;
	private String marketType;
	private String exchange_type;
	private String cost_price;
	private String income_balance_ratio;
	private String market_value;
	private String current_amount;
	private String enable_amount;
	private String last_price;
	private String income_balance;
	private String enroute_amount;
	private Integer count;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMarketType() {
		return marketType;
	}

	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	public String getExchange_type() {
		return exchange_type;
	}

	public void setExchange_type(String exchange_type) {
		this.exchange_type = exchange_type;
	}

	public String getCost_price() {
		return cost_price;
	}

	public void setCost_price(String cost_price) {
		this.cost_price = cost_price;
	}

	public String getIncome_balance_ratio() {
		return income_balance_ratio;
	}

	public void setIncome_balance_ratio(String income_balance_ratio) {
		this.income_balance_ratio = income_balance_ratio;
	}

	public String getMarket_value() {
		return market_value;
	}

	public void setMarket_value(String market_value) {
		this.market_value = market_value;
	}

	public String getCurrent_amount() {
		return current_amount;
	}

	public void setCurrent_amount(String current_amount) {
		this.current_amount = current_amount;
	}

	public String getEnable_amount() {
		return enable_amount;
	}

	public void setEnable_amount(String enable_amount) {
		this.enable_amount = enable_amount;
	}

	public String getLast_price() {
		return last_price;
	}

	public void setLast_price(String last_price) {
		this.last_price = last_price;
	}

	public String getIncome_balance() {
		return income_balance;
	}

	public void setIncome_balance(String income_balance) {
		this.income_balance = income_balance;
	}

	public String getEnroute_amount() {
		return enroute_amount;
	}

	public void setEnroute_amount(String enroute_amount) {
		this.enroute_amount = enroute_amount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
