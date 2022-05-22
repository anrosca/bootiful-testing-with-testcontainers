package inc.evil.clinic.doctor.service;

import inc.evil.clinic.common.exception.NotFoundException;
import inc.evil.clinic.doctor.model.Doctor;
import inc.evil.clinic.doctor.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true)
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Doctor findById(String id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Doctor.class, "id", id));
    }

    @Transactional
    public void deleteById(String id) {
        Doctor doctorToDelete = findById(id);
        doctorRepository.delete(doctorToDelete);
        log.debug("Deleting doctor with id: '{}'", id);
    }

    @Transactional
    public Doctor create(Doctor doctorToCreate) {
        return doctorRepository.save(doctorToCreate);
    }
}
