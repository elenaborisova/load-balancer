package org.example.strategy;

import java.util.List;

public interface LoadBalancingStrategy {
    String selectBackend(List<String> backendsList);
}
