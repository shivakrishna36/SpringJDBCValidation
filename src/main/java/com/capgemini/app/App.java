package com.capgemini.app;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.capgemini.app.account.ui.AccountCUI;
import com.capgemini.app.account.ui.CurrentAccountCUI;

public class App {
	

	public static void main(String[] args) {
		do
		{
			ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
			AccountCUI accountCUI = context.getBean(AccountCUI.class);
			CurrentAccountCUI currentCUI = context.getBean(CurrentAccountCUI.class);
			Scanner scanner = new Scanner(System.in);
			System.out.println("Enter 1 for Savings Account Menu");
			System.out.println("Enter 2 for Current Account Menu");
			System.out.println("Select Your Choice");
			int number = scanner.nextInt();
			if(number == 1)
					accountCUI.startSavingsAccount();
			else
				currentCUI.startCurrentAccount();
			scanner.close();
		}while(true);
	}

}
