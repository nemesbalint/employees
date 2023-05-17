package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeesControllerWebClientIT {

    @MockBean
    EmployeeService employeeService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testCreateEmployees() {
        when(employeeService.createEmployee(any()))
                .thenReturn(new EmployeeDto(1L, "John Doe"));

        webTestClient
                .post()
                .uri("/api/employees")
                .bodyValue( new CreateEmployeeCommand("John Doe"))
                .exchange()
                .expectStatus().isCreated()
//                .expectBody(String.class).value(s-> System.out.println(s))
//                .expectBody().jsonPath("name").isEqualTo("John Doe")
                .expectBody(EmployeeDto.class).value(e -> assertEquals("John Doe", e.getName()))
        ;
    }

    @Test
    public void testFindEmployeeById() {
        when(employeeService.findEmployeeById(1L))
                .thenReturn(new EmployeeDto(1L, "John Doe"));

        webTestClient
                .get()
                .uri("/api/employees/{id}", 1)
                .exchange()
                .expectBody(EmployeeDto.class).value(e -> assertEquals("John Doe", e.getName()));
    }

    @Test
    public void testListEmployees() {
        when(employeeService.listEmployees(any()))
                .thenReturn(List.of(
                        new EmployeeDto(1L, "John Doe"),
                        new EmployeeDto(2L, "Jane Doe")
                ));

        webTestClient
                .get()
                .uri("/api/employees")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .hasSize(2)
                .contains( new EmployeeDto(1L, "John Doe"));
    }

    @Test
    public void testListEmployeesByPrefix() {
        when(employeeService.listEmployees(any()))
                .thenReturn(List.of(
                        new EmployeeDto(1L, "John Doe"),
                        new EmployeeDto(2L, "Jack Doe")
                ));

        webTestClient
                .get()
                .uri(builder -> builder.path("/api/employees").queryParam ("prefix", "JWebClient").build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EmployeeDto.class)
                .hasSize(2)
                .contains( new EmployeeDto(1L, "John Doe"));

    }

}
