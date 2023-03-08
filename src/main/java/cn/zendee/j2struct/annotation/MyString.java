package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface MyString {
    int strLength() default 0;
    String charset() default "UTF-8";
    int byteLen = 1;
    int index();

}
