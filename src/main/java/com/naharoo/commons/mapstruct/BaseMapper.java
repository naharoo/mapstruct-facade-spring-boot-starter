package com.naharoo.commons.mapstruct;

public interface BaseMapper<S, D> {

    D map(S source);

    S mapReverse(D destination);
}
