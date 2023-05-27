package pl.zajavka;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CapacityCalculationServiceImpl implements CapacityCalculationService {
    private final WidthCalculationService widthCalculationService;
    private final HeightCalculationService heightCalculationService;
    private final DepthCalculationService depthCalculationService;

    @Override
    public BigDecimal someCalculation(final InputData inputData) {
        BigDecimal height = heightCalculationService.calculate(inputData);
        BigDecimal width = widthCalculationService.calculate(inputData);
        BigDecimal depth = depthCalculationService.calculate(inputData);
        return height.multiply(width).multiply(depth);
    }
}