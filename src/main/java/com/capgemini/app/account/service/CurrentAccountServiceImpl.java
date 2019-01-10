package com.capgemini.app.account.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.app.account.CurrentAccount;
import com.capgemini.app.account.dao.CurrentAccountDAO;
//import com.capgemini.app.account.dao.CurrentAccountDAOImpl;
import com.capgemini.app.account.factory.AccountFactory;
//import com.capgemini.app.account.util.DBUtil;
import com.capgemini.app.exception.AccountNotFoundException;
import com.capgemini.app.exception.InsufficientFundsException;
import com.capgemini.app.exception.InvalidInputException;

@Service
public class CurrentAccountServiceImpl implements CurrentAccountService {

	private AccountFactory factory;
	@Autowired
	private CurrentAccountDAO currentAccountDAO;

	public CurrentAccountServiceImpl() {
		factory = AccountFactory.getInstance();

	}

	public CurrentAccount createNewAccount(String accountHolderName, double accountBalance, double odlimit)
			throws ClassNotFoundException, SQLException {
		CurrentAccount account = factory.createNewCurrentAccount(accountHolderName, accountBalance, odlimit);

		return currentAccountDAO.createNewAccount(account);
	}

	public CurrentAccount updateAccount(CurrentAccount account, String accountHolderName, double odlimit)
			throws ClassNotFoundException, SQLException {
		return currentAccountDAO.updateAccount(account, accountHolderName, odlimit);
	}

	public CurrentAccount getAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return currentAccountDAO.getAccountById(accountNumber);
	}

	public String deleteAccount(int accountNumber) throws ClassNotFoundException, SQLException {
		return currentAccountDAO.deleteAccount(accountNumber);
	}

	public List<CurrentAccount> getAllCurrentAccounts() throws ClassNotFoundException, SQLException {
		return currentAccountDAO.getAllCurrentAccounts();
	}

	@Transactional

	public void fundTransfer(CurrentAccount sender, CurrentAccount receiver, double amount)
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
	public void deposit(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException {

		double currentBalance = account.getBankAccount().getAccountBalance();
		currentBalance += amount;
		currentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);

	}

	@Override
	public void withdraw(CurrentAccount account, double amount) throws ClassNotFoundException, SQLException {
		double currentBalance = account.getBankAccount().getAccountBalance();

		currentBalance -= amount;
		currentAccountDAO.updateBalance(account.getBankAccount().getAccountNumber(), currentBalance);

	}

	@Override
	public CurrentAccount searchAccountById(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return currentAccountDAO.searchAccountByAccountNumber(accountNumber);
	}

	@Override
	public double checkCurrentBalance(int accountNumber)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return currentAccountDAO.checkCurrentBalance(accountNumber);
	}

	@Override
	public CurrentAccount searchAccountByName(String accountHolderName)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {
		return currentAccountDAO.searchAccountByName(accountHolderName);
	}

	@Override
	public List<CurrentAccount> sortByAccountHolderName() throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortByAccountHolderName();
	}

	@Override
	public List<CurrentAccount> sortBySalaryRange(int minimunbalance, int maximumbalance)
			throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortBySalaryRange(minimunbalance, maximumbalance);
	}

	@Override
	public List<CurrentAccount> sortBySalaryLessthanGivenInput(int amount) throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortBySalaryLessthanGivenInput(amount);
	}

	@Override
	public List<CurrentAccount> sortBySalaryGreaterthanGivenInput(int maximumAmount)
			throws ClassNotFoundException, SQLException {
		return currentAccountDAO.sortBySalaryGreaterthanGivenInput(maximumAmount);
	}

	@Override
	public List<CurrentAccount> getAccountByRange(double minimum, double maximum)
			throws ClassNotFoundException, SQLException, AccountNotFoundException {

		return currentAccountDAO.getAccountByRange(minimum, maximum);
	}
}
