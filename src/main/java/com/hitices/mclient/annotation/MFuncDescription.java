package com.hitices.mclient.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MFuncDescription {
    String value() default "";
    int level() default 1;
}
