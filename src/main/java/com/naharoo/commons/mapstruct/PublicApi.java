package com.naharoo.commons.mapstruct;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Indicates that annotation target is designed to be publicly used
 */
@PrivateApi
@Documented
@Retention(RetentionPolicy.SOURCE)
public @interface PublicApi {
}
