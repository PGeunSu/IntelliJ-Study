package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@ToString
@Entity
@Table(name = "item")
public class Item extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id")
    private Long id; //상품코드
    @Column(nullable = false, length = 50)
    private String itemNm; //상품명
    @Column(name = "price", nullable = false)
    private int price; //가격
    @Column(nullable = false)
    private int StockNumber; //재고 번호
    @Lob @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    private ItemSellStatus itemSellStatus; //상태

}
