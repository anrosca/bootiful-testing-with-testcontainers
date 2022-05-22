package inc.evil.clinic.patient.web;

import inc.evil.clinic.common.validation.OnCreate;
import inc.evil.clinic.common.validation.OnUpdate;
import inc.evil.clinic.patient.facade.PatientFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {
    private final PatientFacade patientFacade;

    public PatientController(PatientFacade patientFacade) {
        this.patientFacade = patientFacade;
    }

    @GetMapping
    public List<PatientResponse> findAll() {
        return patientFacade.findAll();
    }

    @GetMapping("{id}")
    public PatientResponse findById(@PathVariable String id) {
        return patientFacade.findById(id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        patientFacade.deleteById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<PatientResponse> update(@PathVariable String id,
                                                 @RequestBody @Validated(OnUpdate.class) UpsertPatientRequest request) {
        final PatientResponse patientResponse = patientFacade.update(id, request);
        return ResponseEntity.ok(patientResponse);
    }

    @PostMapping
    public ResponseEntity<?> create(@Validated(OnCreate.class) @RequestBody UpsertPatientRequest request) {
        PatientResponse createdPatient = patientFacade.create(request);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdPatient.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location)
                .build();
    }
}
