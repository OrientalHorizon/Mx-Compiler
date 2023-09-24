@str.1 = private unnamed_addr constant [96 x i8] c" !\22#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\00"
@str.2 = private unnamed_addr constant [1 x i8] c"\00"
@str.3 = private unnamed_addr constant [19 x i8] c"nChunk > MAXCHUNK!\00"
@str.4 = private unnamed_addr constant [14 x i8] c"Invalid input\00"
@str.5 = private unnamed_addr constant [11 x i8] c"Not Found!\00"

declare void @print(ptr)
declare void @println(ptr)
declare void @printInt(i32)
declare void @printlnInt(i32)
declare ptr @getString()
declare i32 @getInt()
declare ptr @toString(i32)
declare i32 @_string_length(ptr)
declare ptr @_string_substring(ptr, i32, i32)
declare i32 @_string_parseInt(ptr)
declare i32 @_string_ord(ptr, i32)
declare ptr @_string_add(ptr, ptr)
declare i1 @_string_eq(ptr, ptr)
declare i1 @_string_ne(ptr, ptr)
declare i1 @_string_lt(ptr, ptr)
declare i1 @_string_le(ptr, ptr)
declare i1 @_string_gt(ptr, ptr)
declare i1 @_string_ge(ptr, ptr)
declare i32 @_array_size(ptr)
declare ptr @_new_array(i32, i32)
declare ptr @malloc(i32)

@asciiTable = dso_local global ptr @str.1
@MAXCHUNK = dso_local global i32 100
@MAXLENGTH = dso_local global i32 0
@chunks = dso_local global ptr null
@inputBuffer = dso_local global ptr null
@outputBuffer = dso_local global ptr null

define i32 @hex2int(ptr %x_8) {
hex2int_entry_5:
		%x_60 = alloca ptr
		%hex2int_ret_7 = alloca i32
		%i_62 = alloca i32
		%result_63 = alloca i32
		%digit_68 = alloca i32
		%tmp_72 = alloca i1
		%tmp_83 = alloca i1
		%tmp_95 = alloca i1
		store ptr %x_8, ptr %x_60
		store i32 0, ptr %result_63
		store i32 0, ptr %i_62
  br label %for_cond_37
for_cond_37:
		%tmp_65 = load ptr, ptr %x_60
		%tmp_func_call_64 = call i32 @_string_length(ptr %tmp_65)
		%tmp_66 = load i32, ptr %i_62
		%tmp_binary_67 = icmp slt i32 %tmp_66, %tmp_func_call_64
  br i1 %tmp_binary_67, label %for_body_38, label %for_end_40
for_body_38:
		%tmp_70 = load ptr, ptr %x_60
		%tmp_71 = load i32, ptr %i_62
		%tmp_func_call_69 = call i32 @_string_ord(ptr %tmp_70, i32 %tmp_71)
		store i32 %tmp_func_call_69, ptr %digit_68
		%tmp_73 = load i32, ptr %digit_68
		%tmp_binary_74 = icmp sge i32 %tmp_73, 48
  br i1 %tmp_binary_74, label %logic_right_side_41, label %logic_false_43
logic_right_side_41:
		%tmp_75 = load i32, ptr %digit_68
		%tmp_binary_76 = icmp sle i32 %tmp_75, 57
  br i1 %tmp_binary_76, label %logic_true_42, label %logic_false_43
logic_true_42:
		store i1 true, ptr %tmp_72
  br label %logic_end_44
logic_false_43:
		store i1 false, ptr %tmp_72
  br label %logic_end_44
logic_end_44:
		%_77 = load i1, ptr %tmp_72
  br i1 %_77, label %if_then_45, label %if_else_47
if_then_45:
		%tmp_78 = load i32, ptr %result_63
		%tmp_calc_79 = mul i32 %tmp_78, 16
		%tmp_80 = load i32, ptr %digit_68
		%tmp_calc_81 = add i32 %tmp_calc_79, %tmp_80
		%tmp_calc_82 = sub i32 %tmp_calc_81, 48
		store i32 %tmp_calc_82, ptr %result_63
  br label %if_end_46
if_else_47:
		%tmp_84 = load i32, ptr %digit_68
		%tmp_binary_85 = icmp sge i32 %tmp_84, 65
  br i1 %tmp_binary_85, label %logic_right_side_48, label %logic_false_50
logic_right_side_48:
		%tmp_86 = load i32, ptr %digit_68
		%tmp_binary_87 = icmp sle i32 %tmp_86, 70
  br i1 %tmp_binary_87, label %logic_true_49, label %logic_false_50
logic_true_49:
		store i1 true, ptr %tmp_83
  br label %logic_end_51
logic_false_50:
		store i1 false, ptr %tmp_83
  br label %logic_end_51
logic_end_51:
		%_88 = load i1, ptr %tmp_83
  br i1 %_88, label %if_then_52, label %if_else_54
if_then_52:
		%tmp_89 = load i32, ptr %result_63
		%tmp_calc_90 = mul i32 %tmp_89, 16
		%tmp_91 = load i32, ptr %digit_68
		%tmp_calc_92 = add i32 %tmp_calc_90, %tmp_91
		%tmp_calc_93 = sub i32 %tmp_calc_92, 65
		%tmp_calc_94 = add i32 %tmp_calc_93, 10
		store i32 %tmp_calc_94, ptr %result_63
  br label %if_end_53
if_else_54:
		%tmp_96 = load i32, ptr %digit_68
		%tmp_binary_97 = icmp sge i32 %tmp_96, 97
  br i1 %tmp_binary_97, label %logic_right_side_55, label %logic_false_57
logic_right_side_55:
		%tmp_98 = load i32, ptr %digit_68
		%tmp_binary_99 = icmp sle i32 %tmp_98, 102
  br i1 %tmp_binary_99, label %logic_true_56, label %logic_false_57
logic_true_56:
		store i1 true, ptr %tmp_95
  br label %logic_end_58
logic_false_57:
		store i1 false, ptr %tmp_95
  br label %logic_end_58
logic_end_58:
		%_100 = load i1, ptr %tmp_95
  br i1 %_100, label %if_then_59, label %if_else_61
if_then_59:
		%tmp_101 = load i32, ptr %result_63
		%tmp_calc_102 = mul i32 %tmp_101, 16
		%tmp_103 = load i32, ptr %digit_68
		%tmp_calc_104 = add i32 %tmp_calc_102, %tmp_103
		%tmp_calc_105 = sub i32 %tmp_calc_104, 97
		%tmp_calc_106 = add i32 %tmp_calc_105, 10
		store i32 %tmp_calc_106, ptr %result_63
  br label %if_end_60
if_else_61:
		store i32 0, ptr %hex2int_ret_7
  br label %hex2int_exit_6
if_end_60:
  br label %if_end_53
if_end_53:
  br label %if_end_46
if_end_46:
  br label %for_step_39
for_step_39:
		%tmp_107 = load i32, ptr %i_62
		%tmp_suffix_108 = add i32 %tmp_107, 1
		store i32 %tmp_suffix_108, ptr %i_62
  br label %for_cond_37
for_end_40:
		%tmp_109 = load i32, ptr %result_63
		store i32 %tmp_109, ptr %hex2int_ret_7
  br label %hex2int_exit_6
hex2int_exit_6:
		%ret_61 = load i32, ptr %hex2int_ret_7
  ret i32 %ret_61
}


