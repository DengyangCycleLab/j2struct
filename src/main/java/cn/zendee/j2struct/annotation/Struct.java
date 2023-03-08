package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Struct {

    int index();
}
