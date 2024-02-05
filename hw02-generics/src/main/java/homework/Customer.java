package homework;

import java.util.HashMap;
import java.util.Map;

public class Customer {
    private final long id;
    private static final Map<Long, String> name = new HashMap<>();
    private long scores;

    public Customer(long id, String name, long scores) {
        this.id = id;
        Customer.name.put(id, name);
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name.get(this.id);
    }

    public void setName(String name) {
        Customer.name.put(this.id, name);
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", scores=" + scores + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
