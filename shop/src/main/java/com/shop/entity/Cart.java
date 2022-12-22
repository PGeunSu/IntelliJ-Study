package com.shop.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name ="cart")
@Getter @Setter
@ToString
public class Cart extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public static Cart creatCart(Member member){
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    } //회원 한 명당 1개의 장바구니를 갖고 있으므로 처음 장바구니에 상품을 담을 떄는 해당 회원의 장바구니를 생성해줘야 함
      //Cart Class 에서 Member Entity 를 파라미터로 받아서 Cart Entity 를 생성하는 로직 추가



}
