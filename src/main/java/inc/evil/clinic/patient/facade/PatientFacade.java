package inc.evil.clinic.patient.facade;

import inc.evil.clinic.patient.web.PatientResponse;
import inc.evil.clinic.patient.web.UpsertPatientRequest;

import java.util.List;
import java.util.Map;

public interface PatientFacade {
    List<PatientResponse> findAll();

    PatientResponse findById(String id);

    void deleteById(String id);

    PatientResponse create(UpsertPatientRequest request);

    PatientResponse update(String id, UpsertPatientRequest request);
}
