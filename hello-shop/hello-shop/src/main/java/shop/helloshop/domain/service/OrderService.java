package shop.helloshop.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.helloshop.domain.entity.Member;
import shop.helloshop.domain.entity.Order;
import shop.helloshop.domain.entity.OrderItem;
import shop.helloshop.domain.entity.items.Item;
import shop.helloshop.domain.repository.ItemRepository;
import shop.helloshop.domain.repository.MemberRepository;
import shop.helloshop.domain.repository.OrderRepository;
import shop.helloshop.web.dto.OrderItemDto;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, List<OrderItemDto> orderItemDto) {

        Member findMember = memberRepository.findOne(memberId);
        List<OrderItem> orderItems = new ArrayList<>();

        //OrderItem 생성
        for (OrderItemDto items : orderItemDto) {
            Item findItem = itemRepository.findOne(items.getId());
            OrderItem orderItem = OrderItem.createOrderItem(findItem, items.getCount());
            orderItems.add(orderItem);
        }

        //Order 생성
        Order order = Order.createOrder(findMember, orderItems);

        Long orderId = orderRepository.order(order);

        return orderId;
    }

    public Order findOne(Long id) {
       return orderRepository.findOne(id);
    }

    public List<Order> findMemberOrders(Long memberId) {
        return orderRepository.findMemberOrders(memberId);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void orderCancel(Long orderId) {
        List<Order> orders = orderRepository.orderCancel(orderId);
        Order order = orders.get(0);
        order.cancel();
    }



}
