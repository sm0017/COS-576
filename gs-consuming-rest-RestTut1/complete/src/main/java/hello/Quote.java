package hello;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
// only JSON properties will accepted. Rest properties will be ignored 
@JsonIgnoreProperties(ignoreUnknown = true)

public class Quote {

    private String type;
    private Value value;

}
