package pl.zajavka;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DepthCalculationServiceImpl implements DepthCalculationService {
    @Override
    public BigDecimal calculate(final InputData inputData) {
        return new BigDecimal(inputData.getDepth());
    }
}
