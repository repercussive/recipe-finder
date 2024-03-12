package com.repercussive.recipefinder.helpers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

@Component
public class JsonToEntityConverter {
    private final ObjectMapper jsonMapper;

    public JsonToEntityConverter(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public <TDto, TEntity> TEntity jsonToEntitySingle(
            String json,
            Function<TDto, TEntity> dtoToEntityMapper,
            TypeReference<TDto> dtoTypeRef
    ) throws IOException {
        TDto dto = jsonMapper.readValue(json, dtoTypeRef);
        return dtoToEntityMapper.apply(dto);

    }

    public <TDto, TEntity> List<TEntity> jsonToEntityList(
            InputStream jsonStream,
            Function<TDto, TEntity> dtoToEntityMapper,
            TypeReference<List<TDto>> dtoListTypeRef
    ) throws IOException {
        try (jsonStream) {
            List<TDto> dtos = jsonMapper.readValue(jsonStream, dtoListTypeRef);
            return dtos.stream().map(dtoToEntityMapper).toList();
        }
    }

    public <TDto, TEntity> List<TEntity> jsonToEntityList(
            String json,
            Function<TDto, TEntity> dtoToEntityMapper,
            TypeReference<List<TDto>> dtoListTypeRef
    ) throws IOException {
        InputStream stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        return jsonToEntityList(stream, dtoToEntityMapper, dtoListTypeRef);
    }
}
