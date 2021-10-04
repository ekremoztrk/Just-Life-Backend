package com.justlife.justlifebackend.service;

import com.justlife.justlifebackend.exception.ResourceNotFoundException;
import com.justlife.justlifebackend.model.base.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface IBaseService<T extends BaseEntity, ID extends Serializable> {

    List<T> findAll();

    List<T> findAll(Specification<T> specification);

    Page<T> findAll(int page, int size, String sortInput);

    Page<T> findAll(Pageable pageRequest);

    Page<T> findAll(Specification<T> spec, int page, int size, String sortInput);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    T findById(ID id);

    Pageable getPageable(int page, int size, String sortInput);

    <S extends T> S save(S s);

    <S extends T> List<S> saveAll(Iterable<S> iterable);

    boolean deleteById(ID id);

    boolean delete(T t);
}
