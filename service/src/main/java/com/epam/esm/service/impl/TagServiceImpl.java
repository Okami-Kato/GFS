package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.dto.request.CreateTagRequest;
import com.epam.esm.service.dto.response.TagResponse;
import com.epam.esm.service.exception.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {
    private ModelMapper mapper;
    private TagDao tagDao;

    @Autowired
    public TagServiceImpl(TagDao tagDao, ModelMapper mapper) {
        this.tagDao = tagDao;
        this.mapper = mapper;
    }

    @Override
    public List<TagResponse> getAll(int pageNumber, int pageSize) {
        try {
            return tagDao.getAll(pageNumber, pageSize).stream()
                    .map(tag -> mapper.map(tag, TagResponse.class))
                    .collect(Collectors.toList());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TagResponse> getAll(int pageNumber, int pageSize, int certificateId) {
        try {
            return tagDao.getAll(pageNumber, pageSize, certificateId).stream()
                    .map(tag -> mapper.map(tag, TagResponse.class))
                    .collect(Collectors.toList());
        } catch (InvalidDataAccessApiUsageException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<TagResponse> get(int id) {
        return tagDao.get(id).map(tag -> mapper.map(tag, TagResponse.class));
    }

    @Override
    public Optional<TagResponse> get(String name) {
        try {
            return tagDao.get(name).map(tag -> mapper.map(tag, TagResponse.class));
        } catch (InvalidDataAccessApiUsageException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<TagResponse> getTheMostUsedTagOfUserWithTheMaximumCost() {
        return tagDao.getTheMostUsedTagOfUserWithTheMaximumCost().map(tag -> mapper.map(tag, TagResponse.class));
    }

    @Override
    public long getCount() {
        return tagDao.getCount();
    }

    @Override
    public TagResponse create(CreateTagRequest tag) {
        Tag tagToCreate = mapper.map(tag, Tag.class);
        try {
            tagDao.create(tagToCreate);
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException(e);
        }
        return mapper.map(tagToCreate, TagResponse.class);
    }

    @Override
    public void delete(int id) {
        try {
            tagDao.delete(id);
        } catch (InvalidDataAccessApiUsageException e) {
            throw new ServiceException(e);
        }
    }
}
