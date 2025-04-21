package com.app.ignouapp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ignouapp.model.StudentInfo;

public interface StudentInfoRepository extends JpaRepository<StudentInfo, Long> {

}
