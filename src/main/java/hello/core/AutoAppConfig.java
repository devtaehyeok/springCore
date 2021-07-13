package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

// 자동스캔에서 AppConfig 등 제외. 실무에서는 스캔 정보를 지우지는 않음.
@Configuration
@ComponentScan(
        basePackages = "hello.core", // 기본은 해당 파일이 있는 패키지 하위.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
