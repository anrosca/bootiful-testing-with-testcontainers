package inc.evil.clinic.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.s3.DiskBufferingS3OutputStreamProvider;
import io.awspring.cloud.s3.Jackson2JsonS3ObjectConverter;
import io.awspring.cloud.s3.PropertiesS3ObjectContentTypeResolver;
import io.awspring.cloud.s3.S3Template;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;

import java.net.URI;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AwsProperties.class)
public class AwsConfig extends AbstractAwsConfig {
    @Bean
    public AmazonS3 amazonS3(AwsProperties awsProperties) {
        return awsClientBuilderFrom(awsProperties, AmazonS3Client.builder())
                .build();
    }

    @Bean
    public S3Client s3Client(AwsProperties awsProperties) {
        S3ClientBuilder builder = S3Client.builder();
        if (awsProperties.endpointOverride() != null) {
            int localstackPort = URI.create(awsProperties.endpointOverride()).getPort();
            builder.endpointOverride(URI.create("http://s3.localhost.localstack.cloud:" + localstackPort));
        }
        return builder
                .build();
    }

    @Bean
    public AmazonSQSAsync amazonSQSAsync(AwsProperties awsProperties) {
        return awsClientBuilderFrom(awsProperties, AmazonSQSAsyncClient.asyncBuilder())
                .build();
    }

    @Bean
    public S3Template s3Template(S3Client s3Client, ObjectMapper objectMapper) {
        return new S3Template(s3Client, new DiskBufferingS3OutputStreamProvider(s3Client, new PropertiesS3ObjectContentTypeResolver()), new Jackson2JsonS3ObjectConverter(objectMapper));
    }
}
