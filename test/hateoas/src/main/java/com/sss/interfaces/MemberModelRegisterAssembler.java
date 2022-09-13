package com.sss.interfaces;

import com.sss.domain.MemberQuery;
import lombok.SneakyThrows;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelRegisterAssembler extends RepresentationModelAssemblerSupport<MemberQuery.Main, EntityModel<MemberDto.RegisterResponse>> {

    public MemberModelRegisterAssembler() {
        super(MemberApiController.class, EntityModel.class); // TODO 일단 커밋하자,, 껄껄
    }

    @SneakyThrows
    @Override
    public EntityModel<MemberDto.RegisterResponse> toModel(MemberQuery.Main dto) {
        return instantiateModel(dto)
                .add(linkTo(methodOn(MemberApiController.class).registerMember(null)).withSelfRel());
    }
}
