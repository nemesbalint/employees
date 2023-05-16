package employees;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

public class EmployeeNotFoundException extends ErrorResponseException {
    public EmployeeNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND,
                asProblemDetail(String.format("Employee with id %d not found!", id)),
                null
        );
    }

    private static ProblemDetail asProblemDetail(String message) {
        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message);
        problemDetail.setType(URI.create("employees/not-found"));
        problemDetail.setTitle("Not found");
        return problemDetail;
    }
}
