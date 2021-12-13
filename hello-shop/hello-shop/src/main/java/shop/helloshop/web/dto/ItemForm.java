package shop.helloshop.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import shop.helloshop.domain.entity.items.ItemSize;
import shop.helloshop.domain.entity.items.PhoneColor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemForm {

    private String select;

    @NotBlank(message = "필수 값 입니다")
    private String name;

    @Positive(message = "입력해주세요")
    private int price;

    @Positive(message = "입력해주세요")
    private int quantity;

    private ItemSize itemSize;

    private PhoneColor phoneColor;

    private List<MultipartFile> multipartFileList = new ArrayList<>();


}
