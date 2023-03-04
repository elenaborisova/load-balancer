package org.example;

import org.example.strategy.LoadBalancingStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Execution(ExecutionMode.CONCURRENT)
public class LoadBalancerTest {
    private LoadBalancer loadBalancer;
    private LoadBalancingStrategy mockStrategy;

    @BeforeEach
    public void setUp() {
        mockStrategy = mock(LoadBalancingStrategy.class);
        loadBalancer = new LoadBalancer(mockStrategy);
    }

    @Test
    public void testRegister_whenOneValidBackends_shouldAddToBackendsList() {
        String backendInstance = "1";

        loadBalancer.registerBackend(backendInstance);

        Assertions.assertEquals(1, loadBalancer.getBackendsList().size());
    }

    @Test
    public void testRegister_whenTenValidBackends_shouldAddToBackendsList() {
        for (int i = 1; i < 11; i++) {
            loadBalancer.registerBackend(String.valueOf(i));
        }

        Assertions.assertEquals(10, loadBalancer.getBackendsList().size());
    }

    @Test
    public void testRegister_whenRepeatedBackends_shouldThrowException() {
        loadBalancer.registerBackend("1");

        Throwable exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> loadBalancer.registerBackend("1"));
        Assertions.assertEquals("The backend instance has been already registered!", exception.getMessage());
    }

    @Test
    public void testRegister_whenMaxCountReached_shouldThrowException() {
        for (int i = 1; i < 11; i++) {
            loadBalancer.registerBackend(String.valueOf(i));
        }

        Throwable exception = Assertions.assertThrows(IllegalStateException.class,
                () -> loadBalancer.registerBackend("11"));
        Assertions.assertEquals("Max number of backends reached!", exception.getMessage());
    }

    @Test
    public void testGetBackend_whenOneBackend_shouldReturnRandom() {
        loadBalancer.registerBackend("1");
        when(mockStrategy.selectBackend(loadBalancer.getBackendsList())).thenReturn("1");
        Assertions.assertEquals("1", loadBalancer.getBackend());
    }

    @Test
    public void testGetBackend_whenSeveralBackend_shouldReturnRandom() {
        loadBalancer.registerBackend("1");
        loadBalancer.registerBackend("2");
        loadBalancer.registerBackend("3");

        when(mockStrategy.selectBackend(loadBalancer.getBackendsList())).thenReturn("1");

        Assertions.assertEquals("1", loadBalancer.getBackend());
    }

    @Test
    public void testGetBackend_whenNoBackends_shouldThrowException() {
        Throwable exception = Assertions.assertThrows(IllegalStateException.class,
                () -> loadBalancer.getBackend());
        Assertions.assertEquals("There are no registered backends.", exception.getMessage());
    }
}
