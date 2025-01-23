package com.abcignite.test.repository;

import com.abcignite.test.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClassesRepository extends JpaRepository<Classes,UUID> {

}
