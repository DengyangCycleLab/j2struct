package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface StringArray {
    int length();
    int strLength() default 0;
    String charset() default "UTF-8";
    int byteLen = MyString.byteLen;
    int index();
}
