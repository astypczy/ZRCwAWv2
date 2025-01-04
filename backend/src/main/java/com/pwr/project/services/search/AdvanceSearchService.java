package com.pwr.project.services.search;

import com.pwr.project.entities.Notice;
import com.pwr.project.entities.search.SearchCriteriaRequest;
import com.pwr.project.entities.search.SearchResponse;
import com.pwr.project.repositories.NoticeRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvanceSearchService {
    private final NoticeRepository noticeRepository;

    private final ModelMapper modelMapper;

    public List<SearchResponse> searchNoticeByCriteria(SearchCriteriaRequest searchCriteriaRequest){
        List<Notice> notices = noticeRepository.findAll(
                SearchSpecification.createSpecification(searchCriteriaRequest.getSearchCriteria()));
        return notices.stream().map(i -> modelMapper.map(i, SearchResponse.class)).toList();
    }
}
