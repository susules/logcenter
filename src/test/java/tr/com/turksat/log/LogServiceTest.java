package tr.com.turksat.log;

/**
 * @author scinkir
 * @since 25.11.2024
 */
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import jakarta.persistence.EntityManager;
import tr.com.turksat.log.domain.entity.WebServiceBaseLog;
import tr.com.turksat.log.domain.payload.LogRequest;
import tr.com.turksat.log.service.LogService;

import java.time.Instant;
import static org.junit.jupiter.api.Assertions.*;


@QuarkusTest
public class LogServiceTest {

    @Inject
    LogService logService;

    @InjectMock
    EntityManager entityManager;

    private LogRequest createSampleLogRequest() {
        return LogRequest.builder()
                .serviceName("TestService")
                .operationName("testOperation")
                .giris("{\"test\": \"data\"}")
                .cikis("{\"result\": \"success\"}")
                .userId(123L)
                .clientIp("127.0.0.1")
                .build();
    }

    @Nested
    @DisplayName("createLog Tests")
    class CreateLogTests {

        @Test
        @DisplayName("Should successfully create a log entry")
        void shouldCreateLogSuccessfully() {
            // Arrange
            LogRequest request = createSampleLogRequest();

            // Act
            WebServiceBaseLog result = logService.createLog(request);

            // Assert
            assertNotNull(result);
            assertEquals(request.getServiceName(), result.getDtype());
            assertEquals(request.getOperationName(), result.getMetotAdi());
            assertEquals(request.getGiris(), result.getGiris());
            assertEquals(request.getCikis(), result.getCikis());
            assertEquals(request.getUserId(), result.getOlusturanKullaniciId());
            assertNotNull(result.getOlusturulmaTarihi());
        }

        @Test
        @DisplayName("Should handle null cikis")
        void shouldHandleNullCikis() {
            // Arrange
            LogRequest request = createSampleLogRequest();
            request.setCikis(null);

            // Act
            WebServiceBaseLog result = logService.createLog(request);

            // Assert
            assertNotNull(result);
            assertNull(result.getCikis());
        }

        @Test
        @DisplayName("Should validate JSON format in giris")
        void shouldValidateGirisJsonFormat() {
            // Arrange
            LogRequest request = createSampleLogRequest();
            request.setGiris("invalid json");

            // Act & Assert
            assertThrows(IllegalArgumentException.class, () -> {
                request.validate();
            });
        }
    }

    @Nested
    @DisplayName("Search Log Tests")
    class SearchLogTests {

        @Test
        @DisplayName("Should find logs by service name")
        void shouldFindLogsByServiceName() {
            // Arrange
            String serviceName = "TestService";

            // Act
            var results = logService.findByServiceName(serviceName);

            // Assert
            assertNotNull(results);
        }


        @Nested
        @DisplayName("JSON Processing Tests")
        class JsonProcessingTests {

            @Test
            @DisplayName("Should handle complex JSON in giris")
            void shouldHandleComplexJsonInGiris() {
                // Arrange
                String complexJson = """
                        {
                            "user": {
                                "id": 123,
                                "name": "Test User",
                                "roles": ["admin", "user"],
                                "settings": {
                                    "theme": "dark",
                                    "notifications": true
                                }
                            }
                        }
                        """;

                LogRequest request = createSampleLogRequest();
                request.setGiris(complexJson);

                // Act
                WebServiceBaseLog result = logService.createLog(request);

                // Assert
                assertNotNull(result);
                assertEquals(complexJson, result.getGiris());
            }
        }
    }

}
