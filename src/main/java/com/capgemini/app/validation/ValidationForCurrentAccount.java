package com.capgemini.app.validation;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.capgemini.app.account.CurrentAccount;
import com.capgemini.app.exception.InsufficientFundsException;

@Aspect
@Component
public class ValidationForCurrentAccount {

	private static Logger logger = Logger.getLogger(ValidationAspect.class.getName());

	@Around("execution(* com.capgemini.app.account.service.CurrentAccountService.withdraw(..))")
	public void withdrawAspect(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("Before the method:");
		Object[] params = pjp.getArgs();
		CurrentAccount account = (CurrentAccount) params[0];
		double currentBalance = account.getBankAccount().getAccountBalance();
		double amount = (double) params[1];

		if (amount > 0 && currentBalance >= (amount + account.getOdlimit())) {
			pjp.proceed();
			logger.info("Withdrawn Successfully and current balance is:" + (currentBalance - amount));
		}

		else {
			logger.info("Invalid Input or Insufficient Funds!");
		}
		
	}

	@Around("execution(* com.capgemini.app.account.service.CurrentAccountService.deposit(..))")
	public void depositAspect(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("Before the method:");
		Object[] params = pjp.getArgs();
		CurrentAccount account = (CurrentAccount) params[0];
		double currentBalance = account.getBankAccount().getAccountBalance();
		double amount = (double) params[1];
		if (amount > 0) {
			pjp.proceed();
			logger.info("Deposited Successfully And current balance is:" + (amount + currentBalance));
		} else {
			logger.info("Invalid Input");
		}
		
	}

}
