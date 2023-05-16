package employees;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Csak a Kontroller reteget akarjuk tesztelni ezert @WebMvcTest
//a controllers azert kell mert a HelloService-t is keresi, a HelloController elinditasakor
@WebMvcTest(controllers = EmployeesController.class)
public class EmployeesControllerWebMvcIT {

    @MockBean
    EmployeeService employeeService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testListEmployees() throws Exception {
        when(employeeService.listEmployees(any()))
                .thenReturn(List.of(
                        new EmployeeDto(1L, "John Doe"),
                        new EmployeeDto(2L, "Jane Doe")
                ));

        mockMvc.perform(get("/api/employees"))
//                .andDo(print());
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name",equalTo("John Doe")));
    }
}
