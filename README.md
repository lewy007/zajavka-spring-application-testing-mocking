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