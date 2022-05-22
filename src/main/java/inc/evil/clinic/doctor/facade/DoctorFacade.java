package inc.evil.clinic.doctor.facade;

import inc.evil.clinic.doctor.web.DoctorResponse;
import inc.evil.clinic.doctor.web.UpsertDoctorRequest;

import java.util.List;
import java.util.Map;

public interface DoctorFacade {
    List<DoctorResponse> findAll();

    DoctorResponse findById(String id);

    DoctorResponse create(UpsertDoctorRequest request);

    void deleteById(String id);

}
