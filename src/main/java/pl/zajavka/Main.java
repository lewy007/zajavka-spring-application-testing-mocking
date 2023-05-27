package pl.zajavka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfiguration.class);
        CapacityCalculationServiceImpl contextBean = context.getBean(CapacityCalculationServiceImpl.class);

        BigDecimal result = contextBean.someCalculation(new InputData("10", "10", "10"));
        System.out.println(result);
    }
}
