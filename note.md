# Assembly 笔记

word 后面跟着的数是值而不是大小😅

函数返回值一般存在 a0 里面，到时候需要 mv a0 (%1)

在 ir 里面检查 malloc 的空间是不是 memberNum << 2

TODO: 给 IR 打补丁，加上 param_index

问题：一个寄存器到底是它的地址重要，还是它存的值重要？

应该是值，因为地址就那 32 个，没什么意义

内存的地址倒是值得研究



如果只有 def 没有 use，那应该怎么分配？

还是老样子，看槽里还有什么就分配，没有就 ASMVirtualRegister 好了