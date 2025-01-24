package com.abcignite.test.repository;

import com.abcignite.test.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassesRepository extends JpaRepository<Class,UUID> {

    Optional<Class> findByClassId(UUID classId);

    List<Class> findAllByClassIdIn(List<UUID> classIds);
}
