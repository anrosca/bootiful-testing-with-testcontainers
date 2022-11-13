package inc.evil.clinic.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AwsProperties.class)
public class AwsConfig extends AbstractAwsConfig {
    @Bean
    public AmazonS3 amazonS3(AwsProperties awsProperties) {
        return awsClientBuilderFrom(awsProperties, AmazonS3Client.builder())
                .build();
    }
}
