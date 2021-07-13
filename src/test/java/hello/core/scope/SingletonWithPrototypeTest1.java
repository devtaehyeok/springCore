package hello.core.scope;

import jdk.jfr.Description;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonWithPrototypeTest1 {
    @Test
    @DisplayName("프로토타입 빈은 새로운 객체를 계속 생성한다.")
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        bean1.addCount();
        bean2.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);
        assertThat(bean2.getCount()).isEqualTo(1);

    }

    @Test
    @DisplayName("싱글톤 빈은 재사용된다. 따라서 프로토타입 빈도 생성시점에 주입되어 재사용된다.")
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        Cb cb1 = ac.getBean(ClientBean.class);
        assertThat(cb1.logic()).isEqualTo(1);
        Cb cb2 = ac.getBean(ClientBean.class);
        assertThat(cb1).isEqualTo(cb2);
        assertThat(cb2.logic()).isEqualTo(2);


    }


    @Test
    @DisplayName("필요할 때마다 DL로 새로운 프토토타입 빈을 받아서 쓴다.")
    void singletonClientUsePrototype2() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean2.class, PrototypeBean.class);
        Cb cb1 = ac.getBean(ClientBean2.class);
        assertThat(cb1.logic()).isEqualTo(1);
        Cb cb2 = ac.getBean(ClientBean2.class);
        assertThat(cb1).isEqualTo(cb2);
        assertThat(cb2.logic()).isEqualTo(1);
    }

    @Test
    @DisplayName("필요할 때마다 DL로 새로운 프토토타입 빈을 받아서 쓴다. 자바 표준이므로 다른 컨테이너에서도 사용할 수 있다..")
    void singletonClientUsePrototype3() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean3.class, PrototypeBean.class);
        Cb cb1 = ac.getBean(ClientBean3.class);
        assertThat(cb1.logic()).isEqualTo(1);
        Cb cb2 = ac.getBean(ClientBean3.class);
        assertThat(cb1).isEqualTo(cb2);
        assertThat(cb2.logic()).isEqualTo(1);
    }


    interface Cb {
        int logic();
    }

    @Component
    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean implements Cb {
        private final PrototypeBean prototypeBean;

        public int logic() {
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Component
    @Scope("singleton")
    static class ClientBean2 implements Cb {
        @Autowired
        private ObjectFactory<PrototypeBean> prototypeBeanProvider;


        public int logic() {
            PrototypeBean prototypeBean =
                    prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Component
    @Scope("singleton")
    static class ClientBean3 implements Cb {
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean =
                    prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Component
    @Getter
    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");
        }

        @PreDestroy
        @Description("스프링 빈에서 관리되지 않아 출력되지 않음.")
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
