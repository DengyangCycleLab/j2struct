package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface WStringArray {
    int length();
    int strLength() default 0;
    String charset() default "UTF-16LE";
    int byteLen = WString.byteLen;
    int index();
}
