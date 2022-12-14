package inc.evil.clinic.appointment.repository;


import inc.evil.clinic.appointment.model.Appointment;
import inc.evil.clinic.doctor.model.Doctor;
import inc.evil.clinic.patient.model.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AppointmentRepositoryTest {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenAppointmentStartsOneMinuteTooEarly_andOverlapsWithAnotherOne_shouldReturnConflictingAppointment() {
        String doctorId = "a23e4567-a89b-12d3-a456-42661417400a";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(17, 59);
        LocalTime endTime = LocalTime.of(18, 30);
        Optional<Appointment> expectedConflictingAppointment = Optional.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "a23e4567-a89b-12d3-a456-42661417400a"))
                        .patient(entityManager.find(Patient.class, "b23e4567-b89b-12d3-a456-42661417400"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .details("New patient")
                        .build()
        );

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(expectedConflictingAppointment);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenAppointmentStartsAfterAnExistingOne_butTheyOverlap_shouldReturnConflictingAppointment() {
        String doctorId = "a23e4567-a89b-12d3-a456-42661417400a";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(17, 5);
        LocalTime endTime = LocalTime.of(17, 30);
        Optional<Appointment> expectedConflictingAppointment = Optional.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "a23e4567-a89b-12d3-a456-42661417400a"))
                        .patient(entityManager.find(Patient.class, "b23e4567-b89b-12d3-a456-42661417400"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .details("New patient")
                        .build()
        );

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(expectedConflictingAppointment);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenAppointmentEndTimeOverlapsWithAnotherOne_shouldReturnConflictingAppointment() {
        String doctorId = "a23e4567-a89b-12d3-a456-42661417400a";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(16, 30);
        LocalTime endTime = LocalTime.of(17, 30);
        Optional<Appointment> expectedConflictingAppointment = Optional.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "a23e4567-a89b-12d3-a456-42661417400a"))
                        .patient(entityManager.find(Patient.class, "b23e4567-b89b-12d3-a456-42661417400"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .details("New patient")
                        .build()
        );

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(expectedConflictingAppointment);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenSecondAppointmentIsAtTheSameTimeWithAnExistingOne_shouldReturnConflictingAppointment() {
        String doctorId = "a23e4567-a89b-12d3-a456-42661417400a";
        LocalDate appointmentDate = LocalDate.of(2021, 12, 12);
        LocalTime startTime = LocalTime.of(17, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        Optional<Appointment> expectedConflictingAppointment = Optional.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "a23e4567-a89b-12d3-a456-42661417400a"))
                        .patient(entityManager.find(Patient.class, "b23e4567-b89b-12d3-a456-42661417400"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .details("New patient")
                        .build()
        );

        Optional<Appointment> conflictingAppointment =
                appointmentRepository.findConflictingAppointment(doctorId, appointmentDate, startTime, endTime);

        assertThat(conflictingAppointment).usingRecursiveComparison().isEqualTo(expectedConflictingAppointment);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointments_forOneDayTimeRange() {
        String doctorId = "a23e4567-a89b-12d3-a456-42661417400a";
        LocalDate startDate = LocalDate.of(2021, 12, 12);
        LocalDate endDate = LocalDate.of(2021, 12, 12);
        List<Appointment> expectedAppointments = List.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "a23e4567-a89b-12d3-a456-42661417400a"))
                        .patient(entityManager.find(Patient.class, "b23e4567-b89b-12d3-a456-42661417400"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .details("New patient")
                        .build()
        );

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).usingRecursiveComparison().isEqualTo(expectedAppointments);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointments_forOneMonthTimeRange() {
        String doctorId = "a23e4567-a89b-12d3-a456-42661417400a";
        LocalDate startDate = LocalDate.of(2021, 11, 1);
        LocalDate endDate = LocalDate.of(2021, 12, 17);
        List<Appointment> expectedAppointments = List.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "a23e4567-a89b-12d3-a456-42661417400a"))
                        .patient(entityManager.find(Patient.class, "b23e4567-b89b-12d3-a456-42661417400"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .details("New patient")
                        .build()
        );

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).usingRecursiveComparison().isEqualTo(expectedAppointments);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointments_forOneWeekTimeRange() {
        String doctorId = "a23e4567-a89b-12d3-a456-42661417400a";
        LocalDate startDate = LocalDate.of(2021, 12, 10);
        LocalDate endDate = LocalDate.of(2021, 12, 17);
        List<Appointment> expectedAppointments = List.of(
                Appointment.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .doctor(entityManager.find(Doctor.class, "a23e4567-a89b-12d3-a456-42661417400a"))
                        .patient(entityManager.find(Patient.class, "b23e4567-b89b-12d3-a456-42661417400"))
                        .appointmentDate(LocalDate.of(2021, 12, 12))
                        .startTime(LocalTime.of(17, 0))
                        .endTime(LocalTime.of(18, 0))
                        .operation("Выдача каппы")
                        .details("New patient")
                        .build()
        );

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).usingRecursiveComparison().isEqualTo(expectedAppointments);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenThereAreNoAppointmentsInTimeRange_shouldReturnEmptyList() {
        String doctorId = "a23e4567-a89b-12d3-a456-42661417400a";
        LocalDate startDate = LocalDate.of(2021, 12, 10);
        LocalDate endDate = LocalDate.of(2021, 12, 11);

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).isEmpty();
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenDoctorDoesNotExist_shouldReturnEmptyList() {
        String doctorId = "1";
        LocalDate startDate = LocalDate.of(2021, 12, 10);
        LocalDate endDate = LocalDate.of(2021, 12, 11);

        List<Appointment> actualAppointments =
                appointmentRepository.findByDoctorIdAndTimeRange(doctorId, startDate, endDate);

        assertThat(actualAppointments).isEmpty();
    }
}
