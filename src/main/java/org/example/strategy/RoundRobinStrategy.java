package org.example.strategy;

import java.util.List;

public class RoundRobinStrategy implements LoadBalancingStrategy {
    private int currentIndex = 0;

    @Override
    public synchronized String selectBackend(List<String> backendsList) {
        String backend = backendsList.get(currentIndex);
        currentIndex = (currentIndex + 1) % backendsList.size();
        return backend;
    }
}
