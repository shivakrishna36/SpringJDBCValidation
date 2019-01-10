package com.capgemini.app.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.capgemini.app.account.SavingsAccount;

public class SavingsAccountMapper implements RowMapper<SavingsAccount>{

	
	public SavingsAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		SavingsAccount account = new SavingsAccount(rs.getInt("account_id"),rs.getString("account_hn"),rs.getDouble("account_bal"),rs.getBoolean("salary"));
		return account;
	}

}
