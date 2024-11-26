package tr.com.turksat.log.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import tr.com.turksat.log.dao.WebServiceBaseLogRepository;
import tr.com.turksat.log.dao.data.LogSearchCriteria;
import tr.com.turksat.log.domain.entity.WebServiceBaseLog;
import tr.com.turksat.log.domain.payload.LogRequest;

import java.util.List;

/**
 * @author scinkir
 * @since 25.11.2024
 */
@ApplicationScoped
public class LogService {
    @Inject
    WebServiceBaseLogRepository repository;
    @Transactional
    public WebServiceBaseLog createLog(LogRequest request) {
        WebServiceBaseLog log = new WebServiceBaseLog();
        log.setDtype(request.getServiceName());
        log.setMetotAdi(request.getOperationName());
        log.setGiris(request.getGiris());
        log.setCikis(request.getCikis());
        return repository.saveLog(log);
    }

    public List<WebServiceBaseLog> findByServiceName(String metotAdi) {
        if (metotAdi == null || metotAdi.trim().isEmpty()) {
            throw new IllegalArgumentException("Service name cannot be null or empty");
        }

        return repository.findByServiceName(metotAdi);
    }

    public List<WebServiceBaseLog> searchLogs(LogSearchCriteria criteria) {
        return repository.searchWithCriteria(criteria);
    }

}

