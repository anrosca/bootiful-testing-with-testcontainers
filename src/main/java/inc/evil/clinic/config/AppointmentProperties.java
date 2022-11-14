package inc.evil.clinic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("appointments")
@ConstructorBinding
public record AppointmentProperties(String queueName, String bucketName) {
}
