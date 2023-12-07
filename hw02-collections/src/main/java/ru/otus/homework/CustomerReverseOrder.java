package ru.otus.homework;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class CustomerReverseOrder {
    private final Queue<Customer> queueOfCustomers = Collections.asLifoQueue(new LinkedList<>());

    public void add(Customer customer) {
        queueOfCustomers.add(customer);
    }

    public Customer take() {
        return queueOfCustomers.poll();
    }
}