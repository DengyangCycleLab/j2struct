package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ShortArray {
   int length();
   int byteLen = MyShort.byteLen;
   int index();
}
