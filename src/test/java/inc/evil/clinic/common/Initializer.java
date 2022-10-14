package inc.evil.clinic.common;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;
import java.util.stream.Stream;

public class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    public static Map<String, Object> getPropertiesOf(PostgreSQLContainer<?> postgresContainer) {
        Startables.deepStart(Stream.of(postgresContainer)).join();

        return Map.of(
                "spring.datasource.url", postgresContainer.getJdbcUrl(),
                "spring.datasource.username", postgresContainer.getUsername(),
                "spring.datasource.password", postgresContainer.getPassword()
        );
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        var env = context.getEnvironment();
        env.getPropertySources().addFirst(new MapPropertySource("testcontainers", getPropertiesOf(postgres)));
    }
}
