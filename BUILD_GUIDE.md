# 安卓扫雷项目 - 本地编译指南

## 问题分析

从终端输出可以看出，当前环境中缺少以下配置：
1. **JAVA_HOME** 环境变量未设置
2. **Android SDK** 相关环境变量未设置
3. 系统PATH中未包含Java和Android SDK的可执行文件路径

## 解决方案

### 步骤1：安装Java JDK

1. 下载Java JDK 8或以上版本：
   - 推荐从Oracle官网下载：https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
   - 或使用OpenJDK：https://adoptopenjdk.net/

2. 安装JDK，记住安装路径（例如：`C:\Program Files\Java\jdk1.8.0_281`）

3. 配置环境变量：
   - 右键点击「此电脑」→「属性」→「高级系统设置」→「环境变量」
   - 在「系统变量」中点击「新建」：
     - 变量名：`JAVA_HOME`
     - 变量值：`C:\Program Files\Java\jdk1.8.0_281`（替换为您的实际安装路径）
   - 找到「Path」变量，点击「编辑」，添加：`%JAVA_HOME%\bin`

### 步骤2：安装Android SDK

1. 下载并安装Android Studio：https://developer.android.com/studio
2. 启动Android Studio，完成初始设置
3. 打开SDK Manager（菜单栏：Tools → SDK Manager）
4. 确保安装了以下组件：
   - Android SDK Build-Tools 30.0.3
   - Android SDK Platform 30
   - Android SDK Platform-Tools
   - Android Emulator

5. 配置环境变量：
   - 在「系统变量」中点击「新建」：
     - 变量名：`ANDROID_HOME`
     - 变量值：`C:\Users\<用户名>\AppData\Local\Android\Sdk`（替换为您的实际SDK路径）
   - 找到「Path」变量，点击「编辑」，添加：
     - `%ANDROID_HOME%\platform-tools`
     - `%ANDROID_HOME%\tools`
     - `%ANDROID_HOME%\tools\bin`

### 步骤3：验证环境配置

1. 打开新的命令提示符窗口
2. 运行以下命令验证配置：
   ```cmd
   java -version
   javac -version
   adb version
   sdkmanager --version
   ```

   所有命令都应该返回版本信息，没有错误。

### 步骤4：编译项目

1. 打开命令提示符，进入项目根目录：
   ```cmd
   cd c:\Users\Admin\Documents\trae_projects\saolei1
   ```

2. 运行Gradle构建命令：
   ```cmd
   gradlew assembleDebug
   ```

   构建成功后，APK文件将生成在：`app\build\outputs\apk\debug\app-debug.apk`

### 步骤5：安装并运行APK

#### 方法1：使用Android模拟器
1. 在Android Studio中启动模拟器
2. 运行以下命令安装APK：
   ```cmd
   adb install app\build\outputs\apk\debug\app-debug.apk
   ```
3. 在模拟器中找到「扫雷」应用并点击运行

#### 方法2：使用真实设备
1. 启用设备的开发者选项：
   - 进入「设置」→「关于手机」→「软件信息」
   - 连续点击「版本号」7次，直到提示开发者选项已启用
   - 返回「设置」，找到并进入「开发者选项」
   - 启用「USB调试」

2. 用USB线连接设备到电脑
3. 运行以下命令安装APK：
   ```cmd
   adb install app\build\outputs\apk\debug\app-debug.apk
   ```
4. 在设备上找到「扫雷」应用并点击运行

## 替代方案：使用Android Studio打开项目

如果上述命令行方式遇到困难，您可以直接使用Android Studio打开项目：

1. 启动Android Studio
2. 选择「Open an existing project」
3. 浏览到项目根目录：`c:\Users\Admin\Documents\trae_projects\saolei1`
4. 点击「OK」
5. Android Studio会自动配置项目并下载所需依赖
6. 点击「Run」按钮（绿色三角形）编译并运行项目

## 项目结构说明

```
saolei1/
├── app/                     # 应用模块
│   ├── build.gradle         # 模块构建配置
│   └── src/main/            # 主源码目录
│       ├── AndroidManifest.xml  # 应用清单文件
│       ├── java/com/example/saolei/  # Java源码
│       │   └── MainActivity.java      # 主活动类
│       └── res/             # 资源目录
│           ├── layout/      # 布局文件
│           │   └── activity_main.xml  # 主布局
│           └── values/      # 值资源
│               ├── colors.xml   # 颜色定义
│               └── strings.xml  # 字符串定义
├── build.gradle             # 项目构建配置
├── gradle/                  # Gradle包装器文件
└── gradlew                  # Gradle启动脚本
```

## 游戏功能说明

- **游戏规则**：10x10网格，15个地雷
- **操作方式**：
  - 点击单元格：揭示内容
  - 长按单元格：标记/取消标记地雷
- **游戏状态**：
  - 点击到地雷：游戏结束，显示所有地雷
  - 标记所有地雷或揭示所有安全单元格：游戏胜利

## 常见问题解决

1. **Gradle构建失败**：
   - 确保网络连接正常，Gradle需要下载依赖
   - 尝试删除`~/.gradle/caches`目录，然后重新构建

2. **ADB设备未检测到**：
   - 确保USB调试已启用
   - 尝试更换USB线或USB端口
   - 运行`adb kill-server`然后`adb start-server`重启ADB服务

3. **模拟器运行缓慢**：
   - 确保启用了硬件加速
   - 参考：https://developer.android.com/studio/run/emulator-acceleration

## 技术支持

如果您在编译或运行过程中遇到问题，可以：
- 查看Android Studio的Build窗口获取详细错误信息
- 检查项目根目录下的`build.log`文件
- 参考Android官方文档：https://developer.android.com/guide

祝您游戏开发愉快！