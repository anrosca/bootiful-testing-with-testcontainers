package inc.evil.clinic.common;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.lang.NonNull;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;
import java.util.stream.Stream;

import static inc.evil.clinic.common.Initializer.getPropertiesOf;

public class WithPostgresqlExtension extends AbstractTestExecutionListener implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    @Override
    public void beforeTestClass(@NonNull TestContext testContext) throws Exception {
    }

    @Override
    public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
        Startables.deepStart(Stream.of(postgres)).join();
        applicationContext.getEnvironment().getPropertySources().addFirst(new MapPropertySource("testcontainers", (Map) getPropertiesOf(postgres)));
    }
}
