package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = {"id"}, allowGetters = true)
public class Dto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String id;
}