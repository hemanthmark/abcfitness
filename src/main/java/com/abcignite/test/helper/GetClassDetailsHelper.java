package com.abcignite.test.helper;

import com.abcignite.test.dto.ClassDetailsDTO;
import com.abcignite.test.entity.Booking;
import com.abcignite.test.entity.Class;
import com.abcignite.test.repository.ClassesRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GetClassDetailsHelper {

    private final ClassesRepository classesRepository;

    public GetClassDetailsHelper(ClassesRepository classesRepository) {
        this.classesRepository = classesRepository;
    }


    public Map<UUID, ClassDetailsDTO> getClassDetails(Page<Booking> bookings){
        List<UUID> classIds = bookings.stream().map(Booking::getClassId).toList();
        List<Class> classes = classesRepository.findAllByClassIdIn(classIds);
        return classes.stream().collect(Collectors.toMap(
                        Class::getClassId,
                        c -> new ClassDetailsDTO(c.getClassName(), c.getStartTime())
                ));
    }


}
