import java.io.*;
import java.util.*;

public class OrderRecord
{
    private static OrderRecord o;
    
    private OrderRecord(){}
    
    public static OrderRecord getInstance(){
        if(o==null){
            return new OrderRecord();
        }else{
            return null;
        }
    }
    
    public String record(Order ord, Item item){
        try(BufferedWriter bf = new BufferedWriter(new FileWriter("output.csv",true))){
            bf.write(ord.getItem()+","+ord.getQuantity()+","+ord.getCardNumber()+","+(ord.getQuantity()*item.getPrice())+"\n");
            return "write successful";
        }catch(Exception exception){
            exception.printStackTrace();
            return exception.getMessage();
        }
    }
    
    public void recordError(String er){
        try(BufferedWriter bf = new BufferedWriter(new FileWriter("output.txt",true))){
            bf.write(er + "\n");
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }
    
    public ArrayList<Order> convertCSV(String filePath){
        try(Scanner read = new Scanner(new File(filePath))){
            ArrayList<Order> orderList = new ArrayList<>();
            while(read.hasNextLine()){
                String line = read.nextLine();
                System.out.println(line);
                String[] order =  line.split(",");
                orderList.add(new Order.Builder(order[0]).setQuantity(Integer.parseInt(order[1])).setCardNumber(order[2]).build());
                System.out.println(orderList.size());
            }
            return orderList;
        }catch(Exception exception){
            System.out.println("H");
            exception.printStackTrace();
            System.out.println("I");
            return null;
        }
    }
}