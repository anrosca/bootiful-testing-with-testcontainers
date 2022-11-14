package inc.evil.clinic.appointment.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppointmentCreatedEvent {
    private String id;
    private String appointmentDate;
    private String startTime;
    private String endTime;
    private String operation;
    private String details;
    private String doctorId;
    private String patientId;

    public static AppointmentCreatedEvent from(Appointment appointment) {
        return AppointmentCreatedEvent.builder()
                .id(appointment.getId())
                .appointmentDate(appointment.getAppointmentDate().toString())
                .startTime(appointment.getStartTime().toString())
                .endTime(appointment.getEndTime().toString())
                .operation(appointment.getOperation())
                .details(appointment.getDetails())
                .doctorId(appointment.getDoctor().getId())
                .patientId(appointment.getPatient().getId())
                .build();
    }
}
