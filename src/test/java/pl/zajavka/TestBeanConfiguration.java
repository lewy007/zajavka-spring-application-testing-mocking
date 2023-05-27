package pl.zajavka;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// konfiguracja wykonana tylko na potrzeby wykonania testow
@Configuration
@ComponentScan(value = "pl.zajavka")
public class TestBeanConfiguration {

    @Mock
    private DepthCalculationService depthCalculationService;

    // niskopoziomowe podejscie mockowania obiketow (w praktyce stosuje sie inaczej)
    public TestBeanConfiguration() {
        try (AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this)) {
            System.out.println("Correctly opened mocks");
        } catch (Exception e) {
            System.out.println("Unable to open mocks: " + e);
            throw new RuntimeException(e);
        }
    }


    // tworzymy beany na dwa sposoby, aby byly mockiem
    @Bean
    public DepthCalculationService depthCalculationService() {
        return depthCalculationService;
    }

    @Bean
    public WidthCalculationService widthCalculationService() {
        return Mockito.mock(WidthCalculationService.class);
    }
}
