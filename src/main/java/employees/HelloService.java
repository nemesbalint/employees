package employees;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//@Service kkomentezhető ha Java konfigurációban mi fogjuk péládnyosítani a HelloService-t
@Service
public class HelloService {
    public String sayHello() {
        return "Hello Spring Boot (service - devtools 12) "+ LocalDateTime.now();
    }

}
