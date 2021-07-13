package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class AtConfiguration {

    @Test
    @DisplayName("AppConfig에서 얻은 빈들은 전부 싱글톤이다.")
    void configTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();
        assertThat(memberRepository1).isEqualTo(memberRepository2).isEqualTo(memberRepository);
    }

    @Test
    @DisplayName("해당 비밀은 AppConfig에 있다.")
    void configTestDip() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        AppConfig bean = ac.getBean(AppConfig.class);
        // 클래스 이름이 복잡해진 것을 볼 수 있다.
        // CGLIB의 내부 기술을 활용한다.
        // 스프링 컨테이너에 없으면 등록, 있으면 반환.
        // 해당 로직 덕택에 싱글톤이 보장.
        // 실제로 AppConfig가 등록되는게 아니라 CGLIB이 붙은 자식 클래스가 반환한다.
        // @Configuration을 붙이면 Singleton보장 아니면 싱글톤 아님. 컨테이너가 관리안함.
        System.out.println("bean = " + bean);
    }
}
