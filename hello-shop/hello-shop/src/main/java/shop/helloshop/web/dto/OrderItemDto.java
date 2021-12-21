package shop.helloshop.web.dto;

import lombok.Getter;
import lombok.Setter;
import shop.helloshop.domain.entity.items.ItemSize;
import shop.helloshop.domain.entity.items.PhoneColor;

@Getter @Setter
public class OrderItemDto {

    private Long id;

    private String name;

    private PhoneColor phoneColor;

    private ItemSize itemSize;

    private int count;

    private int price;
}
