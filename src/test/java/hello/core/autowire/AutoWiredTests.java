package hello.core.autowire;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

/**
 * 주입할 스프링 빈이 없어도 동작해야 할 때가 있다.
 * 그런데 @Autowired만 사용하면 required 옵션의 기본값이 true로 되어 있어서 자동주입 대상이 없으면 오류가 발생한다.
 */
@SpringBootTest
public class AutoWiredTests {

    @Test
    void AutowiredOption() {
        new AnnotationConfigApplicationContext(TestBean.class);

    }

    static class TestBean {
        // true면 오류남.
        // 자동 주입할 대상이 없으면 호출 자체가 안된다.
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        // 호출은 되는데 null로 들어온다.
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        @Autowired
        public void setNoBean3(@Nullable Optional<Member> noBean3) {
            System.out.println("noBean2 = " + noBean3);
        }
    }
}
