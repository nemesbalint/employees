package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelloIT {
    @Autowired
    HelloController helloController;

    @Test
    public void sayHelloTest() {
        var message = helloController.sayHello();
        assertThat(message).startsWith("Hello");
    }
}
