package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface BoolArray {
   int length();
   int byteLen = Bool.byteLen;
   int index();
}
