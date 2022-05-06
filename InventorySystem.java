import java.util.*;

public class InventorySystem implements Inventory
{
    private String name;
    private OrderRecord o = OrderRecord.getInstance();
    private ArrayList<Order> ordersList;
    private HashMap<String, Item> itemsList;
    private ArrayList<Card> cardsList;
    
    public InventorySystem(String name){
        this.name = name;
        ordersList = new ArrayList<>();
        cardsList = new ArrayList<>();
        itemsList = new HashMap<>();
        loadItems();
        loadCards();
    }
    
    @Override
    public String getName(){
        return this.name;
    }
    
    @Override
    public String listAllItems(){
        String details="";
        for(Map.Entry<String, Item> maps: itemsList.entrySet()){
            details+=maps.getKey()+" ".repeat(20-maps.getKey().length())+maps.getValue().toString()+"\n";
        }
        return details;
    }
    
    @Override
    public String listAllCards(){
        String details = "";
        for(Card card: cardsList){
            details+=card.toString()+"\n";
        }
        return details;
    }
    
    //validation for incorrect item name, quantity limit
    @Override
    public String orderOneItem(Order order){
        
        if(itemsList.get(order.getItem())==null){
            o.recordError("incorrect item name "+ order.getItem());
            return "incorrect item name " + order.getItem();
        }
        
        if(!checkQuantity(order)){
            return "item " + order.getItem() + "not in inventory";
        }
        
        if(validateOrderQuantity(order)){
            ordersList.add(order);
            updateQuantity(order);
            if(!cardExists(order.getCardNumber())){
                cardsList.add(new Card.Builder(order.getCardNumber()).buildCard());
            }
            o.record(order, itemsList.get(order.getItem()));
            return "order successful for "+ order.getItem();
        }else{
            o.recordError("please enter correct quantities for "+ order.getItem());
            return "please enter correct quantities for "+ order.getItem();    
        }
    }

    //file-path for csv
    @Override
    public String orderByCSV(String filePath){
        for(Order ord : o.convertCSV(filePath)){
            orderOneItem(ord);
        }
        return "order successful for " + filePath;
    }
    
    //validate for essentials items>3, luxury items>4 and misc items>6
    private boolean validateOrderQuantity(Order ord){
        Item orderedItem = itemsList.get(ord.getItem());
        switch(orderedItem.getCategory()){
            case essentials:
                if(ord.getQuantity()>3){
                    return false;   
                }
                break;
            case luxury:
                if(ord.getQuantity()>4){
                    return false;   
                }
                break;
            case miscellaneous:
                if(ord.getQuantity()>6){
                    return false;   
                }
                break;
        }
        return true;
    }
    
    private boolean cardExists(String cardNumber){
        for(Card c: cardsList){
            if(c.getCardNumber().equalsIgnoreCase(cardNumber)){
                return true;
            }
        }
        return false;
    }
    
    private void updateQuantity(Order ord){
        Item orderItem = itemsList.get(ord.getItem());
        orderItem = new Item.Builder(orderItem.getCategory()).setPrice(orderItem.getPrice()).setQuantity(orderItem.getQuantity()-ord.getQuantity()).buildItem();
        itemsList.put(ord.getItem(), orderItem);
    }
    
    private boolean checkQuantity(Order ord){
        if(itemsList.get(ord.getItem()).getQuantity()>=ord.getQuantity()){
            return true;
        }else{
            return false;
        }
    }
    
    private void loadItems(){
        //essentials objects in HashMaps
        itemsList.put("Clothes",new Item.Builder(Category.essentials).setPrice(20).setQuantity(100).buildItem());
        itemsList.put("Soap",new Item.Builder(Category.essentials).setPrice(5).setQuantity(200).buildItem());
        itemsList.put("Shampoo",new Item.Builder(Category.essentials).setPrice(10).setQuantity(10).buildItem());
        itemsList.put("Milk",new Item.Builder(Category.essentials).setPrice(5).setQuantity(100).buildItem());
        //luxury objects in HashMaps
        itemsList.put("Perfume",new Item.Builder(Category.luxury).setPrice(50).setQuantity(50).buildItem());
        itemsList.put("Chocolates",new Item.Builder(Category.luxury).setPrice(3).setQuantity(300).buildItem());
        itemsList.put("Handbag",new Item.Builder(Category.luxury).setPrice(150).setQuantity(75).buildItem());
        itemsList.put("Wallet",new Item.Builder(Category.luxury).setPrice(100).setQuantity(100).buildItem());
        //miscellaneous objects in HashMaps
        itemsList.put("Bedsheet",new Item.Builder(Category.miscellaneous).setPrice(75).setQuantity(150).buildItem());
        itemsList.put("Footware",new Item.Builder(Category.miscellaneous).setPrice(25).setQuantity(200).buildItem());
        itemsList.put("HomeDecorPiece",new Item.Builder(Category.miscellaneous).setPrice(40).setQuantity(100).buildItem());
        itemsList.put("pen",new Item.Builder(Category.miscellaneous).setPrice(3).setQuantity(400).buildItem());
        itemsList.put("pencil",new Item.Builder(Category.miscellaneous).setPrice(3).setQuantity(400).buildItem());

    }
    
    private void loadCards(){
        //cards in ArrayList of Card objects
        cardsList.add(new Card.Builder("54109989897623450").buildCard());
        cardsList.add(new Card.Builder("4123787890761256").buildCard());
        cardsList.add(new Card.Builder("9909235621789125").buildCard());
        cardsList.add(new Card.Builder("6765789012672322").buildCard());

    }
}
