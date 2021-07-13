package hello.core.lifeCycle;

import jdk.jfr.Description;
import lombok.Setter;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.annotation.Documented;

@Setter
@Description("InitializingBean을 구현하면 초기화 완료 후 호출 메소드 DisposableBean 빈 파괴 시 메소드 구현 : 이 방법은 스프링 의존성 생김")
public class NetworkClient implements InitializingBean, DisposableBean {
    private String url;

    public NetworkClient() {
        System.out.println(" 생성자 호출 URL = " + url);
        connect();
        call("초기화 연결 메세지");
    }

    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println(" call : " + url + " message : " + message);
    }

    @Override
    @Description("이 방법으로도 가능")
    @PostConstruct
    public void destroy() throws Exception {
        disConnect();
    }


    public void disConnect() {
        System.out.println("close : " + url);
    }


    @Override
    @Description("이 방법으로도 가능")
    @PreDestroy
    public void afterPropertiesSet() throws Exception {
        connect();
        call("초기화 연결 메세지");
    }
}
