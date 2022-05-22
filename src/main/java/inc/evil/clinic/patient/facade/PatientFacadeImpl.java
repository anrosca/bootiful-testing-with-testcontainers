package inc.evil.clinic.patient.facade;

import inc.evil.clinic.patient.model.Patient;
import inc.evil.clinic.patient.service.PatientService;
import inc.evil.clinic.patient.web.PatientResponse;
import inc.evil.clinic.patient.web.UpsertPatientRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PatientFacadeImpl implements PatientFacade {
    private final PatientService patientService;

    public PatientFacadeImpl(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public List<PatientResponse> findAll() {
        return patientService.findAll()
                .stream()
                .map(PatientResponse::from)
                .toList();
    }

    @Override
    public PatientResponse findById(String id) {
        return PatientResponse.from(patientService.findById(id));
    }

    @Override
    public void deleteById(String id) {
        patientService.deleteById(id);
    }

    @Override
    public PatientResponse create(UpsertPatientRequest request) {
        Patient createdPatient = patientService.create(request.toPatient());
        return PatientResponse.from(createdPatient);
    }

    @Override
    public PatientResponse update(String id, UpsertPatientRequest request) {
        return PatientResponse.from(patientService.update(id, request.toPatient()));
    }
}
