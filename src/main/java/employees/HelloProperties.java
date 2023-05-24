package employees;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Validated
public class HelloProperties {
    @NotBlank
    private String message;
}
