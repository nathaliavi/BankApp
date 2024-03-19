package Agencia;

import utils.Utils;

public class Account {

	private static int accountCounter = 1;
	
	private int number;
	private String agencyNumber;
	
	private Client client;
	
	private Double amount = 0.0; 
	private Double limit_day = 1000.0;
	private Double limit_night = 1000.0;
	private String type;

	

	public Double getLimit_day() {
		return limit_day;
	}

	public void setLimit_day(Double limit_day) {
		this.limit_day = limit_day;
	}

	public Double getLimit_night() {
		return limit_night;
	}

	public void setLimit_night(Double limit_night) {
		this.limit_night = limit_night;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAgencyNumber() {
		return agencyNumber;
	}

	public void setAgencyNumber(String agencyNumber) {
		this.agencyNumber = agencyNumber;
	}

	public Account(Client client, String agencyNumber) {
		this.number = accountCounter;
		this.client = client;
		this.agencyNumber = agencyNumber;
		accountCounter += 1;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String toString() {
		return "\nAccount ID: " + this.getNumber() +
				"\nAgency Number: " + this.getAgencyNumber() +
				"\nName: " + this.client.getName() +
				"\nIdentifier: " + this.client.getCNPJCPF() +
				"\nEmail: " + this.client.getEmail() +
				"\nType: " + this.getType() +
				"\nAmount: " + Utils.doubleToString(this.getAmount()) +
				"\nLimit Day: " + Utils.doubleToString(this.getLimit_day()) +
				"\nLimit Night: " + Utils.doubleToString(this.getLimit_night()) +
				"\n";
	}

	public void deposit(Double value) {
		if(value > 0) {
			setAmount(getAmount() + value);
			System.out.println("Deposit successfully completed.");
		} else {
			System.out.println("It was not possible to complete the deposit.");
		}
		
	}
	
	public void withdrawal(Double value) {
		if(value > 0 && this.getAmount() >= value) {
			setAmount(getAmount() - value);
			System.out.println("Withdrawal successfully completed.");
		} else {
			System.out.println("It was not possible to complete the withdrawal.");
		}
		
	}
	
	public void transfer(Account account, Double value) {
		if(this.getAmount() >= value && value > 0) {
			this.setAmount(this.getAmount() - value);
			
			account.amount = account.getAmount() + value;
			System.out.println("Transfer successfully completed.");
		} else {
			System.out.println("It was not possible to complete the transfer.");
		}
		
	}
	
	public void changeLimit(Double value, String shift) {
		if( value > 0) {
			if(shift.equals("D"))
				setLimit_day(value);
			else if(shift == "N")
				setLimit_night(value);
			System.out.println("New limit successfully ajusted.");
		} else {
			System.out.println("It was not possible to complete the limit change.");
		}
	}

	
}
