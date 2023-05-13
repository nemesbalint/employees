package employees;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

//@Controller
@RestController
public class HelloController {

    private HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/")
    //@ResponseBody, mert @RestController esetén nem kell
    public String sayHello() {
        //return "Hello Spring Boot "+ LocalDateTime.now();
        return helloService.sayHello();
    }
}