define ptr @int2chr(i32 %x_10) {
int2chr_entry_7:
		%x_110 = alloca i32
		%int2chr_ret_9 = alloca ptr
		%tmp_112 = alloca i1
		store i32 %x_10, ptr %x_110
		%tmp_113 = load i32, ptr %x_110
		%tmp_binary_114 = icmp sge i32 %tmp_113, 32
  br i1 %tmp_binary_114, label %logic_right_side_62, label %logic_false_64
logic_right_side_62:
		%tmp_115 = load i32, ptr %x_110
		%tmp_binary_116 = icmp sle i32 %tmp_115, 126
  br i1 %tmp_binary_116, label %logic_true_63, label %logic_false_64
logic_true_63:
		store i1 true, ptr %tmp_112
  br label %logic_end_65
logic_false_64:
		store i1 false, ptr %tmp_112
  br label %logic_end_65
logic_end_65:
		%_117 = load i1, ptr %tmp_112
  br i1 %_117, label %if_then_66, label %if_end_67
if_then_66:
		%tmp_119 = load ptr, ptr @asciiTable
		%tmp_120 = load i32, ptr %x_110
		%tmp_calc_121 = sub i32 %tmp_120, 32
		%tmp_122 = load i32, ptr %x_110
		%tmp_calc_123 = sub i32 %tmp_122, 31
		%tmp_func_call_118 = call ptr @_string_substring(ptr %tmp_119, i32 %tmp_calc_121, i32 %tmp_calc_123)
		store ptr %tmp_func_call_118, ptr %int2chr_ret_9
  br label %int2chr_exit_8
if_end_67:
		store [1 x i8]* @str.2, ptr %int2chr_ret_9
  br label %int2chr_exit_8
int2chr_exit_8:
		%ret_111 = load ptr, ptr %int2chr_ret_9
  ret ptr %ret_111
}


define ptr @toStringHex(i32 %x_12) {
toStringHex_entry_9:
		%x_124 = alloca i32
		%toStringHex_ret_11 = alloca ptr
		%ret_126 = alloca ptr
		%i_127 = alloca i32
		%digit_130 = alloca i32
		store i32 %x_12, ptr %x_124
		store [1 x i8]* @str.2, ptr %ret_126
		store i32 28, ptr %i_127
  br label %for_cond_69
for_cond_69:
		%tmp_128 = load i32, ptr %i_127
		%tmp_binary_129 = icmp sge i32 %tmp_128, 0
  br i1 %tmp_binary_129, label %for_body_70, label %for_end_72
for_body_70:
		%tmp_131 = load i32, ptr %x_124
		%tmp_132 = load i32, ptr %i_127
		%tmp_calc_133 = ashr i32 %tmp_131, %tmp_132
		%tmp_calc_134 = and i32 %tmp_calc_133, 15
		store i32 %tmp_calc_134, ptr %digit_130
		%tmp_135 = load i32, ptr %digit_130
		%tmp_binary_136 = icmp slt i32 %tmp_135, 10
  br i1 %tmp_binary_136, label %if_then_73, label %if_else_75
if_then_73:
		%tmp_138 = load i32, ptr %digit_130
		%tmp_calc_139 = add i32 48, %tmp_138
		%tmp_func_call_137 = call ptr @int2chr(i32 %tmp_calc_139)
		%tmp_140 = load ptr, ptr %ret_126
		%tmp_string_141 = call ptr @_string_add(ptr %tmp_140, ptr %tmp_func_call_137)
		store ptr %tmp_string_141, ptr %ret_126
  br label %if_end_74
if_else_75:
		%tmp_143 = load i32, ptr %digit_130
		%tmp_calc_144 = add i32 65, %tmp_143
		%tmp_calc_145 = sub i32 %tmp_calc_144, 10
		%tmp_func_call_142 = call ptr @int2chr(i32 %tmp_calc_145)
		%tmp_146 = load ptr, ptr %ret_126
		%tmp_string_147 = call ptr @_string_add(ptr %tmp_146, ptr %tmp_func_call_142)
		store ptr %tmp_string_147, ptr %ret_126
  br label %if_end_74
if_end_74:
  br label %for_step_71
for_step_71:
		%tmp_148 = load i32, ptr %i_127
		%tmp_calc_149 = sub i32 %tmp_148, 4
		store i32 %tmp_calc_149, ptr %i_127
  br label %for_cond_69
for_end_72:
		%tmp_150 = load ptr, ptr %ret_126
		store ptr %tmp_150, ptr %toStringHex_ret_11
  br label %toStringHex_exit_10
toStringHex_exit_10:
		%ret_125 = load ptr, ptr %toStringHex_ret_11
  ret ptr %ret_125
}


define i32 @rotate_left(i32 %x_14, i32 %shift_15) {
rotate_left_entry_11:
		%x_151 = alloca i32
		%shift_152 = alloca i32
		%rotate_left_ret_13 = alloca i32
		store i32 %x_14, ptr %x_151
		store i32 %shift_15, ptr %shift_152
		%tmp_154 = load i32, ptr %shift_152
		%tmp_155 = icmp eq i32 %tmp_154, 1
  br i1 %tmp_155, label %if_then_76, label %if_end_77
if_then_76:
		%tmp_156 = load i32, ptr %x_151
		%tmp_calc_157 = and i32 %tmp_156, 2147483647
		%tmp_calc_158 = shl i32 %tmp_calc_157, 1
		%tmp_159 = load i32, ptr %x_151
		%tmp_calc_160 = ashr i32 %tmp_159, 31
		%tmp_calc_161 = and i32 %tmp_calc_160, 1
		%tmp_calc_162 = or i32 %tmp_calc_158, %tmp_calc_161
		store i32 %tmp_calc_162, ptr %rotate_left_ret_13
  br label %rotate_left_exit_12
if_end_77:
		%tmp_163 = load i32, ptr %shift_152
		%tmp_164 = icmp eq i32 %tmp_163, 31
  br i1 %tmp_164, label %if_then_79, label %if_end_80
if_then_79:
		%tmp_165 = load i32, ptr %x_151
		%tmp_calc_166 = and i32 %tmp_165, 1
		%tmp_calc_167 = shl i32 %tmp_calc_166, 31
		%tmp_168 = load i32, ptr %x_151
		%tmp_calc_169 = ashr i32 %tmp_168, 1
		%tmp_calc_170 = and i32 %tmp_calc_169, 2147483647
		%tmp_calc_171 = or i32 %tmp_calc_167, %tmp_calc_170
		store i32 %tmp_calc_171, ptr %rotate_left_ret_13
  br label %rotate_left_exit_12
if_end_80:
		%tmp_172 = load i32, ptr %shift_152
		%tmp_calc_173 = sub i32 32, %tmp_172
		%tmp_calc_174 = shl i32 1, %tmp_calc_173
		%tmp_calc_175 = sub i32 %tmp_calc_174, 1
		%tmp_176 = load i32, ptr %x_151
		%tmp_calc_177 = and i32 %tmp_176, %tmp_calc_175
		%tmp_178 = load i32, ptr %shift_152
		%tmp_calc_179 = shl i32 %tmp_calc_177, %tmp_178
		%tmp_180 = load i32, ptr %shift_152
		%tmp_calc_181 = sub i32 32, %tmp_180
		%tmp_182 = load i32, ptr %x_151
		%tmp_calc_183 = ashr i32 %tmp_182, %tmp_calc_181
		%tmp_184 = load i32, ptr %shift_152
		%tmp_calc_185 = shl i32 1, %tmp_184
		%tmp_calc_186 = sub i32 %tmp_calc_185, 1
		%tmp_calc_187 = and i32 %tmp_calc_183, %tmp_calc_186
		%tmp_calc_188 = or i32 %tmp_calc_179, %tmp_calc_187
		store i32 %tmp_calc_188, ptr %rotate_left_ret_13
  br label %rotate_left_exit_12
rotate_left_exit_12:
		%ret_153 = load i32, ptr %rotate_left_ret_13
  ret i32 %ret_153
}


