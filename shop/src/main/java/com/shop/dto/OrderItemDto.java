package com.shop.dto;

import com.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto {

    private String itemNm;
    private int count;
    private int orderPrice;
    private String imgUrl;

    //OrderItemDto 클래스의 생정자로 orderItem 객체와 이미지 경로를 파라미터로 받아서 멤버 변수값을 세팅
    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.itemNm = orderItem.getItem().getItemNm();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
    }
}
