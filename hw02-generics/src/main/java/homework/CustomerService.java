package homework;

import lombok.SneakyThrows;

import java.util.Comparator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private NavigableMap<Customer, String> customers = new TreeMap<>(Comparator.comparing(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        return copyEntry(customers.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copyEntry(customers.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }

    @SneakyThrows
    private Map.Entry<Customer, String> copyEntry(Map.Entry<Customer, String> entry) {
        return entry == null
                ? null
                : Map.entry(entry.getKey().clone(), entry.getValue());
    }
}
