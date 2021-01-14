package com.hitices.mclient.annotation;


import com.hitices.mclient.config.MClientAutoComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({MClientAutoComponentScan.class})
public @interface MClient {
}
