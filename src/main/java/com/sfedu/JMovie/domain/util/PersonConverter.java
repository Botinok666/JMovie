package com.sfedu.JMovie.domain.util;

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
}
