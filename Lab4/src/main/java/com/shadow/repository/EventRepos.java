package com.shadow.repository;

import org.springframework.data.repository.CrudRepository;
import com.shadow.entity.Event;

public interface EventRepos extends CrudRepository<Event, Long> {
}
