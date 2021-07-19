package com.gwssi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//https://www.cnblogs.com/softidea/p/5833248.html
@Controller
public class MenuController {
	@RequestMapping({ "index" })
	public String doIndex() {
		return "data/index";
	}

	@RequestMapping({ "data01" })
	public String data01() {
		return "data/01";
	}

	@RequestMapping({ "data02" })
	public String data02() {
		return "data/02";
	}

	@RequestMapping({ "data03" })
	public String data03() {
		return "data/03";
	}

	@RequestMapping({ "data04" })
	public String data04() {
		return "data/04";
	}

	@RequestMapping({ "data05" })
	public String data05() {
		return "data/05";
	}

	@RequestMapping({ "data06" })
	public String data06() {
		return "data/06";
	}

	@RequestMapping({ "data07" })
	public String data07() {
		return "data/07";
	}

	@RequestMapping({ "data08" })
	public String data08() {
		return "data/08";
	}

	@RequestMapping({ "data09" })
	public String data09() {
		return "data/09";
	}

	@RequestMapping({ "data10" })
	public String data10() {
		return "data/10";
	}

	@RequestMapping({ "data11" })
	public String data11() {
		return "data/11";
	}

	@RequestMapping({ "data12" })
	public String data12() {
		return "data/12";
	}

	@RequestMapping({ "data13" })
	public String data13() {
		return "data/13";
	}

	@RequestMapping({ "data14" })
	public String data14() {
		return "data/14";
	}

	@RequestMapping({ "data15" })
	public String data15() {
		return "data/15";
	}

	@RequestMapping({ "data16" })
	public String data16() {
		return "data/16";
	}

	@RequestMapping({ "data17" })
	public String data17() {
		return "data/17";
	}

	@RequestMapping({ "data18" })
	public String data18() {
		return "data/18";
	}

	@RequestMapping({ "data19" })
	public String data19() {
		return "data/19";
	}
	
	
	@RequestMapping({ "data20" })
	public String data20() {
		return "data/20";
	}
	
	@RequestMapping({ "data21" })
	public String data21() {
		return "data/21";
	}
}