package ru.clevertec.ecl.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.mapper.TagMapperImpl;
import ru.clevertec.ecl.model.dto.TagCreateDto;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagUpdateDto;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.repository.TagRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class TagServiceImplTest {

    private final TagServiceImpl tagService;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapperImpl mapper;
    private Tag tag;
    private TagDto tagDto;
    private TagUpdateDto tagForCreateDto;
    private TagCreateDto tagCreateDto;

    TagServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        this.tagService = new TagServiceImpl(tagRepository, mapper);
    }

    @BeforeEach
    void setUp() {
        tag = ObjectSupplier.getTag();
        tagDto = ObjectSupplier.getTagDto();
        tagForCreateDto = ObjectSupplier.getTagPutDto();
        tagCreateDto = ObjectSupplier.getTagCreateDto();
    }

    @Test
    void givenTagId_whenFindTagDtoById_thenTagDto() {
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));
        given(mapper.toDto(tag)).willReturn(tagDto);
        TagDto tagDtoById = tagService.findTagDtoById(1L);
        assertEquals(tagDtoById, tagDto);
    }

    @Test
    void givenTagId_whenDelete() {
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));
        tagService.delete(1L);
        verify(tagRepository).delete(tag);
    }

    @Test
    void givenTagDto_whenUpdate() {
        given(tagRepository.findById(1L)).willReturn(Optional.of(tag));
        given(mapper.toDto(tag)).willReturn(tagDto);
        given(mapper.toPutEntity(1L, tag, tagForCreateDto)).willReturn(tag);
        given(tagRepository.save(tag)).willReturn(tag);
        TagDto update = tagService.update(tagForCreateDto, 1L);
        assertEquals(update, tagDto);
    }

    @Test
    void whenFindAll_thenTagDtoList() {
        PageRequest pageRequest = PageRequest.of(0, 20);
        List<Tag> tagList = List.of(this.tag);
        List<TagDto> tagDtoList = List.of(this.tagDto);
        given(tagRepository.findAll(pageRequest)).willReturn(new PageImpl<>(tagList));
        given(mapper.toDtoList(tagList)).willReturn(tagDtoList);
        List<TagDto> tagAll = tagService.findAll(pageRequest);
        assertEquals(tagAll, tagDtoList);
    }

    @Test
    void givenTagDto_whenSaveTagDto() {
        given(tagRepository.save(tag)).willReturn(tag);
        given(mapper.toDto(tag)).willReturn(tagDto);
        given(mapper.toEntity(tagCreateDto)).willReturn(tag);
        TagDto tagDtoResult = tagService.saveTagDto(tagCreateDto);
        assertEquals(tagDtoResult, tagDto);
    }
}