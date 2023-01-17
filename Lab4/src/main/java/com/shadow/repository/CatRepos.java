package com.shadow.repository;

import com.shadow.entity.Cat;
import org.springframework.data.repository.CrudRepository;

public interface CatRepos extends CrudRepository <Cat, String> {

}