define i32 @add(i32 %x_17, i32 %y_18) {
add_entry_13:
		%x_189 = alloca i32
		%y_190 = alloca i32
		%add_ret_16 = alloca i32
		%low_192 = alloca i32
		%high_198 = alloca i32
		store i32 %x_17, ptr %x_189
		store i32 %y_18, ptr %y_190
		%tmp_193 = load i32, ptr %x_189
		%tmp_calc_194 = and i32 %tmp_193, 65535
		%tmp_195 = load i32, ptr %y_190
		%tmp_calc_196 = and i32 %tmp_195, 65535
		%tmp_calc_197 = add i32 %tmp_calc_194, %tmp_calc_196
		store i32 %tmp_calc_197, ptr %low_192
		%tmp_199 = load i32, ptr %x_189
		%tmp_calc_200 = ashr i32 %tmp_199, 16
		%tmp_calc_201 = and i32 %tmp_calc_200, 65535
		%tmp_202 = load i32, ptr %y_190
		%tmp_calc_203 = ashr i32 %tmp_202, 16
		%tmp_calc_204 = and i32 %tmp_calc_203, 65535
		%tmp_calc_205 = add i32 %tmp_calc_201, %tmp_calc_204
		%tmp_206 = load i32, ptr %low_192
		%tmp_calc_207 = ashr i32 %tmp_206, 16
		%tmp_calc_208 = add i32 %tmp_calc_205, %tmp_calc_207
		%tmp_calc_209 = and i32 %tmp_calc_208, 65535
		store i32 %tmp_calc_209, ptr %high_198
		%tmp_210 = load i32, ptr %high_198
		%tmp_calc_211 = shl i32 %tmp_210, 16
		%tmp_212 = load i32, ptr %low_192
		%tmp_calc_213 = and i32 %tmp_212, 65535
		%tmp_calc_214 = or i32 %tmp_calc_211, %tmp_calc_213
		store i32 %tmp_calc_214, ptr %add_ret_16
  br label %add_exit_14
add_exit_14:
		%ret_191 = load i32, ptr %add_ret_16
  ret i32 %ret_191
}


define i32 @lohi(i32 %lo_20, i32 %hi_21) {
lohi_entry_15:
		%lo_215 = alloca i32
		%hi_216 = alloca i32
		%lohi_ret_19 = alloca i32
		store i32 %lo_20, ptr %lo_215
		store i32 %hi_21, ptr %hi_216
		%tmp_218 = load i32, ptr %hi_216
		%tmp_calc_219 = shl i32 %tmp_218, 16
		%tmp_220 = load i32, ptr %lo_215
		%tmp_calc_221 = or i32 %tmp_220, %tmp_calc_219
		store i32 %tmp_calc_221, ptr %lohi_ret_19
  br label %lohi_exit_16
lohi_exit_16:
		%ret_217 = load i32, ptr %lohi_ret_19
  ret i32 %ret_217
}


