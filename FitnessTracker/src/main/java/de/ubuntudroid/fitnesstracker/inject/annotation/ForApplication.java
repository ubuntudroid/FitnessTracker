package de.ubuntudroid.fitnesstracker.inject.annotation;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ubuntudroid on 02/01/14.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForApplication {
}
