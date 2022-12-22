package com.shop.entity;

import com.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime orderDate; //주문일자

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문 상태

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    //orphanRemoval : 부모가 삭제되었을 때 자식도 함께 삭제시키는 역할
    private List<OrderItem> orderItems = new ArrayList<>();

    //order 내부에 orderItems 가 OneToMany 로 양방향으로 선언되어 있기 때문에 양쪽에 값을 넣을 addOrderItem 메서드 선언
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
        //양방향 참조 관계이므로 orderItem 객체에도 order 객체 세팅
    }
    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();
        order.setMember(member); //상품을 주문한 회원의 정보를 셋팅

        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }
        //상품 페이지 에서는 1개의 상품을 주문하지만, 장바구니 페이지에서는 한 번의 여러개의 상품을 주문할 수 있음
        //여러 개의 주문 상품을 담을 수 있도록 리스트 형태로 파라미터 값을 받으며 주문 객체에 orderItem 객체를 추가

        order.setOrderStatus(OrderStatus.ORDER); //주문상태를 ORDER 로 세팅
        order.setOrderDate(LocalDateTime.now()); //현재 시간을 주문시간으로 세팅
        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    } //Item 클래스에서 주문 취소 시 주문 수량을 상품의 재고에 더해주는 로직과 주문상태를 취소 상태로 바꿔주는 메서드 구현

}
