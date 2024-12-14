## Chisel-HDLBits-Template

### 项目简介

`chisel-HDLBits-template` 是一个模板项目，旨在帮助用户高效地使用 Chisel 语言完成 HDLBits 上的数字电路练习。项目提供了一套便捷的工具链和工作流，包括生成代码模板、清理多余前缀、记录练习历史等功能。

部分模板内容来自 [chisel-template](https://github.com/chipsalliance/chisel-template) [chisel-playground](https://github.com/OSCPU/chisel-playground)

------

### 功能概述

1. **模板生成**：通过 `make start` 命令生成模板并切换到 `practice` 分支：
2. **移除 `io_` 前缀**：生成的 `.sv` 文件会自动去除 `io_` 前缀，方便将代码复制到 HDLBits 测试框架中。
3. **练习记录管理**：
   - 每次完成练习后，运行 `make commit-and-reset` 将练习提交为一个 commit。
   - 同时，恢复代码到模板状态，为下一题准备。
4. **自动化支持**：通过 Makefile 集成所有操作，使整个练习过程高效、简洁。

------

### 项目结构

```plaintext
.
├── build-sv/               # 生成的 SystemVerilog 文件存储目录
├── src/                    # Chisel 源代码目录
├── remove_io_prefix.sh     # 脚本：移除生成代码中的 `io_` 前缀
├── Makefile                # 构建和管理练习的核心文件
└── README.md               # 项目说明文件
```

------

### 使用方法

#### **1. 初始化项目**（如果修改模板）

在开始练习之前，请确保已将模板代码提交到 Git 仓库，并设置模板分支 `main`：

```bash
git add .
git commit -m "Initial empty template"
```

#### **2. 生成模板并开始练习**

运行以下命令，生成一个模板并切换到 `practice` 分支：

```bash
make start
```

> 如果 `practice` 分支已存在，命令会直接切换到该分支。

#### **3. 完成练习并生成 Verilog 文件**

修改模板文件完成练习后，运行以下命令生成 `.sv` 文件：

```bash
make runh
```

- 生成的 `.sv` 文件会存储在 `build-sv/` 目录下。
- 自动移除了 `io_` 前缀，便于复制到 HDLBits 平台检测。
- **注意**：仅复制 `module` 体的部分代码到 HDLBits 中测试（忽略 `top` 和接口声明部分）。

#### **4. 提交练习并恢复模板**

完成练习后，运行以下命令提交练习记录并恢复模板状态：

```bash
make commit-and-reset
```

- 生成一个练习提交（`Practice commit <时间>`）。
- 恢复代码为模板状态，为下一次练习准备。

#### **5. 清理生成文件**

清除生成的 `.sv` 文件和构建目录：

```bash
make clean
```

------

### Makefile 配置项

- **BUILD_DIR**：`./build-sv`
   Verilog 文件的生成目录。
- **PRJ**：`playground`
   Chisel 项目的名称。
- **TEMPLATE_BRANCH**：`main`
   保存练习模板的分支名称。
- **PRACTICE_BRANCH**：`practice`
   用于练习的固定分支名称。
- **FILES**：`src/`
   包含需要提交的练习代码文件。
- **REMOVE_IO_SCRIPT**：`remove_io_prefix.sh`
   移除生成代码中 `io_` 前缀的脚本。

------

### 常用命令

| 命令                    | 功能说明                                                     |
| ----------------------- | ------------------------------------------------------------ |
| `make start`            | 初始化模板练习，切换到练习分支（如果不存在则创建）。         |
| `make run`              | 生成 Verilog 文件到 `build-sv/` 目录中（不移除 `io_` 前缀）。 |
| `make runh`             | 生成 Verilog 文件，并移除 `io_` 前缀，便于复制到 HDLBits 平台检测。 |
| `make commit`           | 提交当前练习记录，生成一个 `Practice commit` 的 Git 提交记录。 |
| `make reset`            | 恢复模板文件到工作区（不生成提交记录）。                     |
| `make commit-and-reset` | 提交练习记录并恢复模板状态，为下一次练习准备。               |
| `make clean`            | 删除生成文件，清空构建目录。                                 |

------

### 注意事项

1. **文件复制**：从生成的 Verilog 文件中，仅复制 `module` 体部分代码到 HDLBits 平台测试，忽略顶层模块和接口声明部分。

2. 模板更新：如果需要修改模板，请切换到 

   ```
   main
   ```

    分支修改后提交：

   ```bash
   git checkout main
   # 修改模板文件
   git commit -am "Update template"
   git checkout practice
   git checkout main -- src/  # 同步模板到练习分支
   ```

3. **历史记录**：`practice` 分支中的每次提交均记录了完成的练习，便于复盘和查看进度。

------

### 示例操作流程

1. 运行 `make start` 开始新的练习。
2. 编辑 `src/main/Top.scala`，完成 HDLBits 的某个题目。
3. 运行 `make runh`，生成移除 `io_` 前缀后的 Verilog 文件，复制到 HDLBits 平台测试。
4. 测试通过后，运行 `make commit-and-reset`，提交练习并恢复到模板状态。
5. 重复上述步骤完成后续练习。
