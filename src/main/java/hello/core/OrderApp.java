package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.*;
import order.Order;
import order.OrderService;
import order.OrderServiceImpl;

public class OrderApp {
    // psvm
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();
        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);
        System.out.println("order = " + order);
        System.out.println("order price= " + order.calculatePrice());

    }
}
