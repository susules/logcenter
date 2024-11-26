package tr.com.turksat.log.domain.payload;

/**
 * @author scinkir
 * @since 25.11.2024
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@Schema(description = "Log Request Object")
public class LogRequest {

    @NotBlank(message = "Service name cannot be blank")
    @Schema(description = "Name of the service", example = "UserService", required = true)
    private String serviceName;

    @NotBlank(message = "Operation name cannot be blank")
    @Schema(description = "Name of the operation", example = "createUser", required = true)
    private String operationName;

    @NotNull(message = "Request payload cannot be null")
    @Schema(description = "Request payload in JSON format", required = true)
    private String giris;

    @Schema(description = "Response payload in JSON format")
    private String cikis;

    @Schema(description = "User ID performing the operation", example = "123")
    private Long userId;

    @Schema(description = "Client IP address", example = "192.168.1.1")
    private String clientIp;

    @Schema(description = "Additional metadata in JSON format")
    private String metadata;

    // Custom validation method
    public void validate() {
        validateJsonFormat(giris, "Request payload (giris)");
        if (cikis != null) {
            validateJsonFormat(cikis, "Response payload (cikis)");
        }
        if (metadata != null) {
            validateJsonFormat(metadata, "Metadata");
        }
    }

    private void validateJsonFormat(String jsonStr, String fieldName) {
        try {
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            objectMapper.readTree(jsonStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON format in " + fieldName + ": " + e.getMessage());
        }
    }
}

