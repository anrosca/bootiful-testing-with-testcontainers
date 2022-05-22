package inc.evil.clinic.patient.service;

import inc.evil.clinic.patient.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findAll();

    Patient findById(String id);

    void deleteById(String id);

    Patient create(Patient patientToCreate);

    Patient update(String id, Patient patientToUpdate);
}
