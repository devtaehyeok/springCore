package hello.core.singleton;

public class SingletonService {
    // 1. static 영역에 객체를 딱 1개만 생성해둔다.
    private static final SingletonService instance = new SingletonService();

    // 2. 해당 메소드로만 인스턴스를 얻을 수 있다.
    public static SingletonService getInstance() {
        return instance;
    }

    // 3 생성자를 private로 선언해서 외부에서 new 키워드를 사용해 객체 생성을 못하게 한다.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 호출");
    }
}
