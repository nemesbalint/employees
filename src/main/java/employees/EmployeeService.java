package employees;

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

    private EmployeesDao employeesDao;
    private EmployeeMapper employeeMapper;

    public List<EmployeeDto> listEmployees(Optional<String> prefix) {

        List<Employee> filtered =
            employeesDao.findAll().stream()
                    .filter(e -> prefix.isEmpty() || e.getName().toLowerCase().startsWith(prefix.get().toLowerCase()))
                    .collect(Collectors.toList());

        log.debug("listEmployees called with prefix: {}, result count is {} ", prefix, filtered.size());

        return employeeMapper.toDto(filtered);
    }

    public EmployeeDto findEmployeeById(long id) {
        return employeeMapper.toDto(employeesDao.findById(id));
    }

    public EmployeeDto createEmployee(CreateEmployeeCommand command) {
        Employee employee = new Employee(command.getName());
        employeesDao.createEmployee(employee);
        log.info("Employee has been created");
        log.debug("Employee has been created with name {}", command.getName());
        return employeeMapper.toDto(employee);
    }

    public EmployeeDto updateEmployee(long id, UpdateEmployeeCommand command) {
        Employee employee = new Employee(id, command.getName());
        employeesDao.updateEmployee(employee);
        log.debug("Employee has been updated with name {}", command.getName());
        return employeeMapper.toDto(employee);
    }

    public void deleteEmployee(long id) {
        log.debug("Employee has been deleted with id {}", id);
        employeesDao.deleteEmployee(id);
    }

    public void deleteAllEmployees() {
        log.debug("Employees has been deleted");
        employeesDao.deleteAll();
    }
}
