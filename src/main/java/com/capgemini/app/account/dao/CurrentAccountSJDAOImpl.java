package com.capgemini.app.account.dao;

import java.sql.SQLException;
import java.util.List;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.capgemini.app.account.CurrentAccount;
import com.capgemini.app.exception.AccountNotFoundException;

@Repository
@Primary
public class CurrentAccountSJDAOImpl implements CurrentAccountDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public CurrentAccount createNewAccount(CurrentAccount account) {
		jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)",
				new Object[] { account.getBankAccount().getAccountNumber(),
						account.getBankAccount().getAccountHolderName(), account.getBankAccount().getAccountBalance(),
						null, account.getOdlimit(), "CA" });
		return account;
	}

	
	public List<CurrentAccount> getAllCurrentAccounts() {
		return jdbcTemplate.query("SELECT * from account", new CurrentAccountMapper());

	}

	
	public double checkCurrentBalance(int accountNumber) {
		return jdbcTemplate.queryForObject("select account_bal from account WHERE account_id=?",
				new Object[] { accountNumber }, Double.class);

	}

	
	public CurrentAccount getAccountById(int accountNumber) {
		return jdbcTemplate.queryForObject("SELECT * from account WHERE account_id=?", new Object[] { accountNumber },
				new CurrentAccountMapper());
	}

	
	public CurrentAccount updateAccount(CurrentAccount account, String accountHolderName, double odlimit) {
		jdbcTemplate.update("update account set account_hn=?,odlimit=? where account_id=?",
				new Object[] { accountHolderName, odlimit, account.getBankAccount().getAccountNumber() });
		return account;
	}

	
	public String deleteAccount(int accountNumber) {
		jdbcTemplate.update("Delete From account WHERE account_id=?", new Object[] { accountNumber });
		return "Account deleted";
	}

	
	public void updateBalance(int accountNumber, double currentBalance) {
		jdbcTemplate.update("UPDATE ACCOUNT SET account_bal=? where account_id=?",
				new Object[] { currentBalance, accountNumber });

	}

	
	public CurrentAccount searchAccountByName(String accountHolderName) {
		return jdbcTemplate.queryForObject("SELECT * from account WHERE account_hn=?",
				new Object[] { accountHolderName }, new CurrentAccountMapper());
	}

	
	public CurrentAccount searchAccountByAccountNumber(int accountNumber) {
		return jdbcTemplate.queryForObject("SELECT * from account WHERE account_id=?", new Object[] { accountNumber },
				new CurrentAccountMapper());
	}

	
	public List<CurrentAccount> getAccountByRange(double minimum, double minimum2) {
		return jdbcTemplate.query("SELECT * from account WHERE account_bal BETWEEN ? AND ?",
				new Object[] { minimum, minimum2 }, new CurrentAccountMapper());
	}

	
	public List<CurrentAccount> sortByAccountHolderName() {
		return jdbcTemplate.query("SELECT * from account ORDER BY account_hn", new CurrentAccountMapper());
	}

	
	public List<CurrentAccount> sortBySalaryRange(int minimunbalance, int maximumbalance) {
		return jdbcTemplate.query("SELECT * from account WHERE account_bal BETWEEN ? AND ?",
				new Object[] { minimunbalance, maximumbalance }, new CurrentAccountMapper());
	}

	
	public List<CurrentAccount> sortBySalaryLessthanGivenInput(int amount) {
		return jdbcTemplate.query("SELECT * from account WHERE account_bal<=?", new Object[] { amount },
				new CurrentAccountMapper());
	}

	
	public List<CurrentAccount> sortBySalaryGreaterthanGivenInput(int maximumAmount) {
		return jdbcTemplate.query("SELECT * from account WHERE account_bal>=?", new Object[] { maximumAmount },
				new CurrentAccountMapper());
	}

}
