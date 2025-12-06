# Git安装和GitHub推送指南

## 问题分析

当前环境中没有安装Git，导致无法直接执行Git命令。本指南将帮助您：
1. 安装Git
2. 配置Git
3. 将项目推送到GitHub

## 步骤1：安装Git

### 方法1：从官网下载安装

1. 访问Git官网：https://git-scm.com/download/win
2. 点击下载按钮，选择适合您系统的版本（32位或64位）
3. 运行下载的安装程序
4. 在安装向导中，接受默认选项即可（或根据需要自定义）
5. 安装完成后，打开新的命令提示符窗口

### 方法2：使用Windows包管理器（可选）

如果您安装了Windows Subsystem for Linux (WSL)，可以使用以下命令：

```bash
sudo apt-get update
sudo apt-get install git
```

## 步骤2：验证Git安装

打开新的命令提示符窗口，运行以下命令：

```cmd
git --version
```

如果安装成功，将显示Git版本信息，例如：
```
git version 2.37.1.windows.1
```

## 步骤3：配置Git

在命令提示符中运行以下命令，配置您的Git用户名和邮箱：

```cmd
git config --global user.name "您的GitHub用户名"
git config --global user.email "您的GitHub注册邮箱"
```

## 步骤4：创建GitHub仓库

1. 登录GitHub（https://github.com）
2. 点击右上角的「+」按钮，选择「New repository」
3. 填写仓库信息：
   - **Repository name**：输入仓库名称（例如：`saolei-android`）
   - **Description**：（可选）输入仓库描述
   - **Visibility**：选择「Public」或「Private」
4. 点击「Create repository」

## 步骤5：推送项目到GitHub

1. 打开命令提示符，进入项目根目录：
   ```cmd
   cd c:\Users\Admin\Documents\trae_projects\saolei1
   ```

2. 初始化Git仓库：
   ```cmd
   git init
   ```

3. 添加所有文件到暂存区：
   ```cmd
   git add .
   ```

4. 提交初始版本：
   ```cmd
   git commit -m "Initial commit"
   ```

5. 添加GitHub远程仓库：
   ```cmd
   git remote add origin https://github.com/<您的用户名>/<仓库名称>.git
   ```
   例如：
   ```cmd
   git remote add origin https://github.com/username/saolei-android.git
   ```

6. 推送到GitHub：
   ```cmd
   git push -u origin main
   ```

7. 首次推送时，会提示您输入GitHub用户名和密码：
   - 输入您的GitHub用户名
   - 输入您的GitHub个人访问令牌（不是密码！）

## 生成GitHub个人访问令牌

由于GitHub已经不再支持使用密码进行Git操作，您需要生成一个个人访问令牌：

1. 登录GitHub，点击右上角的头像 → 「Settings」
2. 在左侧菜单中，点击「Developer settings」
3. 点击「Personal access tokens」→ 「Tokens (classic)」
4. 点击「Generate new token」→ 「Generate new token (classic)」
5. 填写令牌信息：
   - **Note**：输入令牌描述（例如：`Git push token`）
   - **Expiration**：选择令牌有效期
   - **Select scopes**：勾选「repo」选项（所有子选项都会自动选中）
6. 点击底部的「Generate token」
7. 复制生成的令牌，妥善保存（关闭页面后将无法再次查看）

## 推送成功后的操作

1. 打开GitHub仓库页面，您将看到所有项目文件
2. 点击顶部导航栏的「Actions」选项卡
3. GitHub Actions将自动开始构建您的项目
4. 构建完成后，您可以在「Artifacts」部分下载生成的APK文件

## 常见问题解决

### 问题1：Git命令仍然无法识别

- 确保已打开新的命令提示符窗口
- 检查系统PATH环境变量是否包含Git安装目录
- 重新启动计算机

### 问题2：推送失败，提示权限错误

- 确保使用的是个人访问令牌，而不是GitHub密码
- 检查令牌是否具有「repo」权限
- 确保远程仓库URL中的用户名和仓库名称正确

### 问题3：推送失败，提示分支不存在

- 尝试使用`git push -u origin master`（如果GitHub默认分支是master）
- 或者在GitHub仓库设置中更改默认分支名称

## 技术支持

如果遇到其他问题，您可以：

1. 查看GitHub官方文档：https://docs.github.com/en/get-started/getting-started-with-git
2. 搜索Stack Overflow相关问题
3. 参考Git官方文档：https://git-scm.com/doc

## 总结

按照本指南完成所有步骤后，您的Android扫雷项目将成功推送到GitHub，并且GitHub Actions会自动构建APK文件。您可以在Actions页面下载构建生成的APK，无需在本地配置复杂的开发环境。

祝您操作顺利！