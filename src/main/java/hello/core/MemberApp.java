package hello.core;

import hello.core.member.*;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        // 스프링 IOC 컨테이너
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 메서드 명이 스프링 빈 이름
        MemberService memberService = applicationContext.getBean("memServiceBean", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);
        Member findMember = memberService.findMember(1L);
        System.out.println("findMember = " + findMember.getName());
        System.out.println("memberA = " + memberA.getName());


    }
}
