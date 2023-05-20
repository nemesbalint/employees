package employees;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class EmployeeDto {

    @Schema(description = "Identifier of employee", example = "1")
    private Long id;
    @Schema(description = "Name of the employee", example = "John Doe")
    private String name;
}
