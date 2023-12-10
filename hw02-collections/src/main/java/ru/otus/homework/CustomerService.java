package ru.otus.homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> firstByScore = customers.firstEntry();
        if (firstByScore == null) {
            return null;
        }
        Customer customerFirstByScore = firstByScore.getKey();
        return Map.entry(
                new Customer(
                        customerFirstByScore.getId(), customerFirstByScore.getName(), customerFirstByScore.getScores()),
                firstByScore.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> nextByScore = customers.higherEntry(customer);
        if (nextByScore == null) {
            return null;
        }
        Customer customerNextByScore = nextByScore.getKey();
        return Map.entry(
                new Customer(
                        customerNextByScore.getId(), customerNextByScore.getName(), customerNextByScore.getScores()),
                nextByScore.getValue());
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}