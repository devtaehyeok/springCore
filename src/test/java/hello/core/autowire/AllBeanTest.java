package hello.core.autowire;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

// 전략 패턴의 활용.
// Map을 이용해 key에 맞는 전략으로 discountPolicy를 사용한다.
public class AllBeanTest {

    @Test
    @DisplayName("스프링이 해당 타입에 맞는 모든 빈을 찾아 넣어준다.")
    void findAllBean() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DiscountService.class, AutoAppConfig.class);
        DiscountService ds = ac.getBean("allBeanTest.DiscountService", DiscountService.class);
        ds.showMap();
        ds.showPolicies();
    }

    @Test
    @DisplayName("전략 패턴을 쉽게 사용할 수 있다.")
    void strategyPattern() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(DiscountService.class, AutoAppConfig.class);
        DiscountService ds = ac.getBean("allBeanTest.DiscountService", DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int discountPrice = ds.discount(member, 10000, "fixDiscountPolicy"); // 고정 할인 정책 천원
        assertThat(ds).isInstanceOf(DiscountService.class);
        assertThat(discountPrice).isEqualTo(1000);
        int rateDiscountPrice = ds.discount(member, 20000, "rateDiscountPolicy"); // 비율 할인 정책 천원
        assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    @RequiredArgsConstructor
    static class DiscountService {

        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        public void showMap() {
            System.out.println("policyMap = " + policyMap);
        }

        public void showPolicies() {
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String discountCode) {
            DiscountPolicy discountPolicy = this.policyMap.get(discountCode);
            return discountPolicy.discount(member, price);
        }

    }
}
