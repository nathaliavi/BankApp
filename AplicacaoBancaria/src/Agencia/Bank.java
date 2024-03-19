package Agencia;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {

	static Scanner input = new Scanner(System.in);
	static ArrayList<Account> bankAccounts;
	static ArrayList<Transaction> bankTransactions;
	static TransactionManager transactionManager;
	
	public static void main(String[] args) {
		bankAccounts = new ArrayList<Account>();
		bankTransactions = new ArrayList<Transaction>();
//		TransactionManager transactionManager = new TransactionManager();
		actions();
	}
	
	public static void actions()  {
		System.out.println("---------------------------------------");
		System.out.println("-------- Welcome to our Bank! ---------");
		System.out.println("---------------------------------------");
		System.out.println("********* Select an option ************");
		System.out.println("| Option 1 - Create Account           |");
		System.out.println("| Option 2 - Deposit                  |"); 
		System.out.println("| Option 3 - Withdrawal               |");
		System.out.println("| Option 4 - Transfer                 |");
		System.out.println("| Option 5 - Change Limit Amount      |");
		System.out.println("| Option 6 - Accounts List            |");
		System.out.println("| Option 7 - Transactions List        |");
		System.out.println("| Option 8 - Exit                     |");
		
		int action = input.nextInt();
		
		switch(action) {
		case 1:
			createAccount();
			break;
		case 2:
			deposit();
			break;
		case 3:
			withdrawal();
			break;
		case 4:
			transfer();
			break;
		case 5:
			changeLimit();
			break;
		case 6:
			list();
			break;
		case 7:
			transactionList();
			break;
		case 8:
			System.out.println("Thank you! We hope to see you soon!");
			System.exit(0);
			break;
		default:
			System.out.println("Invalid option!");
			actions();
			break;
			
		}
	}
		
	public static void createAccount() {
		String name = validateInput("\nName: ", "[a-zA-Z]+");
			
		String email = validateInput("\nEmail: ", "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
			
		String cpfCnpj = validateInput("\nCPF/CNPJ: (only numbers)", "[0-9]+");
		
		String type = validateInput("\nAccount Type: D (Deposit), S (Salary)", "[DS]");
		
		System.out.println("Would you like to change the default agency (0001)? Y/N");
		String yesNo = input.next();
        
		String agencyNumber = "";
        if(yesNo.equalsIgnoreCase("y")) {
    		System.out.println("What is the new agency?");
    		agencyNumber = input.next();
        } else {
        	agencyNumber = "0001";
        }
        
		Client client = new Client(name, cpfCnpj, email);
		
		Account account = new Account(client, agencyNumber);
		account.setType(type);	
		
		bankAccounts.add(account);
		
		System.out.println("Your account was sucessfully created!");
		
		actions();
			
	} 
	
	private static String validateInput(String label, String regex) {
		String name = "";
        boolean isValid = false;

        while (!isValid) {
            System.out.println(label);
            name = input.next();

            if (name.matches(regex)) {
                isValid = true;
                
            } else {
                System.out.println("Invalid input. Please try again!");
            }
        }
        return name;
        
	}
		
	private static int getTime() {
		LocalTime currentTime = LocalTime.now();
		
        int hour = currentTime.getHour();
        
        return hour;
	}
			
	private static Account findAccount(int numberAccount, String agencyNumber) {
		Account account = null;
		
		if(bankAccounts.size() > 0) {
			for(Account c: bankAccounts) {
				if(c.getNumber() == numberAccount && c.getAgencyNumber().equals(agencyNumber)) {
					account = c;
				}
			}
		}
		return account;
		
	}
	
	private static String getDate() {
		LocalDate currentDate = LocalDate.now();

        // Format the date as a string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        
        return formattedDate;
	}
	
	private static Double getPossibleTransacValue(int accountNumber, String agencyNumber, String date, Double limit, String senderID) {
		Double possibleTransacValue = limit;
		bankTransactions = TransactionManager.getBankTransactions();

		Boolean transactionSend = false;
		for (Transaction transaction : bankTransactions) {
			transactionSend = transaction.getDate().equals(date) && (transaction.getDescription().equals("Withdrawal") || transaction.getDescription().equals("Transfer") ) && transaction.getSender().equals(senderID);
			if (transactionSend) {
	            possibleTransacValue -= transaction.getValue();
	            System.out.println("Dentro for: " + possibleTransacValue);
	        }
	    }
		
		System.out.println("Fora for" + possibleTransacValue);
		return possibleTransacValue;
	}
	
	public static void deposit() {
		System.out.println("Account number?");
		int accountNumber = input.nextInt();
		
		System.out.println("Agency number?");
		String agencyNumber = input.next();
		
		Account account = findAccount(accountNumber, agencyNumber);
		
		String date = getDate();
		
		if(account != null) {
			System.out.println("Deposit value?");
			Double value = input.nextDouble();
			
			account.deposit(value);
			Transaction t = new Transaction(date, "Deposit", value, "External", account.getClient().getCNPJCPF());
			TransactionManager.addTransaction(t);
			
		} else {
			System.out.println("Invalid account!");
		}
		
		actions();
	}
	
	
	public static void withdrawal() {
		System.out.println("Account number?");
		int accountNumber = input.nextInt();
		
		System.out.println("Agency number?");
		String agencyNumber = input.next();
		
		Account account = findAccount(accountNumber, agencyNumber);
		
		String date = getDate();
		
		int hour = getTime();
		
		Double limit;
		
		if(hour > 22) {
			limit = account.getLimit_night();
		} else {
			limit = account.getLimit_day();
		}
		
		String senderID = account.getClient().getCNPJCPF();
		
		Double possibleTransacValue = getPossibleTransacValue(accountNumber, agencyNumber, date, limit, senderID);
		
		if(account != null) {
			System.out.println("Withdrawal value?");
			Double value = input.nextDouble();
			
			if(value > possibleTransacValue ) {
				System.out.println("Limit exceeded!");
			} else {
				account.withdrawal(value);
				Transaction t = new Transaction(date, "Withdrawal", value, account.getClient().getCNPJCPF(), "External");
				TransactionManager.addTransaction(t);
			}
			
		} else {
			System.out.println("Invalid account!");
		}
		
		actions();
	}
	
	public static void transfer() {
		System.out.println("Sender account number?");
		int senderAccountNumber = input.nextInt();
		
		System.out.println("Sender agency number?");
		String senderAgencyNumber = input.next();
		
		Account senderAccount = findAccount(senderAccountNumber, senderAgencyNumber);
		System.out.println(senderAccount);
		String date = getDate();
		
		int hour = getTime();
		
		Double limit;
		
		if(hour > 22) {
			limit = senderAccount.getLimit_night();
		} else {
			limit = senderAccount.getLimit_day();
		}
		
		String senderID = senderAccount.getClient().getCNPJCPF();
		
		Double possibleTransacValue = getPossibleTransacValue(senderAccountNumber, senderAgencyNumber, date, limit, senderID);
		
		
		if (senderAccount != null) {
			if (senderAccount.getType().equals("D")) {
				System.out.println("Receiver account number?");
				int receiverAccountNumber = input.nextInt();
				
				System.out.println("Receiver agency number?");
				String receiverAgencyNumber = input.next();

				Account receiverAccount = findAccount(receiverAccountNumber, receiverAgencyNumber);

				if (receiverAccount != null) {
					System.out.println("Transfer value?");
					Double value = input.nextDouble();
					
					if( value > possibleTransacValue) {
						System.out.println("Limit exceeded!");
					} else {						
						senderAccount.transfer(receiverAccount, value);
						
						Transaction t = new Transaction(date, "Transfer", value, senderAccount.getClient().getCNPJCPF(), receiverAccount.getClient().getCNPJCPF());
						TransactionManager.addTransaction(t);
					}
				} else {
					System.out.println("Invalid account!");
				}
			} else {
				System.out.println("Only deposit accounts can transfer money!");
			}

		} else {
			System.out.println("Invalid account!");
		}
		
		actions();
	}
	
	public static void list() {
		if(bankAccounts.size() > 0 ) {
			for(Account account:bankAccounts) {
				System.out.println(account);
			}
		} else {
			System.out.println("There are no accounts registered.");
		}
		
		actions();
	}
	
	public static void changeLimit() {
		System.out.println("Account number?");
		int accountNumber = input.nextInt();
		
		System.out.println("Agency number?");
		String agencyNumber = input.next();
		
		Account account = findAccount(accountNumber, agencyNumber);
		
		String date = getDate();
		
		if(account != null) {
			String shift = validateInput("Do you want to change Day limit (D) or Night limit (N)?", "[DN]" );
			
			System.out.println("New limit value?");
			Double value = input.nextDouble();
			
			account.changeLimit(value, shift);
			
			Transaction t = new Transaction(date, "Change limit", value, account.getClient().getCNPJCPF(), account.getClient().getCNPJCPF());
			
			TransactionManager.addTransaction(t);
		} else {
			System.out.println("Invalid account!");
		}
		
		actions();
	}
	
	public static void transactionList() {
		try {
			bankTransactions = TransactionManager.getBankTransactions();
			TransactionManager.exportTransactions(bankTransactions);
			System.out.println("Check for the file 'transactions.csv' on your Desktop folder.");
			
		} catch(FileNotFoundException e) {
	       System.err.println("File not found: " + e.getMessage());
		}
		actions();
	}
	
}
