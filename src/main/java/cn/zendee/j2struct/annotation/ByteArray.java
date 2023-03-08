package cn.zendee.j2struct.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ByteArray {
   int length();
   int byteLen = MyByte.byteLen;
   int index();
}
