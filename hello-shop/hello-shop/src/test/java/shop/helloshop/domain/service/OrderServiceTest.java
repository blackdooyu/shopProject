package shop.helloshop.domain.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.DeliveryStatus;
import shop.helloshop.domain.entity.Member;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.domain.entity.items.Room;
import shop.helloshop.web.dto.OrderItemDto;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;

    @Test
    void 주문() {

        //given
        Member member = createMember("회원1");
        Item item = createRoom("상품1");

        memberService.save(member);
        itemService.save(item,member.getId());

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(item.getId());
        orderItemDto.setCount(5);

        //when
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItemDtoList.add(orderItemDto);

        Long orderId = orderService.order(member.getId(), orderItemDtoList);

        em.flush();
        em.clear();

        //then
        assertEquals(orderService.findOne(orderId).getMember(),memberService.findOne(member.getId()));
        assertEquals(orderService.findOne(orderId).getOrderItems().get(0).getOrderPrice(),5*15000);
        assertEquals(itemService.findOne(item.getId()).getQuantity(),5);
        assertEquals(itemService.findOne(item.getId()).getSalesQuantity(),5);
        assertEquals(orderService.findAllOrders().size(),1);
        assertEquals(orderService.findMemberOrders(member.getId()).size(),1);


    }

    @Test
    void 주문_취소() {

        //given
        Member member = createMember("회원1");
        Item item = createRoom("상품1");

        memberService.save(member);
        itemService.save(item,member.getId());

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(item.getId());
        orderItemDto.setCount(5);

        //when
        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItemDtoList.add(orderItemDto);
        Long orderId = orderService.order(member.getId(), orderItemDtoList);
        em.flush();
        em.clear();
        orderService.orderCancel(orderId);

        //then
        assertEquals(orderService.findOne(orderId).getDeliveryStatus(), DeliveryStatus.CANCEL);
        assertEquals(itemService.findOne(item.getId()).getQuantity(),10);
        assertEquals(itemService.findOne(item.getId()).getSalesQuantity(),0);

    }

    private Item createRoom(String name) {
        Room item = new Room();
        item.setPrice(15000);
        item.setQuantity(10);
        item.setDay("20211001");
        item.setName(name);
        return item;
    }

    private Member createMember(String name) {
        Member member = new Member();
        member.setName(name);
        return member;
    }

}