package JpaBook.JpaShop.service;

import JpaBook.JpaShop.domain.Member;
import JpaBook.JpaShop.domain.item.Item;
import JpaBook.JpaShop.domain.order.Delivery;
import JpaBook.JpaShop.domain.order.DeliveryStatus;
import JpaBook.JpaShop.domain.order.Order;
import JpaBook.JpaShop.domain.order.OrderItem;
import JpaBook.JpaShop.repository.ItemRepository;
import JpaBook.JpaShop.repository.MemberRepository;
import JpaBook.JpaShop.repository.OrderRepository;
import JpaBook.JpaShop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /** 주문 */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        Member member = memberRepository.findOne(memberId); Item item = itemRepository.findOne(itemId);
        //배송정보 생성
        Delivery delivery = new Delivery(); delivery.setAddress(member.getAddress()); delivery.setStatus(DeliveryStatus.READY);
        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(),count);
        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        //주문 저장
        orderRepository.save(order);
        return order.getId();

    }

    /** 주문 취소 */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }
}
