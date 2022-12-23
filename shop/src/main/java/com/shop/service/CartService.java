package com.shop.service;

import com.shop.dto.CartDetailDto;
import com.shop.dto.CartItemDto;
import com.shop.dto.CartOrderDto;
import com.shop.dto.OrderDto;
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
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final OrderService orderService;

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

    //현재 로그인한 회원의 정보를 이용해서 장바구니에 들어있는 상품을 조회
    @Transactional(readOnly = true)
    public List<CartDetailDto> getCartList(String email){
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMemberId(member.getId());
        //현재 로그인한 회원의 장바구니 엔티티 조회
        if(cart == null){
            return cartDetailDtoList;
        }
        cartDetailDtoList = cartItemRepository.findCartDetailDtoList(cart.getId());

        return cartDetailDtoList;
    }
    @Transactional(readOnly = true)
    public boolean validateCartItem(Long cartItemId, String email) {
        Member curMember = memberRepository.findByEmail(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        if (!StringUtils.equals(curMember.getEmail(), savedMember.getEmail())) {
            //현재 로그인한 회원과 장바구니 상품을 저장한 회원이 다를 경우  false , 같을 경우 true
            return false;
        }

        return true;
    }

    //장바구니 상품의 수량을 업데이트하는 메서드
    public void updateCartItemCount(Long cartItemId, int count) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);

        cartItem.updateCount(count);
    }

    public void deleteCartItem(Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }

    public Long orderCartItem(List<CartOrderDto> cartOrderDtoList, String email){
        List<OrderDto> orderDtoList = new ArrayList<>();
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            //장바구니 페이지에서 전달받은 주문 상품번호를 이용하여 주문 로직
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            OrderDto orderDto = new OrderDto();
            orderDto.setItemId(cartItem.getItem().getId());
            orderDto.setCount(cartItem.getCount());
            orderDtoList.add(orderDto);
        }
        Long orderId = orderService.orders(orderDtoList, email);
        //장바구니에 담은 상품을 주문하도록 로직 호출
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            //주문한 상품을 장바구니에서 제거
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }
        return orderId;
    }




}
