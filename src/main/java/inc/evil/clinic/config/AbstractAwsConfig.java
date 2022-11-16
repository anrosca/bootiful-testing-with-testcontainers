package inc.evil.clinic.config;

import com.amazonaws.client.builder.AwsClientBuilder;

public class AbstractAwsConfig {
    public static <BuilderT extends AwsClientBuilder<BuilderT, ClientT>, ClientT> AwsClientBuilder<BuilderT, ClientT> awsClientBuilderFrom(
            AwsProperties awsProperties, AwsClientBuilder<BuilderT, ClientT> builder) {
        if (awsProperties.endpointOverride() != null) {
            AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(awsProperties.endpointOverride(), null);
            builder.withEndpointConfiguration(endpointConfiguration);
        } else {
            builder.withRegion(awsProperties.region());
        }
        return builder;
    }
}

