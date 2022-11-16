package inc.evil.clinic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("aws")
@ConstructorBinding
public record AwsProperties(String endpointOverride, String region) {
}
