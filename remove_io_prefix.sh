#!/bin/bash

# 检查输入参数是否正确
if [ $# -ne 1 ]; then
  echo "Usage: $0 <verilog_file>"
  exit 1
fi

verilog_file=$1

# 检查文件是否存在
if [ ! -f "$verilog_file" ]; then
  echo "Error: File $verilog_file does not exist."
  exit 1
fi

# 使用 sed 去掉 io_ 前缀
sed -i 's/\bio_\([a-zA-Z0-9_]*\)/\1/g' "$verilog_file"

echo "Processed file: $verilog_file. Removed 'io_' prefix."
