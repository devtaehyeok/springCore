package hello.core.lifeCycle;

import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifecycleTest {

    @Test
    @DisplayName("초기화 콜백은 빈이 생성되고 의존관계주입이 '완료' 된 후 호출되며 / 소멸전 콜백은 빈이 '소멸되기 직전' 호출된다.")
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();

    }

    @Configuration
    @DisplayName("스프링 빈의 라이프 사이클 : 스프링 컨테이너 생성 / 빈 생성 / 의존관계주입 / 초기화 콜백  / 사용 / 소멸전콜백 / 스프링 종료")
    static class LifeCycleConfig {
        @Description("아래처럼 쓰면 스프링 interface 구현 없이 라이프사이클 메서드 사용 가능. destoryMethod는 디폴트 추론기능이 있음. close, shutDown")
        @Bean(initMethod = "afterPropertiesSet", destroyMethod = "destroy")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
