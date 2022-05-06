
import java.util.*;

public interface Inventory
{
    String getName();
    String listAllItems();
    String listAllCards();
    String orderOneItem(Order order);
    String orderByCSV(String filePath);
}
