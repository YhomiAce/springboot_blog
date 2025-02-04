package com.ace.blog.services;

import com.ace.blog.domain.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface TagService {
    List<Tag> getTags();

    List<Tag> createTags(Set<String> tagNames);

    void deleteTag(UUID id);
}
