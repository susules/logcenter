package tr.com.turksat.log.dao.data;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

/**
 * @author scinkir
 * @since 25.11.2024
 */
@Data
@Builder
public class LogSearchCriteria {
    private String serviceName;
    private Instant startDate;
    private Instant endDate;
    private String statusCode;
    private String userId;
    private String clientIp;
    private Long minExecutionTime;
    private Long maxExecutionTime;
}

