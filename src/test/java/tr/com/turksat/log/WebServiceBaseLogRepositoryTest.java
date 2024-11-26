package tr.com.turksat.log;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tr.com.turksat.log.dao.WebServiceBaseLogRepository;
import tr.com.turksat.log.domain.entity.WebServiceBaseLog;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author scinkir
 * @since 25.11.2024
 */
@QuarkusTest
public class WebServiceBaseLogRepositoryTest {

    @Inject
    WebServiceBaseLogRepository repository;

    @Test
    @DisplayName("Should find logs by giris content")
    void shouldFindByGirisContent() {
        // Arrange
        Map<String, Object> searchCriteria = Map.of(
                "user", Map.of("id", 123)
        );

        // Act
        //List<WebServiceBaseLog> results = repository.findByGirisContent(searchCriteria);

        // Assert
      //  assertNotNull(results);
    }

    @Test
    @DisplayName("Should find logs by giris content")
    @Transactional
    void shouldFindByServiceName() {
        WebServiceBaseLog log = new WebServiceBaseLog();
        log.setGiris("{\"key\": \"value\"}");
        log.setMetotAdi("test");
        log.setDtype("DTYPE1");
        repository.saveLog(log);
    }
}