define ptr @sha1(ptr %input_23, i32 %length_24) {
sha1_entry_17:
		%input_222 = alloca ptr
		%length_223 = alloca i32
		%sha1_ret_22 = alloca ptr
		%nChunk_225 = alloca i32
		%i_235 = alloca i32
		%j_236 = alloca i32
		%h0_328 = alloca i32
		%h1_329 = alloca i32
		%h2_331 = alloca i32
		%h3_333 = alloca i32
		%h4_334 = alloca i32
		%a_385 = alloca i32
		%b_387 = alloca i32
		%c_389 = alloca i32
		%d_391 = alloca i32
		%e_393 = alloca i32
		%f_397 = alloca i32
		%k_398 = alloca i32
		%temp_436 = alloca i32
		store ptr %input_23, ptr %input_222
		store i32 %length_24, ptr %length_223
		%tmp_226 = load i32, ptr %length_223
		%tmp_calc_227 = add i32 %tmp_226, 64
		%tmp_calc_228 = sub i32 %tmp_calc_227, 56
		%tmp_calc_229 = sdiv i32 %tmp_calc_228, 64
		%tmp_calc_230 = add i32 %tmp_calc_229, 1
		store i32 %tmp_calc_230, ptr %nChunk_225
		%tmp_231 = load i32, ptr %nChunk_225
		%tmp_232 = load i32, ptr @MAXCHUNK
		%tmp_binary_233 = icmp sgt i32 %tmp_231, %tmp_232
  br i1 %tmp_binary_233, label %if_then_82, label %if_end_83
if_then_82:
		call void @println([19 x i8]* @str.3)
		store ptr null, ptr %sha1_ret_22
  br label %sha1_exit_18
if_end_83:
		store i32 0, ptr %i_235
  br label %for_cond_85
for_cond_85:
		%tmp_237 = load i32, ptr %i_235
		%tmp_238 = load i32, ptr %nChunk_225
		%tmp_binary_239 = icmp slt i32 %tmp_237, %tmp_238
  br i1 %tmp_binary_239, label %for_body_86, label %for_end_88
for_body_86:
		store i32 0, ptr %j_236
  br label %for_cond_89
for_cond_89:
		%tmp_240 = load i32, ptr %j_236
		%tmp_binary_241 = icmp slt i32 %tmp_240, 80
  br i1 %tmp_binary_241, label %for_body_90, label %for_end_92
for_body_90:
		%tmp_242 = load ptr, ptr @chunks
		%tmp_244 = load i32, ptr %i_235
		%tmp_subscript_243 = getelementptr ptr, ptr %tmp_242, i32 %tmp_244
		%tmp_245 = load ptr, ptr %tmp_subscript_243
		%tmp_247 = load i32, ptr %j_236
		%tmp_subscript_246 = getelementptr i32, ptr %tmp_245, i32 %tmp_247
		store i32 0, ptr %tmp_subscript_246
  br label %for_step_91
for_step_91:
		%tmp_248 = load i32, ptr %j_236
		%tmp_suffix_249 = add i32 %tmp_248, 1
		store i32 %tmp_suffix_249, ptr %j_236
  br label %for_cond_89
for_end_92:
  br label %for_step_87
for_step_87:
		%tmp_250 = load i32, ptr %i_235
		%tmp_suffix_251 = add i32 %tmp_250, 1
		store i32 %tmp_suffix_251, ptr %i_235
  br label %for_cond_85
for_end_88:
		store i32 0, ptr %i_235
  br label %for_cond_93
for_cond_93:
		%tmp_252 = load i32, ptr %i_235
		%tmp_253 = load i32, ptr %length_223
		%tmp_binary_254 = icmp slt i32 %tmp_252, %tmp_253
  br i1 %tmp_binary_254, label %for_body_94, label %for_end_96
for_body_94:
		%tmp_255 = load i32, ptr %i_235
		%tmp_calc_256 = sdiv i32 %tmp_255, 64
		%tmp_257 = load ptr, ptr @chunks
		%tmp_subscript_258 = getelementptr ptr, ptr %tmp_257, i32 %tmp_calc_256
		%tmp_259 = load i32, ptr %i_235
		%tmp_calc_260 = srem i32 %tmp_259, 64
		%tmp_calc_261 = sdiv i32 %tmp_calc_260, 4
		%tmp_262 = load ptr, ptr %tmp_subscript_258
		%tmp_subscript_263 = getelementptr i32, ptr %tmp_262, i32 %tmp_calc_261
		%tmp_264 = load i32, ptr %i_235
		%tmp_calc_265 = sdiv i32 %tmp_264, 64
		%tmp_266 = load ptr, ptr @chunks
		%tmp_subscript_267 = getelementptr ptr, ptr %tmp_266, i32 %tmp_calc_265
		%tmp_268 = load i32, ptr %i_235
		%tmp_calc_269 = srem i32 %tmp_268, 64
		%tmp_calc_270 = sdiv i32 %tmp_calc_269, 4
		%tmp_271 = load ptr, ptr %tmp_subscript_267
		%tmp_subscript_272 = getelementptr i32, ptr %tmp_271, i32 %tmp_calc_270
		%tmp_273 = load ptr, ptr %input_222
		%tmp_275 = load i32, ptr %i_235
		%tmp_subscript_274 = getelementptr i32, ptr %tmp_273, i32 %tmp_275
		%tmp_276 = load i32, ptr %i_235
		%tmp_calc_277 = srem i32 %tmp_276, 4
		%tmp_calc_278 = sub i32 3, %tmp_calc_277
		%tmp_calc_279 = mul i32 %tmp_calc_278, 8
		%tmp_280 = load i32, ptr %tmp_subscript_274
		%tmp_calc_281 = shl i32 %tmp_280, %tmp_calc_279
		%tmp_282 = load i32, ptr %tmp_subscript_272
		%tmp_calc_283 = or i32 %tmp_282, %tmp_calc_281
		store i32 %tmp_calc_283, ptr %tmp_subscript_263
  br label %for_step_95
for_step_95:
		%tmp_284 = load i32, ptr %i_235
		%tmp_suffix_285 = add i32 %tmp_284, 1
		store i32 %tmp_suffix_285, ptr %i_235
  br label %for_cond_93
for_end_96:
		%tmp_286 = load i32, ptr %i_235
		%tmp_calc_287 = sdiv i32 %tmp_286, 64
		%tmp_288 = load ptr, ptr @chunks
		%tmp_subscript_289 = getelementptr ptr, ptr %tmp_288, i32 %tmp_calc_287
		%tmp_290 = load i32, ptr %i_235
		%tmp_calc_291 = srem i32 %tmp_290, 64
		%tmp_calc_292 = sdiv i32 %tmp_calc_291, 4
		%tmp_293 = load ptr, ptr %tmp_subscript_289
		%tmp_subscript_294 = getelementptr i32, ptr %tmp_293, i32 %tmp_calc_292
		%tmp_295 = load i32, ptr %i_235
		%tmp_calc_296 = sdiv i32 %tmp_295, 64
		%tmp_297 = load ptr, ptr @chunks
		%tmp_subscript_298 = getelementptr ptr, ptr %tmp_297, i32 %tmp_calc_296
		%tmp_299 = load i32, ptr %i_235
		%tmp_calc_300 = srem i32 %tmp_299, 64
		%tmp_calc_301 = sdiv i32 %tmp_calc_300, 4
		%tmp_302 = load ptr, ptr %tmp_subscript_298
		%tmp_subscript_303 = getelementptr i32, ptr %tmp_302, i32 %tmp_calc_301
		%tmp_304 = load i32, ptr %i_235
		%tmp_calc_305 = srem i32 %tmp_304, 4
		%tmp_calc_306 = sub i32 3, %tmp_calc_305
		%tmp_calc_307 = mul i32 %tmp_calc_306, 8
		%tmp_calc_308 = shl i32 128, %tmp_calc_307
		%tmp_309 = load i32, ptr %tmp_subscript_303
		%tmp_calc_310 = or i32 %tmp_309, %tmp_calc_308
		store i32 %tmp_calc_310, ptr %tmp_subscript_294
		%tmp_311 = load i32, ptr %nChunk_225
		%tmp_calc_312 = sub i32 %tmp_311, 1
		%tmp_313 = load ptr, ptr @chunks
		%tmp_subscript_314 = getelementptr ptr, ptr %tmp_313, i32 %tmp_calc_312
		%tmp_315 = load ptr, ptr %tmp_subscript_314
		%tmp_subscript_316 = getelementptr i32, ptr %tmp_315, i32 15
		%tmp_317 = load i32, ptr %length_223
		%tmp_calc_318 = shl i32 %tmp_317, 3
		store i32 %tmp_calc_318, ptr %tmp_subscript_316
		%tmp_319 = load i32, ptr %nChunk_225
		%tmp_calc_320 = sub i32 %tmp_319, 1
		%tmp_321 = load ptr, ptr @chunks
		%tmp_subscript_322 = getelementptr ptr, ptr %tmp_321, i32 %tmp_calc_320
		%tmp_323 = load ptr, ptr %tmp_subscript_322
		%tmp_subscript_324 = getelementptr i32, ptr %tmp_323, i32 14
		%tmp_325 = load i32, ptr %length_223
		%tmp_calc_326 = ashr i32 %tmp_325, 29
		%tmp_calc_327 = and i32 %tmp_calc_326, 7
		store i32 %tmp_calc_327, ptr %tmp_subscript_324
		store i32 1732584193, ptr %h0_328
		%tmp_func_call_330 = call i32 @lohi(i32 43913, i32 61389)
		store i32 %tmp_func_call_330, ptr %h1_329
		%tmp_func_call_332 = call i32 @lohi(i32 56574, i32 39098)
		store i32 %tmp_func_call_332, ptr %h2_331
		store i32 271733878, ptr %h3_333
		%tmp_func_call_335 = call i32 @lohi(i32 57840, i32 50130)
		store i32 %tmp_func_call_335, ptr %h4_334
		store i32 0, ptr %i_235
  br label %for_cond_97
for_cond_97:
		%tmp_336 = load i32, ptr %i_235
		%tmp_337 = load i32, ptr %nChunk_225
		%tmp_binary_338 = icmp slt i32 %tmp_336, %tmp_337
  br i1 %tmp_binary_338, label %for_body_98, label %for_end_100
for_body_98:
		store i32 16, ptr %j_236
  br label %for_cond_101
for_cond_101:
		%tmp_339 = load i32, ptr %j_236
		%tmp_binary_340 = icmp slt i32 %tmp_339, 80
  br i1 %tmp_binary_340, label %for_body_102, label %for_end_104
for_body_102:
		%tmp_341 = load ptr, ptr @chunks
		%tmp_343 = load i32, ptr %i_235
		%tmp_subscript_342 = getelementptr ptr, ptr %tmp_341, i32 %tmp_343
		%tmp_344 = load ptr, ptr %tmp_subscript_342
		%tmp_346 = load i32, ptr %j_236
		%tmp_subscript_345 = getelementptr i32, ptr %tmp_344, i32 %tmp_346
		%tmp_348 = load ptr, ptr @chunks
		%tmp_350 = load i32, ptr %i_235
		%tmp_subscript_349 = getelementptr ptr, ptr %tmp_348, i32 %tmp_350
		%tmp_351 = load i32, ptr %j_236
		%tmp_calc_352 = sub i32 %tmp_351, 3
		%tmp_353 = load ptr, ptr %tmp_subscript_349
		%tmp_subscript_354 = getelementptr i32, ptr %tmp_353, i32 %tmp_calc_352
		%tmp_355 = load ptr, ptr @chunks
		%tmp_357 = load i32, ptr %i_235
		%tmp_subscript_356 = getelementptr ptr, ptr %tmp_355, i32 %tmp_357
		%tmp_358 = load i32, ptr %j_236
		%tmp_calc_359 = sub i32 %tmp_358, 8
		%tmp_360 = load ptr, ptr %tmp_subscript_356
		%tmp_subscript_361 = getelementptr i32, ptr %tmp_360, i32 %tmp_calc_359
		%tmp_362 = load i32, ptr %tmp_subscript_354
		%tmp_363 = load i32, ptr %tmp_subscript_361
		%tmp_calc_364 = xor i32 %tmp_362, %tmp_363
		%tmp_365 = load ptr, ptr @chunks
		%tmp_367 = load i32, ptr %i_235
		%tmp_subscript_366 = getelementptr ptr, ptr %tmp_365, i32 %tmp_367
		%tmp_368 = load i32, ptr %j_236
		%tmp_calc_369 = sub i32 %tmp_368, 14
		%tmp_370 = load ptr, ptr %tmp_subscript_366
		%tmp_subscript_371 = getelementptr i32, ptr %tmp_370, i32 %tmp_calc_369
		%tmp_372 = load i32, ptr %tmp_subscript_371
		%tmp_calc_373 = xor i32 %tmp_calc_364, %tmp_372
		%tmp_374 = load ptr, ptr @chunks
		%tmp_376 = load i32, ptr %i_235
		%tmp_subscript_375 = getelementptr ptr, ptr %tmp_374, i32 %tmp_376
		%tmp_377 = load i32, ptr %j_236
		%tmp_calc_378 = sub i32 %tmp_377, 16
		%tmp_379 = load ptr, ptr %tmp_subscript_375
		%tmp_subscript_380 = getelementptr i32, ptr %tmp_379, i32 %tmp_calc_378
		%tmp_381 = load i32, ptr %tmp_subscript_380
		%tmp_calc_382 = xor i32 %tmp_calc_373, %tmp_381
		%tmp_func_call_347 = call i32 @rotate_left(i32 %tmp_calc_382, i32 1)
		store i32 %tmp_func_call_347, ptr %tmp_subscript_345
  br label %for_step_103
for_step_103:
		%tmp_383 = load i32, ptr %j_236
		%tmp_suffix_384 = add i32 %tmp_383, 1
		store i32 %tmp_suffix_384, ptr %j_236
  br label %for_cond_101
for_end_104:
		%tmp_386 = load i32, ptr %h0_328
		store i32 %tmp_386, ptr %a_385
		%tmp_388 = load i32, ptr %h1_329
		store i32 %tmp_388, ptr %b_387
		%tmp_390 = load i32, ptr %h2_331
		store i32 %tmp_390, ptr %c_389
		%tmp_392 = load i32, ptr %h3_333
		store i32 %tmp_392, ptr %d_391
		%tmp_394 = load i32, ptr %h4_334
		store i32 %tmp_394, ptr %e_393
		store i32 0, ptr %j_236
  br label %for_cond_105
for_cond_105:
		%tmp_395 = load i32, ptr %j_236
		%tmp_binary_396 = icmp slt i32 %tmp_395, 80
  br i1 %tmp_binary_396, label %for_body_106, label %for_end_108
for_body_106:
		%tmp_399 = load i32, ptr %j_236
		%tmp_binary_400 = icmp slt i32 %tmp_399, 20
  br i1 %tmp_binary_400, label %if_then_109, label %if_else_111
if_then_109:
		%tmp_401 = load i32, ptr %b_387
		%tmp_402 = load i32, ptr %c_389
		%tmp_calc_403 = and i32 %tmp_401, %tmp_402
		%tmp_405 = load i32, ptr %b_387
		%tmp_bitwise_not_404 = xor i32 %tmp_405, -1
		%tmp_406 = load i32, ptr %d_391
		%tmp_calc_407 = and i32 %tmp_bitwise_not_404, %tmp_406
		%tmp_calc_408 = or i32 %tmp_calc_403, %tmp_calc_407
		store i32 %tmp_calc_408, ptr %f_397
		store i32 1518500249, ptr %k_398
  br label %if_end_110
if_else_111:
		%tmp_409 = load i32, ptr %j_236
		%tmp_binary_410 = icmp slt i32 %tmp_409, 40
  br i1 %tmp_binary_410, label %if_then_112, label %if_else_114
if_then_112:
		%tmp_411 = load i32, ptr %b_387
		%tmp_412 = load i32, ptr %c_389
		%tmp_calc_413 = xor i32 %tmp_411, %tmp_412
		%tmp_414 = load i32, ptr %d_391
		%tmp_calc_415 = xor i32 %tmp_calc_413, %tmp_414
		store i32 %tmp_calc_415, ptr %f_397
		store i32 1859775393, ptr %k_398
  br label %if_end_113
if_else_114:
		%tmp_416 = load i32, ptr %j_236
		%tmp_binary_417 = icmp slt i32 %tmp_416, 60
  br i1 %tmp_binary_417, label %if_then_115, label %if_else_117
if_then_115:
		%tmp_418 = load i32, ptr %b_387
		%tmp_419 = load i32, ptr %c_389
		%tmp_calc_420 = and i32 %tmp_418, %tmp_419
		%tmp_421 = load i32, ptr %b_387
		%tmp_422 = load i32, ptr %d_391
		%tmp_calc_423 = and i32 %tmp_421, %tmp_422
		%tmp_calc_424 = or i32 %tmp_calc_420, %tmp_calc_423
		%tmp_425 = load i32, ptr %c_389
		%tmp_426 = load i32, ptr %d_391
		%tmp_calc_427 = and i32 %tmp_425, %tmp_426
		%tmp_calc_428 = or i32 %tmp_calc_424, %tmp_calc_427
		store i32 %tmp_calc_428, ptr %f_397
		%tmp_func_call_429 = call i32 @lohi(i32 48348, i32 36635)
		store i32 %tmp_func_call_429, ptr %k_398
  br label %if_end_116
if_else_117:
		%tmp_430 = load i32, ptr %b_387
		%tmp_431 = load i32, ptr %c_389
		%tmp_calc_432 = xor i32 %tmp_430, %tmp_431
		%tmp_433 = load i32, ptr %d_391
		%tmp_calc_434 = xor i32 %tmp_calc_432, %tmp_433
		store i32 %tmp_calc_434, ptr %f_397
		%tmp_func_call_435 = call i32 @lohi(i32 49622, i32 51810)
		store i32 %tmp_func_call_435, ptr %k_398
  br label %if_end_116
if_end_116:
  br label %if_end_113
if_end_113:
  br label %if_end_110
if_end_110:
		%tmp_441 = load i32, ptr %a_385
		%tmp_func_call_440 = call i32 @rotate_left(i32 %tmp_441, i32 5)
		%tmp_442 = load i32, ptr %e_393
		%tmp_func_call_439 = call i32 @add(i32 %tmp_func_call_440, i32 %tmp_442)
		%tmp_444 = load i32, ptr %f_397
		%tmp_445 = load i32, ptr %k_398
		%tmp_func_call_443 = call i32 @add(i32 %tmp_444, i32 %tmp_445)
		%tmp_func_call_438 = call i32 @add(i32 %tmp_func_call_439, i32 %tmp_func_call_443)
		%tmp_446 = load ptr, ptr @chunks
		%tmp_448 = load i32, ptr %i_235
		%tmp_subscript_447 = getelementptr ptr, ptr %tmp_446, i32 %tmp_448
		%tmp_449 = load ptr, ptr %tmp_subscript_447
		%tmp_451 = load i32, ptr %j_236
		%tmp_subscript_450 = getelementptr i32, ptr %tmp_449, i32 %tmp_451
		%tmp_452 = load i32, ptr %tmp_subscript_450
		%tmp_func_call_437 = call i32 @add(i32 %tmp_func_call_438, i32 %tmp_452)
		store i32 %tmp_func_call_437, ptr %temp_436
		%tmp_453 = load i32, ptr %d_391
		store i32 %tmp_453, ptr %e_393
		%tmp_454 = load i32, ptr %c_389
		store i32 %tmp_454, ptr %d_391
		%tmp_456 = load i32, ptr %b_387
		%tmp_func_call_455 = call i32 @rotate_left(i32 %tmp_456, i32 30)
		store i32 %tmp_func_call_455, ptr %c_389
		%tmp_457 = load i32, ptr %a_385
		store i32 %tmp_457, ptr %b_387
		%tmp_458 = load i32, ptr %temp_436
		store i32 %tmp_458, ptr %a_385
  br label %for_step_107
for_step_107:
		%tmp_459 = load i32, ptr %j_236
		%tmp_suffix_460 = add i32 %tmp_459, 1
		store i32 %tmp_suffix_460, ptr %j_236
  br label %for_cond_105
for_end_108:
		%tmp_462 = load i32, ptr %h0_328
		%tmp_463 = load i32, ptr %a_385
		%tmp_func_call_461 = call i32 @add(i32 %tmp_462, i32 %tmp_463)
		store i32 %tmp_func_call_461, ptr %h0_328
		%tmp_465 = load i32, ptr %h1_329
		%tmp_466 = load i32, ptr %b_387
		%tmp_func_call_464 = call i32 @add(i32 %tmp_465, i32 %tmp_466)
		store i32 %tmp_func_call_464, ptr %h1_329
		%tmp_468 = load i32, ptr %h2_331
		%tmp_469 = load i32, ptr %c_389
		%tmp_func_call_467 = call i32 @add(i32 %tmp_468, i32 %tmp_469)
		store i32 %tmp_func_call_467, ptr %h2_331
		%tmp_471 = load i32, ptr %h3_333
		%tmp_472 = load i32, ptr %d_391
		%tmp_func_call_470 = call i32 @add(i32 %tmp_471, i32 %tmp_472)
		store i32 %tmp_func_call_470, ptr %h3_333
		%tmp_474 = load i32, ptr %h4_334
		%tmp_475 = load i32, ptr %e_393
		%tmp_func_call_473 = call i32 @add(i32 %tmp_474, i32 %tmp_475)
		store i32 %tmp_func_call_473, ptr %h4_334
  br label %for_step_99
for_step_99:
		%tmp_476 = load i32, ptr %i_235
		%tmp_suffix_477 = add i32 %tmp_476, 1
		store i32 %tmp_suffix_477, ptr %i_235
  br label %for_cond_97
for_end_100:
		%tmp_478 = load ptr, ptr @outputBuffer
		%tmp_subscript_479 = getelementptr i32, ptr %tmp_478, i32 0
		%tmp_480 = load i32, ptr %h0_328
		store i32 %tmp_480, ptr %tmp_subscript_479
		%tmp_481 = load ptr, ptr @outputBuffer
		%tmp_subscript_482 = getelementptr i32, ptr %tmp_481, i32 1
		%tmp_483 = load i32, ptr %h1_329
		store i32 %tmp_483, ptr %tmp_subscript_482
		%tmp_484 = load ptr, ptr @outputBuffer
		%tmp_subscript_485 = getelementptr i32, ptr %tmp_484, i32 2
		%tmp_486 = load i32, ptr %h2_331
		store i32 %tmp_486, ptr %tmp_subscript_485
		%tmp_487 = load ptr, ptr @outputBuffer
		%tmp_subscript_488 = getelementptr i32, ptr %tmp_487, i32 3
		%tmp_489 = load i32, ptr %h3_333
		store i32 %tmp_489, ptr %tmp_subscript_488
		%tmp_490 = load ptr, ptr @outputBuffer
		%tmp_subscript_491 = getelementptr i32, ptr %tmp_490, i32 4
		%tmp_492 = load i32, ptr %h4_334
		store i32 %tmp_492, ptr %tmp_subscript_491
		%tmp_493 = load ptr, ptr @outputBuffer
		store ptr %tmp_493, ptr %sha1_ret_22
  br label %sha1_exit_18
sha1_exit_18:
		%ret_224 = load ptr, ptr %sha1_ret_22
  ret ptr %ret_224
}


