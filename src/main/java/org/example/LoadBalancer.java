package org.example;

import org.example.strategy.LoadBalancingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LoadBalancer {
    private static final int MAX_BACKENDS_COUNT = 10;
    private final List<String> backendsList;
    private final ConcurrentMap<String, Boolean> backendsMap;
    private final LoadBalancingStrategy strategy;

    public LoadBalancer(LoadBalancingStrategy strategy) {
        this.backendsList = Collections.synchronizedList(new ArrayList<>());
        this.backendsMap = new ConcurrentHashMap<>();
        this.strategy = strategy;
    }

    public List<String> getBackendsList() {
        return backendsList;
    }

    public synchronized void registerBackend(String backend) {
        if (backendsList.size() == MAX_BACKENDS_COUNT) {
            throw new IllegalStateException("Max number of backends reached!");
        }

        if (backendsMap.putIfAbsent(backend, true) != null) {
            throw new IllegalArgumentException("The backend instance has been already registered!");
        }

        backendsList.add(backend);
    }

    public synchronized String getBackend() {
        if (backendsList.isEmpty()) {
            throw new IllegalStateException("There are no registered backends.");
        }

        return strategy.selectBackend(backendsList);
    }
}
