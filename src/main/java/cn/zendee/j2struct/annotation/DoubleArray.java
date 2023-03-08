package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleArray {
   int length();
   int byteLen = MyDouble.byteLen;
   int index();
}
