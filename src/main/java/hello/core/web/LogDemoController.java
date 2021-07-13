package hello.core.web;

import hello.core.common.MyLogger;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * request마다 다른 UUID가 나오는 것을 볼 수 있음.
 * provider는 필터 / 인터셉터로 변경할 수 있음 {@See webMVC}
 */
@Controller
@Description("웹 scope : request는 뜨는 요청이 없어서 빈이 없는데 주입을 요청하면 처음에 오류남. - provider 써야함.")
@RequiredArgsConstructor
public class LogDemoController {
    private final ObjectProvider<MyLogger> myLoggerProvider;
    private final LogDemoService logDemoService;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        MyLogger myLogger = myLoggerProvider.getObject();
        String requestUrl = request.getRequestURI().toString();
        myLogger.setRequestUrl(requestUrl);
        myLogger.log("controller test");
        logDemoService.logic("testID");
        return "OK";
    }

}
