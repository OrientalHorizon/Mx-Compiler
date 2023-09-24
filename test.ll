@str.1 = private unnamed_addr constant [2 x i8] c" \00"
@str.2 = private unnamed_addr constant [1 x i8] c"\00"

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


define void @bubble_sort(ptr %a_2) {
bubble_sort_entry_5:
		%a_5 = alloca ptr
		%i_6 = alloca i32
		%j_7 = alloca i32
		%n_8 = alloca i32
		store ptr %a_2, ptr %a_5
		%tmp_10 = load ptr, ptr %a_5
		%tmp_func_call_9 = call i32 @_array_size(ptr %tmp_10)
		store i32 %tmp_func_call_9, ptr %n_8
		%tmp_12 = load i32, ptr %n_8
		call void @printlnInt(i32 %tmp_12)
		store i32 0, ptr %i_6
  br label %for_cond_11
for_cond_11:
		%tmp_13 = load i32, ptr %i_6
		%tmp_14 = load i32, ptr %n_8
		%tmp_binary_15 = icmp slt i32 %tmp_13, %tmp_14
  br i1 %tmp_binary_15, label %for_body_12, label %for_end_14
for_body_12:
		%tmp_17 = load i32, ptr %i_6
		call void @printInt(i32 %tmp_17)
		call void @print([2 x i8]* @str.1)
		%tmp_20 = load ptr, ptr %a_5
		%tmp_22 = load i32, ptr %i_6
		%tmp_subscript_21 = getelementptr i32, ptr %tmp_20, i32 %tmp_22
		%tmp_23 = load i32, ptr %tmp_subscript_21
		call void @printInt(i32 %tmp_23)
  br label %for_step_13
for_step_13:
		%tmp_25 = load i32, ptr %i_6
		%tmp_prefix_24 = add i32 %tmp_25, 1
		store i32 %tmp_prefix_24, ptr %i_6
  br label %for_cond_11
for_end_14:
		call void @println([1 x i8]* @str.2)
		%tmp_28 = load i32, ptr %n_8
		call void @printlnInt(i32 %tmp_28)
  br label %bubble_sort_exit_6
bubble_sort_exit_6:
  ret void
}


define i32 @main() {
main_entry_7:
		%main_ret_3 = alloca i32
		%n_30 = alloca i32
		%a_32 = alloca ptr
		%i_37 = alloca i32
		store i32 0, ptr %main_ret_3
		call void @_global_var_init()
		%tmp_func_call_31 = call i32 @getInt()
		store i32 %tmp_func_call_31, ptr %n_30
		%tmp_33 = load i32, ptr %n_30
		%tmp_array_size_34 = mul i32 %tmp_33, 4
		%tmp_array_size_35 = add i32 %tmp_array_size_34, 4
		%tmp_array_36 = call ptr @_new_array(i32 %tmp_array_size_35, i32 %tmp_33)
		store ptr %tmp_array_36, ptr %a_32
		store i32 0, ptr %i_37
  br label %for_cond_15
for_cond_15:
		%tmp_38 = load i32, ptr %i_37
		%tmp_39 = load i32, ptr %n_30
		%tmp_binary_40 = icmp slt i32 %tmp_38, %tmp_39
  br i1 %tmp_binary_40, label %for_body_16, label %for_end_18
for_body_16:
		%tmp_41 = load ptr, ptr %a_32
		%tmp_43 = load i32, ptr %i_37
		%tmp_subscript_42 = getelementptr i32, ptr %tmp_41, i32 %tmp_43
		%tmp_func_call_44 = call i32 @getInt()
		store i32 %tmp_func_call_44, ptr %tmp_subscript_42
  br label %for_step_17
for_step_17:
		%tmp_46 = load i32, ptr %i_37
		%tmp_prefix_45 = add i32 %tmp_46, 1
		store i32 %tmp_prefix_45, ptr %i_37
  br label %for_cond_15
for_end_18:
		%tmp_48 = load ptr, ptr %a_32
		call void @bubble_sort(ptr %tmp_48)
		store i32 0, ptr %main_ret_3
  br label %main_exit_8
main_exit_8:
		%ret_29 = load i32, ptr %main_ret_3
  ret i32 %ret_29
}


define void @_global_var_init() {
_global_var_init_entry_9:
  br label %_global_var_init_exit_10
_global_var_init_exit_10:
  ret void
}


