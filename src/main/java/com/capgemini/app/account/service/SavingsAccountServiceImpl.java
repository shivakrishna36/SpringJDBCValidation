package com.capgemini.app.account.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.app.account.SavingsAccount;
import com.capgemini.app.account.dao.SavingsAccountDAO;
//import com.capgemini.app.account.dao.SavingsAccountDAOImpl;
import com.capgemini.app.account.factory.AccountFactory;
//import com.capgemini.app.account.util.DBUtil;
import com.capgemini.app.exception.AccountNotFoundException;
import com.capgemini.app.exception.InsufficientFundsException;
import com.capgemini.app.exception.InvalidInputException;

@Service
public class SavingsAccountServiceImpl implements SavingsAccountService {

	private AccountFactory factory;
	@Autowired
	private SavingsAccountDAO savingsAccountDAO;

	public SavingsAccountServiceImpl() {
		factory = AccountFactory.getInstance();

	}

	@Override
	public SavingsAccount createNewAccount(String accountHolderName, double accountBalance, boolean salary)
			throws ClassNotFoundException, SQLException {
		SavingsAccount account = factory.createNewSavingsAccount(accountHolderName, accountBalance, salary);
		return savingsAccountDAO.createNewAccount(account);

	}

	@Override
	public List<SavingsAccount> getAllSavingsAccount() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getAllSavingsAccount();
	}

	@Override
	public void deposit(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {

		double currentBalance = account.getBankAccount().getAccountBalance();
		currentBalance += amount;
		savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);

	}

	@Override
	public void withdraw(SavingsAccount account, double amount) throws ClassNotFoundException, SQLException {
		double currentBalance = account.getBankAccount().getAccountBalance();

		currentBalance -= amount;
		savingsAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);

	}

	@Transactional
	@Override
	public void fundTransfer(SavingsAccount sender, SavingsAccount receiver, double amount)
			throws ClassNotFoundException, SQLException {
		try {
			withdraw(sender, amount);
			deposit(receiver, amount);
			// DBUtil.commit();
		} catch (InvalidInputException | InsufficientFundsException e) {
			e.printStackTrace();
			// DBUtil.rollback();
		} catch (Exception e) {
			e.printStackTrace();
			// DBUtil.rollback();
		}
	}

	@Override
	public SavingsAccount updateAccount(SavingsAccount account, String name, boolean value)
			throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.updateAccount(account, name, value);

	}

	@Override
	public SavingsAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.getAccountById(accountNumber);
	}

	@Override
	public String deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.deleteAccount(accountNumber);

	}

	@Override
	public SavingsAccount searchAccount(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.searchAccount(accountNumber);
	}

	@Override
	public double checkCurrentBalance(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.checkCurrentBalance(accountNumber);
	}

	@Override
	public SavingsAccount searchAccountByName(String accountHolderName)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return savingsAccountDAO.searchAccountByName(accountHolderName);

	}

	@Override
	public List<SavingsAccount> sortByAccountHolderName() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.sortByAccountHolderName();
	}

	@Override
	public List<SavingsAccount> sortBySalaryRange(int minimunbalance, int maximumbalance)
			throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.sortBySalaryRange(minimunbalance, maximumbalance);

	}

	@Override
	public List<SavingsAccount> sortBySalaryLessthanGivenInput(int amount) throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.sortByLessthanGivenSalary(amount);
	}

	@Override
	public List<SavingsAccount> sortBySalaryGreaterthanGivenInput(int maximumAmount)
			throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.sortByGreaterthanGivenSalary(maximumAmount);
	}

	@Override
	public List<SavingsAccount> getAccountByRange(double minimum, double maximum)
			throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.getByAccountBalanceRange(minimum, maximum);
	}

	@Override
	public List<SavingsAccount> sortBySalary() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.sortBySalary();
	}

	@Override
	public List<SavingsAccount> sortBySalaried() throws ClassNotFoundException, SQLException {
		return savingsAccountDAO.sortBySalaried();
	}

}
