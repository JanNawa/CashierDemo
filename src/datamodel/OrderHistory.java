package datamodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class models to interact with history file.
 * 
 * @author Nawaphan Chayopathum(Jan)
 */
public class OrderHistory {
    
    File fileLoc = new File("src\\files\\" + HISTORY_FILE);
    
    public static final String HISTORY_FILE = "history.txt";

    /**
     * Retrieve the latest order id from history file
     * 
     * @return latest order id
     * @throws IOException
     */
    public int retrieveOrderId() throws IOException {
        System.out.println("---------- RETRIEVE ORDER ID ----------");
        String fileTxt;
        String idFromFile = "";
        try (BufferedReader br = new BufferedReader(new FileReader(fileLoc))) {
            while ((fileTxt = br.readLine()) != null) {
                if (fileTxt.contains("orderId=")) {
                    idFromFile = fileTxt.substring(8);
                }
            }
        } catch (EOFException e) {
            System.out.println("end of file");
        }
        return Integer.parseInt(idFromFile);
    }

    /**
     * Read order history from history file
     * 
     * @return list of order history
     * @throws IOException 
     */
    public String[] readOrderHistory() throws IOException {
        System.out.println("---------- READ FROM FILE ----------");
        String full = "";
        String input;
        try(BufferedReader br = new BufferedReader(new FileReader(fileLoc))){
            while ((input = br.readLine()) != null) {
                if(input.contains(",")) {
                    String[] orderStr = input.split(",");
                    for (String orderStr1 : orderStr) {
                        full += orderStr1 + "\n";
                    }
                } else {
                    full += input + "\n";
                }
            }
        } catch (EOFException e) {
            System.out.println("end of file");
        }
        String[] seperateOrder = full.split("==========");
        return seperateOrder;
    }

    /**
     * Write order history to the history file
     * 
     * @param cashier the finished cashier from user
     * @throws IOException
     */
    public void writeOrderHistory(Cashier cashier) throws IOException {
        System.out.println("---------- WRITE TO FILE ----------");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileLoc, true))) {
            bw.write(cashier.toString());
            bw.newLine();
            bw.write("==========");
        }
    }
}