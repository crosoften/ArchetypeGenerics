package br.com.crosoften.sample.domain.service;

import br.com.crosoften.sample.domain.repository.GenericRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public abstract class GenericService<T>{

    @Autowired
    private GenericRepository<T> genericRepository;

    public List<T> findAll() {
        return genericRepository.findAll();
    }

    @Transactional(rollbackFor = Throwable.class)
    public void insert(T entity) {
        this.genericRepository.insert(entity);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void insert(List<T> entities) {
        this.genericRepository.insert(entities);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void delete(Long id) {
        this.genericRepository.delete(id);
    }
    @Transactional(rollbackFor = Throwable.class)
    public void delete(T t) {
        this.genericRepository.delete(t);
    }
    @Transactional(rollbackFor = Throwable.class)
    public T findById(Long id) {
        return this.genericRepository.findById(id);
    }
    @Transactional(rollbackFor = Throwable.class)
    public T update(T t) {
        return this.genericRepository.update(t);
    }

}
