package inc.evil.clinic.appointment.model;

import inc.evil.clinic.common.jpa.AbstractEqualityCheckTest;
import inc.evil.clinic.doctor.model.Doctor;
import inc.evil.clinic.patient.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppointmentJpaTest extends AbstractEqualityCheckTest<Appointment> {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void equalsAndHashCodeShouldBeJpaCompliant() {
        Appointment appointment = Appointment.builder()
                .appointmentDate(LocalDate.of(2021, 12, 10))
                .startTime(LocalTime.of(14, 55))
                .endTime(LocalTime.of(15, 30))
                .details("New patient")
                .operation("Cleaning")
                .doctor(entityManager.find(Doctor.class, "f23e4567-e89b-12d3-a456-426614174000"))
                .patient(entityManager.find(Patient.class, "f44e4567-ef9c-12d3-a45b-52661417400a"))
                .build();

        assertEqualityConsistency(Appointment.class, appointment);
    }
}
