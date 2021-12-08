package shop.helloshop.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter @Setter
public class MemberDto {

    @NotBlank(message = "필수 값 입니다")
    private String email;

    @NotBlank(message = "필수 값 입니다")
    private String password;

    @NotBlank(message = "필수 값 입니다")
    private String name;

    private String city;

    private String street;

    private String zipcode;
}
