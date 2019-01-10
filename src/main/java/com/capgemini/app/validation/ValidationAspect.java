
package com.capgemini.app.validation;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.capgemini.app.account.SavingsAccount;

@Aspect
@Component
public class ValidationAspect {

	private static Logger logger = Logger.getLogger(ValidationAspect.class.getName());
	
	@Around("execution(* com.capgemini.app.account.service.SavingsAccountService.withdraw(..))")
	public void withdrawAspect(ProceedingJoinPoint pjp) throws Throwable
	{
		logger.info("Before the method:");
		Object[] params = pjp.getArgs();
		SavingsAccount account = (SavingsAccount) params[0];
		double currentBalance = account.getBankAccount().getAccountBalance();
		double amount = (double) params[1];

		if (amount > 0 && currentBalance >= amount) {
			pjp.proceed();
			logger.info("Withdrawn Successfully and current balance is:"+(currentBalance-amount));
		}
		
		else 
		{
			logger.info("Invalid Input or Insufficient Funds!");
		}
	}
	
	@Around("execution(* com.capgemini.app.account.service.SavingsAccountService.deposit(..))")
	public void depositAspect(ProceedingJoinPoint pjp) throws Throwable
	{
		logger.info("Before the method:");
		Object[] params = pjp.getArgs();
		SavingsAccount account = (SavingsAccount) params[0];
		double currentBalance = account.getBankAccount().getAccountBalance();
		double amount = (double) params[1];
		if (amount > 0) {
			pjp.proceed();
			logger.info("Deposited Successfully And current balance is:"+(amount+currentBalance));
		}
		else 
		{
			logger.info("Invalid Input");
		}
	}
	
	
	
}
