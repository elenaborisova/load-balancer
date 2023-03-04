package org.example.strategy;

import org.example.LoadBalancer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RandomStrategyTest {
    private LoadBalancer loadBalancer;

    @BeforeEach
    public void setUp() {
        LoadBalancingStrategy strategy = new RandomStrategy();
        loadBalancer = new LoadBalancer(strategy);
    }

    @Test
    public void testGetBackend_whenSeveralBackend_shouldReturnRandom() {
        loadBalancer.registerBackend("1");
        loadBalancer.registerBackend("2");
        loadBalancer.registerBackend("3");

        Assertions.assertTrue(1 <= Integer.parseInt(loadBalancer.getBackend())
                && Integer.parseInt(loadBalancer.getBackend()) <= 10);
    }
}
