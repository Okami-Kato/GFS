package com.epam.esm.web.linker;

import com.epam.esm.service.dto.response.TagResponse;
import com.epam.esm.web.controller.TagController;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinker implements RepresentationModelLinker<TagResponse> {
    @Override
    public void processEntity(TagResponse entity) {
        entity.add(linkTo(methodOn(TagController.class).findTag(entity.getId())).withSelfRel());
        entity.add(linkTo(methodOn(TagController.class).deleteTag(entity.getId())).withRel("delete"));
        entity.add(linkTo(methodOn(TagController.class).findCertificates(null, null, entity.getId())).withRel("certificatesAssignedToTag"));
        entity.add(linkTo(methodOn(TagController.class).findAllTags(null, null)).withRel("allTags"));
    }
}
