package com.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "order_item")
public class OrderItem extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count; //수량

    //정적 생성자를 사용해서 매개변수로 item, count 받아서 OrderItem 생성
    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);
        orderItem.setOrderPrice(item.getPrice()); //상품 가격을 주문가격으로 세팅
        item.removeStock(count); //removeStock 메서드를 사용하여 재고를 제거
        return orderItem;
    }
    public int getTotalPrice(){
        return orderPrice * count;
    }
    public void cancel(){
        this.getItem().addStock(count);
    }



}
