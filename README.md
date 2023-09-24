# Mx-Compiler

### IR 出锅记录

1. 函数的 entryBlock 输出两次
2. 字符串长度大 $1$

**检查 currentClass, currentFunction etc. 事后有没有置为 null！！**

待修：call 构造函数的时候 malloc 1；class 的类型 toString 写得有问题；没有在程序开头声明这些类

scope 是乱的，啥玩意都在 classScope 里面？

### 各种指令的构造函数参数列表

Alloca: parentBlock, type, reg

Store: parentBlock, value, addr（左边往右边存）



注意：写之前一定要先提纲挈领，了解大体思路，然后把具体机制想清楚再来动笔！

t20.mx：控制流，短路求值

t71.mx：三目运算符
