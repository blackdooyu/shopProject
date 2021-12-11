package shop.helloshop.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.util.Lazy;
import shop.helloshop.domain.entity.items.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;

    private int count;

    //생성 편의 메서드로만 생성할수있게 막기
    protected OrderItem() {
    }

    //생성 편의 메서드
    public static OrderItem createOrderItem(Item item,int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setCount(count);
        orderItem.setItem(item);
        int orderPrice = item.getPrice(count);
        orderItem.setOrderPrice(orderPrice);

        item.sale(count);

        return orderItem;
    }

    //주문 취소
    public void cancel() {
        this.item.cancel(count);
    }

}
