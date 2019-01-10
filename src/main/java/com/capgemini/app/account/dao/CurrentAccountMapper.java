package com.capgemini.app.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.capgemini.app.account.CurrentAccount;

public class CurrentAccountMapper implements RowMapper<CurrentAccount> {

	public CurrentAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		CurrentAccount account = new CurrentAccount(rs.getInt("account_id"), rs.getString("account_hn"),
				rs.getDouble("account_bal"), rs.getDouble("odlimit"));
		return account;
	}

}
