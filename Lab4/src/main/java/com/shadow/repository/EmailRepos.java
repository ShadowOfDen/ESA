package com.shadow.repository;

import org.springframework.data.repository.CrudRepository;
import com.shadow.entity.Email;

public interface EmailRepos extends CrudRepository<Email, Long> {
}
