 .section .rodata
.str.1:
  .string " "

.str.2:
  .string ""

 .text
 .globl bubble_sort
bubble_sort:
bubble_sort_entry_5.0:
		li t0, 32
		sub sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 20(sp)
		li t0, 8
		add s3, sp, t0
		li t0, 12
		add a5, sp, t0
		li t0, 16
		add s8, sp, t0
		lw t1, 20(sp)
		sw a0, 0(t1)
		lw t1, 20(sp)
		lw s1, 0(t1)
		mv a0, s1
		call _array_size
		mv a5, a0
		sw a5, 0(s8)
		lw s1, 0(s8)
		mv a0, s1
		call printlnInt
		li t0, 0
		sw t0, 0(s3)
		j for_cond_11.1
for_cond_11.1:
		lw t3, 0(s3)
		lw a5, 0(s8)
		slt t6, t3, a5
		bnez t6, for_body_12.2
		j for_end_14.4
for_body_12.2:
		lw s8, 0(s3)
		mv a0, s8
		call printInt
		la t1, .str.1
		mv a0, t1
		call print
		lw t1, 20(sp)
		lw t3, 0(t1)
		lw t6, 0(s3)
		li t0, 4
		mul t0, t6, t0
		sw t0, 28(sp)
		lw t1, 28(sp)
		add a6, t1, t3
		lw s8, 0(a6)
		mv a0, s8
		call printInt
		j for_step_13.3
for_step_13.3:
		lw t3, 0(s3)
		li t0, 1
		add a6, t3, t0
		sw a6, 0(s3)
		j for_cond_11.1
for_end_14.4:
		la t1, .str.2
		mv a0, t1
		call println
		lw s1, 0(s8)
		mv a0, s1
		call printlnInt
		j bubble_sort_exit_6.5
bubble_sort_exit_6.5:
		lw ra, 0(sp)
		li t0, 32
		add sp, sp, t0
		ret

 .text
 .globl main
main:
main_entry_7.6:
		li t0, 28
		sub sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add s5, sp, t0
		li t0, 8
		add s6, sp, t0
		li t0, 12
		add s10, sp, t0
		li t0, 16
		add s1, sp, t0
		li t0, 0
		sw t0, 0(s5)
		call _global_var_init
		call getInt
		mv a1, a0
		sw a1, 0(s6)
		lw s2, 0(s6)
		li t0, 4
		mul a1, s2, t0
		li t0, 4
		add s9, a1, t0
		mv a0, s9
		mv a1, s2
		call _new_array
		mv a1, a0
		sw a1, 0(s10)
		li t0, 0
		sw t0, 0(s1)
		j for_cond_15.7
for_cond_15.7:
		lw a1, 0(s1)
		lw t4, 0(s6)
		slt s2, a1, t4
		bnez s2, for_body_16.8
		j for_end_18.10
for_body_16.8:
		lw a1, 0(s10)
		lw t5, 0(s1)
		li t0, 4
		mul t0, t5, t0
		sw t0, 24(sp)
		lw t1, 24(sp)
		add s8, t1, a1
		call getInt
		mv a1, a0
		sw a1, 0(s8)
		j for_step_17.9
for_step_17.9:
		lw a1, 0(s1)
		li t0, 1
		add a4, a1, t0
		sw a4, 0(s1)
		j for_cond_15.7
for_end_18.10:
		lw s4, 0(s10)
		mv a0, s4
		call bubble_sort
		li t0, 0
		sw t0, 0(s5)
		j main_exit_8.11
main_exit_8.11:
		lw s4, 0(s5)
		lw ra, 0(sp)
		mv a0, s4
		li t0, 28
		add sp, sp, t0
		ret

 .text
 .globl _global_var_init
_global_var_init:
_global_var_init_entry_9.12:
		li t0, 4
		sub sp, sp, t0
		sw ra, 0(sp)
		j _global_var_init_exit_10.13
_global_var_init_exit_10.13:
		lw ra, 0(sp)
		li t0, 4
		add sp, sp, t0
		ret

