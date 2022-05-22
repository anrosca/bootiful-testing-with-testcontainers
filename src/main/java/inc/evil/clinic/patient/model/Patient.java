package inc.evil.clinic.patient.model;

import inc.evil.clinic.common.entity.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String source;
    private LocalDate birthDate;

    protected Patient() {
    }

    private Patient(PatientBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.source = builder.source;
        this.birthDate = builder.birthDate;
        this.id = builder.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getSource() {
        return this.source;
    }

    public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Patient mergeWith(Patient otherPatient) {
        return Patient.builder()
                .id(id)
                .firstName(otherPatient.getFirstName() != null ? otherPatient.getFirstName() : firstName)
                .lastName(otherPatient.getLastName() != null ? otherPatient.getLastName() : lastName)
                .birthDate(otherPatient.getBirthDate() != null ? otherPatient.getBirthDate() : birthDate)
                .phoneNumber(otherPatient.getPhoneNumber() != null ? otherPatient.getPhoneNumber() : phoneNumber)
                .source(otherPatient.getSource() != null ? otherPatient.getSource() : source)
                .build();
    }

    public static PatientBuilder builder() {
        return new PatientBuilder();
    }

    public static class PatientBuilder {
        private String id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String source;
        private LocalDate birthDate;

        public PatientBuilder id(String id) {
            this.id = id;
            return this;
        }

        public PatientBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PatientBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PatientBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PatientBuilder source(String source) {
            this.source = source;
            return this;
        }

        public PatientBuilder birthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public Patient build() {
            return new Patient(this);
        }
    }
}
