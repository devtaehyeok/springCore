package hello.core.singleton;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StatefulServiceTest {
    @Test
    @DisplayName("A주문 중간에 B가 끼어들어 A 주문 가격 조회가 20000원이 된다.")
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);
        // ThreadA : A사용자 10000 주문
        statefulService1.order("userA", 10000);
        // ThreadB : B사용자 10000 주문
        statefulService2.order("userB", 20000);
        // 사용자 A 금액 조회
        int price = statefulService1.getPrice(); // 20000 되버림 ㅠ
        assertThat(price).isEqualTo(20000);

    }

    @Test
    @DisplayName("무상태 빈으로 해결하자.")
    void statelessServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);
        assertThat(statefulService1.orderStateless("userA", 10000)).isEqualTo(10000);
        assertThat(statefulService2.orderStateless("userB", 20000)).isEqualTo(20000);

    }


    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }

    }
}
