package com.abcignite.test.respositorytest;



import com.abcignite.test.entity.Class;
import com.abcignite.test.repository.ClassesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassesRepositoryTest {

    @Mock
    private ClassesRepository classesRepository;

    @InjectMocks
    private ClassesRepositoryTest classesRepositoryTest;

    private UUID classId;
    private List<UUID> classIds;

    @BeforeEach
    void setUp() {
        classId = UUID.randomUUID();
        classIds = Arrays.asList(UUID.randomUUID(), UUID.randomUUID());
    }

    @Test
    void testFindByClassId() {
        // Mock the method behavior for finding a class by classId
        Class class1 = new Class();
        class1.setClassId(classId);
        Optional<Class> mockClass = Optional.of(class1);

        when(classesRepository.findByClassId(classId)).thenReturn(mockClass);


        Optional<Class> classOptional = classesRepository.findByClassId(classId);


        assertTrue(classOptional.isPresent());
        assertEquals(classId, classOptional.get().getClassId());
    }

    @Test
    void testFindByClassIdNotFound() {
        // Mock the method behavior for class not found
        when(classesRepository.findByClassId(classId)).thenReturn(Optional.empty());


        Optional<Class> classOptional = classesRepository.findByClassId(classId);

        // Assert the results
        assertFalse(classOptional.isPresent());
    }

    @Test
    void testFindAllByClassIdIn() {
        // Mock the method behavior for finding classes by a list of classIds
        Class class1 = new Class();
        Class class2 = new Class();
        class1.setClassId(classIds.get(0)); 
        class2.setClassId(classIds.get(1));
        List<Class> mockClasses = Arrays.asList(class1, class2);

        when(classesRepository.findAllByClassIdIn(classIds)).thenReturn(mockClasses);

        // Call the method on the repository
        List<Class> classes = classesRepository.findAllByClassIdIn(classIds);

        // Assert the results
        assertNotNull(classes);
        assertEquals(2, classes.size());
        assertTrue(classes.contains(class1));
        assertTrue(classes.contains(class2));
    }

    @Test
    void testFindAllByClassIdInEmptyList() {
        // Mock the method behavior for an empty list of classIds
        when(classesRepository.findAllByClassIdIn(classIds)).thenReturn(Arrays.asList());

        // Call the method on the repository
        List<Class> classes = classesRepository.findAllByClassIdIn(classIds);

        // Assert the results
        assertNotNull(classes);
        assertTrue(classes.isEmpty());
    }

    @Test
    void testFindAllByClassIdInWithNullResults() {
        // Test for handling null values in return
        when(classesRepository.findAllByClassIdIn(classIds)).thenReturn(null);

        // Call the method on the repository
        List<Class> classes = classesRepository.findAllByClassIdIn(classIds);

        // Assert the results
        assertNull(classes);
    }

    @Test
    void testFindAllByClassIdInWithException() {
        // Mock an exception in the repository
        when(classesRepository.findAllByClassIdIn(classIds)).thenThrow(new RuntimeException("Database error"));

        // Call the method and assert exception
        assertThrows(RuntimeException.class, () -> classesRepository.findAllByClassIdIn(classIds));
    }
}

