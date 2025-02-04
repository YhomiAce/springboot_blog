package com.ace.blog.controllers;

import com.ace.blog.domain.dtos.CreateTagsRequest;
import com.ace.blog.domain.dtos.TagResponse;
import com.ace.blog.domain.entities.Tag;
import com.ace.blog.mappers.TagMapper;
import com.ace.blog.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
       List<Tag> tags = tagService.getTags();
       List<TagResponse> tagResponses = tags.stream().map(tagMapper::toTagResponse).toList();
       return ResponseEntity.ok(tagResponses);
    }

    @PostMapping
    public ResponseEntity<List<TagResponse>> createTags(@RequestBody CreateTagsRequest createTagsRequest) {
        List<Tag> savedTags = tagService.createTags(createTagsRequest.getNames());
       List<TagResponse> tagResponses = savedTags.stream().map(tagMapper::toTagResponse).toList();
       return new ResponseEntity<>(tagResponses, HttpStatus.CREATED);
    }

    @DeleteMapping("{tagId}")
    public ResponseEntity<Map<String, String>> deleteTag(@PathVariable UUID tagId) {
        tagService.deleteTag(tagId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tag Deleted successfully");
        return ResponseEntity.ok(response);
    }
}
