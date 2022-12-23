package com.shop.controller;

import com.shop.dto.CartDetailDto;
import com.shop.dto.CartItemDto;
import com.shop.dto.CartOrderDto;
import com.shop.service.CartService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal){

        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> filedErrors = bindingResult.getFieldErrors();

            for(FieldError fieldError : filedErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        //현재 로그인한 회원의 이메일 정보를 변수에 저장
        String email = principal.getName();
        Long cartItemId;

        try{
            cartItemId = cartService.addCart(cartItemDto, email);
            //값으로 넘어온 장바구니에 담을 상품 정보와 현재 로그인한 회원의 이메일 정보를 이용하여 장바구니에 상품을 담는 로직을 호출
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
        //결과값으로 생성된 장바구니 상품 아이디와 요청이 성공하였다는 Http 응답 상태 코드 반환
    }

    @GetMapping("/cart")
    public String orderHist(Principal principal, Model model){
        List<CartDetailDto> cartDetailDtoList = cartService.getCartList(principal.getName());
        //현재 로그인한 사용자의 이메일 정보를 이용해서 장바구니의 담겨있는 상품 정보 조회
        model.addAttribute("cartItems", cartDetailDtoList);

        return "cart/cartList";
    }
    //Http 메서드에서 patch 는 요청된 자원의 일부를 업데이트할 때 patch 사용
    //장바구니 상품의 수량만 업데이트하기 때문에 @PatchMapping 사용
    @PatchMapping("/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count, Principal principal){

        if(count <= 0){ //상품의 개수를 0개 이하로 업데이트 요청하면 에러
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        }else if(!cartService.validateCartItem(cartItemId, principal.getName())){ //수정 권한 체크
            return new ResponseEntity<String>("수정 권환이 없습니다.",HttpStatus.FORBIDDEN);
        }
        cartService.updateCartItemCount(cartItemId, count); //장바구니 상품의 개수를 업데이트 한다.
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @DeleteMapping("/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {

        if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<String>("수정권환이 없습니다.", HttpStatus.FORBIDDEN);
        }
        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

   @PostMapping("/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal){
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();

        if(cartOrderDtoList == null || cartOrderDtoList.size() == 0){
            return new ResponseEntity<String>("주문할 상품을 선택해주세요", HttpStatus.FORBIDDEN);
        }
        for(CartOrderDto cartOrder : cartOrderDtoList){
            if(!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())){
                return new ResponseEntity<String>("주문 권한이 없습니다", HttpStatus.FORBIDDEN);
            }
        }
        Long orderId = cartService.orderCartItem(cartOrderDtoList, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }


}
