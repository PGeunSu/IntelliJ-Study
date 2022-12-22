package com.shop.controller;

import com.shop.dto.OrderDto;
import com.shop.dto.OrderHistoryDto;
import com.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    //스프링에서 비동기 처리할 때
    //@RequestBody - Http 요청의 본문 Body 에 담긴 내용을 자바객체로 전달
    //@ResponseBody - 자바 객체를 Http 요청의 Body 로 전달

    //Principal 은 Security 에서 제공하는 객체, Principal 또는 Authentication 객체를 사용해도 무관
    //Principal 현재 접속한 사용자의 기본 정보를 제공 받는다.

    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto, BindingResult bindingResult, Principal principal){
        //주문 정보를 받는 orderDto 객체에 데이터 바인딩 시 에러가 있는 지 검사
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for(FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        //현재 로그인 유저의 정보를 얻기위해 @Controller 어노테이션이 선언된 클래스에서 메소드 인자로 Principal 객체로 넘겨줄 경우 해당 객체에 접근할 수 있다.
        //Principal 객체에서 현재 로그인한 화면의 이메일 정보를 조회
        Long orderId;


        try{
            orderId = orderService.order(orderDto, email);
            //화면으로 부터 넘어오는 주문 정보와 회원의 이메일 정보를 이용하여 주문 로직을 호출
        }catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
        //결과값으로 생성된 주문 번호와 요청이 성공했다는 Http 응답 상태 코드를 반환

    }

    @GetMapping(value = {"/orders","/orders/{page}"})
    public String orderHist(@PathVariable("path") Optional<Integer> page, Principal principal, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<OrderHistoryDto> orderHistoryDtoList = orderService.getOrderList(principal.getName(), pageable);
        //현재 로그인한 회원은 이메일과 페이징 객체를 파라미터로 전달하여 화면에 전달한 주문 목록 데이터를 리턴값으로 받는다.
        model.addAttribute("orders",orderHistoryDtoList);
        model.addAttribute("page",pageable.getPageNumber());
        model.addAttribute("maxPage",3);

        return "order/orderHist";
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cacelOrder(@PathVariable("orderId") Long orderId, Principal principal){
        if(!orderService.validateOrder(orderId, principal.getName())){
            return new ResponseEntity<String>("주문 취소 권환이 없습니다.",HttpStatus.FORBIDDEN);
        }
        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId,HttpStatus.OK);
    }

}

