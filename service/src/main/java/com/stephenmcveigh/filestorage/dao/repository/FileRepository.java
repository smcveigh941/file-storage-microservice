package com.stephenmcveigh.filestorage.dao.repository;

import com.stephenmcveigh.filestorage.dao.entity.File;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends CrudRepository<File, Long> {
  Optional<File> findByData(byte[] bytes);
}
