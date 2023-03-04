package org.example.strategy;

import org.example.LoadBalancer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class RoundRobinStrategyTest {
    private LoadBalancer loadBalancer;

    @BeforeEach
    public void setUp() {
        LoadBalancingStrategy strategy = new RoundRobinStrategy();
        loadBalancer = new LoadBalancer(strategy);
    }

    @Test
    public void testGetBackend_whenSeveralBackend_shouldReturnRandom() {
        loadBalancer.registerBackend("1");
        loadBalancer.registerBackend("2");
        loadBalancer.registerBackend("3");

        Assertions.assertEquals("1", loadBalancer.getBackend());
        Assertions.assertEquals("2", loadBalancer.getBackend());
        Assertions.assertEquals("3", loadBalancer.getBackend());
    }
}
