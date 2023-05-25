package employees;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {

    private EmployeesRepository repository;
    private EmployeeMapper employeeMapper;

    public List<EmployeeDto> listEmployees(Optional<String> prefix) {

        List<Employee> filtered =
            repository.findAll().stream()
                    .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                    .collect(Collectors.toList());

        log.debug("listEmployees called with prefix: {}, result count is {} ", prefix, filtered.size());

        return employeeMapper.toDto(filtered);
    }

    public EmployeeDto findEmployeeById(long id) {
        return employeeMapper.toDto(repository.findById(id).orElseThrow(()->new IllegalArgumentException("employee not found")));
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee(command.getName());
        repository.save(employee);
        log.info("Employee has been created");
        log.debug("Employee has been created with name {}", command.getName());
        return employeeMapper.toDto(employee);
    }

    @Transactional
    public EmployeeDto updateEmployee(long id, UpdateEmployeeCommand command) {
        Employee employee = repository.findById(id).orElseThrow(()->new IllegalArgumentException("employee not found"));
        employee.setName(command.getName());
        log.debug("Employee has been updated with name {}", command.getName());
        return employeeMapper.toDto(employee);
    }

    public void deleteEmployee(long id) {
        log.debug("Employee has been deleted with id {}", id);
        repository.deleteById(id);
    }

    public void deleteAllEmployees() {
        log.debug("Employees has been deleted");
        repository.deleteAll();
    }
}
