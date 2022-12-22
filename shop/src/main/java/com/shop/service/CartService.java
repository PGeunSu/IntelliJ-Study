package com.shop.service;

import com.shop.dto.CartItemDto;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private CartRepository cartRepository;
    private OrderService orderService;

    public Long addCart(CartItemDto cartItemDto, String email){
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        //장바구니에 담을 Item Entity 조회
        Member member = memberRepository.findByEmail(email);
        //현재 로그인한 Member Entity 조회
        Cart cart = cartRepository.findByMemberId(member.getId());
        //현재 로그인한 회원의 Cart Entity 조회
        if(cart == null){ //상품을 처음으로 장바구니에 담을 경우 해당 회원의 Cart Entity 생성
            cart = Cart.creatCart(member);
            cartRepository.save(cart);
        }
        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
        //현재 상품이 장바구니에 이미 들어가 있는 지 조회
        if(savedCartItem != null){
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
            //장바구니에 이미 있던 상품일 경우 기존 수량에 현재 장바구니에 담을 수량만큼 더함
        }else{
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            //Cart Entity, Item Entity, Count 를 이용하여 CartItem Entity 생성
            cartItemRepository.save(cartItem); //장바구니 저장
            return cartItem.getId();
        }
    }

}
