package nextstep.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ThemeResponseDto {

    @Setter
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public ThemeResponseDto(String name, String desc, Integer price) {
        this(0L, name, desc, price);
    }

}
