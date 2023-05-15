package employees;

//import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeesApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeesApplication.class, args);
	}

	//Java konfigurációban mi fogjuk péládnyosítani a HelloService-t
	//azonban a saját komponenesinket érdemesebb anotációval ellátni
	//és third party komponenseket érdemes Java konfigurációval péládnyosítani
	//a @SpringBootApplication tartalmaz @ComponentScant
//	@Bean
//	public HelloService helloService() {
//		return new HelloService();
//	}

//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}

}
