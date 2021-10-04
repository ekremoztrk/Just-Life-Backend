package com.justlife.justlifebackend.service.impl;

import com.justlife.justlifebackend.exception.ResourceNotFoundException;
import com.justlife.justlifebackend.model.base.BaseEntity;
import com.justlife.justlifebackend.repository.base.BaseRepository;
import com.justlife.justlifebackend.service.IBaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BaseService<T extends BaseEntity, ID extends Serializable> implements IBaseService<T, ID> {

    private String resourceName;

    private BaseRepository<T, ID> repository;

    public BaseService(String resourceName, BaseRepository<T, ID> repository) {

        this.resourceName = resourceName;
        this.repository = repository;
    }

    public List<T> findAll() {

        return repository.findAll();
    }

    public List<T> findAll(Specification<T> specification) {

        return repository.findAll(specification);
    }

    public Page<T> findAll(int page, int size, String sortInput) {

        return findAll(getPageable(page, size, sortInput));
    }

    public Page<T> findAll(Pageable pageRequest) {

        return findAll(Specification.where(null), pageRequest);
    }

    public Page<T> findAll(Specification<T> spec, int page, int size, String sortInput) {

        return findAll(spec, getPageable(page, size, sortInput));
    }

    public Page<T> findAll(Specification<T> spec, Pageable pageable) {

        return repository.findAll(spec, pageable);
    }

    private Sort parseSortInput(String sortInput) {

        Sort sort = Sort.unsorted();
        if (sortInput != null) {
            List<String> properties = new LinkedList<>(Arrays.asList(sortInput.split(",")));
            if (properties.contains("desc")) {
                properties.remove("desc");
                String[] arrProp = new String[properties.size()];
                sort = Sort.by(Sort.Direction.DESC, properties.toArray(arrProp));
            } else {
                properties.remove("asc");
                String[] arrProp = new String[properties.size()];
                sort = Sort.by(Sort.Direction.ASC, properties.toArray(arrProp));
            }
        }
        return sort;
    }

    public T findById(ID id) {

        Optional<T> t = repository.findById(id);
        return t.orElseThrow(() -> new ResourceNotFoundException(resourceName, id));
    }

    public Pageable getPageable(int page, int size, String sortInput) {

        Sort sort = parseSortInput(sortInput);
        return PageRequest.of(page, size, sort);
    }

    public String getResourceName() {

        return resourceName;
    }

    public void setResourceName(String resourceName) {

        this.resourceName = resourceName;
    }

    public <S extends T> S save(S s) {

        return repository.save(s);
    }

    public <S extends T> List<S> saveAll(Iterable<S> iterable) {

        return repository.saveAll(iterable);
    }

    public boolean deleteById(ID id) {

        repository.deleteById(id);
        return true;
    }

    public boolean delete(T t) {

        repository.delete(t);
        return true;
    }

    protected LocalDateTime getCurrentTime() {

        //ZonedDateTime utcZoned = ZonedDateTime.of(LocalDateTime.now(), ZoneOffset.UTC);
        //ZoneId turkeyZone = ZoneId.of("Turkey");
        //ZonedDateTime turkeyZoned = utcZoned.withZoneSameInstant(turkeyZone);
        return LocalDateTime.now();
    }

}
