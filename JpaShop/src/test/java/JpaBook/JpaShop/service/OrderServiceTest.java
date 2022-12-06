package JpaBook.JpaShop.service;

import JpaBook.JpaShop.domain.Member;
import JpaBook.JpaShop.domain.item.Book;
import JpaBook.JpaShop.domain.item.Item;
import JpaBook.JpaShop.domain.order.Address;
import JpaBook.JpaShop.domain.order.Order;
import JpaBook.JpaShop.domain.order.OrderStatus;
import JpaBook.JpaShop.exception.NotEnoughStockException;
import JpaBook.JpaShop.repository.ItemRepository;
import JpaBook.JpaShop.repository.MemberRepository;
import JpaBook.JpaShop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired ItemRepository itemRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderService orderService;
    @Autowired EntityManager em;

    @Test
    public void order() throws Exception {
        //Given
        Member member = createMember(); //회원 1 생성
        //테스트 코드에서 Member 와 Book 을 생성함수로 생성
        int stockQuantity = 10;
        Book book = createBook("Jpa 기본서" , 100000, stockQuantity);


        int orderCount = 2;
        //When
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //Then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문 시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(),"주문한 상품 종류 수가 일치해야한다.");
        assertEquals(stockQuantity-orderCount,book.getStockQuantity(),"주문 수량만큼 재고가 줄어야 한다.");
        assertEquals(100000 * orderCount, getOrder.getTotalPrice(), "주문 금액 * 수량은 총 주문 금액이랑 같아야한다.");


    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강변로","123-123"));
        em.persist(member);
        return member;
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember(); //회원1 생성

        int stockQuantity = 10;
        Item item = createBook("JPA 기본서",100000,stockQuantity);
        int orderCount = 11;

        //when
        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });

        //then
        //fail("재고 수량 부족 예외가 발생해야 한다.");

    }

    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember(); //회원1 생성

        int stockQuantity = 10;
        Item item = createBook("JPA 기본서",100000,stockQuantity);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals( OrderStatus.CANCEL, getOrder.getStatus(), "상품 주문 취소시 상태는 CANCEL");
        assertEquals( 10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야한다.");

    }
}