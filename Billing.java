import java.util.*;
import java.io.*;

public class Billing
{   
    private Scanner reader = new Scanner(System.in);
    private InventorySystem inventory = new InventorySystem("Inventory Management System");
    
    public void start(){
        int option = options();
        if(option == 1){listAllItems();}
        else if(option == 2){listAllCards();}
        else if(option == 3){orderOneItem();}
        else if(option == 4){orderListOfItems();}
        else if(option == 5){quit();}
        start();
    }
    //list options
    public int options(){
        System.out.println("Welcome to " + inventory.getName() + "!");
        System.out.println("1. list of all items(inventory)");
        System.out.println("2. list of all cards");
        System.out.println("3. order one item");
        System.out.println("4. order using csv-file");
        System.out.println("5. quit\n");
        System.out.println("please select an option: ");
        return reader.nextInt();
    }

    public void listAllItems(){
        System.out.println("------------------------");
        System.out.println(inventory.listAllItems());
        System.out.println("------------------------");
    }
    
    public void listAllCards(){
        System.out.println("------------------------");
        System.out.println(inventory.listAllCards());
        System.out.println("------------------------");
    }
    
    public void orderOneItem(){
        System.out.println("------------------------");
        System.out.print("enter item: ");
        String itemName = reader.next();
        System.out.print("enter quantity of "+itemName+": ");
        int quantity = reader.nextInt();
        System.out.print("enter card number: ");
        String cardNumber = reader.next();
        System.out.println(inventory.orderOneItem(new Order.Builder(itemName).setQuantity(quantity).setCardNumber(cardNumber).build()));
        System.out.println("------------------------");
    }
    //order using csv
    public void orderListOfItems(){
        System.out.println("------------------------");
        reader.nextLine();
        System.out.println("enter file path to csv: ");
        String path = reader.nextLine();
        System.out.println(inventory.orderByCSV(path));
        System.out.println("------------------------");
    }
    //quit
    public void quit(){
        System.out.println("Thank you for using " + inventory.getName() +"!");
        System.exit(0);
    }
    
    public static void main(String[] arg){
         Billing bill = new Billing();
         bill.start();
    }
}
