 .section .data
asciiTable:
  .word .str.1

MAXCHUNK:
  .word 100

MAXLENGTH:
  .word 0

chunks:
  .word 0

inputBuffer:
  .word 0

outputBuffer:
  .word 0

 .section .rodata
.str.1:
  .string " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~"

.str.2:
  .string ""

.str.3:
  .string "nChunk > MAXCHUNK!"

.str.4:
  .string "Invalid input"

.str.5:
  .string "Not Found!"

 .text
 .globl hex2int
hex2int:
.L0.0:
		li t0, -268
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 36(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 40(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 44(sp)
		li t0, 16
		add t0, sp, t0
		sw t0, 48(sp)
		li t0, 20
		add t0, sp, t0
		sw t0, 52(sp)
		li t0, 24
		add t0, sp, t0
		sw t0, 56(sp)
		li t0, 28
		add t0, sp, t0
		sw t0, 60(sp)
		li t0, 32
		add t0, sp, t0
		sw t0, 64(sp)
		lw t1, 36(sp)
		sw a0, 0(t1)
		lw t1, 48(sp)
		li t0, 0
		sw t0, 0(t1)
		lw t1, 44(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L1.1
.L1.1:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 68(sp)
		lw t1, 68(sp)
		mv a0, t1
		call _string_length
		mv t0, a0
		sw t0, 72(sp)
		lw t1, 44(sp)
		lw t0, 0(t1)
		sw t0, 76(sp)
		lw t1, 76(sp)
		lw t0, 72(sp)
		slt t0, t1, t0
		sb t0, 84(sp)
		lb t1, 84(sp)
		beqz t1, .L25.25
		j .L2.2
.L2.2:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 88(sp)
		lw t1, 44(sp)
		lw t0, 0(t1)
		sw t0, 92(sp)
		lw t1, 88(sp)
		mv a0, t1
		lw t1, 92(sp)
		mv a1, t1
		call _string_ord
		mv t0, a0
		sw t0, 96(sp)
		lw t1, 52(sp)
		lw t0, 96(sp)
		sw t0, 0(t1)
		lw t1, 52(sp)
		lw t0, 0(t1)
		sw t0, 100(sp)
		lw t1, 100(sp)
		li t0, 48
		slt t0, t1, t0
		sw t0, 104(sp)
		lw t1, 104(sp)
		xori t0, t1, 1
		sb t0, 108(sp)
		lb t1, 108(sp)
		beqz t1, .L5.5
		j .L3.3
.L3.3:
		lw t1, 52(sp)
		lw t0, 0(t1)
		sw t0, 112(sp)
		li t1, 57
		lw t0, 112(sp)
		slt t0, t1, t0
		sw t0, 116(sp)
		lw t1, 116(sp)
		xori t0, t1, 1
		sb t0, 120(sp)
		lb t1, 120(sp)
		beqz t1, .L5.5
		j .L4.4
.L4.4:
		lw t1, 56(sp)
		li t0, 1
		sb t0, 0(t1)
		j .L6.6
.L5.5:
		lw t1, 56(sp)
		li t0, 0
		sb t0, 0(t1)
		j .L6.6
.L6.6:
		lw t1, 56(sp)
		lb t0, 0(t1)
		sb t0, 124(sp)
		lb t1, 124(sp)
		beqz t1, .L8.8
		j .L7.7
.L7.7:
		lw t1, 48(sp)
		lw t0, 0(t1)
		sw t0, 128(sp)
		lw t1, 128(sp)
		li t0, 16
		mul t0, t1, t0
		sw t0, 132(sp)
		lw t1, 52(sp)
		lw t0, 0(t1)
		sw t0, 136(sp)
		lw t1, 132(sp)
		lw t0, 136(sp)
		add t0, t1, t0
		sw t0, 140(sp)
		lw t1, 140(sp)
		li t0, 48
		sub t0, t1, t0
		sw t0, 144(sp)
		lw t1, 48(sp)
		lw t0, 144(sp)
		sw t0, 0(t1)
		j .L23.23
.L8.8:
		lw t1, 52(sp)
		lw t0, 0(t1)
		sw t0, 148(sp)
		lw t1, 148(sp)
		li t0, 65
		slt t0, t1, t0
		sw t0, 152(sp)
		lw t1, 152(sp)
		xori t0, t1, 1
		sb t0, 156(sp)
		lb t1, 156(sp)
		beqz t1, .L11.11
		j .L9.9
.L9.9:
		lw t1, 52(sp)
		lw t0, 0(t1)
		sw t0, 160(sp)
		li t1, 70
		lw t0, 160(sp)
		slt t0, t1, t0
		sw t0, 164(sp)
		lw t1, 164(sp)
		xori t0, t1, 1
		sb t0, 168(sp)
		lb t1, 168(sp)
		beqz t1, .L11.11
		j .L10.10
.L10.10:
		lw t1, 60(sp)
		li t0, 1
		sb t0, 0(t1)
		j .L12.12
.L11.11:
		lw t1, 60(sp)
		li t0, 0
		sb t0, 0(t1)
		j .L12.12
.L12.12:
		lw t1, 60(sp)
		lb t0, 0(t1)
		sb t0, 172(sp)
		lb t1, 172(sp)
		beqz t1, .L14.14
		j .L13.13
.L13.13:
		lw t1, 48(sp)
		lw t0, 0(t1)
		sw t0, 176(sp)
		lw t1, 176(sp)
		li t0, 16
		mul t0, t1, t0
		sw t0, 180(sp)
		lw t1, 52(sp)
		lw t0, 0(t1)
		sw t0, 184(sp)
		lw t1, 180(sp)
		lw t0, 184(sp)
		add t0, t1, t0
		sw t0, 188(sp)
		lw t1, 188(sp)
		li t0, 65
		sub t0, t1, t0
		sw t0, 192(sp)
		lw t1, 192(sp)
		li t0, 10
		add t0, t1, t0
		sw t0, 196(sp)
		lw t1, 48(sp)
		lw t0, 196(sp)
		sw t0, 0(t1)
		j .L22.22
.L14.14:
		lw t1, 52(sp)
		lw t0, 0(t1)
		sw t0, 200(sp)
		lw t1, 200(sp)
		li t0, 97
		slt t0, t1, t0
		sw t0, 204(sp)
		lw t1, 204(sp)
		xori t0, t1, 1
		sb t0, 208(sp)
		lb t1, 208(sp)
		beqz t1, .L17.17
		j .L15.15
.L15.15:
		lw t1, 52(sp)
		lw t0, 0(t1)
		sw t0, 212(sp)
		li t1, 102
		lw t0, 212(sp)
		slt t0, t1, t0
		sw t0, 216(sp)
		lw t1, 216(sp)
		xori t0, t1, 1
		sb t0, 220(sp)
		lb t1, 220(sp)
		beqz t1, .L17.17
		j .L16.16
.L16.16:
		lw t1, 64(sp)
		li t0, 1
		sb t0, 0(t1)
		j .L18.18
.L17.17:
		lw t1, 64(sp)
		li t0, 0
		sb t0, 0(t1)
		j .L18.18
.L18.18:
		lw t1, 64(sp)
		lb t0, 0(t1)
		sb t0, 224(sp)
		lb t1, 224(sp)
		beqz t1, .L20.20
		j .L19.19
.L19.19:
		lw t1, 48(sp)
		lw t0, 0(t1)
		sw t0, 228(sp)
		lw t1, 228(sp)
		li t0, 16
		mul t0, t1, t0
		sw t0, 232(sp)
		lw t1, 52(sp)
		lw t0, 0(t1)
		sw t0, 236(sp)
		lw t1, 232(sp)
		lw t0, 236(sp)
		add t0, t1, t0
		sw t0, 240(sp)
		lw t1, 240(sp)
		li t0, 97
		sub t0, t1, t0
		sw t0, 244(sp)
		lw t1, 244(sp)
		li t0, 10
		add t0, t1, t0
		sw t0, 248(sp)
		lw t1, 48(sp)
		lw t0, 248(sp)
		sw t0, 0(t1)
		j .L21.21
.L20.20:
		lw t1, 40(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L26.26
.L21.21:
		j .L22.22
.L22.22:
		j .L23.23
.L23.23:
		j .L24.24
.L24.24:
		lw t1, 44(sp)
		lw t0, 0(t1)
		sw t0, 252(sp)
		lw t1, 252(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 256(sp)
		lw t1, 44(sp)
		lw t0, 256(sp)
		sw t0, 0(t1)
		j .L1.1
.L25.25:
		lw t1, 48(sp)
		lw t0, 0(t1)
		sw t0, 260(sp)
		lw t1, 40(sp)
		lw t0, 260(sp)
		sw t0, 0(t1)
		j .L26.26
.L26.26:
		lw t1, 40(sp)
		lw t0, 0(t1)
		sw t0, 264(sp)
		lw t1, 264(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 268
		add sp, sp, t0
		ret

 .text
 .globl int2chr
int2chr:
.L27.27:
		li t0, -84
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 16(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 20(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 24(sp)
		lw t1, 16(sp)
		sw a0, 0(t1)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 28(sp)
		lw t1, 28(sp)
		li t0, 32
		slt t0, t1, t0
		sw t0, 32(sp)
		lw t1, 32(sp)
		xori t0, t1, 1
		sb t0, 36(sp)
		lb t1, 36(sp)
		beqz t1, .L30.30
		j .L28.28
.L28.28:
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 40(sp)
		li t1, 126
		lw t0, 40(sp)
		slt t0, t1, t0
		sw t0, 44(sp)
		lw t1, 44(sp)
		xori t0, t1, 1
		sb t0, 48(sp)
		lb t1, 48(sp)
		beqz t1, .L30.30
		j .L29.29
.L29.29:
		lw t1, 24(sp)
		li t0, 1
		sb t0, 0(t1)
		j .L31.31
.L30.30:
		lw t1, 24(sp)
		li t0, 0
		sb t0, 0(t1)
		j .L31.31
.L31.31:
		lw t1, 24(sp)
		lb t0, 0(t1)
		sb t0, 52(sp)
		lb t1, 52(sp)
		beqz t1, .L33.33
		j .L32.32
.L32.32:
		lui t1, %hi(asciiTable)
		addi t1, t1, %lo(asciiTable)
		lw t0, 0(t1)
		sw t0, 56(sp)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 60(sp)
		lw t1, 60(sp)
		li t0, 32
		sub t0, t1, t0
		sw t0, 64(sp)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 68(sp)
		lw t1, 68(sp)
		li t0, 31
		sub t0, t1, t0
		sw t0, 72(sp)
		lw t1, 56(sp)
		mv a0, t1
		lw t1, 64(sp)
		mv a1, t1
		lw t1, 72(sp)
		mv a2, t1
		call _string_substring
		mv t0, a0
		sw t0, 76(sp)
		lw t1, 20(sp)
		lw t0, 76(sp)
		sw t0, 0(t1)
		j .L34.34
.L33.33:
		lw t1, 20(sp)
		lui t0, %hi(.str.2)
		addi t0, t0, %lo(.str.2)
		sw t0, 0(t1)
		j .L34.34
.L34.34:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 80(sp)
		lw t1, 80(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 84
		add sp, sp, t0
		ret

 .text
 .globl toStringHex
toStringHex:
.L35.35:
		li t0, -144
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 24(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 28(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 32(sp)
		li t0, 16
		add t0, sp, t0
		sw t0, 36(sp)
		li t0, 20
		add t0, sp, t0
		sw t0, 40(sp)
		lw t1, 24(sp)
		sw a0, 0(t1)
		lw t1, 32(sp)
		lui t0, %hi(.str.2)
		addi t0, t0, %lo(.str.2)
		sw t0, 0(t1)
		lw t1, 36(sp)
		li t0, 28
		sw t0, 0(t1)
		j .L36.36
.L36.36:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 44(sp)
		lw t1, 44(sp)
		li t0, 0
		slt t0, t1, t0
		sw t0, 48(sp)
		lw t1, 48(sp)
		xori t0, t1, 1
		sb t0, 52(sp)
		lb t1, 52(sp)
		beqz t1, .L42.42
		j .L37.37
.L37.37:
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 56(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 60(sp)
		lw t1, 56(sp)
		lw t0, 60(sp)
		sra t0, t1, t0
		sw t0, 64(sp)
		lw t1, 64(sp)
		li t0, 15
		and t0, t1, t0
		sw t0, 68(sp)
		lw t1, 40(sp)
		lw t0, 68(sp)
		sw t0, 0(t1)
		lw t1, 40(sp)
		lw t0, 0(t1)
		sw t0, 72(sp)
		lw t1, 72(sp)
		li t0, 10
		slt t0, t1, t0
		sb t0, 80(sp)
		lb t1, 80(sp)
		beqz t1, .L39.39
		j .L38.38
.L38.38:
		lw t1, 40(sp)
		lw t0, 0(t1)
		sw t0, 84(sp)
		li t1, 48
		lw t0, 84(sp)
		add t0, t1, t0
		sw t0, 88(sp)
		lw t1, 88(sp)
		mv a0, t1
		call int2chr
		mv t0, a0
		sw t0, 92(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 96(sp)
		lw t1, 96(sp)
		mv a0, t1
		lw t1, 92(sp)
		mv a1, t1
		call _string_add
		mv t0, a0
		sw t0, 100(sp)
		lw t1, 32(sp)
		lw t0, 100(sp)
		sw t0, 0(t1)
		j .L40.40
.L39.39:
		lw t1, 40(sp)
		lw t0, 0(t1)
		sw t0, 104(sp)
		li t1, 65
		lw t0, 104(sp)
		add t0, t1, t0
		sw t0, 108(sp)
		lw t1, 108(sp)
		li t0, 10
		sub t0, t1, t0
		sw t0, 112(sp)
		lw t1, 112(sp)
		mv a0, t1
		call int2chr
		mv t0, a0
		sw t0, 116(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 120(sp)
		lw t1, 120(sp)
		mv a0, t1
		lw t1, 116(sp)
		mv a1, t1
		call _string_add
		mv t0, a0
		sw t0, 124(sp)
		lw t1, 32(sp)
		lw t0, 124(sp)
		sw t0, 0(t1)
		j .L40.40
.L40.40:
		j .L41.41
.L41.41:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 128(sp)
		lw t1, 128(sp)
		li t0, 4
		sub t0, t1, t0
		sw t0, 132(sp)
		lw t1, 36(sp)
		lw t0, 132(sp)
		sw t0, 0(t1)
		j .L36.36
.L42.42:
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 136(sp)
		lw t1, 28(sp)
		lw t0, 136(sp)
		sw t0, 0(t1)
		j .L43.43
.L43.43:
		lw t1, 28(sp)
		lw t0, 0(t1)
		sw t0, 140(sp)
		lw t1, 140(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 144
		add sp, sp, t0
		ret

 .text
 .globl rotate_left
rotate_left:
.L44.44:
		li t0, -180
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 16(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 20(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 24(sp)
		lw t1, 16(sp)
		sw a0, 0(t1)
		lw t1, 20(sp)
		sw a1, 0(t1)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 28(sp)
		lw t1, 28(sp)
		li t0, 1
		sub t0, t1, t0
		sw t0, 32(sp)
		lw t1, 32(sp)
		seqz t0, t1
		sb t0, 36(sp)
		lb t1, 36(sp)
		beqz t1, .L46.46
		j .L45.45
.L45.45:
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 40(sp)
		lw t1, 40(sp)
		li t0, 2147483647
		and t0, t1, t0
		sw t0, 44(sp)
		lw t1, 44(sp)
		li t0, 1
		sll t0, t1, t0
		sw t0, 48(sp)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 52(sp)
		lw t1, 52(sp)
		li t0, 31
		sra t0, t1, t0
		sw t0, 56(sp)
		lw t1, 56(sp)
		li t0, 1
		and t0, t1, t0
		sw t0, 60(sp)
		lw t1, 48(sp)
		lw t0, 60(sp)
		or t0, t1, t0
		sw t0, 64(sp)
		lw t1, 24(sp)
		lw t0, 64(sp)
		sw t0, 0(t1)
		j .L49.49
.L46.46:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 68(sp)
		lw t1, 68(sp)
		li t0, 31
		sub t0, t1, t0
		sw t0, 72(sp)
		lw t1, 72(sp)
		seqz t0, t1
		sb t0, 76(sp)
		lb t1, 76(sp)
		beqz t1, .L48.48
		j .L47.47
.L47.47:
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 80(sp)
		lw t1, 80(sp)
		li t0, 1
		and t0, t1, t0
		sw t0, 84(sp)
		lw t1, 84(sp)
		li t0, 31
		sll t0, t1, t0
		sw t0, 88(sp)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 92(sp)
		lw t1, 92(sp)
		li t0, 1
		sra t0, t1, t0
		sw t0, 96(sp)
		lw t1, 96(sp)
		li t0, 2147483647
		and t0, t1, t0
		sw t0, 100(sp)
		lw t1, 88(sp)
		lw t0, 100(sp)
		or t0, t1, t0
		sw t0, 104(sp)
		lw t1, 24(sp)
		lw t0, 104(sp)
		sw t0, 0(t1)
		j .L49.49
.L48.48:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 108(sp)
		li t1, 32
		lw t0, 108(sp)
		sub t0, t1, t0
		sw t0, 112(sp)
		li t1, 1
		lw t0, 112(sp)
		sll t0, t1, t0
		sw t0, 116(sp)
		lw t1, 116(sp)
		li t0, 1
		sub t0, t1, t0
		sw t0, 120(sp)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 124(sp)
		lw t1, 124(sp)
		lw t0, 120(sp)
		and t0, t1, t0
		sw t0, 128(sp)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 132(sp)
		lw t1, 128(sp)
		lw t0, 132(sp)
		sll t0, t1, t0
		sw t0, 136(sp)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 140(sp)
		li t1, 32
		lw t0, 140(sp)
		sub t0, t1, t0
		sw t0, 144(sp)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 148(sp)
		lw t1, 148(sp)
		lw t0, 144(sp)
		sra t0, t1, t0
		sw t0, 152(sp)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 156(sp)
		li t1, 1
		lw t0, 156(sp)
		sll t0, t1, t0
		sw t0, 160(sp)
		lw t1, 160(sp)
		li t0, 1
		sub t0, t1, t0
		sw t0, 164(sp)
		lw t1, 152(sp)
		lw t0, 164(sp)
		and t0, t1, t0
		sw t0, 168(sp)
		lw t1, 136(sp)
		lw t0, 168(sp)
		or t0, t1, t0
		sw t0, 172(sp)
		lw t1, 24(sp)
		lw t0, 172(sp)
		sw t0, 0(t1)
		j .L49.49
.L49.49:
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 176(sp)
		lw t1, 176(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 180
		add sp, sp, t0
		ret

 .text
 .globl add
add:
.L50.50:
		li t0, -132
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 24(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 28(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 32(sp)
		li t0, 16
		add t0, sp, t0
		sw t0, 36(sp)
		li t0, 20
		add t0, sp, t0
		sw t0, 40(sp)
		lw t1, 24(sp)
		sw a0, 0(t1)
		lw t1, 28(sp)
		sw a1, 0(t1)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 44(sp)
		lw t1, 44(sp)
		li t0, 65535
		and t0, t1, t0
		sw t0, 48(sp)
		lw t1, 28(sp)
		lw t0, 0(t1)
		sw t0, 52(sp)
		lw t1, 52(sp)
		li t0, 65535
		and t0, t1, t0
		sw t0, 56(sp)
		lw t1, 48(sp)
		lw t0, 56(sp)
		add t0, t1, t0
		sw t0, 60(sp)
		lw t1, 36(sp)
		lw t0, 60(sp)
		sw t0, 0(t1)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 64(sp)
		lw t1, 64(sp)
		li t0, 16
		sra t0, t1, t0
		sw t0, 68(sp)
		lw t1, 68(sp)
		li t0, 65535
		and t0, t1, t0
		sw t0, 72(sp)
		lw t1, 28(sp)
		lw t0, 0(t1)
		sw t0, 76(sp)
		lw t1, 76(sp)
		li t0, 16
		sra t0, t1, t0
		sw t0, 80(sp)
		lw t1, 80(sp)
		li t0, 65535
		and t0, t1, t0
		sw t0, 84(sp)
		lw t1, 72(sp)
		lw t0, 84(sp)
		add t0, t1, t0
		sw t0, 88(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 92(sp)
		lw t1, 92(sp)
		li t0, 16
		sra t0, t1, t0
		sw t0, 96(sp)
		lw t1, 88(sp)
		lw t0, 96(sp)
		add t0, t1, t0
		sw t0, 100(sp)
		lw t1, 100(sp)
		li t0, 65535
		and t0, t1, t0
		sw t0, 104(sp)
		lw t1, 40(sp)
		lw t0, 104(sp)
		sw t0, 0(t1)
		lw t1, 40(sp)
		lw t0, 0(t1)
		sw t0, 108(sp)
		lw t1, 108(sp)
		li t0, 16
		sll t0, t1, t0
		sw t0, 112(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 116(sp)
		lw t1, 116(sp)
		li t0, 65535
		and t0, t1, t0
		sw t0, 120(sp)
		lw t1, 112(sp)
		lw t0, 120(sp)
		or t0, t1, t0
		sw t0, 124(sp)
		lw t1, 32(sp)
		lw t0, 124(sp)
		sw t0, 0(t1)
		j .L51.51
.L51.51:
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 128(sp)
		lw t1, 128(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 132
		add sp, sp, t0
		ret

 .text
 .globl lohi
lohi:
.L52.52:
		li t0, -48
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 16(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 20(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 24(sp)
		lw t1, 16(sp)
		sw a0, 0(t1)
		lw t1, 20(sp)
		sw a1, 0(t1)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 28(sp)
		lw t1, 28(sp)
		li t0, 16
		sll t0, t1, t0
		sw t0, 32(sp)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 36(sp)
		lw t1, 36(sp)
		lw t0, 32(sp)
		or t0, t1, t0
		sw t0, 40(sp)
		lw t1, 24(sp)
		lw t0, 40(sp)
		sw t0, 0(t1)
		j .L53.53
.L53.53:
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 44(sp)
		lw t1, 44(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 48
		add sp, sp, t0
		ret

 .text
 .globl sha1
sha1:
.L54.54:
		li t0, -1340
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 80(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 84(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 88(sp)
		li t0, 16
		add t0, sp, t0
		sw t0, 92(sp)
		li t0, 20
		add t0, sp, t0
		sw t0, 96(sp)
		li t0, 24
		add t0, sp, t0
		sw t0, 100(sp)
		li t0, 28
		add t0, sp, t0
		sw t0, 104(sp)
		li t0, 32
		add t0, sp, t0
		sw t0, 108(sp)
		li t0, 36
		add t0, sp, t0
		sw t0, 112(sp)
		li t0, 40
		add t0, sp, t0
		sw t0, 116(sp)
		li t0, 44
		add t0, sp, t0
		sw t0, 120(sp)
		li t0, 48
		add t0, sp, t0
		sw t0, 124(sp)
		li t0, 52
		add t0, sp, t0
		sw t0, 128(sp)
		li t0, 56
		add t0, sp, t0
		sw t0, 132(sp)
		li t0, 60
		add t0, sp, t0
		sw t0, 136(sp)
		li t0, 64
		add t0, sp, t0
		sw t0, 140(sp)
		li t0, 68
		add t0, sp, t0
		sw t0, 144(sp)
		li t0, 72
		add t0, sp, t0
		sw t0, 148(sp)
		li t0, 76
		add t0, sp, t0
		sw t0, 152(sp)
		lw t1, 80(sp)
		sw a0, 0(t1)
		lw t1, 84(sp)
		sw a1, 0(t1)
		lw t1, 84(sp)
		lw t0, 0(t1)
		sw t0, 156(sp)
		lw t1, 156(sp)
		li t0, 64
		add t0, t1, t0
		sw t0, 160(sp)
		lw t1, 160(sp)
		li t0, 56
		sub t0, t1, t0
		sw t0, 164(sp)
		lw t1, 164(sp)
		li t0, 64
		div t0, t1, t0
		sw t0, 168(sp)
		lw t1, 168(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 172(sp)
		lw t1, 92(sp)
		lw t0, 172(sp)
		sw t0, 0(t1)
		lw t1, 92(sp)
		lw t0, 0(t1)
		sw t0, 176(sp)
		lui t1, %hi(MAXCHUNK)
		addi t1, t1, %lo(MAXCHUNK)
		lw t0, 0(t1)
		sw t0, 180(sp)
		lw t1, 180(sp)
		lw t0, 176(sp)
		slt t0, t1, t0
		sb t0, 188(sp)
		lb t1, 188(sp)
		beqz t1, .L56.56
		j .L55.55
.L55.55:
		lui t1, %hi(.str.3)
		addi t1, t1, %lo(.str.3)
		mv a0, t1
		call println
		mv t0, a0
		sw t0, 192(sp)
		lw t1, 88(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L90.90
.L56.56:
		lw t1, 96(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L57.57
.L57.57:
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 196(sp)
		lw t1, 92(sp)
		lw t0, 0(t1)
		sw t0, 200(sp)
		lw t1, 196(sp)
		lw t0, 200(sp)
		slt t0, t1, t0
		sb t0, 208(sp)
		lb t1, 208(sp)
		beqz t1, .L64.64
		j .L58.58
.L58.58:
		lw t1, 100(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L59.59
.L59.59:
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 212(sp)
		lw t1, 212(sp)
		li t0, 80
		slt t0, t1, t0
		sb t0, 220(sp)
		lb t1, 220(sp)
		beqz t1, .L62.62
		j .L60.60
.L60.60:
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 224(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 228(sp)
		lw t1, 228(sp)
		slli t0, t1, 2
		sw t0, 232(sp)
		lw t1, 224(sp)
		lw t0, 232(sp)
		add t0, t1, t0
		sw t0, 236(sp)
		lw t1, 236(sp)
		lw t0, 0(t1)
		sw t0, 240(sp)
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 244(sp)
		lw t1, 244(sp)
		slli t0, t1, 2
		sw t0, 248(sp)
		lw t1, 240(sp)
		lw t0, 248(sp)
		add t0, t1, t0
		sw t0, 252(sp)
		lw t1, 252(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L61.61
.L61.61:
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 256(sp)
		lw t1, 256(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 260(sp)
		lw t1, 100(sp)
		lw t0, 260(sp)
		sw t0, 0(t1)
		j .L59.59
.L62.62:
		j .L63.63
.L63.63:
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 264(sp)
		lw t1, 264(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 268(sp)
		lw t1, 96(sp)
		lw t0, 268(sp)
		sw t0, 0(t1)
		j .L57.57
.L64.64:
		lw t1, 96(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L65.65
.L65.65:
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 272(sp)
		lw t1, 84(sp)
		lw t0, 0(t1)
		sw t0, 276(sp)
		lw t1, 272(sp)
		lw t0, 276(sp)
		slt t0, t1, t0
		sb t0, 284(sp)
		lb t1, 284(sp)
		beqz t1, .L68.68
		j .L66.66
.L66.66:
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 288(sp)
		lw t1, 288(sp)
		li t0, 64
		div t0, t1, t0
		sw t0, 292(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 296(sp)
		lw t1, 292(sp)
		slli t0, t1, 2
		sw t0, 300(sp)
		lw t1, 296(sp)
		lw t0, 300(sp)
		add t0, t1, t0
		sw t0, 304(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 308(sp)
		lw t1, 308(sp)
		li t0, 64
		rem t0, t1, t0
		sw t0, 312(sp)
		lw t1, 312(sp)
		li t0, 4
		div t0, t1, t0
		sw t0, 316(sp)
		lw t1, 304(sp)
		lw t0, 0(t1)
		sw t0, 320(sp)
		lw t1, 316(sp)
		slli t0, t1, 2
		sw t0, 324(sp)
		lw t1, 320(sp)
		lw t0, 324(sp)
		add t0, t1, t0
		sw t0, 328(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 332(sp)
		lw t1, 332(sp)
		li t0, 64
		div t0, t1, t0
		sw t0, 336(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 340(sp)
		lw t1, 336(sp)
		slli t0, t1, 2
		sw t0, 344(sp)
		lw t1, 340(sp)
		lw t0, 344(sp)
		add t0, t1, t0
		sw t0, 348(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 352(sp)
		lw t1, 352(sp)
		li t0, 64
		rem t0, t1, t0
		sw t0, 356(sp)
		lw t1, 356(sp)
		li t0, 4
		div t0, t1, t0
		sw t0, 360(sp)
		lw t1, 348(sp)
		lw t0, 0(t1)
		sw t0, 364(sp)
		lw t1, 360(sp)
		slli t0, t1, 2
		sw t0, 368(sp)
		lw t1, 364(sp)
		lw t0, 368(sp)
		add t0, t1, t0
		sw t0, 372(sp)
		lw t1, 80(sp)
		lw t0, 0(t1)
		sw t0, 376(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 380(sp)
		lw t1, 380(sp)
		slli t0, t1, 2
		sw t0, 384(sp)
		lw t1, 376(sp)
		lw t0, 384(sp)
		add t0, t1, t0
		sw t0, 388(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 392(sp)
		lw t1, 392(sp)
		li t0, 4
		rem t0, t1, t0
		sw t0, 396(sp)
		li t1, 3
		lw t0, 396(sp)
		sub t0, t1, t0
		sw t0, 400(sp)
		lw t1, 400(sp)
		li t0, 8
		mul t0, t1, t0
		sw t0, 404(sp)
		lw t1, 388(sp)
		lw t0, 0(t1)
		sw t0, 408(sp)
		lw t1, 408(sp)
		lw t0, 404(sp)
		sll t0, t1, t0
		sw t0, 412(sp)
		lw t1, 372(sp)
		lw t0, 0(t1)
		sw t0, 416(sp)
		lw t1, 416(sp)
		lw t0, 412(sp)
		or t0, t1, t0
		sw t0, 420(sp)
		lw t1, 328(sp)
		lw t0, 420(sp)
		sw t0, 0(t1)
		j .L67.67
.L67.67:
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 424(sp)
		lw t1, 424(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 428(sp)
		lw t1, 96(sp)
		lw t0, 428(sp)
		sw t0, 0(t1)
		j .L65.65
.L68.68:
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 432(sp)
		lw t1, 432(sp)
		li t0, 64
		div t0, t1, t0
		sw t0, 436(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 440(sp)
		lw t1, 436(sp)
		slli t0, t1, 2
		sw t0, 444(sp)
		lw t1, 440(sp)
		lw t0, 444(sp)
		add t0, t1, t0
		sw t0, 448(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 452(sp)
		lw t1, 452(sp)
		li t0, 64
		rem t0, t1, t0
		sw t0, 456(sp)
		lw t1, 456(sp)
		li t0, 4
		div t0, t1, t0
		sw t0, 460(sp)
		lw t1, 448(sp)
		lw t0, 0(t1)
		sw t0, 464(sp)
		lw t1, 460(sp)
		slli t0, t1, 2
		sw t0, 468(sp)
		lw t1, 464(sp)
		lw t0, 468(sp)
		add t0, t1, t0
		sw t0, 472(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 476(sp)
		lw t1, 476(sp)
		li t0, 64
		div t0, t1, t0
		sw t0, 480(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 484(sp)
		lw t1, 480(sp)
		slli t0, t1, 2
		sw t0, 488(sp)
		lw t1, 484(sp)
		lw t0, 488(sp)
		add t0, t1, t0
		sw t0, 492(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 496(sp)
		lw t1, 496(sp)
		li t0, 64
		rem t0, t1, t0
		sw t0, 500(sp)
		lw t1, 500(sp)
		li t0, 4
		div t0, t1, t0
		sw t0, 504(sp)
		lw t1, 492(sp)
		lw t0, 0(t1)
		sw t0, 508(sp)
		lw t1, 504(sp)
		slli t0, t1, 2
		sw t0, 512(sp)
		lw t1, 508(sp)
		lw t0, 512(sp)
		add t0, t1, t0
		sw t0, 516(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 520(sp)
		lw t1, 520(sp)
		li t0, 4
		rem t0, t1, t0
		sw t0, 524(sp)
		li t1, 3
		lw t0, 524(sp)
		sub t0, t1, t0
		sw t0, 528(sp)
		lw t1, 528(sp)
		li t0, 8
		mul t0, t1, t0
		sw t0, 532(sp)
		li t1, 128
		lw t0, 532(sp)
		sll t0, t1, t0
		sw t0, 536(sp)
		lw t1, 516(sp)
		lw t0, 0(t1)
		sw t0, 540(sp)
		lw t1, 540(sp)
		lw t0, 536(sp)
		or t0, t1, t0
		sw t0, 544(sp)
		lw t1, 472(sp)
		lw t0, 544(sp)
		sw t0, 0(t1)
		lw t1, 92(sp)
		lw t0, 0(t1)
		sw t0, 548(sp)
		lw t1, 548(sp)
		li t0, 1
		sub t0, t1, t0
		sw t0, 552(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 556(sp)
		lw t1, 552(sp)
		slli t0, t1, 2
		sw t0, 560(sp)
		lw t1, 556(sp)
		lw t0, 560(sp)
		add t0, t1, t0
		sw t0, 564(sp)
		lw t1, 564(sp)
		lw t0, 0(t1)
		sw t0, 568(sp)
		li t1, 15
		slli t0, t1, 2
		sw t0, 572(sp)
		lw t1, 568(sp)
		lw t0, 572(sp)
		add t0, t1, t0
		sw t0, 576(sp)
		lw t1, 84(sp)
		lw t0, 0(t1)
		sw t0, 580(sp)
		lw t1, 580(sp)
		li t0, 3
		sll t0, t1, t0
		sw t0, 584(sp)
		lw t1, 576(sp)
		lw t0, 584(sp)
		sw t0, 0(t1)
		lw t1, 92(sp)
		lw t0, 0(t1)
		sw t0, 588(sp)
		lw t1, 588(sp)
		li t0, 1
		sub t0, t1, t0
		sw t0, 592(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 596(sp)
		lw t1, 592(sp)
		slli t0, t1, 2
		sw t0, 600(sp)
		lw t1, 596(sp)
		lw t0, 600(sp)
		add t0, t1, t0
		sw t0, 604(sp)
		lw t1, 604(sp)
		lw t0, 0(t1)
		sw t0, 608(sp)
		li t1, 14
		slli t0, t1, 2
		sw t0, 612(sp)
		lw t1, 608(sp)
		lw t0, 612(sp)
		add t0, t1, t0
		sw t0, 616(sp)
		lw t1, 84(sp)
		lw t0, 0(t1)
		sw t0, 620(sp)
		lw t1, 620(sp)
		li t0, 29
		sra t0, t1, t0
		sw t0, 624(sp)
		lw t1, 624(sp)
		li t0, 7
		and t0, t1, t0
		sw t0, 628(sp)
		lw t1, 616(sp)
		lw t0, 628(sp)
		sw t0, 0(t1)
		lw t1, 104(sp)
		li t0, 1732584193
		sw t0, 0(t1)
		li t1, 43913
		mv a0, t1
		li t1, 61389
		mv a1, t1
		call lohi
		mv t0, a0
		sw t0, 632(sp)
		lw t1, 108(sp)
		lw t0, 632(sp)
		sw t0, 0(t1)
		li t1, 56574
		mv a0, t1
		li t1, 39098
		mv a1, t1
		call lohi
		mv t0, a0
		sw t0, 636(sp)
		lw t1, 112(sp)
		lw t0, 636(sp)
		sw t0, 0(t1)
		lw t1, 116(sp)
		li t0, 271733878
		sw t0, 0(t1)
		li t1, 57840
		mv a0, t1
		li t1, 50130
		mv a1, t1
		call lohi
		mv t0, a0
		sw t0, 640(sp)
		lw t1, 120(sp)
		lw t0, 640(sp)
		sw t0, 0(t1)
		lw t1, 96(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L69.69
.L69.69:
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 644(sp)
		lw t1, 92(sp)
		lw t0, 0(t1)
		sw t0, 648(sp)
		lw t1, 644(sp)
		lw t0, 648(sp)
		slt t0, t1, t0
		sb t0, 656(sp)
		lb t1, 656(sp)
		beqz t1, .L89.89
		j .L70.70
.L70.70:
		lw t1, 100(sp)
		li t0, 16
		sw t0, 0(t1)
		j .L71.71
.L71.71:
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 660(sp)
		lw t1, 660(sp)
		li t0, 80
		slt t0, t1, t0
		sb t0, 668(sp)
		lb t1, 668(sp)
		beqz t1, .L74.74
		j .L72.72
.L72.72:
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 672(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 676(sp)
		lw t1, 676(sp)
		slli t0, t1, 2
		sw t0, 680(sp)
		lw t1, 672(sp)
		lw t0, 680(sp)
		add t0, t1, t0
		sw t0, 684(sp)
		lw t1, 684(sp)
		lw t0, 0(t1)
		sw t0, 688(sp)
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 692(sp)
		lw t1, 692(sp)
		slli t0, t1, 2
		sw t0, 696(sp)
		lw t1, 688(sp)
		lw t0, 696(sp)
		add t0, t1, t0
		sw t0, 700(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 704(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 708(sp)
		lw t1, 708(sp)
		slli t0, t1, 2
		sw t0, 712(sp)
		lw t1, 704(sp)
		lw t0, 712(sp)
		add t0, t1, t0
		sw t0, 716(sp)
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 720(sp)
		lw t1, 720(sp)
		li t0, 3
		sub t0, t1, t0
		sw t0, 724(sp)
		lw t1, 716(sp)
		lw t0, 0(t1)
		sw t0, 728(sp)
		lw t1, 724(sp)
		slli t0, t1, 2
		sw t0, 732(sp)
		lw t1, 728(sp)
		lw t0, 732(sp)
		add t0, t1, t0
		sw t0, 736(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 740(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 744(sp)
		lw t1, 744(sp)
		slli t0, t1, 2
		sw t0, 748(sp)
		lw t1, 740(sp)
		lw t0, 748(sp)
		add t0, t1, t0
		sw t0, 752(sp)
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 756(sp)
		lw t1, 756(sp)
		li t0, 8
		sub t0, t1, t0
		sw t0, 760(sp)
		lw t1, 752(sp)
		lw t0, 0(t1)
		sw t0, 764(sp)
		lw t1, 760(sp)
		slli t0, t1, 2
		sw t0, 768(sp)
		lw t1, 764(sp)
		lw t0, 768(sp)
		add t0, t1, t0
		sw t0, 772(sp)
		lw t1, 736(sp)
		lw t0, 0(t1)
		sw t0, 776(sp)
		lw t1, 772(sp)
		lw t0, 0(t1)
		sw t0, 780(sp)
		lw t1, 776(sp)
		lw t0, 780(sp)
		xor t0, t1, t0
		sw t0, 784(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 788(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 792(sp)
		lw t1, 792(sp)
		slli t0, t1, 2
		sw t0, 796(sp)
		lw t1, 788(sp)
		lw t0, 796(sp)
		add t0, t1, t0
		sw t0, 800(sp)
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 804(sp)
		lw t1, 804(sp)
		li t0, 14
		sub t0, t1, t0
		sw t0, 808(sp)
		lw t1, 800(sp)
		lw t0, 0(t1)
		sw t0, 812(sp)
		lw t1, 808(sp)
		slli t0, t1, 2
		sw t0, 816(sp)
		lw t1, 812(sp)
		lw t0, 816(sp)
		add t0, t1, t0
		sw t0, 820(sp)
		lw t1, 820(sp)
		lw t0, 0(t1)
		sw t0, 824(sp)
		lw t1, 784(sp)
		lw t0, 824(sp)
		xor t0, t1, t0
		sw t0, 828(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 832(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 836(sp)
		lw t1, 836(sp)
		slli t0, t1, 2
		sw t0, 840(sp)
		lw t1, 832(sp)
		lw t0, 840(sp)
		add t0, t1, t0
		sw t0, 844(sp)
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 848(sp)
		lw t1, 848(sp)
		li t0, 16
		sub t0, t1, t0
		sw t0, 852(sp)
		lw t1, 844(sp)
		lw t0, 0(t1)
		sw t0, 856(sp)
		lw t1, 852(sp)
		slli t0, t1, 2
		sw t0, 860(sp)
		lw t1, 856(sp)
		lw t0, 860(sp)
		add t0, t1, t0
		sw t0, 864(sp)
		lw t1, 864(sp)
		lw t0, 0(t1)
		sw t0, 868(sp)
		lw t1, 828(sp)
		lw t0, 868(sp)
		xor t0, t1, t0
		sw t0, 872(sp)
		lw t1, 872(sp)
		mv a0, t1
		li t1, 1
		mv a1, t1
		call rotate_left
		mv t0, a0
		sw t0, 876(sp)
		lw t1, 700(sp)
		lw t0, 876(sp)
		sw t0, 0(t1)
		j .L73.73
.L73.73:
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 880(sp)
		lw t1, 880(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 884(sp)
		lw t1, 100(sp)
		lw t0, 884(sp)
		sw t0, 0(t1)
		j .L71.71
.L74.74:
		lw t1, 104(sp)
		lw t0, 0(t1)
		sw t0, 888(sp)
		lw t1, 124(sp)
		lw t0, 888(sp)
		sw t0, 0(t1)
		lw t1, 108(sp)
		lw t0, 0(t1)
		sw t0, 892(sp)
		lw t1, 128(sp)
		lw t0, 892(sp)
		sw t0, 0(t1)
		lw t1, 112(sp)
		lw t0, 0(t1)
		sw t0, 896(sp)
		lw t1, 132(sp)
		lw t0, 896(sp)
		sw t0, 0(t1)
		lw t1, 116(sp)
		lw t0, 0(t1)
		sw t0, 900(sp)
		lw t1, 136(sp)
		lw t0, 900(sp)
		sw t0, 0(t1)
		lw t1, 120(sp)
		lw t0, 0(t1)
		sw t0, 904(sp)
		lw t1, 140(sp)
		lw t0, 904(sp)
		sw t0, 0(t1)
		lw t1, 100(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L75.75
.L75.75:
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 908(sp)
		lw t1, 908(sp)
		li t0, 80
		slt t0, t1, t0
		sb t0, 916(sp)
		lb t1, 916(sp)
		beqz t1, .L87.87
		j .L76.76
.L76.76:
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 920(sp)
		lw t1, 920(sp)
		li t0, 20
		slt t0, t1, t0
		sb t0, 928(sp)
		lb t1, 928(sp)
		beqz t1, .L78.78
		j .L77.77
.L77.77:
		lw t1, 128(sp)
		lw t0, 0(t1)
		sw t0, 932(sp)
		lw t1, 132(sp)
		lw t0, 0(t1)
		sw t0, 936(sp)
		lw t1, 932(sp)
		lw t0, 936(sp)
		and t0, t1, t0
		sw t0, 940(sp)
		lw t1, 128(sp)
		lw t0, 0(t1)
		sw t0, 944(sp)
		lw t1, 944(sp)
		li t0, -1
		xor t0, t1, t0
		sw t0, 948(sp)
		lw t1, 136(sp)
		lw t0, 0(t1)
		sw t0, 952(sp)
		lw t1, 948(sp)
		lw t0, 952(sp)
		and t0, t1, t0
		sw t0, 956(sp)
		lw t1, 940(sp)
		lw t0, 956(sp)
		or t0, t1, t0
		sw t0, 960(sp)
		lw t1, 144(sp)
		lw t0, 960(sp)
		sw t0, 0(t1)
		lw t1, 148(sp)
		li t0, 1518500249
		sw t0, 0(t1)
		j .L85.85
.L78.78:
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 964(sp)
		lw t1, 964(sp)
		li t0, 40
		slt t0, t1, t0
		sb t0, 972(sp)
		lb t1, 972(sp)
		beqz t1, .L80.80
		j .L79.79
.L79.79:
		lw t1, 128(sp)
		lw t0, 0(t1)
		sw t0, 976(sp)
		lw t1, 132(sp)
		lw t0, 0(t1)
		sw t0, 980(sp)
		lw t1, 976(sp)
		lw t0, 980(sp)
		xor t0, t1, t0
		sw t0, 984(sp)
		lw t1, 136(sp)
		lw t0, 0(t1)
		sw t0, 988(sp)
		lw t1, 984(sp)
		lw t0, 988(sp)
		xor t0, t1, t0
		sw t0, 992(sp)
		lw t1, 144(sp)
		lw t0, 992(sp)
		sw t0, 0(t1)
		lw t1, 148(sp)
		li t0, 1859775393
		sw t0, 0(t1)
		j .L84.84
.L80.80:
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 996(sp)
		lw t1, 996(sp)
		li t0, 60
		slt t0, t1, t0
		sb t0, 1004(sp)
		lb t1, 1004(sp)
		beqz t1, .L82.82
		j .L81.81
.L81.81:
		lw t1, 128(sp)
		lw t0, 0(t1)
		sw t0, 1008(sp)
		lw t1, 132(sp)
		lw t0, 0(t1)
		sw t0, 1012(sp)
		lw t1, 1008(sp)
		lw t0, 1012(sp)
		and t0, t1, t0
		sw t0, 1016(sp)
		lw t1, 128(sp)
		lw t0, 0(t1)
		sw t0, 1020(sp)
		lw t1, 136(sp)
		lw t0, 0(t1)
		sw t0, 1024(sp)
		lw t1, 1020(sp)
		lw t0, 1024(sp)
		and t0, t1, t0
		sw t0, 1028(sp)
		lw t1, 1016(sp)
		lw t0, 1028(sp)
		or t0, t1, t0
		sw t0, 1032(sp)
		lw t1, 132(sp)
		lw t0, 0(t1)
		sw t0, 1036(sp)
		lw t1, 136(sp)
		lw t0, 0(t1)
		sw t0, 1040(sp)
		lw t1, 1036(sp)
		lw t0, 1040(sp)
		and t0, t1, t0
		sw t0, 1044(sp)
		lw t1, 1032(sp)
		lw t0, 1044(sp)
		or t0, t1, t0
		sw t0, 1048(sp)
		lw t1, 144(sp)
		lw t0, 1048(sp)
		sw t0, 0(t1)
		li t1, 48348
		mv a0, t1
		li t1, 36635
		mv a1, t1
		call lohi
		mv t0, a0
		sw t0, 1052(sp)
		lw t1, 148(sp)
		lw t0, 1052(sp)
		sw t0, 0(t1)
		j .L83.83
.L82.82:
		lw t1, 128(sp)
		lw t0, 0(t1)
		sw t0, 1056(sp)
		lw t1, 132(sp)
		lw t0, 0(t1)
		sw t0, 1060(sp)
		lw t1, 1056(sp)
		lw t0, 1060(sp)
		xor t0, t1, t0
		sw t0, 1064(sp)
		lw t1, 136(sp)
		lw t0, 0(t1)
		sw t0, 1068(sp)
		lw t1, 1064(sp)
		lw t0, 1068(sp)
		xor t0, t1, t0
		sw t0, 1072(sp)
		lw t1, 144(sp)
		lw t0, 1072(sp)
		sw t0, 0(t1)
		li t1, 49622
		mv a0, t1
		li t1, 51810
		mv a1, t1
		call lohi
		mv t0, a0
		sw t0, 1076(sp)
		lw t1, 148(sp)
		lw t0, 1076(sp)
		sw t0, 0(t1)
		j .L83.83
.L83.83:
		j .L84.84
.L84.84:
		j .L85.85
.L85.85:
		lw t1, 124(sp)
		lw t0, 0(t1)
		sw t0, 1080(sp)
		lw t1, 1080(sp)
		mv a0, t1
		li t1, 5
		mv a1, t1
		call rotate_left
		mv t0, a0
		sw t0, 1084(sp)
		lw t1, 140(sp)
		lw t0, 0(t1)
		sw t0, 1088(sp)
		lw t1, 1084(sp)
		mv a0, t1
		lw t1, 1088(sp)
		mv a1, t1
		call add
		mv t0, a0
		sw t0, 1092(sp)
		lw t1, 144(sp)
		lw t0, 0(t1)
		sw t0, 1096(sp)
		lw t1, 148(sp)
		lw t0, 0(t1)
		sw t0, 1100(sp)
		lw t1, 1096(sp)
		mv a0, t1
		lw t1, 1100(sp)
		mv a1, t1
		call add
		mv t0, a0
		sw t0, 1104(sp)
		lw t1, 1092(sp)
		mv a0, t1
		lw t1, 1104(sp)
		mv a1, t1
		call add
		mv t0, a0
		sw t0, 1108(sp)
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 0(t1)
		sw t0, 1112(sp)
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 1116(sp)
		lw t1, 1116(sp)
		slli t0, t1, 2
		sw t0, 1120(sp)
		lw t1, 1112(sp)
		lw t0, 1120(sp)
		add t0, t1, t0
		sw t0, 1124(sp)
		lw t1, 1124(sp)
		lw t0, 0(t1)
		sw t0, 1128(sp)
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 1132(sp)
		lw t1, 1132(sp)
		slli t0, t1, 2
		sw t0, 1136(sp)
		lw t1, 1128(sp)
		lw t0, 1136(sp)
		add t0, t1, t0
		sw t0, 1140(sp)
		lw t1, 1140(sp)
		lw t0, 0(t1)
		sw t0, 1144(sp)
		lw t1, 1108(sp)
		mv a0, t1
		lw t1, 1144(sp)
		mv a1, t1
		call add
		mv t0, a0
		sw t0, 1148(sp)
		lw t1, 152(sp)
		lw t0, 1148(sp)
		sw t0, 0(t1)
		lw t1, 136(sp)
		lw t0, 0(t1)
		sw t0, 1152(sp)
		lw t1, 140(sp)
		lw t0, 1152(sp)
		sw t0, 0(t1)
		lw t1, 132(sp)
		lw t0, 0(t1)
		sw t0, 1156(sp)
		lw t1, 136(sp)
		lw t0, 1156(sp)
		sw t0, 0(t1)
		lw t1, 128(sp)
		lw t0, 0(t1)
		sw t0, 1160(sp)
		lw t1, 1160(sp)
		mv a0, t1
		li t1, 30
		mv a1, t1
		call rotate_left
		mv t0, a0
		sw t0, 1164(sp)
		lw t1, 132(sp)
		lw t0, 1164(sp)
		sw t0, 0(t1)
		lw t1, 124(sp)
		lw t0, 0(t1)
		sw t0, 1168(sp)
		lw t1, 128(sp)
		lw t0, 1168(sp)
		sw t0, 0(t1)
		lw t1, 152(sp)
		lw t0, 0(t1)
		sw t0, 1172(sp)
		lw t1, 124(sp)
		lw t0, 1172(sp)
		sw t0, 0(t1)
		j .L86.86
.L86.86:
		lw t1, 100(sp)
		lw t0, 0(t1)
		sw t0, 1176(sp)
		lw t1, 1176(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 1180(sp)
		lw t1, 100(sp)
		lw t0, 1180(sp)
		sw t0, 0(t1)
		j .L75.75
.L87.87:
		lw t1, 104(sp)
		lw t0, 0(t1)
		sw t0, 1184(sp)
		lw t1, 124(sp)
		lw t0, 0(t1)
		sw t0, 1188(sp)
		lw t1, 1184(sp)
		mv a0, t1
		lw t1, 1188(sp)
		mv a1, t1
		call add
		mv t0, a0
		sw t0, 1192(sp)
		lw t1, 104(sp)
		lw t0, 1192(sp)
		sw t0, 0(t1)
		lw t1, 108(sp)
		lw t0, 0(t1)
		sw t0, 1196(sp)
		lw t1, 128(sp)
		lw t0, 0(t1)
		sw t0, 1200(sp)
		lw t1, 1196(sp)
		mv a0, t1
		lw t1, 1200(sp)
		mv a1, t1
		call add
		mv t0, a0
		sw t0, 1204(sp)
		lw t1, 108(sp)
		lw t0, 1204(sp)
		sw t0, 0(t1)
		lw t1, 112(sp)
		lw t0, 0(t1)
		sw t0, 1208(sp)
		lw t1, 132(sp)
		lw t0, 0(t1)
		sw t0, 1212(sp)
		lw t1, 1208(sp)
		mv a0, t1
		lw t1, 1212(sp)
		mv a1, t1
		call add
		mv t0, a0
		sw t0, 1216(sp)
		lw t1, 112(sp)
		lw t0, 1216(sp)
		sw t0, 0(t1)
		lw t1, 116(sp)
		lw t0, 0(t1)
		sw t0, 1220(sp)
		lw t1, 136(sp)
		lw t0, 0(t1)
		sw t0, 1224(sp)
		lw t1, 1220(sp)
		mv a0, t1
		lw t1, 1224(sp)
		mv a1, t1
		call add
		mv t0, a0
		sw t0, 1228(sp)
		lw t1, 116(sp)
		lw t0, 1228(sp)
		sw t0, 0(t1)
		lw t1, 120(sp)
		lw t0, 0(t1)
		sw t0, 1232(sp)
		lw t1, 140(sp)
		lw t0, 0(t1)
		sw t0, 1236(sp)
		lw t1, 1232(sp)
		mv a0, t1
		lw t1, 1236(sp)
		mv a1, t1
		call add
		mv t0, a0
		sw t0, 1240(sp)
		lw t1, 120(sp)
		lw t0, 1240(sp)
		sw t0, 0(t1)
		j .L88.88
.L88.88:
		lw t1, 96(sp)
		lw t0, 0(t1)
		sw t0, 1244(sp)
		lw t1, 1244(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 1248(sp)
		lw t1, 96(sp)
		lw t0, 1248(sp)
		sw t0, 0(t1)
		j .L69.69
.L89.89:
		lui t1, %hi(outputBuffer)
		addi t1, t1, %lo(outputBuffer)
		lw t0, 0(t1)
		sw t0, 1252(sp)
		li t1, 0
		slli t0, t1, 2
		sw t0, 1256(sp)
		lw t1, 1252(sp)
		lw t0, 1256(sp)
		add t0, t1, t0
		sw t0, 1260(sp)
		lw t1, 104(sp)
		lw t0, 0(t1)
		sw t0, 1264(sp)
		lw t1, 1260(sp)
		lw t0, 1264(sp)
		sw t0, 0(t1)
		lui t1, %hi(outputBuffer)
		addi t1, t1, %lo(outputBuffer)
		lw t0, 0(t1)
		sw t0, 1268(sp)
		li t1, 1
		slli t0, t1, 2
		sw t0, 1272(sp)
		lw t1, 1268(sp)
		lw t0, 1272(sp)
		add t0, t1, t0
		sw t0, 1276(sp)
		lw t1, 108(sp)
		lw t0, 0(t1)
		sw t0, 1280(sp)
		lw t1, 1276(sp)
		lw t0, 1280(sp)
		sw t0, 0(t1)
		lui t1, %hi(outputBuffer)
		addi t1, t1, %lo(outputBuffer)
		lw t0, 0(t1)
		sw t0, 1284(sp)
		li t1, 2
		slli t0, t1, 2
		sw t0, 1288(sp)
		lw t1, 1284(sp)
		lw t0, 1288(sp)
		add t0, t1, t0
		sw t0, 1292(sp)
		lw t1, 112(sp)
		lw t0, 0(t1)
		sw t0, 1296(sp)
		lw t1, 1292(sp)
		lw t0, 1296(sp)
		sw t0, 0(t1)
		lui t1, %hi(outputBuffer)
		addi t1, t1, %lo(outputBuffer)
		lw t0, 0(t1)
		sw t0, 1300(sp)
		li t1, 3
		slli t0, t1, 2
		sw t0, 1304(sp)
		lw t1, 1300(sp)
		lw t0, 1304(sp)
		add t0, t1, t0
		sw t0, 1308(sp)
		lw t1, 116(sp)
		lw t0, 0(t1)
		sw t0, 1312(sp)
		lw t1, 1308(sp)
		lw t0, 1312(sp)
		sw t0, 0(t1)
		lui t1, %hi(outputBuffer)
		addi t1, t1, %lo(outputBuffer)
		lw t0, 0(t1)
		sw t0, 1316(sp)
		li t1, 4
		slli t0, t1, 2
		sw t0, 1320(sp)
		lw t1, 1316(sp)
		lw t0, 1320(sp)
		add t0, t1, t0
		sw t0, 1324(sp)
		lw t1, 120(sp)
		lw t0, 0(t1)
		sw t0, 1328(sp)
		lw t1, 1324(sp)
		lw t0, 1328(sp)
		sw t0, 0(t1)
		lui t1, %hi(outputBuffer)
		addi t1, t1, %lo(outputBuffer)
		lw t0, 0(t1)
		sw t0, 1332(sp)
		lw t1, 88(sp)
		lw t0, 1332(sp)
		sw t0, 0(t1)
		j .L90.90
.L90.90:
		lw t1, 88(sp)
		lw t0, 0(t1)
		sw t0, 1336(sp)
		lw t1, 1336(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 1340
		add sp, sp, t0
		ret

 .text
 .globl computeSHA1
computeSHA1:
.L91.91:
		li t0, -160
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 16(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 20(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 24(sp)
		lw t1, 16(sp)
		sw a0, 0(t1)
		lw t1, 20(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L92.92
.L92.92:
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 28(sp)
		lw t1, 28(sp)
		mv a0, t1
		call _string_length
		mv t0, a0
		sw t0, 32(sp)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 36(sp)
		lw t1, 36(sp)
		lw t0, 32(sp)
		slt t0, t1, t0
		sb t0, 44(sp)
		lb t1, 44(sp)
		beqz t1, .L95.95
		j .L93.93
.L93.93:
		lui t1, %hi(inputBuffer)
		addi t1, t1, %lo(inputBuffer)
		lw t0, 0(t1)
		sw t0, 48(sp)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 52(sp)
		lw t1, 52(sp)
		slli t0, t1, 2
		sw t0, 56(sp)
		lw t1, 48(sp)
		lw t0, 56(sp)
		add t0, t1, t0
		sw t0, 60(sp)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 64(sp)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 68(sp)
		lw t1, 64(sp)
		mv a0, t1
		lw t1, 68(sp)
		mv a1, t1
		call _string_ord
		mv t0, a0
		sw t0, 72(sp)
		lw t1, 60(sp)
		lw t0, 72(sp)
		sw t0, 0(t1)
		j .L94.94
.L94.94:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 76(sp)
		lw t1, 76(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 80(sp)
		lw t1, 20(sp)
		lw t0, 80(sp)
		sw t0, 0(t1)
		j .L92.92
.L95.95:
		lui t1, %hi(inputBuffer)
		addi t1, t1, %lo(inputBuffer)
		lw t0, 0(t1)
		sw t0, 84(sp)
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 88(sp)
		lw t1, 88(sp)
		mv a0, t1
		call _string_length
		mv t0, a0
		sw t0, 92(sp)
		lw t1, 84(sp)
		mv a0, t1
		lw t1, 92(sp)
		mv a1, t1
		call sha1
		mv t0, a0
		sw t0, 96(sp)
		lw t1, 24(sp)
		lw t0, 96(sp)
		sw t0, 0(t1)
		lw t1, 20(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L96.96
.L96.96:
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 100(sp)
		lw t1, 100(sp)
		mv a0, t1
		call _array_size
		mv t0, a0
		sw t0, 104(sp)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 108(sp)
		lw t1, 108(sp)
		lw t0, 104(sp)
		slt t0, t1, t0
		sb t0, 116(sp)
		lb t1, 116(sp)
		beqz t1, .L99.99
		j .L97.97
.L97.97:
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 120(sp)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 124(sp)
		lw t1, 124(sp)
		slli t0, t1, 2
		sw t0, 128(sp)
		lw t1, 120(sp)
		lw t0, 128(sp)
		add t0, t1, t0
		sw t0, 132(sp)
		lw t1, 132(sp)
		lw t0, 0(t1)
		sw t0, 136(sp)
		lw t1, 136(sp)
		mv a0, t1
		call toStringHex
		mv t0, a0
		sw t0, 140(sp)
		lw t1, 140(sp)
		mv a0, t1
		call print
		mv t0, a0
		sw t0, 144(sp)
		j .L98.98
.L98.98:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 148(sp)
		lw t1, 148(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 152(sp)
		lw t1, 20(sp)
		lw t0, 152(sp)
		sw t0, 0(t1)
		j .L96.96
.L99.99:
		lui t1, %hi(.str.2)
		addi t1, t1, %lo(.str.2)
		mv a0, t1
		call println
		mv t0, a0
		sw t0, 156(sp)
		j .L100.100
.L100.100:
		lw ra, 0(sp)
		li t0, 160
		add sp, sp, t0
		ret

 .text
 .globl nextLetter
nextLetter:
.L101.101:
		li t0, -72
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 12(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 16(sp)
		lw t1, 12(sp)
		sw a0, 0(t1)
		lw t1, 12(sp)
		lw t0, 0(t1)
		sw t0, 20(sp)
		lw t1, 20(sp)
		li t0, 122
		sub t0, t1, t0
		sw t0, 24(sp)
		lw t1, 24(sp)
		seqz t0, t1
		sb t0, 28(sp)
		lb t1, 28(sp)
		beqz t1, .L103.103
		j .L102.102
.L102.102:
		li t1, 0
		li t0, 1
		sub t0, t1, t0
		sw t0, 32(sp)
		lw t1, 16(sp)
		lw t0, 32(sp)
		sw t0, 0(t1)
		j .L108.108
.L103.103:
		lw t1, 12(sp)
		lw t0, 0(t1)
		sw t0, 36(sp)
		lw t1, 36(sp)
		li t0, 90
		sub t0, t1, t0
		sw t0, 40(sp)
		lw t1, 40(sp)
		seqz t0, t1
		sb t0, 44(sp)
		lb t1, 44(sp)
		beqz t1, .L105.105
		j .L104.104
.L104.104:
		lw t1, 16(sp)
		li t0, 97
		sw t0, 0(t1)
		j .L108.108
.L105.105:
		lw t1, 12(sp)
		lw t0, 0(t1)
		sw t0, 48(sp)
		lw t1, 48(sp)
		li t0, 57
		sub t0, t1, t0
		sw t0, 52(sp)
		lw t1, 52(sp)
		seqz t0, t1
		sb t0, 56(sp)
		lb t1, 56(sp)
		beqz t1, .L107.107
		j .L106.106
.L106.106:
		lw t1, 16(sp)
		li t0, 65
		sw t0, 0(t1)
		j .L108.108
.L107.107:
		lw t1, 12(sp)
		lw t0, 0(t1)
		sw t0, 60(sp)
		lw t1, 60(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 64(sp)
		lw t1, 16(sp)
		lw t0, 64(sp)
		sw t0, 0(t1)
		j .L108.108
.L108.108:
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 68(sp)
		lw t1, 68(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 72
		add sp, sp, t0
		ret

 .text
 .globl nextText
nextText:
.L109.109:
		li t0, -156
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
		lw t1, 24(sp)
		sw a1, 0(t1)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 36(sp)
		lw t1, 36(sp)
		li t0, 1
		sub t0, t1, t0
		sw t0, 40(sp)
		lw t1, 32(sp)
		lw t0, 40(sp)
		sw t0, 0(t1)
		j .L110.110
.L110.110:
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 44(sp)
		lw t1, 44(sp)
		li t0, 0
		slt t0, t1, t0
		sw t0, 48(sp)
		lw t1, 48(sp)
		xori t0, t1, 1
		sb t0, 52(sp)
		lb t1, 52(sp)
		beqz t1, .L116.116
		j .L111.111
.L111.111:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 56(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 60(sp)
		lw t1, 60(sp)
		slli t0, t1, 2
		sw t0, 64(sp)
		lw t1, 56(sp)
		lw t0, 64(sp)
		add t0, t1, t0
		sw t0, 68(sp)
		lw t1, 20(sp)
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
		lw t1, 84(sp)
		lw t0, 0(t1)
		sw t0, 88(sp)
		lw t1, 88(sp)
		mv a0, t1
		call nextLetter
		mv t0, a0
		sw t0, 92(sp)
		lw t1, 68(sp)
		lw t0, 92(sp)
		sw t0, 0(t1)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 96(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 100(sp)
		lw t1, 100(sp)
		slli t0, t1, 2
		sw t0, 104(sp)
		lw t1, 96(sp)
		lw t0, 104(sp)
		add t0, t1, t0
		sw t0, 108(sp)
		li t1, 0
		li t0, 1
		sub t0, t1, t0
		sw t0, 112(sp)
		lw t1, 108(sp)
		lw t0, 0(t1)
		sw t0, 116(sp)
		lw t1, 116(sp)
		lw t0, 112(sp)
		sub t0, t1, t0
		sw t0, 120(sp)
		lw t1, 120(sp)
		seqz t0, t1
		sb t0, 124(sp)
		lb t1, 124(sp)
		beqz t1, .L113.113
		j .L112.112
.L112.112:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 128(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 132(sp)
		lw t1, 132(sp)
		slli t0, t1, 2
		sw t0, 136(sp)
		lw t1, 128(sp)
		lw t0, 136(sp)
		add t0, t1, t0
		sw t0, 140(sp)
		lw t1, 140(sp)
		li t0, 48
		sw t0, 0(t1)
		j .L114.114
.L113.113:
		lw t1, 28(sp)
		li t0, 1
		sb t0, 0(t1)
		j .L117.117
.L114.114:
		j .L115.115
.L115.115:
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 144(sp)
		lw t1, 144(sp)
		li t0, 1
		sub t0, t1, t0
		sw t0, 148(sp)
		lw t1, 32(sp)
		lw t0, 148(sp)
		sw t0, 0(t1)
		j .L110.110
.L116.116:
		lw t1, 28(sp)
		li t0, 0
		sb t0, 0(t1)
		j .L117.117
.L117.117:
		lw t1, 28(sp)
		lb t0, 0(t1)
		sb t0, 152(sp)
		lb t1, 152(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 156
		add sp, sp, t0
		ret

 .text
 .globl array_equal
array_equal:
.L118.118:
		li t0, -140
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
		lw t1, 24(sp)
		sw a1, 0(t1)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 36(sp)
		lw t1, 36(sp)
		mv a0, t1
		call _array_size
		mv t0, a0
		sw t0, 40(sp)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 44(sp)
		lw t1, 44(sp)
		mv a0, t1
		call _array_size
		mv t0, a0
		sw t0, 48(sp)
		lw t1, 40(sp)
		lw t0, 48(sp)
		sub t0, t1, t0
		sw t0, 52(sp)
		lw t1, 52(sp)
		snez t0, t1
		sb t0, 56(sp)
		lb t1, 56(sp)
		beqz t1, .L120.120
		j .L119.119
.L119.119:
		lw t1, 28(sp)
		li t0, 0
		sb t0, 0(t1)
		j .L127.127
.L120.120:
		lw t1, 32(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L121.121
.L121.121:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 60(sp)
		lw t1, 60(sp)
		mv a0, t1
		call _array_size
		mv t0, a0
		sw t0, 64(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 68(sp)
		lw t1, 68(sp)
		lw t0, 64(sp)
		slt t0, t1, t0
		sb t0, 76(sp)
		lb t1, 76(sp)
		beqz t1, .L126.126
		j .L122.122
.L122.122:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 80(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 84(sp)
		lw t1, 84(sp)
		slli t0, t1, 2
		sw t0, 88(sp)
		lw t1, 80(sp)
		lw t0, 88(sp)
		add t0, t1, t0
		sw t0, 92(sp)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 96(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 100(sp)
		lw t1, 100(sp)
		slli t0, t1, 2
		sw t0, 104(sp)
		lw t1, 96(sp)
		lw t0, 104(sp)
		add t0, t1, t0
		sw t0, 108(sp)
		lw t1, 92(sp)
		lw t0, 0(t1)
		sw t0, 112(sp)
		lw t1, 108(sp)
		lw t0, 0(t1)
		sw t0, 116(sp)
		lw t1, 112(sp)
		lw t0, 116(sp)
		sub t0, t1, t0
		sw t0, 120(sp)
		lw t1, 120(sp)
		snez t0, t1
		sb t0, 124(sp)
		lb t1, 124(sp)
		beqz t1, .L124.124
		j .L123.123
.L123.123:
		lw t1, 28(sp)
		li t0, 0
		sb t0, 0(t1)
		j .L127.127
.L124.124:
		j .L125.125
.L125.125:
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 128(sp)
		lw t1, 128(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 132(sp)
		lw t1, 32(sp)
		lw t0, 132(sp)
		sw t0, 0(t1)
		j .L121.121
.L126.126:
		lw t1, 28(sp)
		li t0, 1
		sb t0, 0(t1)
		j .L127.127
.L127.127:
		lw t1, 28(sp)
		lb t0, 0(t1)
		sb t0, 136(sp)
		lb t1, 136(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 140
		add sp, sp, t0
		ret

 .text
 .globl crackSHA1
crackSHA1:
.L128.128:
		li t0, -392
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 28(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 32(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 36(sp)
		li t0, 16
		add t0, sp, t0
		sw t0, 40(sp)
		li t0, 20
		add t0, sp, t0
		sw t0, 44(sp)
		li t0, 24
		add t0, sp, t0
		sw t0, 48(sp)
		lw t1, 28(sp)
		sw a0, 0(t1)
		li t1, 24
		mv a0, t1
		li t1, 5
		mv a1, t1
		call _new_array
		mv t0, a0
		sw t0, 52(sp)
		lw t1, 32(sp)
		lw t0, 52(sp)
		sw t0, 0(t1)
		lw t1, 28(sp)
		lw t0, 0(t1)
		sw t0, 56(sp)
		lw t1, 56(sp)
		mv a0, t1
		call _string_length
		mv t0, a0
		sw t0, 60(sp)
		lw t1, 60(sp)
		li t0, 40
		sub t0, t1, t0
		sw t0, 64(sp)
		lw t1, 64(sp)
		snez t0, t1
		sb t0, 68(sp)
		lb t1, 68(sp)
		beqz t1, .L130.130
		j .L129.129
.L129.129:
		lui t1, %hi(.str.4)
		addi t1, t1, %lo(.str.4)
		mv a0, t1
		call println
		mv t0, a0
		sw t0, 72(sp)
		j .L158.158
.L130.130:
		lw t1, 36(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L131.131
.L131.131:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 76(sp)
		lw t1, 76(sp)
		li t0, 5
		slt t0, t1, t0
		sb t0, 84(sp)
		lb t1, 84(sp)
		beqz t1, .L134.134
		j .L132.132
.L132.132:
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 88(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 92(sp)
		lw t1, 92(sp)
		slli t0, t1, 2
		sw t0, 96(sp)
		lw t1, 88(sp)
		lw t0, 96(sp)
		add t0, t1, t0
		sw t0, 100(sp)
		lw t1, 100(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L133.133
.L133.133:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 104(sp)
		lw t1, 104(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 108(sp)
		lw t1, 36(sp)
		lw t0, 108(sp)
		sw t0, 0(t1)
		j .L131.131
.L134.134:
		lw t1, 36(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L135.135
.L135.135:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 112(sp)
		lw t1, 112(sp)
		li t0, 40
		slt t0, t1, t0
		sb t0, 120(sp)
		lb t1, 120(sp)
		beqz t1, .L138.138
		j .L136.136
.L136.136:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 124(sp)
		lw t1, 124(sp)
		li t0, 8
		div t0, t1, t0
		sw t0, 128(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 132(sp)
		lw t1, 128(sp)
		slli t0, t1, 2
		sw t0, 136(sp)
		lw t1, 132(sp)
		lw t0, 136(sp)
		add t0, t1, t0
		sw t0, 140(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 144(sp)
		lw t1, 144(sp)
		li t0, 8
		div t0, t1, t0
		sw t0, 148(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 152(sp)
		lw t1, 148(sp)
		slli t0, t1, 2
		sw t0, 156(sp)
		lw t1, 152(sp)
		lw t0, 156(sp)
		add t0, t1, t0
		sw t0, 160(sp)
		lw t1, 28(sp)
		lw t0, 0(t1)
		sw t0, 164(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 168(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 172(sp)
		lw t1, 172(sp)
		li t0, 4
		add t0, t1, t0
		sw t0, 176(sp)
		lw t1, 164(sp)
		mv a0, t1
		lw t1, 168(sp)
		mv a1, t1
		lw t1, 176(sp)
		mv a2, t1
		call _string_substring
		mv t0, a0
		sw t0, 180(sp)
		lw t1, 180(sp)
		mv a0, t1
		call hex2int
		mv t0, a0
		sw t0, 184(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 188(sp)
		lw t1, 188(sp)
		li t0, 4
		div t0, t1, t0
		sw t0, 192(sp)
		lw t1, 192(sp)
		li t0, 2
		rem t0, t1, t0
		sw t0, 196(sp)
		li t1, 1
		lw t0, 196(sp)
		sub t0, t1, t0
		sw t0, 200(sp)
		lw t1, 200(sp)
		li t0, 16
		mul t0, t1, t0
		sw t0, 204(sp)
		lw t1, 184(sp)
		lw t0, 204(sp)
		sll t0, t1, t0
		sw t0, 208(sp)
		lw t1, 160(sp)
		lw t0, 0(t1)
		sw t0, 212(sp)
		lw t1, 212(sp)
		lw t0, 208(sp)
		or t0, t1, t0
		sw t0, 216(sp)
		lw t1, 140(sp)
		lw t0, 216(sp)
		sw t0, 0(t1)
		j .L137.137
.L137.137:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 220(sp)
		lw t1, 220(sp)
		li t0, 4
		add t0, t1, t0
		sw t0, 224(sp)
		lw t1, 36(sp)
		lw t0, 224(sp)
		sw t0, 0(t1)
		j .L135.135
.L138.138:
		lw t1, 40(sp)
		li t0, 4
		sw t0, 0(t1)
		lw t1, 44(sp)
		li t0, 1
		sw t0, 0(t1)
		j .L139.139
.L139.139:
		lw t1, 44(sp)
		lw t0, 0(t1)
		sw t0, 228(sp)
		lw t1, 40(sp)
		lw t0, 0(t1)
		sw t0, 232(sp)
		lw t1, 232(sp)
		lw t0, 228(sp)
		slt t0, t1, t0
		sw t0, 236(sp)
		lw t1, 236(sp)
		xori t0, t1, 1
		sb t0, 240(sp)
		lb t1, 240(sp)
		beqz t1, .L157.157
		j .L140.140
.L140.140:
		lw t1, 36(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L141.141
.L141.141:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 244(sp)
		lw t1, 44(sp)
		lw t0, 0(t1)
		sw t0, 248(sp)
		lw t1, 244(sp)
		lw t0, 248(sp)
		slt t0, t1, t0
		sb t0, 256(sp)
		lb t1, 256(sp)
		beqz t1, .L144.144
		j .L142.142
.L142.142:
		lui t1, %hi(inputBuffer)
		addi t1, t1, %lo(inputBuffer)
		lw t0, 0(t1)
		sw t0, 260(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 264(sp)
		lw t1, 264(sp)
		slli t0, t1, 2
		sw t0, 268(sp)
		lw t1, 260(sp)
		lw t0, 268(sp)
		add t0, t1, t0
		sw t0, 272(sp)
		lw t1, 272(sp)
		li t0, 48
		sw t0, 0(t1)
		j .L143.143
.L143.143:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 276(sp)
		lw t1, 276(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 280(sp)
		lw t1, 36(sp)
		lw t0, 280(sp)
		sw t0, 0(t1)
		j .L141.141
.L144.144:
		j .L145.145
.L145.145:
		li t1, 1
		beqz t1, .L155.155
		j .L146.146
.L146.146:
		lui t1, %hi(inputBuffer)
		addi t1, t1, %lo(inputBuffer)
		lw t0, 0(t1)
		sw t0, 284(sp)
		lw t1, 44(sp)
		lw t0, 0(t1)
		sw t0, 288(sp)
		lw t1, 284(sp)
		mv a0, t1
		lw t1, 288(sp)
		mv a1, t1
		call sha1
		mv t0, a0
		sw t0, 292(sp)
		lw t1, 48(sp)
		lw t0, 292(sp)
		sw t0, 0(t1)
		lw t1, 48(sp)
		lw t0, 0(t1)
		sw t0, 296(sp)
		lw t1, 32(sp)
		lw t0, 0(t1)
		sw t0, 300(sp)
		lw t1, 296(sp)
		mv a0, t1
		lw t1, 300(sp)
		mv a1, t1
		call array_equal
		mv t0, a0
		sb t0, 304(sp)
		lb t1, 304(sp)
		beqz t1, .L152.152
		j .L147.147
.L147.147:
		lw t1, 36(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L148.148
.L148.148:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 308(sp)
		lw t1, 44(sp)
		lw t0, 0(t1)
		sw t0, 312(sp)
		lw t1, 308(sp)
		lw t0, 312(sp)
		slt t0, t1, t0
		sb t0, 320(sp)
		lb t1, 320(sp)
		beqz t1, .L151.151
		j .L149.149
.L149.149:
		lui t1, %hi(inputBuffer)
		addi t1, t1, %lo(inputBuffer)
		lw t0, 0(t1)
		sw t0, 324(sp)
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 328(sp)
		lw t1, 328(sp)
		slli t0, t1, 2
		sw t0, 332(sp)
		lw t1, 324(sp)
		lw t0, 332(sp)
		add t0, t1, t0
		sw t0, 336(sp)
		lw t1, 336(sp)
		lw t0, 0(t1)
		sw t0, 340(sp)
		lw t1, 340(sp)
		mv a0, t1
		call int2chr
		mv t0, a0
		sw t0, 344(sp)
		lw t1, 344(sp)
		mv a0, t1
		call print
		mv t0, a0
		sw t0, 348(sp)
		j .L150.150
.L150.150:
		lw t1, 36(sp)
		lw t0, 0(t1)
		sw t0, 352(sp)
		lw t1, 352(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 356(sp)
		lw t1, 36(sp)
		lw t0, 356(sp)
		sw t0, 0(t1)
		j .L148.148
.L151.151:
		lui t1, %hi(.str.2)
		addi t1, t1, %lo(.str.2)
		mv a0, t1
		call println
		mv t0, a0
		sw t0, 360(sp)
		j .L158.158
.L152.152:
		lui t1, %hi(inputBuffer)
		addi t1, t1, %lo(inputBuffer)
		lw t0, 0(t1)
		sw t0, 364(sp)
		lw t1, 44(sp)
		lw t0, 0(t1)
		sw t0, 368(sp)
		lw t1, 364(sp)
		mv a0, t1
		lw t1, 368(sp)
		mv a1, t1
		call nextText
		mv t0, a0
		sb t0, 372(sp)
		lb t1, 372(sp)
		li t0, 1
		xor t0, t1, t0
		sb t0, 376(sp)
		lb t1, 376(sp)
		beqz t1, .L154.154
		j .L153.153
.L153.153:
		j .L155.155
.L154.154:
		j .L145.145
.L155.155:
		j .L156.156
.L156.156:
		lw t1, 44(sp)
		lw t0, 0(t1)
		sw t0, 380(sp)
		lw t1, 380(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 384(sp)
		lw t1, 44(sp)
		lw t0, 384(sp)
		sw t0, 0(t1)
		j .L139.139
.L157.157:
		lui t1, %hi(.str.5)
		addi t1, t1, %lo(.str.5)
		mv a0, t1
		call println
		mv t0, a0
		sw t0, 388(sp)
		j .L158.158
.L158.158:
		lw ra, 0(sp)
		li t0, 392
		add sp, sp, t0
		ret

 .text
 .globl main
main:
.L159.159:
		li t0, -96
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 16(sp)
		li t0, 8
		add t0, sp, t0
		sw t0, 20(sp)
		li t0, 12
		add t0, sp, t0
		sw t0, 24(sp)
		lw t1, 16(sp)
		li t0, 0
		sw t0, 0(t1)
		call _global_var_init
		j .L160.160
.L160.160:
		li t1, 1
		beqz t1, .L169.169
		j .L161.161
.L161.161:
		call getInt
		mv t0, a0
		sw t0, 28(sp)
		lw t1, 20(sp)
		lw t0, 28(sp)
		sw t0, 0(t1)
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 32(sp)
		lw t1, 32(sp)
		li t0, 0
		sub t0, t1, t0
		sw t0, 36(sp)
		lw t1, 36(sp)
		seqz t0, t1
		sb t0, 40(sp)
		lb t1, 40(sp)
		beqz t1, .L163.163
		j .L162.162
.L162.162:
		j .L169.169
.L163.163:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 44(sp)
		lw t1, 44(sp)
		li t0, 1
		sub t0, t1, t0
		sw t0, 48(sp)
		lw t1, 48(sp)
		seqz t0, t1
		sb t0, 52(sp)
		lb t1, 52(sp)
		beqz t1, .L165.165
		j .L164.164
.L164.164:
		call getString
		mv t0, a0
		sw t0, 56(sp)
		lw t1, 24(sp)
		lw t0, 56(sp)
		sw t0, 0(t1)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 60(sp)
		lw t1, 60(sp)
		mv a0, t1
		call computeSHA1
		mv t0, a0
		sw t0, 64(sp)
		j .L168.168
.L165.165:
		lw t1, 20(sp)
		lw t0, 0(t1)
		sw t0, 68(sp)
		lw t1, 68(sp)
		li t0, 2
		sub t0, t1, t0
		sw t0, 72(sp)
		lw t1, 72(sp)
		seqz t0, t1
		sb t0, 76(sp)
		lb t1, 76(sp)
		beqz t1, .L167.167
		j .L166.166
.L166.166:
		call getString
		mv t0, a0
		sw t0, 80(sp)
		lw t1, 24(sp)
		lw t0, 80(sp)
		sw t0, 0(t1)
		lw t1, 24(sp)
		lw t0, 0(t1)
		sw t0, 84(sp)
		lw t1, 84(sp)
		mv a0, t1
		call crackSHA1
		mv t0, a0
		sw t0, 88(sp)
		j .L167.167
.L167.167:
		j .L168.168
.L168.168:
		j .L160.160
.L169.169:
		lw t1, 16(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L170.170
.L170.170:
		lw t1, 16(sp)
		lw t0, 0(t1)
		sw t0, 92(sp)
		lw t1, 92(sp)
		mv a0, t1
		lw ra, 0(sp)
		li t0, 96
		add sp, sp, t0
		ret

 .text
 .globl _global_var_init
_global_var_init:
.L171.171:
		li t0, -100
		add sp, sp, t0
		sw ra, 0(sp)
		li t0, 4
		add t0, sp, t0
		sw t0, 8(sp)
		lui t1, %hi(MAXCHUNK)
		addi t1, t1, %lo(MAXCHUNK)
		lw t0, 0(t1)
		sw t0, 12(sp)
		lw t1, 12(sp)
		li t0, 1
		sub t0, t1, t0
		sw t0, 16(sp)
		lw t1, 16(sp)
		li t0, 64
		mul t0, t1, t0
		sw t0, 20(sp)
		lw t1, 20(sp)
		li t0, 16
		sub t0, t1, t0
		sw t0, 24(sp)
		lui t1, %hi(MAXLENGTH)
		addi t1, t1, %lo(MAXLENGTH)
		lw t0, 24(sp)
		sw t0, 0(t1)
		lui t1, %hi(MAXCHUNK)
		addi t1, t1, %lo(MAXCHUNK)
		lw t0, 0(t1)
		sw t0, 28(sp)
		lw t1, 28(sp)
		li t0, 4
		mul t0, t1, t0
		sw t0, 32(sp)
		lw t1, 32(sp)
		li t0, 4
		add t0, t1, t0
		sw t0, 36(sp)
		lw t1, 36(sp)
		mv a0, t1
		lw t1, 28(sp)
		mv a1, t1
		call _new_array
		mv t0, a0
		sw t0, 40(sp)
		lw t1, 8(sp)
		li t0, 0
		sw t0, 0(t1)
		j .L172.172
.L172.172:
		lw t1, 8(sp)
		lw t0, 0(t1)
		sw t0, 44(sp)
		lw t1, 44(sp)
		lw t0, 28(sp)
		slt t0, t1, t0
		sb t0, 52(sp)
		lb t1, 52(sp)
		beqz t1, .L175.175
		j .L173.173
.L173.173:
		li t1, 324
		mv a0, t1
		li t1, 80
		mv a1, t1
		call _new_array
		mv t0, a0
		sw t0, 56(sp)
		lw t1, 8(sp)
		lw t0, 0(t1)
		sw t0, 60(sp)
		lw t1, 60(sp)
		slli t0, t1, 2
		sw t0, 64(sp)
		lw t1, 40(sp)
		lw t0, 64(sp)
		add t0, t1, t0
		sw t0, 68(sp)
		lw t1, 68(sp)
		lw t0, 56(sp)
		sw t0, 0(t1)
		j .L174.174
.L174.174:
		lw t1, 8(sp)
		lw t0, 0(t1)
		sw t0, 72(sp)
		lw t1, 72(sp)
		li t0, 1
		add t0, t1, t0
		sw t0, 76(sp)
		lw t1, 8(sp)
		lw t0, 76(sp)
		sw t0, 0(t1)
		j .L172.172
.L175.175:
		lui t1, %hi(chunks)
		addi t1, t1, %lo(chunks)
		lw t0, 40(sp)
		sw t0, 0(t1)
		lui t1, %hi(MAXLENGTH)
		addi t1, t1, %lo(MAXLENGTH)
		lw t0, 0(t1)
		sw t0, 80(sp)
		lw t1, 80(sp)
		li t0, 4
		mul t0, t1, t0
		sw t0, 84(sp)
		lw t1, 84(sp)
		li t0, 4
		add t0, t1, t0
		sw t0, 88(sp)
		lw t1, 88(sp)
		mv a0, t1
		lw t1, 80(sp)
		mv a1, t1
		call _new_array
		mv t0, a0
		sw t0, 92(sp)
		lui t1, %hi(inputBuffer)
		addi t1, t1, %lo(inputBuffer)
		lw t0, 92(sp)
		sw t0, 0(t1)
		li t1, 24
		mv a0, t1
		li t1, 5
		mv a1, t1
		call _new_array
		mv t0, a0
		sw t0, 96(sp)
		lui t1, %hi(outputBuffer)
		addi t1, t1, %lo(outputBuffer)
		lw t0, 96(sp)
		sw t0, 0(t1)
		j .L176.176
.L176.176:
		lw ra, 0(sp)
		li t0, 100
		add sp, sp, t0
		ret

