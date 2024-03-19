package Agencia;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;

public class TransactionManager {
	private static ArrayList<Transaction> bankTransactions = new ArrayList<>();
	
	public TransactionManager() {
		bankTransactions = new ArrayList<>();
	}
	
	public static void addTransaction(Transaction transaction) {
		bankTransactions.add(transaction);
	}

	public static ArrayList<Transaction> getBankTransactions() {
		return bankTransactions;
	}

	public static void exportTransactions(ArrayList<Transaction> bankTransactions) throws FileNotFoundException {
		String userHome = System.getProperty("user.home");
		String filePath = userHome + File.separator + "Desktop" + File.separator + "transactions.csv";
		
		PrintWriter pw = new PrintWriter(new File(filePath));
        StringBuilder sb = new StringBuilder();
 
        String header = "Operation, Date, Sender ID, Receiver ID, Value";
        sb.append(header);
 
        for (int i = 0; i < bankTransactions.size(); i++) {
            sb.append("\r\n");
 
            String description = bankTransactions.get(i).getDescription();
            String date = bankTransactions.get(i).getDate();
            String sender = bankTransactions.get(i).getSender();
            String receiver = bankTransactions.get(i).getReceiver();
            String value = Double.toString(bankTransactions.get(i).getValue());
 
            String line = description + ", " + date + ", " + sender + ", " + receiver + ", " + value;
            sb.append(line);
        }
 
        pw.write(sb.toString());
        pw.close();
    }
	
}
