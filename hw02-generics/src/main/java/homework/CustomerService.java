package homework;

import java.util.*;

public class CustomerService {

    private NavigableMap<Customer, String> customers = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return customers.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return customers.ceilingEntry(customer);
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