define void @computeSHA1(ptr %input_26) {
computeSHA1_entry_19:
		%input_494 = alloca ptr
		%i_495 = alloca i32
		%result_508 = alloca ptr
		store ptr %input_26, ptr %input_494
		store i32 0, ptr %i_495
  br label %for_cond_118
for_cond_118:
		%tmp_497 = load ptr, ptr %input_494
		%tmp_func_call_496 = call i32 @_string_length(ptr %tmp_497)
		%tmp_498 = load i32, ptr %i_495
		%tmp_binary_499 = icmp slt i32 %tmp_498, %tmp_func_call_496
  br i1 %tmp_binary_499, label %for_body_119, label %for_end_121
for_body_119:
		%tmp_500 = load ptr, ptr @inputBuffer
		%tmp_502 = load i32, ptr %i_495
		%tmp_subscript_501 = getelementptr i32, ptr %tmp_500, i32 %tmp_502
		%tmp_504 = load ptr, ptr %input_494
		%tmp_505 = load i32, ptr %i_495
		%tmp_func_call_503 = call i32 @_string_ord(ptr %tmp_504, i32 %tmp_505)
		store i32 %tmp_func_call_503, ptr %tmp_subscript_501
  br label %for_step_120
for_step_120:
		%tmp_506 = load i32, ptr %i_495
		%tmp_suffix_507 = add i32 %tmp_506, 1
		store i32 %tmp_suffix_507, ptr %i_495
  br label %for_cond_118
for_end_121:
		%tmp_510 = load ptr, ptr @inputBuffer
		%tmp_512 = load ptr, ptr %input_494
		%tmp_func_call_511 = call i32 @_string_length(ptr %tmp_512)
		%tmp_func_call_509 = call ptr @sha1(ptr %tmp_510, i32 %tmp_func_call_511)
		store ptr %tmp_func_call_509, ptr %result_508
		store i32 0, ptr %i_495
  br label %for_cond_122
for_cond_122:
		%tmp_514 = load ptr, ptr %result_508
		%tmp_func_call_513 = call i32 @_array_size(ptr %tmp_514)
		%tmp_515 = load i32, ptr %i_495
		%tmp_binary_516 = icmp slt i32 %tmp_515, %tmp_func_call_513
  br i1 %tmp_binary_516, label %for_body_123, label %for_end_125
for_body_123:
		%tmp_519 = load ptr, ptr %result_508
		%tmp_521 = load i32, ptr %i_495
		%tmp_subscript_520 = getelementptr i32, ptr %tmp_519, i32 %tmp_521
		%tmp_522 = load i32, ptr %tmp_subscript_520
		%tmp_func_call_518 = call ptr @toStringHex(i32 %tmp_522)
		call void @print(ptr %tmp_func_call_518)
  br label %for_step_124
for_step_124:
		%tmp_523 = load i32, ptr %i_495
		%tmp_suffix_524 = add i32 %tmp_523, 1
		store i32 %tmp_suffix_524, ptr %i_495
  br label %for_cond_122
for_end_125:
		call void @println([1 x i8]* @str.2)
  br label %computeSHA1_exit_20
computeSHA1_exit_20:
  ret void
}


