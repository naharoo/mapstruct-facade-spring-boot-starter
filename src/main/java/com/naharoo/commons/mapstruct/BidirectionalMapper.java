package com.naharoo.commons.mapstruct;

@PublicApi
public interface BidirectionalMapper<S, D> extends Mapper {

    @PublicApi
    D map(S source);

    @PublicApi
    S mapReverse(D destination);
}
