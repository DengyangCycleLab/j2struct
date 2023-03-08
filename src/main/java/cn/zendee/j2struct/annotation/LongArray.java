package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface LongArray {
   int length();
   int byteLen = MyLong.byteLen;
   int index();
}
