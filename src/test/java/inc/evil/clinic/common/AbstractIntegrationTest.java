package inc.evil.clinic.common;

import java.io.File;
import java.io.IOException;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.utility.DockerImageName;

public class AbstractIntegrationTest {
    private static final String BUCKET_NAME = "appointments";
    private static final String QUEUE_NAME = "appointments";

    public static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.3"));

    public static final LocalStackContainer localstack =
            new LocalStackContainer(DockerImageName.parse("localstack/localstack:0.12.15"))
                    .withServices(Service.S3, Service.SQS);


    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("cloud.aws.credentials.secretKey", localstack::getSecretKey);
        registry.add("cloud.aws.credentials.accessKey", localstack::getAccessKey);
        registry.add("aws.endpoint-override", () -> localstack.getEndpointOverride(Service.S3).toString());
        registry.add("appointments.queue-name", () -> QUEUE_NAME);
        registry.add("appointments.bucket-name", () -> BUCKET_NAME);
    }

    static {
        postgres.start();
        localstack.start();
        createResources();
    }

    private static void createResources() {
        try {
            localstack.execInContainer("awslocal", "s3", "mb", "s3://" + BUCKET_NAME);
            localstack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", QUEUE_NAME);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}































//    public static final DockerComposeContainer<?> environment =
//        new DockerComposeContainer<>(new File("src/test/resources/docker-compose.yaml"))
//            .withExposedService("postgres", 5432)
//            .withExposedService("localstack", 4566);
//
//    @DynamicPropertySource
//    public static void properties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5432/spring-declarative-tx-db");
//        registry.add("spring.datasource.username", () -> "postgres");
//        registry.add("spring.datasource.password", () -> "postgres");
//        registry.add("cloud.aws.credentials.secretKey", () -> "secretkey");
//        registry.add("cloud.aws.credentials.accessKey", () -> "accesskey");
//        registry.add("aws.endpoint-override", () -> "http://localhost:4566");
//        registry.add("appointments.queue-name", () -> QUEUE_NAME);
//        registry.add("appointments.bucket-name", () -> BUCKET_NAME);
//    }
