package com.sfedu.JMovie.domain.util;

import com.sfedu.JMovie.api.data.CountryData;
import com.sfedu.JMovie.db.entity.Country;
import com.sfedu.JMovie.domain.model.CountryDomain;

import java.util.List;
import java.util.stream.Collectors;

public final class CountryConverter {
    private CountryConverter(){}
    public static CountryDomain convertToCountryDomain(Country country){
        return new CountryDomain(country.getId(), country.getName());
    }
    public static List<CountryDomain> convertToCountryDomainList(List<Country> countries){
        return countries.stream()
                .map(CountryConverter::convertToCountryDomain)
                .collect(Collectors.toList());
    }
    public static CountryData convertToCountryDTO(CountryDomain country){
        return new CountryData(country.getId(), country.getName());
    }
    public static List<CountryData> convertToCountryListDTO(List<CountryDomain> countries){
        return countries.stream()
                .map(CountryConverter::convertToCountryDTO)
                .collect(Collectors.toList());
    }
}
