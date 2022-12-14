package inc.evil.clinic.patient.model;

import inc.evil.clinic.common.validation.OnCreate;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UpsertPatientRequest {
    @NotBlank(groups = {OnCreate.class})
    private String firstName;

    @NotBlank(groups = {OnCreate.class})
    private String lastName;

    @NotBlank(groups = {OnCreate.class})
    private String phoneNumber;

    @NotBlank(groups = {OnCreate.class})
    private String source;

    @NotNull(groups = {OnCreate.class})
    private LocalDate birthDate;

    public Patient toPatient() {
        return Patient.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .source(source)
                .birthDate(birthDate)
                .build();
    }
}
