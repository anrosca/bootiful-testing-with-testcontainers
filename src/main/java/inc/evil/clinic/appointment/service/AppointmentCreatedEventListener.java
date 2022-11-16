package inc.evil.clinic.appointment.service;

import inc.evil.clinic.appointment.model.AppointmentCreatedEvent;
import inc.evil.clinic.config.AppointmentProperties;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class AppointmentCreatedEventListener {
    private final AppointmentProperties appointmentProperties;
    private final QueueMessagingTemplate queueMessagingTemplate;

    public AppointmentCreatedEventListener(AppointmentProperties appointmentProperties, QueueMessagingTemplate queueMessagingTemplate) {
        this.appointmentProperties = appointmentProperties;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

//    @TransactionalEventListener(AppointmentCreatedEvent.class)
    public void on(AppointmentCreatedEvent event) {
        queueMessagingTemplate.convertAndSend(appointmentProperties.queueName(), event);
        log.info("Successfully sent appointment to sqs queue");
    }
}
