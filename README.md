## Testowanie aplikacji spring + mockowanie
na etapie metody setUp podnosimy kontekst springowy i sprawdzamy czy beany nie sa nullami

### 1 commit to test integracyjny z podniesieniem wszytskich beanow (zadnej klasy nie zaslepialismy)

```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = BeanConfiguration.class)
class CapacityCalculationServiceImplTest {

    @Autowired
    private CapacityCalculationService capacityCalculationService;
    @Autowired
    private WidthCalculationService widthCalculationService;
    @Autowired
    private HeightCalculationService heightCalculationService;
    @Autowired
    private DepthCalculationService depthCalculationService;

    @BeforeEach
    public void setUp() {
        Assertions.assertNotNull(capacityCalculationService);
        Assertions.assertNotNull(widthCalculationService);
        Assertions.assertNotNull(heightCalculationService);
        Assertions.assertNotNull(depthCalculationService);
    }

    @Test
    void someCalculation() {
        //given
        final var inputData = someInputData();

        //when
        final var result = capacityCalculationService.someCalculation(inputData);

        //then
        Assertions.assertEquals(new BigDecimal("6"), result);
    }

    private InputData someInputData() {
        return InputData.builder()
                .depth("1")
                .width("2")
                .height("3")
                .build();
    }
}
```

### 2 commit - to mockowanie wybranych obiektów. Stworzylismy klase TestBeanConfiguration a jej konfiguracja wykonana tylko na potrzeby wykonania testow. Zaslepiono (zmockowano) w niej dwa beany, a to nastepnie wykorzystano w glownej klasie testujacej w when...
```java
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
```

### czyli dwie zaslepki wykorzsytano tutaj:
```java
 @Test
    void someCalculation() {
        //given
        final var inputData = someInputData();
        Mockito.when(widthCalculationService.calculate(Mockito.any())).thenReturn(BigDecimal.TEN);
        Mockito.when(depthCalculationService.calculate(Mockito.any())).thenReturn(new BigDecimal("20"));

        //when
        final var result = capacityCalculationService.someCalculation(inputData);

        //then
        Assertions.assertEquals(new BigDecimal("6"), result);
    }
```
