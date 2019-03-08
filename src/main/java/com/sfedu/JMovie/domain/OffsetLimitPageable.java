package com.sfedu.JMovie.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

public class OffsetLimitPageable implements Pageable {
    private int offset, limit;

    public OffsetLimitPageable(int offset, int limit){
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    public int getPageNumber() {
        return 1;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    @NonNull
    public Sort getSort() {
        return Sort.unsorted();
    }

    @Override
    @NonNull
    public Pageable next() {
        return this;
    }

    @Override
    @NonNull
    public Pageable previousOrFirst() {
        return this;
    }

    @Override
    @NonNull
    public Pageable first() {
        return this;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }
}
