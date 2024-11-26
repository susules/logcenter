package tr.com.turksat.log.dao;

/**
 * @author scinkir
 * @since 25.11.2024
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import tr.com.turksat.log.dao.data.LogSearchCriteria;
import tr.com.turksat.log.domain.entity.WebServiceBaseLog;

import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class WebServiceBaseLogRepository implements PanacheRepository<WebServiceBaseLog> {

    private static final Logger LOG = Logger.getLogger(WebServiceBaseLogRepository.class);

    @Inject
    EntityManager em;

    @Inject
    ObjectMapper objectMapper;

    /**
     * Find logs by service name
     */
    public List<WebServiceBaseLog> findByServiceName(String serviceName) {
        return list("serviceName", serviceName);
    }

    /**
     * Find logs by date range
     */
    public List<WebServiceBaseLog> findByDateRange(Instant startDate, Instant endDate) {
        return find("requestTime between ?1 and ?2", startDate, endDate).list();
    }

    /**
     * Find logs by service name and date range
     */
    public List<WebServiceBaseLog> findByServiceNameAndDateRange(
            String serviceName, Instant startDate, Instant endDate) {
        return find("serviceName = ?1 and requestTime between ?2 and ?3",
                serviceName, startDate, endDate).list();
    }

    /**
     * Search in JSONB giris field
     */
    public List<WebServiceBaseLog> findByGirisContent(String jsonPath, String value) {
        String query = "FROM WebServiceBaseLog WHERE giris #>> :path = :value";
        return em.createQuery(query, WebServiceBaseLog.class)
                .setParameter("path", jsonPath)
                .setParameter("value", value)
                .getResultList();
    }

    /**
     * Full text search in JSONB fields
     */
    public List<WebServiceBaseLog> searchInJsonContent(String searchTerm) {
        String query = "FROM WebServiceBaseLog WHERE " +
                "CAST(giris AS text) ILIKE :term OR " +
                "CAST(cikis AS text) ILIKE :term";

        return em.createQuery(query, WebServiceBaseLog.class)
                .setParameter("term", "%" + searchTerm + "%")
                .getResultList();
    }

    /**
     * Find logs by status code
     */
    public List<WebServiceBaseLog> findByStatusCode(String statusCode) {
        return list("statusCode", statusCode);
    }

    /**
     * Find logs with execution time greater than specified milliseconds
     */
    public List<WebServiceBaseLog> findSlowRequests(Long executionTimeThreshold) {
        return find("executionTime > ?1", executionTimeThreshold).list();
    }

    /**
     * Find logs by user ID
     */
    public List<WebServiceBaseLog> findByUserId(String userId) {
        return list("userId", userId);
    }

    /**
     * Find logs by client IP
     */
    public List<WebServiceBaseLog> findByClientIp(String clientIp) {
        return list("clientIp", clientIp);
    }

    /**
     * Advanced search with multiple criteria
     */
    public List<WebServiceBaseLog> searchWithCriteria(LogSearchCriteria criteria) {
        StringBuilder queryBuilder = new StringBuilder("FROM WebServiceBaseLog WHERE 1=1");

        if (criteria.getUserId() != null) {
            queryBuilder.append(" AND serviceName = :serviceName");
        }
        if (criteria.getStartDate() != null) {
            queryBuilder.append(" AND requestTime >= :startDate");
        }
        if (criteria.getEndDate() != null) {
            queryBuilder.append(" AND requestTime <= :endDate");
        }
        if (criteria.getStatusCode() != null) {
            queryBuilder.append(" AND statusCode = :statusCode");
        }
        if (criteria.getUserId() != null) {
            queryBuilder.append(" AND userId = :userId");
        }

        TypedQuery<WebServiceBaseLog> query = em.createQuery(queryBuilder.toString(), WebServiceBaseLog.class);

        if (criteria.getServiceName() != null) {
            query.setParameter("serviceName", criteria.getServiceName());
        }
        if (criteria.getStartDate() != null) {
            query.setParameter("startDate", criteria.getStartDate());
        }
        if (criteria.getEndDate() != null) {
            query.setParameter("endDate", criteria.getEndDate());
        }
        if (criteria.getStatusCode() != null) {
            query.setParameter("statusCode", criteria.getStatusCode());
        }
        if (criteria.getUserId() != null) {
            query.setParameter("userId", criteria.getUserId());
        }

        return query.getResultList();
    }

    /**
     * Save log with validation
     */
    @Transactional
    public WebServiceBaseLog saveLog(WebServiceBaseLog log) {
        try {
            if (log.getGiris() != null) {
                // Validate JSON format
                objectMapper.readTree(log.getGiris());
            }
            if (log.getCikis() != null) {
                // Validate JSON format
                objectMapper.readTree(log.getCikis());
            }

            persist(log);
            return log;
        } catch (Exception e) {
            LOG.error("Error saving log", e);
            throw new RuntimeException("Error saving log: " + e.getMessage(), e);
        }
    }

    /**
     * Delete old logs
     */
    @Transactional
    public long deleteOldLogs(Instant beforeDate) {
        return delete("requestTime < ?1", beforeDate);
    }


}

