package com.example.rumblr.repos;

import com.example.rumblr.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long>
{
    User findByUsername(String username);

    User findByActivationCode(String code);
}
