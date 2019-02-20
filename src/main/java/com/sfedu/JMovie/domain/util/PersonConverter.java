package com.sfedu.JMovie.domain.util;

import com.sfedu.JMovie.api.data.PersonData;
import com.sfedu.JMovie.db.entity.Person;
import com.sfedu.JMovie.domain.model.PersonDomain;

import java.util.List;
import java.util.stream.Collectors;

public final class PersonConverter {
    private PersonConverter(){}
    public static PersonDomain convertToPersonDomain(Person person){
        return new PersonDomain(person.getId(), person.getName());
    }
    public static List<PersonDomain> convertToPersonDomainList(List<Person> people){
        return people.stream()
                .map(PersonConverter::convertToPersonDomain)
                .collect(Collectors.toList());
    }
    public static PersonData convertToPersonDTO(PersonDomain person){
        return new PersonData(person.getId(), person.getName());
    }
    public static List<PersonData> convertToPersonListDTO(List<PersonDomain> people){
        return people.stream()
                .map(PersonConverter::convertToPersonDTO)
                .collect(Collectors.toList());
    }
}
