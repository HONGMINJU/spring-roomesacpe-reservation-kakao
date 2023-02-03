package nextstep.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String desc;

    @Positive
    private Integer price;

}
