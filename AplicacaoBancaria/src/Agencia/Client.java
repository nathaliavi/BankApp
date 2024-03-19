package Agencia;

public class Client {

	private static int id = 1;
	
	private String name;
	private String cnpjCpf;
	private String email;
	
	public Client(String name, String cnpjCpf,String email) {
		this.name = name;
		this.cnpjCpf = cnpjCpf;
		this.email = email;
		id += 1; 
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCNPJCPF() {
		return cnpjCpf;
	}
	public void setCNPJCPF(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}



	public String toString() {
		return "\nNome: " + this.getName() +
				"\nIdentifier: " + this.getCNPJCPF() +
				"\nEmail: " + this.getEmail();
	}
}