define i32 @nextLetter(i32 %now_28) {
nextLetter_entry_21:
		%now_526 = alloca i32
		%nextLetter_ret_27 = alloca i32
		store i32 %now_28, ptr %now_526
		%tmp_528 = load i32, ptr %now_526
		%tmp_529 = icmp eq i32 %tmp_528, 122
  br i1 %tmp_529, label %if_then_126, label %if_end_127
if_then_126:
		%tmp_neg_530 = sub i32 0, 1
		store i32 %tmp_neg_530, ptr %nextLetter_ret_27
  br label %nextLetter_exit_22
if_end_127:
		%tmp_531 = load i32, ptr %now_526
		%tmp_532 = icmp eq i32 %tmp_531, 90
  br i1 %tmp_532, label %if_then_129, label %if_end_130
if_then_129:
		store i32 97, ptr %nextLetter_ret_27
  br label %nextLetter_exit_22
if_end_130:
		%tmp_533 = load i32, ptr %now_526
		%tmp_534 = icmp eq i32 %tmp_533, 57
  br i1 %tmp_534, label %if_then_132, label %if_end_133
if_then_132:
		store i32 65, ptr %nextLetter_ret_27
  br label %nextLetter_exit_22
if_end_133:
		%tmp_535 = load i32, ptr %now_526
		%tmp_calc_536 = add i32 %tmp_535, 1
		store i32 %tmp_calc_536, ptr %nextLetter_ret_27
  br label %nextLetter_exit_22
nextLetter_exit_22:
		%ret_527 = load i32, ptr %nextLetter_ret_27
  ret i32 %ret_527
}


define i1 @nextText(ptr %now_30, i32 %length_31) {
nextText_entry_23:
		%now_537 = alloca ptr
		%length_538 = alloca i32
		%nextText_ret_29 = alloca i1
		%i_540 = alloca i32
		store ptr %now_30, ptr %now_537
		store i32 %length_31, ptr %length_538
		%tmp_541 = load i32, ptr %length_538
		%tmp_calc_542 = sub i32 %tmp_541, 1
		store i32 %tmp_calc_542, ptr %i_540
  br label %for_cond_135
for_cond_135:
		%tmp_543 = load i32, ptr %i_540
		%tmp_binary_544 = icmp sge i32 %tmp_543, 0
  br i1 %tmp_binary_544, label %for_body_136, label %for_end_138
for_body_136:
		%tmp_545 = load ptr, ptr %now_537
		%tmp_547 = load i32, ptr %i_540
		%tmp_subscript_546 = getelementptr i32, ptr %tmp_545, i32 %tmp_547
		%tmp_549 = load ptr, ptr %now_537
		%tmp_551 = load i32, ptr %i_540
		%tmp_subscript_550 = getelementptr i32, ptr %tmp_549, i32 %tmp_551
		%tmp_552 = load i32, ptr %tmp_subscript_550
		%tmp_func_call_548 = call i32 @nextLetter(i32 %tmp_552)
		store i32 %tmp_func_call_548, ptr %tmp_subscript_546
		%tmp_553 = load ptr, ptr %now_537
		%tmp_555 = load i32, ptr %i_540
		%tmp_subscript_554 = getelementptr i32, ptr %tmp_553, i32 %tmp_555
		%tmp_neg_556 = sub i32 0, 1
		%tmp_557 = load i32, ptr %tmp_subscript_554
		%tmp_558 = icmp eq i32 %tmp_557, %tmp_neg_556
  br i1 %tmp_558, label %if_then_139, label %if_else_141
if_then_139:
		%tmp_559 = load ptr, ptr %now_537
		%tmp_561 = load i32, ptr %i_540
		%tmp_subscript_560 = getelementptr i32, ptr %tmp_559, i32 %tmp_561
		store i32 48, ptr %tmp_subscript_560
  br label %if_end_140
if_else_141:
		store i1 true, ptr %nextText_ret_29
  br label %nextText_exit_24
if_end_140:
  br label %for_step_137
for_step_137:
		%tmp_562 = load i32, ptr %i_540
		%tmp_suffix_563 = sub i32 %tmp_562, 1
		store i32 %tmp_suffix_563, ptr %i_540
  br label %for_cond_135
for_end_138:
		store i1 false, ptr %nextText_ret_29
  br label %nextText_exit_24
nextText_exit_24:
		%ret_539 = load i1, ptr %nextText_ret_29
  ret i1 %ret_539
}


