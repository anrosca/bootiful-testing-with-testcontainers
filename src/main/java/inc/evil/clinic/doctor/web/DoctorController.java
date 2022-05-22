package inc.evil.clinic.doctor.web;

import inc.evil.clinic.common.validation.OnCreate;
import inc.evil.clinic.common.validation.OnUpdate;
import inc.evil.clinic.doctor.facade.DoctorFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorFacade doctorFacade;

    @GetMapping
    public List<DoctorResponse> findAll() {
        return doctorFacade.findAll();
    }

    @GetMapping("{id}")
    public DoctorResponse findById(@PathVariable String id) {
        return doctorFacade.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        doctorFacade.deleteById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Validated(OnCreate.class) UpsertDoctorRequest request) {
        DoctorResponse createdDoctor = doctorFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdDoctor.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).body(createdDoctor);
    }
}
