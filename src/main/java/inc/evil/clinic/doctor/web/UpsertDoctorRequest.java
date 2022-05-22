package inc.evil.clinic.doctor.web;

import inc.evil.clinic.common.validation.OnCreate;
import inc.evil.clinic.doctor.model.Doctor;
import inc.evil.clinic.doctor.model.Specialty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class UpsertDoctorRequest {
    @NotBlank(groups = {OnCreate.class})
    private String firstName;
    @NotBlank(groups = {OnCreate.class})
    private String lastName;
    @NotBlank(groups = {OnCreate.class})
    @Email(groups = {OnCreate.class})
    private String email;
    @NotBlank(groups = {OnCreate.class})
    private String password;
    @NotNull(groups = {OnCreate.class})
    private Specialty specialty;
    @NotBlank(groups = {OnCreate.class})
    private String telephoneNumber;

    public Doctor toDoctor() {

        Doctor doctor = Doctor.builder()
                .email(this.getEmail())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .telephoneNumber(this.getTelephoneNumber())
                .specialty(this.getSpecialty())
                .build();

        return doctor;
    }
}