define i1 @array_equal(ptr %a_33, ptr %b_34) {
array_equal_entry_25:
		%a_564 = alloca ptr
		%b_565 = alloca ptr
		%array_equal_ret_32 = alloca i1
		%i_572 = alloca i32
		store ptr %a_33, ptr %a_564
		store ptr %b_34, ptr %b_565
		%tmp_568 = load ptr, ptr %a_564
		%tmp_func_call_567 = call i32 @_array_size(ptr %tmp_568)
		%tmp_570 = load ptr, ptr %b_565
		%tmp_func_call_569 = call i32 @_array_size(ptr %tmp_570)
		%tmp_571 = icmp ne i32 %tmp_func_call_567, %tmp_func_call_569
  br i1 %tmp_571, label %if_then_142, label %if_end_143
if_then_142:
		store i1 false, ptr %array_equal_ret_32
  br label %array_equal_exit_26
if_end_143:
		store i32 0, ptr %i_572
  br label %for_cond_145
for_cond_145:
		%tmp_574 = load ptr, ptr %a_564
		%tmp_func_call_573 = call i32 @_array_size(ptr %tmp_574)
		%tmp_575 = load i32, ptr %i_572
		%tmp_binary_576 = icmp slt i32 %tmp_575, %tmp_func_call_573
  br i1 %tmp_binary_576, label %for_body_146, label %for_end_148
for_body_146:
		%tmp_577 = load ptr, ptr %a_564
		%tmp_579 = load i32, ptr %i_572
		%tmp_subscript_578 = getelementptr i32, ptr %tmp_577, i32 %tmp_579
		%tmp_580 = load ptr, ptr %b_565
		%tmp_582 = load i32, ptr %i_572
		%tmp_subscript_581 = getelementptr i32, ptr %tmp_580, i32 %tmp_582
		%tmp_583 = load i32, ptr %tmp_subscript_578
		%tmp_584 = load i32, ptr %tmp_subscript_581
		%tmp_585 = icmp ne i32 %tmp_583, %tmp_584
  br i1 %tmp_585, label %if_then_149, label %if_end_150
if_then_149:
		store i1 false, ptr %array_equal_ret_32
  br label %array_equal_exit_26
if_end_150:
  br label %for_step_147
for_step_147:
		%tmp_586 = load i32, ptr %i_572
		%tmp_suffix_587 = add i32 %tmp_586, 1
		store i32 %tmp_suffix_587, ptr %i_572
  br label %for_cond_145
for_end_148:
		store i1 true, ptr %array_equal_ret_32
  br label %array_equal_exit_26
array_equal_exit_26:
		%ret_566 = load i1, ptr %array_equal_ret_32
  ret i1 %ret_566
}


define void @crackSHA1(ptr %input_36) {
crackSHA1_entry_27:
		%input_588 = alloca ptr
		%target_589 = alloca ptr
		%i_595 = alloca i32
		%MAXDIGIT_629 = alloca i32
		%digit_630 = alloca i32
		%out_642 = alloca ptr
		store ptr %input_36, ptr %input_588
		%tmp_array_590 = call ptr @_new_array(i32 24, i32 5)
		store ptr %tmp_array_590, ptr %target_589
		%tmp_592 = load ptr, ptr %input_588
		%tmp_func_call_591 = call i32 @_string_length(ptr %tmp_592)
		%tmp_593 = icmp ne i32 %tmp_func_call_591, 40
  br i1 %tmp_593, label %if_then_152, label %if_end_153
if_then_152:
		call void @println([14 x i8]* @str.4)
  br label %crackSHA1_exit_28
if_end_153:
		store i32 0, ptr %i_595
  br label %for_cond_155
for_cond_155:
		%tmp_596 = load i32, ptr %i_595
		%tmp_binary_597 = icmp slt i32 %tmp_596, 5
  br i1 %tmp_binary_597, label %for_body_156, label %for_end_158
for_body_156:
		%tmp_598 = load ptr, ptr %target_589
		%tmp_600 = load i32, ptr %i_595
		%tmp_subscript_599 = getelementptr i32, ptr %tmp_598, i32 %tmp_600
		store i32 0, ptr %tmp_subscript_599
  br label %for_step_157
for_step_157:
		%tmp_601 = load i32, ptr %i_595
		%tmp_suffix_602 = add i32 %tmp_601, 1
		store i32 %tmp_suffix_602, ptr %i_595
  br label %for_cond_155
for_end_158:
		store i32 0, ptr %i_595
  br label %for_cond_159
for_cond_159:
		%tmp_603 = load i32, ptr %i_595
		%tmp_binary_604 = icmp slt i32 %tmp_603, 40
  br i1 %tmp_binary_604, label %for_body_160, label %for_end_162
for_body_160:
		%tmp_605 = load i32, ptr %i_595
		%tmp_calc_606 = sdiv i32 %tmp_605, 8
		%tmp_607 = load ptr, ptr %target_589
		%tmp_subscript_608 = getelementptr i32, ptr %tmp_607, i32 %tmp_calc_606
		%tmp_609 = load i32, ptr %i_595
		%tmp_calc_610 = sdiv i32 %tmp_609, 8
		%tmp_611 = load ptr, ptr %target_589
		%tmp_subscript_612 = getelementptr i32, ptr %tmp_611, i32 %tmp_calc_610
		%tmp_615 = load ptr, ptr %input_588
		%tmp_616 = load i32, ptr %i_595
		%tmp_617 = load i32, ptr %i_595
		%tmp_calc_618 = add i32 %tmp_617, 4
		%tmp_func_call_614 = call ptr @_string_substring(ptr %tmp_615, i32 %tmp_616, i32 %tmp_calc_618)
		%tmp_func_call_613 = call i32 @hex2int(ptr %tmp_func_call_614)
		%tmp_619 = load i32, ptr %i_595
		%tmp_calc_620 = sdiv i32 %tmp_619, 4
		%tmp_calc_621 = srem i32 %tmp_calc_620, 2
		%tmp_calc_622 = sub i32 1, %tmp_calc_621
		%tmp_calc_623 = mul i32 %tmp_calc_622, 16
		%tmp_calc_624 = shl i32 %tmp_func_call_613, %tmp_calc_623
		%tmp_625 = load i32, ptr %tmp_subscript_612
		%tmp_calc_626 = or i32 %tmp_625, %tmp_calc_624
		store i32 %tmp_calc_626, ptr %tmp_subscript_608
  br label %for_step_161
for_step_161:
		%tmp_627 = load i32, ptr %i_595
		%tmp_calc_628 = add i32 %tmp_627, 4
		store i32 %tmp_calc_628, ptr %i_595
  br label %for_cond_159
for_end_162:
		store i32 4, ptr %MAXDIGIT_629
		store i32 1, ptr %digit_630
  br label %for_cond_163
for_cond_163:
		%tmp_631 = load i32, ptr %digit_630
		%tmp_632 = load i32, ptr %MAXDIGIT_629
		%tmp_binary_633 = icmp sle i32 %tmp_631, %tmp_632
  br i1 %tmp_binary_633, label %for_body_164, label %for_end_166
for_body_164:
		store i32 0, ptr %i_595
  br label %for_cond_167
for_cond_167:
		%tmp_634 = load i32, ptr %i_595
		%tmp_635 = load i32, ptr %digit_630
		%tmp_binary_636 = icmp slt i32 %tmp_634, %tmp_635
  br i1 %tmp_binary_636, label %for_body_168, label %for_end_170
for_body_168:
		%tmp_637 = load ptr, ptr @inputBuffer
		%tmp_639 = load i32, ptr %i_595
		%tmp_subscript_638 = getelementptr i32, ptr %tmp_637, i32 %tmp_639
		store i32 48, ptr %tmp_subscript_638
  br label %for_step_169
for_step_169:
		%tmp_640 = load i32, ptr %i_595
		%tmp_suffix_641 = add i32 %tmp_640, 1
		store i32 %tmp_suffix_641, ptr %i_595
  br label %for_cond_167
for_end_170:
  br label %while_cond_171
while_cond_171:
  br i1 true, label %while_body_172, label %while_end_173
while_body_172:
		%tmp_644 = load ptr, ptr @inputBuffer
		%tmp_645 = load i32, ptr %digit_630
		%tmp_func_call_643 = call ptr @sha1(ptr %tmp_644, i32 %tmp_645)
		store ptr %tmp_func_call_643, ptr %out_642
		%tmp_647 = load ptr, ptr %out_642
		%tmp_648 = load ptr, ptr %target_589
		%tmp_func_call_646 = call i1 @array_equal(ptr %tmp_647, ptr %tmp_648)
  br i1 %tmp_func_call_646, label %if_then_174, label %if_end_175
if_then_174:
		store i32 0, ptr %i_595
  br label %for_cond_177
for_cond_177:
		%tmp_649 = load i32, ptr %i_595
		%tmp_650 = load i32, ptr %digit_630
		%tmp_binary_651 = icmp slt i32 %tmp_649, %tmp_650
  br i1 %tmp_binary_651, label %for_body_178, label %for_end_180
for_body_178:
		%tmp_654 = load ptr, ptr @inputBuffer
		%tmp_656 = load i32, ptr %i_595
		%tmp_subscript_655 = getelementptr i32, ptr %tmp_654, i32 %tmp_656
		%tmp_657 = load i32, ptr %tmp_subscript_655
		%tmp_func_call_653 = call ptr @int2chr(i32 %tmp_657)
		call void @print(ptr %tmp_func_call_653)
  br label %for_step_179
for_step_179:
		%tmp_658 = load i32, ptr %i_595
		%tmp_suffix_659 = add i32 %tmp_658, 1
		store i32 %tmp_suffix_659, ptr %i_595
  br label %for_cond_177
for_end_180:
		call void @println([1 x i8]* @str.2)
  br label %crackSHA1_exit_28
if_end_175:
		%tmp_662 = load ptr, ptr @inputBuffer
		%tmp_663 = load i32, ptr %digit_630
		%tmp_func_call_661 = call i1 @nextText(ptr %tmp_662, i32 %tmp_663)
		%tmp_logic_not_664 = xor i1 %tmp_func_call_661, 1
  br i1 %tmp_logic_not_664, label %if_then_181, label %if_end_182
if_then_181:
  br label %while_end_173
if_end_182:
  br label %while_cond_171
while_end_173:
  br label %for_step_165
for_step_165:
		%tmp_665 = load i32, ptr %digit_630
		%tmp_suffix_666 = add i32 %tmp_665, 1
		store i32 %tmp_suffix_666, ptr %digit_630
  br label %for_cond_163
for_end_166:
		call void @println([11 x i8]* @str.5)
  br label %crackSHA1_exit_28
crackSHA1_exit_28:
  ret void
}


