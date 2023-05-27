package pl.zajavka;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class InputData {
    String width;
    String height;
    String depth;
}
