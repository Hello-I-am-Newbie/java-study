package com.onlinejava.project.bookstore.core.cli;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface CliCommand {

    String ID() default "";

    String title() default "";

    String description() default "";

    int order() default Integer.MAX_VALUE;
}
