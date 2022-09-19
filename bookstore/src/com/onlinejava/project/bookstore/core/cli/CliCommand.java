package com.onlinejava.project.bookstore.core.cli;


import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
@Inherited
public @interface CliCommand {

    String ID() default "";

    String title() default "";

    String description() default "";

    int order() default Integer.MAX_VALUE;
}
