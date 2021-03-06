package com.sfedu.JMovie.domain.util;

import com.sfedu.JMovie.api.data.ViewingData;
import com.sfedu.JMovie.db.entity.Viewing;
import com.sfedu.JMovie.domain.model.ViewingDomain;

import java.util.List;
import java.util.stream.Collectors;

public final class ViewingConverter {
    private ViewingConverter(){}
    public static ViewingDomain convertToViewingDomain(Viewing viewing){
        return new ViewingDomain(viewing.getId(), viewing.getDate(), viewing.getRatingUser());
    }
    public static List<ViewingDomain> convertToViewingDomainList(List<Viewing> viewings){
        return viewings.stream()
                .map(ViewingConverter::convertToViewingDomain)
                .collect(Collectors.toList());
    }
    public static ViewingData convertToViewingDTO(ViewingDomain viewing){
        return new ViewingData(viewing.getId(), viewing.getDate(), viewing.getRatingUser());
    }
    public static List<ViewingData> convertToViewingListDTO(List<ViewingDomain> viewings){
        return viewings.stream()
                .map(ViewingConverter::convertToViewingDTO)
                .collect(Collectors.toList());
    }
}
