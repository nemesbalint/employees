package employees;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EmployeeService {

    public static final String EMPLOYEES_CREATED_COUNTER = "employees.created";
    private EmployeesRepository repository;
    private EmployeeMapper employeeMapper;
    private AddressesGateway addressesGateway;
    private EventStoreGateway eventStoreGateway;
    private MeterRegistry meterRegistry;
    private ApplicationEventPublisher eventPublisher;

    @PostConstruct
    public void init() {
        Counter.builder(EMPLOYEES_CREATED_COUNTER)
                .baseUnit("employees")
                .description("Number of created employees")
                .register(meterRegistry);
    }

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
        meterRegistry.counter(EMPLOYEES_CREATED_COUNTER).increment();
//        eventPublisher.publishEvent(new AuditApplicationEvent("anonymous", "employee_has_been_created",
//                Map.of("name", command.getName())));
//        eventStoreGateway.sendMessage(command.getName());
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

    public AddressDto findAddressById(long id) {
        log.debug("findAddressById start with id: {}", id );
        Employee employee = repository.findById(id).orElseThrow(()->new IllegalArgumentException("employee not found"));
        log.debug("findAddressById findAddressByName with name: {}", employee.getName() );
        return addressesGateway.findAddressByName(employee.getName());
    }

}