define i32 @main() {
main_entry_29:
		%main_ret_37 = alloca i32
		%op_669 = alloca i32
		%input_670 = alloca ptr
		store i32 0, ptr %main_ret_37
		call void @_global_var_init()
  br label %while_cond_184
while_cond_184:
  br i1 true, label %while_body_185, label %while_end_186
while_body_185:
		%tmp_func_call_671 = call i32 @getInt()
		store i32 %tmp_func_call_671, ptr %op_669
		%tmp_672 = load i32, ptr %op_669
		%tmp_673 = icmp eq i32 %tmp_672, 0
  br i1 %tmp_673, label %if_then_187, label %if_end_188
if_then_187:
  br label %while_end_186
if_end_188:
		%tmp_674 = load i32, ptr %op_669
		%tmp_675 = icmp eq i32 %tmp_674, 1
  br i1 %tmp_675, label %if_then_190, label %if_else_192
if_then_190:
		%tmp_func_call_676 = call ptr @getString()
		store ptr %tmp_func_call_676, ptr %input_670
		%tmp_678 = load ptr, ptr %input_670
		call void @computeSHA1(ptr %tmp_678)
  br label %if_end_191
if_else_192:
		%tmp_679 = load i32, ptr %op_669
		%tmp_680 = icmp eq i32 %tmp_679, 2
  br i1 %tmp_680, label %if_then_193, label %if_end_194
if_then_193:
		%tmp_func_call_681 = call ptr @getString()
		store ptr %tmp_func_call_681, ptr %input_670
		%tmp_683 = load ptr, ptr %input_670
		call void @crackSHA1(ptr %tmp_683)
  br label %if_end_194
if_end_194:
  br label %if_end_191
if_end_191:
  br label %while_cond_184
while_end_186:
		store i32 0, ptr %main_ret_37
  br label %main_exit_30
main_exit_30:
		%ret_668 = load i32, ptr %main_ret_37
  ret i32 %ret_668
}


define void @_global_var_init() {
_global_var_init_entry_31:
		%tmp_array_index_47 = alloca ptr
		%tmp_39 = load i32, ptr @MAXCHUNK
		%tmp_calc_40 = sub i32 %tmp_39, 1
		%tmp_calc_41 = mul i32 %tmp_calc_40, 64
		%tmp_calc_42 = sub i32 %tmp_calc_41, 16
		store i32 %tmp_calc_42, ptr @MAXLENGTH
		%tmp_43 = load i32, ptr @MAXCHUNK
		%tmp_array_size_44 = mul i32 %tmp_43, 4
		%tmp_array_size_45 = add i32 %tmp_array_size_44, 4
		%tmp_array_46 = call ptr @_new_array(i32 %tmp_array_size_45, i32 %tmp_43)
		store i32 0, ptr %tmp_array_index_47
  br label %array_cond_33
array_cond_33:
		%_48 = load i32, ptr %tmp_array_index_47
		%_49 = icmp slt i32 %_48, %tmp_43
  br i1 %_49, label %array_body_34, label %array_end_36
array_body_34:
		%tmp_array_50 = call ptr @_new_array(i32 324, i32 80)
		%_52 = load i32, ptr %tmp_array_index_47
		%_51 = getelementptr ptr, ptr %tmp_array_46, i32 %_52
		store ptr %tmp_array_50, ptr %_51
  br label %array_step_35
array_step_35:
		%_54 = load i32, ptr %tmp_array_index_47
		%_53 = add i32 %_54, 1
		store i32 %_53, ptr %tmp_array_index_47
  br label %array_cond_33
array_end_36:
		store ptr %tmp_array_46, ptr @chunks
		%tmp_55 = load i32, ptr @MAXLENGTH
		%tmp_array_size_56 = mul i32 %tmp_55, 4
		%tmp_array_size_57 = add i32 %tmp_array_size_56, 4
		%tmp_array_58 = call ptr @_new_array(i32 %tmp_array_size_57, i32 %tmp_55)
		store ptr %tmp_array_58, ptr @inputBuffer
		%tmp_array_59 = call ptr @_new_array(i32 24, i32 5)
		store ptr %tmp_array_59, ptr @outputBuffer
  br label %_global_var_init_exit_32
_global_var_init_exit_32:
  ret void
}


