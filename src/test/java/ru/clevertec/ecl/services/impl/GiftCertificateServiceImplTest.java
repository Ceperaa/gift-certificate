package ru.clevertec.ecl.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.model.dto.GiftCertificateCreateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.GiftCertificateUpdateDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.repository.GiftCertificateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class GiftCertificateServiceImplTest {

    private final GiftCertificateServiceImpl service;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private TagServiceImpl tagService;

    private GiftCertificate giftCertificate;
    private GiftCertificateUpdateDto giftCertificateForCreateDto;
    private GiftCertificateDto giftCertificateDto;
    private GiftCertificateCreateDto giftCertificateCreateDto;
    @Mock
    private GiftCertificateMapper mapper;

    GiftCertificateServiceImplTest() {
        MockitoAnnotations.openMocks(this);
        this.service = new GiftCertificateServiceImpl(
                giftCertificateRepository,
                mapper,
                tagService);
    }

    @BeforeEach
    void setUp() {
        giftCertificate = ObjectSupplier.getGiftCertificate();
        giftCertificateForCreateDto = ObjectSupplier.getGiftCertificatePutDto();
        giftCertificateDto = ObjectSupplier.getGiftCertificateDto();
        giftCertificateCreateDto = ObjectSupplier.getGiftCertificateCreateDto();
    }

    @Test
    void givenGiftCertificateId_whenFindGiftCertificateById_thenGiftCertificateDto() {
        given(giftCertificateRepository.findById(1L)).willReturn(Optional.of(giftCertificate));
        given(mapper.toEntity(giftCertificateForCreateDto, List.of(new Tag()))).willReturn(giftCertificate);
        given(mapper.toDto(giftCertificate)).willReturn(giftCertificateDto);
        GiftCertificateDto byId = service.findGiftCertificateDtoById(1L);
        assertEquals(byId, giftCertificateDto);
    }

    @Test
    void givenGiftCertificateId_whenDelete() {
        given(giftCertificateRepository.findById(1L)).willReturn(Optional.of(giftCertificate));
        service.delete(1L);
        verify(giftCertificateRepository).delete(giftCertificate);
    }

    @Test
    void whenUpdateGiftCertificate_thenGiftCertificateDto() {
        List<Tag> of = new ArrayList<>();
        given(giftCertificateRepository.findById(1L)).willReturn(Optional.of(giftCertificate));
        given(tagService.saveTagIfNotExists(giftCertificateForCreateDto.getTagNames())).willReturn(of);
        given(giftCertificateRepository.save(ArgumentMatchers.any())).willReturn(giftCertificate);
        given(mapper.toDto(giftCertificate)).willReturn(giftCertificateDto);
        GiftCertificateDto update = service.update(giftCertificateForCreateDto, 1L);
        assertEquals(update, giftCertificateDto);
    }

    @Test
    void whenFindGiftCertificateAll_thenGiftCertificateDtoList() {
        PageRequest pageRequest = PageRequest.of(0, 20);
        List<GiftCertificate> giftCertificateList = List.of(this.giftCertificate);
        List<GiftCertificateDto> giftCertificateDtoList = List.of(this.giftCertificateDto);
        given(giftCertificateRepository.findAll(pageRequest)).willReturn(new PageImpl<>(giftCertificateList));
        given(mapper.toDtoList(giftCertificateList)).willReturn(giftCertificateDtoList);
        List<GiftCertificateDto> certificateDtoList = service.findAll(pageRequest);
        assertEquals(certificateDtoList, giftCertificateDtoList);
    }

    @Test
    void givenGiftCertificateForSaveDto_whenCreateGiftCertificate_thenGiftCertificateDto() {
        giftCertificateForCreateDto.setTagNames(List.of("tag"));
        given(mapper.toEntity(giftCertificateCreateDto, giftCertificate.getTag())).willReturn(giftCertificate);
        given(mapper.toDto(giftCertificate)).willReturn(giftCertificateDto);
        given(tagService.createTagIfNotExists(List.of("tag"))).willReturn(giftCertificate.getTag());
        given(giftCertificateRepository.save(giftCertificate)).willReturn(giftCertificate);
        GiftCertificateDto giftCertificateDtoResult = service.createGiftCertificateDto(giftCertificateCreateDto);
        assertEquals(giftCertificateDtoResult, giftCertificateDto);
    }

    @Test
    void whenFindByTagName_thenGiftCertificateDtoList1() {
        PageRequest pageRequest = PageRequest.of(0, 20);
        List<GiftCertificate> giftCertificateList = List.of(this.giftCertificate);
        List<GiftCertificateDto> giftCertificateDtoList = List.of(this.giftCertificateDto);
        given(giftCertificateRepository.findByName("tag", pageRequest))
                .willReturn(giftCertificateList);
        given(mapper.toDtoList(giftCertificateList)).willReturn(giftCertificateDtoList);
        List<GiftCertificateDto> certificateDtoList = service.findByTagName("tag", pageRequest);
        assertEquals(certificateDtoList, giftCertificateDtoList);
    }
}