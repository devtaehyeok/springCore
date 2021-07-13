package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

// 싱글톤은 DIP를 위반한다. (구체클래스에서 getInstance) > OCP 위반.
// private 생성자 자식 클래스 만들기 어려움.
// 스프링 컨테이너를 통한 싱글톤으로 해결!!
// 싱글톤은 항상 무상태로 설계하자.
public class SingletonTest {
    @Test
    @DisplayName("스프링 없는 순수 컨테이너는 요청마다 클래스를 매번 생성한다.")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회 : 호출할 때마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        // 2. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();
        // 참조값이 다른 것을 확인
        assertThat(memberService1).isNotEqualTo(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService instance1 = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();
        // same 참조 비교
        // equal 진짜 객체 같은지 깊게 비교
        assertThat(instance1).isSameAs(instance2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);
        // memberService1 == memberservice2
        assertThat(memberService1).isSameAs(memberService2);
    }
}
