BUILD_DIR = ./build-sv
PRJ = playground
TEMPLATE_BRANCH = main
PRACTICE_BRANCH = practice
FILES = src/
REMOVE_IO_SCRIPT = remove_io_prefix.sh


test:
	mill -i $(PRJ).test

run:
	mill -i $(PRJ).runMain top.TopToV --target-dir $(BUILD_DIR)

runh:
	mill -i $(PRJ).runMain top.TopToV --target-dir $(BUILD_DIR)
	@bash $(REMOVE_IO_SCRIPT) $(BUILD_DIR)/Top.sv

clean:
	rm -rf $(BUILD_DIR)

.PHONY: test run runh clean start commit reset commit-and-reset

# 切换到练习分支
# 检查练习分支是否存在，如果存在则切换，否则创建
start:
	@if git show-ref --verify --quiet refs/heads/$(PRACTICE_BRANCH); then \
		echo "Branch '$(PRACTICE_BRANCH)' already exists. Switching to it..."; \
		git checkout $(PRACTICE_BRANCH); \
	else \
		echo "Branch '$(PRACTICE_BRANCH)' does not exist. Creating it..."; \
		git checkout -b $(PRACTICE_BRANCH) $(TEMPLATE_BRANCH); \
	fi

commit:
	git add $(FILES)
	git commit -m "Practice commit $(shell date +'%Y-%m-%d %H:%M:%S')"

# 恢复模板到工作区（不提交）
reset:
	git checkout $(TEMPLATE_BRANCH) -- $(FILES)

# 提交练习代码并恢复模板
commit-and-reset: commit reset