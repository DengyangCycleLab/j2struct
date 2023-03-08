# j2struct
use java to parse the binary struct of "C/C++"
# example
if you have a gay colleague, he is the R&D position of "C/C++", because of some businesses, he wrote binary files using structure.

view the following c/c++ code

![image](https://user-images.githubusercontent.com/24578032/223467236-685dfeae-ef22-4840-addf-ad9a166c4f17.png)
![image](https://user-images.githubusercontent.com/24578032/223469059-00ceb448-6891-4157-b266-21cefeedcb01.png)

you need to read this binary file, but you only know "java" language.

you: "oh, my God, what should I do"

don't worry, j2struct helps you. XDDDDDDDDDD

view the following java code

![image](https://user-images.githubusercontent.com/24578032/223471611-310df82e-a44b-46a7-be8a-ddb7996acade.png)
![image](https://user-images.githubusercontent.com/24578032/223471721-79b025c1-949a-4184-a76e-9ed7dd0ee2ff.png)
![image](https://user-images.githubusercontent.com/24578032/223471967-c010d586-e7c5-467b-bacb-5cc3726a980b.png)
![image](https://user-images.githubusercontent.com/24578032/223472158-e8cf7320-0cfc-4d27-9e71-cc955d42633c.png)

is it very cool?

it may also be very backward! XDDDDDDD
# use
J2Struct.readStruct(); or J2Struct.readSturctAutoAlign(); not StructUtil

if your Class needs to be used as a structure object, use annotation @Struct(index=0).

your Class attribute needs to be used as a structure attribute. please use the corresponding annotation. the index configuration represents the order of attributes in the structure

j2struct can not only read struct binary, but also write struct binary!

j2struct supports all basic types

![image](https://user-images.githubusercontent.com/24578032/223472938-35c7f128-6fd4-4e44-929d-648e68f3ea95.png)
