package employees;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

//@Service kkomentezhető ha Java konfigurációban mi fogjuk péládnyosítani a HelloService-t
@Service
@AllArgsConstructor
@EnableConfigurationProperties(HelloProperties.class)
public class HelloService {

//    private String message;
//    ha a HelloProperties osztályt használjuk akkor ez már nem kell.
//    public HelloService(@Value("${employees.message}") String message) {
//        this.message = message;
//    }

    private HelloProperties helloProperties;
    //ehhez az injection-höz kell egy konstruktor vagy az @AllArgsConstructor

    public String sayHello() {
//        return message + " "+ LocalDateTime.now();
        return helloProperties.getMessage() + " "+ LocalDateTime.now();
    }

}
