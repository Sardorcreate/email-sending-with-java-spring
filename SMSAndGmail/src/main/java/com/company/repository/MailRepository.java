package com.company.repository;

import com.company.entity.Mail;
import com.company.entity.User;
import com.company.enums.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MailRepository extends CrudRepository<Mail, Integer> {

    Optional<Mail> findByUserAndStatus(User user, Status status);

}
