package homework;


import java.util.Deque;
import java.util.LinkedList;

public class CustomerReverseOrder {

    private Deque<Customer> customerList;

    public CustomerReverseOrder() {
        customerList = new LinkedList<>();
    }

    public void add(Customer customer) {
        customerList.addFirst(customer);
    }

    public Customer take() {
        return customerList.pop();
    }
}
