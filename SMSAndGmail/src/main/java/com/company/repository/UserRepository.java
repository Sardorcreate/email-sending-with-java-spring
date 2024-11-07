package com.company.repository;

import com.company.entity.User;
import com.company.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByLogin(String login);

    @Modifying
    @Transactional
    @Query("update User set status =:status where id =:id")
    Integer updateStatus(@Param("status") Status status, @Param("id") Integer id);

}
