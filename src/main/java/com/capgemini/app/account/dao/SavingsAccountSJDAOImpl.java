package com.capgemini.app.account.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.capgemini.app.account.SavingsAccount;
import com.capgemini.app.exception.AccountNotFoundException;

@Repository
@Primary
public class SavingsAccountSJDAOImpl implements SavingsAccountDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public SavingsAccount createNewAccount(SavingsAccount account) {
		jdbcTemplate.update("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)",
				new Object[] { account.getBankAccount().getAccountNumber(),
						account.getBankAccount().getAccountHolderName(), account.getBankAccount().getAccountBalance(),
						account.isSalary(), null, "SA" });
		return account;
	}

	
	public SavingsAccount updateAccount(SavingsAccount account, String name, boolean value) {
		jdbcTemplate.update("update account set account_hn=?,salary=? where account_id=?",
				new Object[] { name, value, account.getBankAccount().getAccountNumber() });
		return account;
	}

	
	public SavingsAccount getAccountById(int accountNumber) {
		return jdbcTemplate.queryForObject("SELECT * from account WHERE account_id=?", new Object[] { accountNumber },
				new SavingsAccountMapper());
	}

	
	public String deleteAccount(int accountNumber) {
		jdbcTemplate.update("Delete From account WHERE account_id=?", new Object[] { accountNumber });
		return "Account deleted";
	}

	
	public List<SavingsAccount> getAllSavingsAccount() {
		return jdbcTemplate.query("SELECT * from account", new SavingsAccountMapper());
	}

	
	public void updateBalance(int accountNumber, double currentBalance) {
		jdbcTemplate.update("UPDATE ACCOUNT SET account_bal=? where account_id=?",
				new Object[] { currentBalance, accountNumber });
	}

	
	public void commit() throws SQLException {
		// TODO Auto-generated method stub

	}

	
	public SavingsAccount searchAccount(int accountNumber) {
		return jdbcTemplate.queryForObject("SELECT * from account WHERE account_id=?", new Object[] { accountNumber },
				new SavingsAccountMapper());

	}

	
	public double checkCurrentBalance(int accountNumber) {

		return jdbcTemplate.queryForObject("select account_bal from account WHERE account_id=?",
				new Object[] { accountNumber }, Double.class);
	}

	
	public SavingsAccount searchAccountByName(String accountHolderName) {
		return jdbcTemplate.queryForObject("SELECT * from account WHERE account_hn=?",
				new Object[] { accountHolderName }, new SavingsAccountMapper());

	}

	
	public List<SavingsAccount> sortByAccountHolderName() {
		return jdbcTemplate.query("SELECT * from account ORDER BY account_hn", new SavingsAccountMapper());
	}

	
	public List<SavingsAccount> sortBySalaryRange(int minimunbalance, int maximumbalance) {
		return jdbcTemplate.query("SELECT * from account WHERE account_bal BETWEEN ? AND ?",
				new Object[] { minimunbalance, maximumbalance }, new SavingsAccountMapper());
	}

	
	public List<SavingsAccount> sortByLessthanGivenSalary(int amount) {
		return jdbcTemplate.query("SELECT * from account WHERE account_bal<=?", new Object[] { amount },
				new SavingsAccountMapper());
	}

	
	public List<SavingsAccount> sortByGreaterthanGivenSalary(int amount) {
		return jdbcTemplate.query("SELECT * from account WHERE account_bal>=?", new Object[] { amount },
				new SavingsAccountMapper());
	}

	
	public List<SavingsAccount> getByAccountBalanceRange(double minimum, double maximum) {
		return jdbcTemplate.query("SELECT * from account WHERE account_bal BETWEEN ? AND ?",
				new Object[] { minimum, maximum }, new SavingsAccountMapper());

	}

	
	public List<SavingsAccount> sortBySalary() {
		return jdbcTemplate.query("SELECT * from account ORDER BY account_bal", new SavingsAccountMapper());
	}

	
	public List<SavingsAccount> sortBySalaried() {
		return jdbcTemplate.query("SELECT * from account ORDER BY salary", new SavingsAccountMapper());
	}

}
