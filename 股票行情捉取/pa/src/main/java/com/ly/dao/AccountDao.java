package com.ly.dao;

import java.util.List;
import java.util.Map;

import com.ly.pojo.Account;

public interface AccountDao {

	public List<Account> getAccountList();

	public List<Account> getGudongList();

	public Account findGudong(Map map);

	public void save(Account ac);

}
