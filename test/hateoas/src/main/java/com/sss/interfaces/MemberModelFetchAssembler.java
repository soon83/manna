package com.sss.interfaces;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelFetchAssembler implements RepresentationModelAssembler<MemberDto.MainResponse, EntityModel<MemberDto.MainResponse>> {

    @Override
    public EntityModel<MemberDto.MainResponse> toModel(MemberDto.MainResponse dto) {
        return EntityModel.of(dto)
                .add(linkTo(methodOn(MemberApiController.class).fetchMember(dto.getMemberId())).withSelfRel())
                .add(linkTo(methodOn(MemberApiController.class).fetchMemberList(null)).withRel("memberList"));
    }

    @Override
    public CollectionModel<EntityModel<MemberDto.MainResponse>> toCollectionModel(Iterable<? extends MemberDto.MainResponse> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(MemberApiController.class).fetchMemberList(null)).withSelfRel());
    }
}
