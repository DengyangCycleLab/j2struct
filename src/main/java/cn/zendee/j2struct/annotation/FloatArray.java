package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface FloatArray {
   int length();
   int byteLen = MyFloat.byteLen;
   int index();
}
