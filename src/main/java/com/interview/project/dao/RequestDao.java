package com.interview.project.dao;

import com.interview.project.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestDao extends JpaRepository<Request, UUID> {

}
