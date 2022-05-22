package inc.evil.clinic.doctor.web;

import inc.evil.clinic.doctor.model.Doctor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DoctorResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<String> authorities;
    private String specialty;
    private String telephoneNumber;

    private String accessToken;

    public static DoctorResponse from(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .email(doctor.getEmail())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .specialty(doctor.getSpecialty().name())
                .telephoneNumber(doctor.getTelephoneNumber())
                .build();
    }

    public static DoctorResponse simpleForm(Doctor doctor) {
        return DoctorResponse.builder()
                .id(doctor.getId())
                .email(doctor.getEmail())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .specialty(doctor.getSpecialty().name())
                .telephoneNumber(doctor.getTelephoneNumber())
                .build();
    }
}
