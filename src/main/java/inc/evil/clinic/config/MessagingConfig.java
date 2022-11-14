package inc.evil.clinic.config;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import io.awspring.cloud.sqs.config.SqsBootstrapConfiguration;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import org.springframework.boot.autoconfigure.jms.JmsProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClientBuilder;

import java.net.URI;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(AppointmentProperties.class)
@Import(SqsBootstrapConfiguration.class)
public class MessagingConfig extends AbstractAwsConfig {
    @Bean
    public MappingJackson2MessageConverter mappingJackson2MessageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter jackson2MessageConverter = new MappingJackson2MessageConverter();
        jackson2MessageConverter.setObjectMapper(objectMapper);
        return jackson2MessageConverter;
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQS) {
        return new QueueMessagingTemplate(amazonSQS);
    }

    @Bean
    public SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory
                .builder()
                .sqsAsyncClient(sqsAsyncClient)
                .build();
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient(AwsProperties awsProperties) {
        SqsAsyncClientBuilder builder = SqsAsyncClient.builder();
        if (awsProperties.endpointOverride() != null) {
            builder.endpointOverride(URI.create(awsProperties.endpointOverride()));
        }
        return builder
                .build();
    }

    @Bean
    public JmsProperties.Listener listener() {
        return new JmsProperties.Listener();
    }
}
