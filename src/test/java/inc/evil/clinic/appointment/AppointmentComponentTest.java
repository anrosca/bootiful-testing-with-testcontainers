package inc.evil.clinic.appointment;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.jayway.jsonpath.JsonPath;
import inc.evil.clinic.appointment.web.AppointmentResponse;
import inc.evil.clinic.common.AbstractWebIntegrationTest;
import inc.evil.clinic.common.component.ComponentTest;
import inc.evil.clinic.doctor.model.Specialty;
import inc.evil.clinic.doctor.web.DoctorResponse;
import inc.evil.clinic.patient.model.PatientResponse;
import io.awspring.cloud.s3.S3Template;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ComponentTest
public class AppointmentComponentTest extends AbstractWebIntegrationTest {
    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToCreateAppointmentsWithExistingPatient() {
        String payload = """
                {
                   "doctorId": "a23e4567-a89b-12d3-a456-42661417400a",
                   "patientId": "b23e4567-b89b-12d3-a456-42661417400",
                   "startDate": "2021-12-12T09:45",
                   "existingPatient": "true",
                   "endDate": "2021-12-12T10:45",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment",
                   "color": {
                        "primary": "#ff1f1f",
                        "secondary": "#D1E8FF"
                   }
                 }
                """;
        RequestEntity<String> request = makeRequestFor("/api/v1/appointments/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeaders().getLocation()).isNotNull();
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAllAppointments() {
        AppointmentResponse[] expectedAppointments = {
                AppointmentResponse.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .startDate("2021-12-12T17:00")
                        .endDate("2021-12-12T18:00")
                        .operation("Выдача каппы")
                        .details("New patient")
                        .doctor(DoctorResponse.builder()
                                .id("a23e4567-a89b-12d3-a456-42661417400a")
                                .firstName("Vasile")
                                .lastName("Usaci")
                                .email("vusaci@gmail.com")
                                .specialty(Specialty.ORTHODONTIST.name())
                                .telephoneNumber("37369666666")
                                .build())
                        .patient(PatientResponse.builder()
                                .id("b23e4567-b89b-12d3-a456-42661417400")
                                .firstName("Jim")
                                .lastName("Morrison")
                                .birthDate("1994-12-13")
                                .phoneNumber("+37369952147")
                                .build())
                        .build()
        };
        RequestEntity<Void> request = makeRequestFor("/api/v1/appointments/", HttpMethod.GET);

        ResponseEntity<AppointmentResponse[]> response = restTemplate.exchange(request, AppointmentResponse[].class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(expectedAppointments);
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointmentsById() {
        AppointmentResponse expectedAppointment = AppointmentResponse.builder()
                .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                .startDate("2021-12-12T17:00")
                .endDate("2021-12-12T18:00")
                .operation("Выдача каппы")
                .details("New patient")
                .doctor(DoctorResponse.builder()
                        .id("a23e4567-a89b-12d3-a456-42661417400a")
                        .firstName("Vasile")
                        .lastName("Usaci")
                        .email("vusaci@gmail.com")
                        .specialty(Specialty.ORTHODONTIST.name())
                        .telephoneNumber("37369666666")
                        .build())
                .patient(PatientResponse.builder()
                        .id("b23e4567-b89b-12d3-a456-42661417400")
                        .firstName("Jim")
                        .lastName("Morrison")
                        .birthDate("1994-12-13")
                        .phoneNumber("+37369952147")
                        .build())
                .build();
        RequestEntity<Void> request = makeRequestFor(
                "/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.GET);

        ResponseEntity<AppointmentResponse> response = restTemplate.exchange(request, AppointmentResponse.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getBody()).isEqualTo(expectedAppointment);
    }


//    @Test
//    @Sql("/test-data/appointment/appointment.sql")
//    public void whenCreatingAppointments_shouldPublishThemToS3(@Autowired S3Template s3Template, @Value("${appointments.bucket-name}") String appointmentEventBucket) {
//        String payload = """
//                {
//                   "doctorId": "a23e4567-a89b-12d3-a456-42661417400a",
//                   "patientId": "b23e4567-b89b-12d3-a456-42661417400",
//                   "startDate": "2021-12-12T09:45",
//                   "existingPatient": "true",
//                   "endDate": "2021-12-12T10:45",
//                   "operation": "Inspection",
//                   "details": "Patient will make an appointment",
//                   "color": {
//                        "primary": "#ff1f1f",
//                        "secondary": "#D1E8FF"
//                   }
//                 }
//                """;
//        RequestEntity<String> request = makeRequestFor("/api/v1/appointments/", HttpMethod.POST, payload);
//
//        ResponseEntity<AppointmentResponse> response = restTemplate.exchange(request, AppointmentResponse.class);
//
//        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.getHeaders().getLocation()).isNotNull();
//        given()
//                .ignoreException(AmazonS3Exception.class)
//                .await()
//                .atMost(5, SECONDS)
//                .untilAsserted(() -> assertTrue(s3Template.download(appointmentEventBucket, response.getBody().getId()).exists()));
//    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void whenNewAppointmentConflictsWithAnExistingOne_shouldReturnErrorResponse() {
        String payload = """
                {
                   "doctorId": "a23e4567-a89b-12d3-a456-42661417400a",
                   "patientId": "b23e4567-b89b-12d3-a456-42661417400",
                   "existingPatient": "true",
                   "startDate": "2021-12-12T17:00",
                   "endDate": "2021-12-12T17:30",
                   "operation": "Inspection",
                   "details": "Patient will make an appointment",
                   "color": {
                        "primary": "#ff1f1f",
                        "secondary": "#D1E8FF"
                   }
                 }
                """;
        RequestEntity<String> request = makeRequestFor("/api/v1/appointments/", HttpMethod.POST, payload);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        String jsonResponse = response.getBody();
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo(
                "Doctor Vasile Usaci has already an appointment, starting from 17:00 till 18:00");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/");
    }

    @Test
    public void whenAppointmentWithTheGivenIdIsNotFound_shouldReturnErrorResponse() {
        RequestEntity<Void> request = makeRequestFor("/api/v1/appointments/1", HttpMethod.GET);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String jsonResponse = response.getBody();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo(
                "Appointment with id equal to [1] could not be found!");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/1");
    }

    @Test
    public void whenDeletingAppointment_AndTheGivenIdIsNotFound_shouldReturnErrorResponse() {
        RequestEntity<Void> request = makeRequestFor("/api/v1/appointments/1", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        String jsonResponse = response.getBody();
        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(JsonPath.<String>read(jsonResponse, "$.messages[0]")).isEqualTo(
                "Appointment with id equal to [1] could not be found!");
        assertThat(JsonPath.<String>read(jsonResponse, "$.path")).isEqualTo("/api/v1/appointments/1");
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToDeleteAppointments() {
        RequestEntity<Void> request = makeRequestFor(
                "/api/v1/appointments/aa3e4567-e89b-12d3-b457-5267141750aa", HttpMethod.DELETE);

        ResponseEntity<String> response = restTemplate.exchange(request, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Sql("/test-data/appointment/appointment.sql")
    public void shouldBeAbleToFindAppointmentsByDoctorId() {
        AppointmentResponse[] expectedAppointments = {
                AppointmentResponse.builder()
                        .id("aa3e4567-e89b-12d3-b457-5267141750aa")
                        .startDate("2021-12-12T17:00")
                        .endDate("2021-12-12T18:00")
                        .operation("Выдача каппы")
                        .details("New patient")
                        .doctor(DoctorResponse.builder()
                                .id("a23e4567-a89b-12d3-a456-42661417400a")
                                .firstName("Vasile")
                                .lastName("Usaci")
                                .email("vusaci@gmail.com")
                                .authorities(null)
                                .specialty(Specialty.ORTHODONTIST.name())
                                .telephoneNumber("37369666666")
                                .build())
                        .patient(PatientResponse.builder()
                                .id("b23e4567-b89b-12d3-a456-42661417400")
                                .firstName("Jim")
                                .lastName("Morrison")
                                .birthDate("1994-12-13")
                                .phoneNumber("+37369952147")
                                .build())
                        .build()
        };
        RequestEntity<Void> request = makeRequestFor("/api/v1/appointments?doctorId=a23e4567-a89b-12d3-a456-42661417400a", HttpMethod.GET);

        ResponseEntity<AppointmentResponse[]> response = restTemplate.exchange(request, AppointmentResponse[].class);

        AssertionsForClassTypes.assertThat(response.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        AssertionsForClassTypes.assertThat(response.getBody()).isEqualTo(expectedAppointments);
    }
}
