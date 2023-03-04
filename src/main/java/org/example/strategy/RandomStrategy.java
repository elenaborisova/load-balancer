package org.example.strategy;

import java.util.List;
import java.util.Random;

public class RandomStrategy implements LoadBalancingStrategy {
    @Override
    public String selectBackend(List<String> backendsList) {
        Random random = new Random();
        return backendsList.get(random.nextInt(backendsList.size()));
    }
}
