package hello.core.lombok;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LombokTest {

    private String name;
    private int age;

    public static void main(String[] args) {
        LombokTest lombokTest = new LombokTest();
        lombokTest.setAge(100);
        lombokTest.setName("hello lombok");
        System.out.println(lombokTest.toString());
    }
}
