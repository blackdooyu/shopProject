package shop.helloshop.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import shop.helloshop.domain.entity.Order;
import shop.helloshop.domain.entity.OrderItem;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public Long order(Order order) {
        em.persist(order);
        return order.getId();
    }

    public Order findOne(Long orderId) {
        return em.find(Order.class,orderId);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o join fetch o.member",Order.class)
                .getResultList();
    }

    public List<Order> findMemberOrders(Long memberId) {
        return em.createQuery("select distinct o from Order o join fetch o.orderItems where o.member.id =:memberId",Order.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<Order> orderCancel(Long orderId) {
        return em.createQuery("select o from Order o join fetch o.orderItems where o.id =:orderId", Order.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }


}
