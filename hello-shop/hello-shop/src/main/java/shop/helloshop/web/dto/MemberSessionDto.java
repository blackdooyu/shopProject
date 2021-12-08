package shop.helloshop.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSessionDto {

    private Long id;
    private String name;

    public MemberSessionDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public MemberSessionDto() {
    }
}
