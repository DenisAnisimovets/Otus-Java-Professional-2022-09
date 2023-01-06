package homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private TreeMap<Customer, String> customerTreeMap;

    public CustomerService() {
        customerTreeMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    private Map.Entry<Customer, String> getCopiedEntry(Map.Entry<Customer, String> mapEntry) {
        if(mapEntry == null) {
            return null;
        }

        return new AbstractMap.SimpleEntry<>(
                new Customer(mapEntry.getKey().getId(), mapEntry.getKey().getName(), mapEntry.getKey().getScores()),
                mapEntry.getValue());

    }
    public Map.Entry<Customer, String> getSmallest() {
        return getCopiedEntry(customerTreeMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getCopiedEntry(customerTreeMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customerTreeMap.put(customer, data);
    }
}
