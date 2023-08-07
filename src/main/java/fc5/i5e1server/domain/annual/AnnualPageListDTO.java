package fc5.i5e1server.domain.annual;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AnnualPageListDTO {
    private List<AnnualPageDTO> annuals;
}