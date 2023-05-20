package employees;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Operations on employees")
public class EmployeesController {
    private EmployeeService employeeService; //kötelező függőség!

    public EmployeesController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "list employees with given prefix")
    public List<EmployeeDto> listEmployees(@RequestParam Optional<String> prefix) {
        return employeeService.listEmployees(prefix);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "list employees with given prefix xml and json version")
    public EmployeesDto listEmployeesAsJsonAndXml(@RequestParam Optional<String> prefix) {
        return new EmployeesDto(employeeService.listEmployees(prefix));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "find employee by given identifier")
    public EmployeeDto findEmployeeById(@PathVariable("id") long id) {
        return employeeService.findEmployeeById(id);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity findEmployeeById(@PathVariable("id") long id) {
//        try {
//            return ResponseEntity.ok(employeeService.findEmployeeById(id));
//        } catch (IllegalArgumentException iea) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "create and employee")
    @ApiResponse(responseCode = "201", description = "employee has been created")
    public EmployeeDto createEmployee(@RequestBody CreateEmployeeCommand command) {
        return employeeService.createEmployee(command);
    }
    @PutMapping("/{id}")
    @Operation(summary = "modify an employee")
    public EmployeeDto updateEmployee(@PathVariable("id") long id, @RequestBody UpdateEmployeeCommand command) {
        return employeeService.updateEmployee(id, command);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "delete given employee")
    public void deleteEmployee(@PathVariable("id") long id) {
        employeeService.deleteEmployee(id);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ResponseEntity<ProblemDetail> handleNotFound(IllegalArgumentException iea) {
//        ProblemDetail problemDetail =
//                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, iea.getMessage());
//        problemDetail.setType(URI.create("employees/not-found"));
//        problemDetail.setTitle("Not found");
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
//                .body(problemDetail);
//    }
}
