package employees;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private EmployeeMapper employeeMapper;

    private AtomicLong id = new AtomicLong();

    private List<Employee> employees = Collections.synchronizedList(new ArrayList<>(
            List.of(
                    new Employee(id.getAndIncrement(), "John Doe"),
                    new Employee(id.getAndIncrement(), "Jack Doe")
            )
    ));

    public EmployeeService(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    public List<EmployeeDto> listEmployees(Optional<String> prefix) {

        List<Employee> filtered = employees.stream()
                .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                .collect(Collectors.toList());

        return employeeMapper.toDto(filtered);
    }

    public EmployeeDto findEmployeeById(long id) {
        return employeeMapper.toDto(employees.stream()
                .filter(e -> e.getId() == id).findAny()
                .orElseThrow(() -> new EmployeeNotFoundException(id)));
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee(id.getAndIncrement(), command.getName());
        employees.add(employee);
        return employeeMapper.toDto(employee);
    }

    public EmployeeDto updateEmployee(long id, UpdateEmployeeCommand command) {
        Employee employee = employees.stream()
                .filter(e-> e.getId() == id)
                .findFirst()
                .orElseThrow(()-> new EmployeeNotFoundException(id));
        employee.setName(command.getName());
        return employeeMapper.toDto(employee);
    }

    public void deleteEmployee(long id) {
        Employee employee = employees.stream()
                .filter(e-> e.getId() == id)
                .findFirst()
                .orElseThrow(()-> new EmployeeNotFoundException(id));
        employees.remove(employee);
    }

    public void deleteAllEmployees() {
        id = new AtomicLong();
        employees.clear();
    }
}
