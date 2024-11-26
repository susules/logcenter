package tr.com.turksat.log.domain.payload;

/**
 * @author scinkir
 * @since 25.11.2024
 */
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
@Schema(description = "Log Response Object")
public class LogResponse {

    @Schema(description = "Log entry ID")
    private Long id;

    @Schema(description = "Status of the log operation", example = "SUCCESS")
    private String status;

    @Schema(description = "Timestamp of when the log was processed")
    private Instant processedAt;

    @Schema(description = "Any additional message", example = "Log entry created successfully")
    private String message;

    // Static factory methods for common responses
    public static LogResponse success(Long id) {
        return LogResponse.builder()
                .id(id)
                .status("SUCCESS")
                .processedAt(Instant.now())
                .message("Log entry created successfully")
                .build();
    }

    public static LogResponse error(String message) {
        return LogResponse.builder()
                .status("ERROR")
                .processedAt(Instant.now())
                .message(message)
                .build();
    }
}

