package inc.evil.clinic.appointment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inc.evil.clinic.appointment.model.AppointmentCreatedEvent;
import io.awspring.cloud.s3.S3Template;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppointmentListener {
    private final S3Template s3Template;
    private final ObjectMapper objectMapper;
    private final String appointmentEventBucket;

    public AppointmentListener(S3Template s3Template, ObjectMapper objectMapper, @Value("${appointments.bucket-name}") String appointmentEventBucket) {
        this.s3Template = s3Template;
        this.objectMapper = objectMapper;
        this.appointmentEventBucket = appointmentEventBucket;
    }

    @SqsListener(value = "${appointments.queue-name}")
    public void processMessage(@Payload AppointmentCreatedEvent event) throws JsonProcessingException {
        s3Template.store(appointmentEventBucket, event.getId(), objectMapper.writeValueAsString(event));
        log.info("Successfully uploaded appointment to S3");
    }
}
