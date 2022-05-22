package inc.evil.clinic.doctor.facade;

import inc.evil.clinic.doctor.service.DoctorService;
import inc.evil.clinic.doctor.web.DoctorResponse;
import inc.evil.clinic.doctor.web.UpsertDoctorRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DoctorFacadeImpl implements DoctorFacade {
    private final DoctorService doctorService;

    @Override
    public List<DoctorResponse> findAll() {
        return doctorService.findAll().stream().map(DoctorResponse::from).collect(Collectors.toList());
    }

    @Override
    public DoctorResponse findById(final String id) {
        return DoctorResponse.from(doctorService.findById(id));
    }

    @Override
    public DoctorResponse create(final UpsertDoctorRequest request) {
        return DoctorResponse.from(doctorService.create(request.toDoctor()));
    }

    @Override
    public void deleteById(final String id) {
        doctorService.deleteById(id);
    }

}
