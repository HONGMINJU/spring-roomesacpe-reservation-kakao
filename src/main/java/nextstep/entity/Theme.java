package nextstep.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nextstep.dto.ThemeResponseDto;

@ToString
@Getter
public class Theme {

    @Setter
    private Long id;
    private String name;
    private String desc;
    private Integer price;

    public Theme(String name, String desc, Integer price) {
        this(0L, name, desc, price);
    }

    public Theme(Long id, String name, String desc, Integer price) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

    public Theme(ThemeResponseDto themeResponseDto) {
        this.id = themeResponseDto.getId();
        this.name = themeResponseDto.getName();
        this.desc = themeResponseDto.getDesc();
        this.price = themeResponseDto.getPrice();
    }

    public ThemeResponseDto toDto() {
        return new ThemeResponseDto(this.id, this.name, this.desc, this.price);
    }
}
