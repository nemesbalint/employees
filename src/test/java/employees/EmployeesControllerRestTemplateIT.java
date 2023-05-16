package employees;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest mert az egesz alkalmazast eliditjuk, webEnvironment valos http rest kereseket kuldunk, a Tomcat is elindul
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeesControllerRestTemplateIT {

    @Autowired
    TestRestTemplate template;

    @Autowired
    EmployeeService employeeService;

//    @Test
    @RepeatedTest(2)
    public void testListEmployees() {

        employeeService.deleteAllEmployees();

        EmployeeDto employeeDto =
                template.postForObject(
                        "/api/employees",
                        new CreateEmployeeCommand("John Doe"),
                        EmployeeDto.class);

        assertEquals("John Doe", employeeDto.getName());

        template.postForObject(
                "/api/employees",
                new CreateEmployeeCommand("Jane Doe"),
                EmployeeDto.class);

        List<EmployeeDto> employees =
            template.exchange(
                    "/api/employees",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<EmployeeDto>>() {})
                    .getBody();

        assertThat(employees)
                .extracting(EmployeeDto::getName)
                .containsExactly("John Doe", "Jane Doe");



    }

}
