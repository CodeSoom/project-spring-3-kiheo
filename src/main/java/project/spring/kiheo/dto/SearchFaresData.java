package project.spring.kiheo.dto;

import com.github.dozermapper.core.Mapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchFaresData {
    @NotBlank
    private String startDt;
    @NotBlank
    private String departureAirport;
    @NotBlank
    private String arriveAirport;
}
