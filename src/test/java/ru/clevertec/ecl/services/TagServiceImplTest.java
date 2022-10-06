package ru.clevertec.ecl.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.TagForPutDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.services.impl.TagServiceImpl;

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
    private TagMapper mapper;
    private Tag tag;
    private TagDto tagDto;
    private TagForPutDto tagForCreateDto;

    TagServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        this.tagService = new TagServiceImpl(tagRepository, mapper);
    }

    @BeforeEach
    void setUp() {
        tag = Tag.builder()
                .id(1L)
                .name("tag")
                .giftCertificate(List.of(new GiftCertificate())).build();
        tagDto = TagDto.builder()
                .id(1L)
                .name("tag").build();
        tagForCreateDto = TagForPutDto.builder()
                .name("tag").build();
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
        given(tagRepository.save(tag)).willReturn(tag);
        given(mapper.toDto(tag)).willReturn(tagDto);
        given(mapper.toPutEntity(1L,tagForCreateDto)).willReturn(tag);
        TagDto tagDtoResult = tagService.update(tagForCreateDto, 1L);
        assertEquals(tagDtoResult, tagDto);
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
        given(mapper.toEntity(tagForCreateDto)).willReturn(tag);
        TagDto tagDtoResult = tagService.saveTagDto(tagForCreateDto);
        assertEquals(tagDtoResult, tagDto);
    }
}