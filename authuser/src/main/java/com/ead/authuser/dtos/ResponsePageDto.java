package com.ead.authuser.dtos;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class ResponsePageDto<T> extends PageImpl<T>{

	private static final long serialVersionUID = -4332319401516382425L;

	@JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
	public ResponsePageDto(@JsonProperty("content") List<T> content,
						   @JsonProperty("number") Integer number,
						   @JsonProperty("size") Integer size,
						   @JsonProperty("totalElements")Long totalElements,
						   @JsonProperty("pageable") JsonNode pageable,
						   @JsonProperty("last") boolean last,
						   @JsonProperty("totalPages") Integer totalPages,
						   @JsonProperty("sort") JsonNode sort,
						   @JsonProperty("first") boolean first,
						   @JsonProperty("empty") boolean empty) {
		super(content, PageRequest.of(number, size), totalElements);
	}

	public ResponsePageDto(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);

	}

	public ResponsePageDto(List<T> content) {
		super(content);

	}
}
