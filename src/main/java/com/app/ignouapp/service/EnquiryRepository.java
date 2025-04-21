package com.app.ignouapp.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.ignouapp.model.Enquiry;

public interface EnquiryRepository extends JpaRepository<Enquiry, Integer> {

}
