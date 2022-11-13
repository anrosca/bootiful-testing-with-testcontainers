package inc.evil.clinic.common;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;

public class AbstractIntegrationTest {
    private static final String BUCKET_NAME = "foo";

    public static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    public static final LocalStackContainer localstack =
            new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.12.15"))
                    .withServices(Service.S3);


    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("aws.endpoint-override", () -> localstack.getEndpointOverride(Service.S3).toString());
    }

    static {
        postgres.start();
        localstack.start();
        createBuckets();
    }

    private static void createBuckets() {
        try {
            localstack.execInContainer("awslocal", "s3", "mb", "s3://" + BUCKET_NAME);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
