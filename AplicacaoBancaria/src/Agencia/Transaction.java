package Agencia;

public class Transaction {
	private Double value;
	private String date;
	private String description;
	private String sender;
	private String receiver;
	
	
	
	public Transaction(String date, String description, Double value, String sender, String receiver) {
		this.value = value;
		this.date = date;
		this.description = description;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public Double getValue() {
		return value;
	}
	public String getDate() {
		return date;
	}
	public String getDescription() {
		return description;
	}
	
	

	
	
}
