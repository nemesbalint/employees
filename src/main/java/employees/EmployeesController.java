package employees;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeesController {
    private EmployeeService employeeService; //kötelező függőség!

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping
    public List<EmployeeDto> listEmployees() {
        return employeeService.listEmployees();
    }

}
