 .section .rodata
.str.1.1:
  .string " "

.str.2.2:
  .string ""

 .text
 .globl bubble_sort
bubble_sort:
.L0.0:
		li t0, -124
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 20(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 24(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 28(sp)
		li t0, 16
		add t0, sp, t0
		sw t0, 32(sp)
		lw t1, 20(sp)
		sw a0, 0(t1)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 36(sp)
		lw t1, 36(sp)
		mv a0, t1
		call _array_size
		mv t0, a0
		sw t0, 40(sp)
		lw t1, 32(sp)
		lw t0, 40(sp)
		sw t0, 0(t1)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 44(sp)
		lw t1, 44(sp)
		mv a0, t1
		call printlnInt
		mv t0, a0
		sw t0, 48(sp)
		lw t1, 24(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L1.1
.L1.1:
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 52(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 56(sp)
		lw t1, 52(sp)
		lw t0, 56(sp)
		slt t0, t1, t0
		sb t0, 64(sp)
		lb t1, 64(sp)
		beqz t1, .L4.4
		j .L2.2
.L2.2:
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 68(sp)
		lw t1, 68(sp)
		mv a0, t1
		call printInt
		mv t0, a0
		sw t0, 72(sp)
		la t1, .str.1.1
		mv a0, t1
		call print
		mv t0, a0
		sw t0, 76(sp)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 80(sp)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 84(sp)
		lw t1, 84(sp)
		slli t0, t1, 2
		sw t0, 88(sp)
		lw t1, 80(sp)
		lw t0, 88(sp)
		add t0, t1, t0
		sw t0, 92(sp)
		lw t1, 92(sp)
		lw t0, 0(t1)
		sw t0, 96(sp)
		lw t1, 96(sp)
		mv a0, t1
		call printInt
		mv t0, a0
		sw t0, 100(sp)
		j .L3.3
.L3.3:
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 104(sp)
		lw t1, 104(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 108(sp)
		lw t1, 24(sp)
		lw t0, 108(sp)
		sw t0, 0(t1)
		j .L1.1
.L4.4:
		la t1, .str.2.2
		mv a0, t1
		call println
		mv t0, a0
		sw t0, 112(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 116(sp)
		lw t1, 116(sp)
		mv a0, t1
		call printlnInt
		mv t0, a0
		sw t0, 120(sp)
		j .L5.5
.L5.5:
		lw ra, 0(sp)
		li t0, 124
		add sp, sp, t0
		ret

 .text
 .globl main
main:
.L6.6:
		li t0, -112
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 20(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 24(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 28(sp)
		li t0, 16
		add t0, sp, t0
		sw t0, 32(sp)
		lw t1, 20(sp)
		li t0, 0
		sw t0, 0(t1)
		call _global_var_init
		call getInt
		mv t0, a0
		sw t0, 36(sp)
		lw t1, 24(sp)
		lw t0, 36(sp)
		sw t0, 0(t1)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 40(sp)
		lw t1, 40(sp)
		li t0, 4
		mul t0, t1, t0
		sw t0, 44(sp)
		lw t1, 44(sp)
		li t0, 4
		add t0, t1, t0
		sw t0, 48(sp)
		lw t1, 48(sp)
		mv a0, t1
		lw t1, 40(sp)
		mv a1, t1
		call _new_array
		mv t0, a0
		sw t0, 52(sp)
		lw t1, 28(sp)
		lw t0, 52(sp)
		sw t0, 0(t1)
		lw t1, 32(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L7.7
.L7.7:
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 56(sp)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 60(sp)
		lw t1, 56(sp)
		lw t0, 60(sp)
		slt t0, t1, t0
		sb t0, 68(sp)
		lb t1, 68(sp)
		beqz t1, .L10.10
		j .L8.8
.L8.8:
		lw t1, 28(sp)
		lw t0, 0(t1)
		sw t0, 72(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 76(sp)
		lw t1, 76(sp)
		slli t0, t1, 2
		sw t0, 80(sp)
		lw t1, 72(sp)
		lw t0, 80(sp)
		add t0, t1, t0
		sw t0, 84(sp)
		call getInt
		mv t0, a0
		sw t0, 88(sp)
		lw t1, 84(sp)
		lw t0, 88(sp)
		sw t0, 0(t1)
		j .L9.9
.L9.9:
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 92(sp)
		lw t1, 92(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 96(sp)
		lw t1, 32(sp)
		lw t0, 96(sp)
		sw t0, 0(t1)
		j .L7.7
.L10.10:
		lw t1, 28(sp)
		lw t0, 0(t1)
		sw t0, 100(sp)
		lw t1, 100(sp)
		mv a0, t1
		call bubble_sort
		mv t0, a0
		sw t0, 104(sp)
		lw t1, 20(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L11.11
.L11.11:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 108(sp)
		lw t1, 108(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 112
		add sp, sp, t0
		ret

 .text
 .globl _global_var_init
_global_var_init:
.L12.12:
		li t0, -4
		add sp, sp, t0
		sw ra, 0(sp)
		j .L13.13
.L13.13:
		lw ra, 0(sp)
		li t0, 4
		add sp, sp, t0
		ret

