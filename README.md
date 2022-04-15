# 权限请求框架

![](logo.png)

* 项目地址：[Github](https://github.com/dahui888/HPermissions)

* [点击此处可直接下载](https://github.com/dahui888/HPermissions/blob/master/HPermissions.apk)

![](picture/demo_code.png)

![](picture/1.jpg) ![](picture/2.jpg) ![](picture/3.jpg)

![](picture/4.jpg) ![](picture/5.jpg) ![](picture/6.jpg)

![](picture/7.jpg) ![](picture/8.jpg) ![](picture/9.jpg)

![](picture/10.jpg) ![](picture/11.jpg)

#### 集成步骤

* 如果你的项目 Gradle 配置是在 `7.0 以下`，需要在 `build.gradle` 文件中加入

```groovy
allprojects {
    repositories {
        // JitPack 远程仓库：https://jitpack.io
        maven { url 'https://jitpack.io' }
    }
}
```

* 如果你的 Gradle 配置是 `7.0 及以上`，则需要在 `settings.gradle` 文件中加入

```groovy
dependencyResolutionManagement {
    repositories {
        // JitPack 远程仓库：https://jitpack.io
        maven { url 'https://jitpack.io' }
    }
}
```

* 配置完远程仓库后，在项目 app 模块下的 `build.gradle` 文件中加入远程依赖

```groovy
android {
    // 支持 JDK 1.8
    compileOptions {
        targetCompatibility JavaVersion.VERSION_1_8
        sourceCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    // 权限请求框架：https://github.com/dahui888/HPermissions
       implementation 'com.github.dahui888:HPermissions:v1.2'
}
```

#### AndroidX

* 如果项目是基于 **AndroidX** 包，请在项目 `gradle.properties` 文件中加入

```text
# 表示将第三方库迁移到 AndroidX
android.enableJetifier = true
```

* 如果项目是基于 **Support** 包则不需要加入此配置

#### 分区存储

* 如果项目已经适配了 Android 10 分区存储特性，请在 `AndroidManifest.xml` 中加入

```xml
<manifest>

    <application>

        <!-- 表示当前项目已经适配了分区存储特性 -->
        <meta-data
            android:name="ScopedStorage"
            android:value="true" />

    </application>

</manifest>
```

* 如果当前项目没有适配这特性，那么这一步骤可以忽略

* 需要注意的是：这个选项是框架用于判断当前项目是否适配了分区存储，需要注意的是，如果你的项目已经适配了分区存储特性，可以使用 `READ_EXTERNAL_STORAGE`、`WRITE_EXTERNAL_STORAGE` 来申请权限，如果你的项目还没有适配分区特性，就算申请了 `READ_EXTERNAL_STORAGE`、`WRITE_EXTERNAL_STORAGE` 权限也会导致无法正常读取外部存储上面的文件，如果你的项目没有适配分区存储，请使用 `MANAGE_EXTERNAL_STORAGE` 来申请权限，这样才能正常读取外部存储上面的文件，你如果想了解更多关于 Android 10 分区存储的特性，可以[点击此处查看和学习](https://www.jianshu.com/p/9a9d260e10b0)。

#### 一句代码搞定权限请求，从未如此简单

* Java 用法示例

```java
HPermissions.with(this)
        // 申请单个权限
        .permission(Permission.RECORD_AUDIO)
        // 申请多个权限
        .permission(Permission.Group.CALENDAR)
        // 设置权限请求拦截器（局部设置）
        //.interceptor(new PermissionInterceptor())
        // 设置不触发错误检测机制（局部设置）
        //.unchecked()
        .request(new OnPermissionCallback() {

            @Override
            public void onGranted(List<String> permissions, boolean all) {
                if (all) {
                    toast("获取录音和日历权限成功");
                } else {
                    toast("获取部分权限成功，但部分权限未正常授予");
                }
            }

            @Override
            public void onDenied(List<String> permissions, boolean never) {
                if (never) {
                    toast("被永久拒绝授权，请手动授予录音和日历权限");
                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                    HPermissions.startPermissionActivity(context, permissions);
                } else {
                    toast("获取录音和日历权限失败");
                }
            }
        });
```

* Kotlin 用法示例

```kotlin
HPermissions.with(this)
    // 申请单个权限
    .permission(Permission.RECORD_AUDIO)
    // 申请多个权限
    .permission(Permission.Group.CALENDAR)
    // 设置权限请求拦截器（局部设置）
    //.interceptor(new PermissionInterceptor())
    // 设置不触发错误检测机制（局部设置）
    //.unchecked()
    .request(object : OnPermissionCallback {

        override fun onGranted(permissions: MutableList<String>, all: Boolean) {
            if (all) {
                toast("获取录音和日历权限成功")
            } else {
                toast("获取部分权限成功，但部分权限未正常授予")
            }
        }

        override fun onDenied(permissions: MutableList<String>, never: Boolean) {
            if (never) {
                toast("被永久拒绝授权，请手动授予录音和日历权限")
                // 如果是被永久拒绝就跳转到应用权限系统设置页面
                HPermissions.startPermissionActivity(context, permissions)
            } else {
                toast("获取录音和日历权限失败")
            }
        }
    })
```

#### 从系统权限设置页返回判断

```java
public class XxxActivity extends AppCompatActivity {

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HPermissions.REQUEST_CODE) {
            if (HPermissions.isGranted(this, Permission.RECORD_AUDIO) &&
                    HPermissions.isGranted(this, Permission.Group.CALENDAR)) {
                toast("用户已经在权限设置页授予了录音和日历权限");
            } else {
                toast("用户没有在权限设置页授予权限");
            }
        }
    }
}
```

#### 框架其他 API 介绍

```java
// 判断一个或多个权限是否全部授予了
HPermissions.isGranted(Context context, String... permissions);

// 获取没有授予的权限
HPermissions.getDenied(Context context, String... permissions);

// 判断某个权限是否为特殊权限
HPermissions.isSpecial(String permission);

// 判断一个或多个权限是否被永久拒绝了
HPermissions.isPermanentDenied(Activity activity, String... permissions);

// 跳转到应用权限设置页
HPermissions.startPermissionActivity(Context context, String... permissions);
HPermissions.startPermissionActivity(Activity activity, String... permissions);
HPermissions.startPermissionActivity(Activity activity, String... permission, OnPermissionPageCallback callback);
HPermissions.startPermissionActivity(Fragment fragment, String... permissions);
HPermissions.startPermissionActivity(Fragment fragment, String... permissions, OnPermissionPageCallback callback);

// 设置不触发错误检测机制（全局设置）
HPermissions.setCheckMode(false);
// 设置权限申请拦截器（全局设置）
HPermissions.setInterceptor(new IPermissionInterceptor() {});
```

#### 关于权限监听回调参数说明

* 我们都知道，如果用户全部授予只会调用 **onGranted** 方法，如果用户全部拒绝只会调用 **onDenied** 方法。

* 但是还有一种情况，如果在请求多个权限的情况下，这些权限不是被全部授予或者全部拒绝了，而是部分授权部分拒绝这种情况，框架会如何处理回调呢？

* 框架会先调用 **onDenied** 方法，再调用 **onGranted** 方法。其中我们可以通过 **onGranted** 方法中的 **all** 参数来判断权限是否全部授予了。

* 如果想知道回调中的某个权限是否被授权或者拒绝，可以调用 **List** 类中的 **contains(Permission.XXX)** 方法来判断这个集合中是否包含了这个权限。

### [其他常见疑问请点击此处查看](HelpDoc.md)

#### 同类权限请求框架之间的对比

|     适配细节    | [HPermissions](https://github.com/dahui888/HPermissions)  | [AndPermission](https://github.com/yanzhenjie/AndPermission) | [PermissionX](https://github.com/guolindev/PermissionX) |  [AndroidUtilCode](https://github.com/Blankj/AndroidUtilCode)   | [RxPermissions](https://github.com/tbruyelle/RxPermissions) | [PermissionsDispatcher](https://github.com/permissions-dispatcher/PermissionsDispatcher) |  [EasyPermissions](https://github.com/googlesamples/easypermissions) |
| :--------: | :------------: | :------------: | :------------: | :------------: | :------------: | :------------: | :------------: |
|    对应版本  |  1.2 |  2.0.3  |  1.6.1    |  1.31.0    |  0.12   |   4.9.1  |  3.0.0   |
|    issues 数   |  [![](https://img.shields.io/github/issues/dahui888/HPermissions.svg)](https://github.com/dahui888/HPermissions/issues)  |  [![](https://img.shields.io/github/issues/yanzhenjie/AndPermission.svg)](https://github.com/yanzhenjie/AndPermission/issues)  |  [![](https://img.shields.io/github/issues/guolindev/PermissionX.svg)](https://github.com/guolindev/PermissionX/issues)  |  [![](https://img.shields.io/github/issues/Blankj/AndroidUtilCode.svg)](https://github.com/Blankj/AndroidUtilCode/issues)  |  [![](https://img.shields.io/github/issues/tbruyelle/RxPermissions.svg)](https://github.com/tbruyelle/RxPermissions/issues)  |  [![](https://img.shields.io/github/issues/permissions-dispatcher/PermissionsDispatcher.svg)](https://github.com/permissions-dispatcher/PermissionsDispatcher/issues)  |  [![](https://img.shields.io/github/issues/googlesamples/easypermissions.svg)](https://github.com/googlesamples/easypermissions/issues)  |
|    框架体积  |  37 KB  | 127 KB  |  78 KB  |   500 KB |  28 KB  | 91 KB  | 48 KB |
|       闹钟提醒权限       |  ✅  |  ❌  |  ❌  |  ❌  |  ❌  |  ❌  |  ❌  |
|     所有文件管理权限      |  ✅  |  ❌  |  ✅  |  ❌  |  ❌  |  ❌  |  ❌  |
|        安装包权限        |  ✅  |  ✅  |  ✅  |  ❌  |  ❌  |  ❌  |  ❌  |
|        悬浮窗权限        |  ✅  |  ✅  |  ✅  |  ✅  |  ❌  |  ✅  |  ❌  |
|       系统设置权限       |  ✅  |  ✅  |  ✅  |  ✅  |  ❌  |  ✅  |  ❌  |
|        通知栏权限        |  ✅  |  ✅  |  ❌  |  ❌  |  ❌  |  ❌  |  ❌  |
|       通知栏监听权限      |  ✅  |  ✅  |  ❌  |  ❌  |  ❌  |  ❌  |  ❌  |
|         勿扰权限         |  ✅  |  ❌  |  ❌  |  ❌  |  ❌  |  ❌  |  ❌  |
|     查看应用使用情况权限   |  ✅  |  ❌  |  ❌  |  ❌  |  ❌  |  ❌  |  ❌  |
|    Android 12 危险权限   |  ✅  |  ❌  |  ✅  |  ❌ |  ❌  |   ❌  |  ❌  |
|    Android 11 危险权限   |  ✅  |  ❌  |  ✅  |  ❌ |  ❌  |   ❌  |  ❌  |
|    Android 10 危险权限   |  ✅  |  ✅  |  ✅  |  ❌ |  ❌  |   ✅  |  ❌  |
|    Android 9.0 危险权限  |  ✅  |  ❌  |  ✅  |  ❌ |  ❌  |   ✅  |  ❌  |
|    Android 8.0 危险权限  |  ✅  |  ✅  |  ✅  |  ❌ |  ❌  |   ✅  |  ❌  |
|    新权限自动兼容旧设备    |  ✅  |  ❌  |  ❌  |  ❌ |  ❌  |  ❌   |  ❌  |
|    屏幕方向旋转场景适配    |  ✅  |  ✅  |  ✅  |  ❌ |  ❌  |  ✅   |  ❌  |
|    后台申请权限场景适配    |  ✅  |  ❌  |  ❌  |  ❌ |  ❌  |  ❌   |  ❌  |
|       错误检测机制        |  ✅  |  ❌  |  ❌  |  ❌ |  ❌  |  ❌   |  ❌  |

#### 新权限自动兼容旧设备介绍

* 随着 Android 版本的不断更新，危险权限和特殊权限也在增加，那么这个时候会有一个版本兼容问题，高版本的安卓设备是支持申请低版本的权限，但是低版本的安卓设备是不支持申请高版本的权限，那么这个时候会出现一个兼容性的问题。

* 经过核查，其他权限框架选择了一种最简单粗暴的方式，就是不去做兼容，而是交给外层的调用者做兼容，需要调用者在外层先判断安卓版本，在高版本上面传入新权限给框架，而在低版本上面传入旧权限给框架，这种方式看似简单粗暴，但是开发体验差，同时也暗藏了一个坑，外层的调用者他们知道这个新权限对应着的旧权限是哪个吗？我觉得不是每个人都知道，而一旦认知出现错误，必然会导致结果出现错误。

* 我觉得最好的做法是交给框架来做，**HPermissions** 正是那么做的，外层调用者申请高版本权限的时候，那么在低版本设备上面，会自动添加低版本的权限进行申请，举个最简单的例子，Android 11 出现的 `MANAGE_EXTERNAL_STORAGE` 新权限，如果是在 Android 10 及以下的设备申请这个权限时，框架会自动添加 `READ_EXTERNAL_STORAGE` 和 `WRITE_EXTERNAL_STORAGE` 进行申请，在 Android 10 及以下的设备上面，我们可以直接把 `MANAGE_EXTERNAL_STORAGE` 当做 `READ_EXTERNAL_STORAGE` 和 `WRITE_EXTERNAL_STORAGE` 来用，因为 `MANAGE_EXTERNAL_STORAGE` 能干的事情，在 Android 10 及以下的设备上面，要用 `READ_EXTERNAL_STORAGE` 和 `WRITE_EXTERNAL_STORAGE` 才能做得了。

* 所以大家在使用 **HPermissions** 的时候，直接拿新的权限去申请就可以了，完全不需要关心新旧权限的兼容问题，框架会自动帮你做处理的，与其他框架不同的是，我更想做的是让大家一句代码搞定权限请求，框架能做到的，统统交给框架做处理。

#### 屏幕旋转场景适配介绍

* 当系统权限申请对话框弹出后对 Activity 进行屏幕旋转，会导致权限申请回调失效，因为屏幕旋转会导致框架中的 Fragment 销毁重建，这样会导致里面的回调对象直接被回收，最终导致回调不正常。解决方案有几种，一是在清单文件中添加  `android:configChanges="orientation"` 属性，这样屏幕旋转时不会导致 Activity 和 Fragment 销毁重建，二是直接在清单文件中固定 Activity 显示的方向，但是以上两种方案都要使用框架的人处理，这样显然是不够灵活的，解铃还须系铃人，框架的问题应当由框架来解决，而 **RxPermissions** 的解决方式是给 PermissionFragment 对象设置 `fragment.setRetainInstance(true)`，这样就算屏幕旋转了，Activity 对象会销毁重建，而 Fragment 也不会跟着销毁重建，还是复用着之前那个对象，但是存在一个问题，如果 Activity 重写了 **onSaveInstanceState** 方法会直接导致这种方式失效，这样做显然只是治标不治本，而 **HPermissions** 的方式会更直接点，在 **PermissionFragment** 绑定到 Activity 上面时，把当前 Activity 的**屏幕方向固定住**，在权限申请结束后再把**屏幕方向还原回去**。

* 在所有的权限请求框架中，只要使用了 Fragment 申请权限都会出现这个问题，而 AndPermission 其实是通过创建新的 Activity 来申请权限，所以不会出现这个问题，PermissionsDispatcher 则是采用了 APT 生成代码的形式来申请权限，所以也没有这个问题，而 PermissionX 则是直接借鉴了 HPermissions的解决方案

#### 后台申请权限场景介绍

* 当我们做耗时操作之后申请权限（例如在闪屏页获取隐私协议再申请权限），在网络请求的过程中将 Activity 返回桌面去（退到后台），然后会导致权限请求是在后台状态中进行，在这个时机上就可能会导致权限申请不正常，表现为不会显示授权对话框，处理不当的还会导致崩溃，例如 [RxPeremission/issues/249](https://github.com/tbruyelle/RxPermissions/issues/249)。原因在于框架中的 PermissionFragment 在 **commit / commitNow** 到 Activity 的时候会做一个检测，如果 Activity 的状态是不可见时则会抛出异常，而 **RxPeremission** 正是使用了 **commitNow** 才会导致崩溃 ，使用 **commitAllowingStateLoss / commitNowAllowingStateLoss** 则可以避开这个检测，虽然这样可以避免崩溃，但是会出现另外一个问题，系统提供的 **requestPermissions** API 在 Activity 不可见时调用也不会弹出授权对话框，**HPermissions** 的解决方式是将 **requestPermissions** 时机从 **create** 转移到了 **resume**，因为 Activity 和 Fragment 的生命周期方法是捆绑在一起的，如果 Activity 是不可见的，那么就算创建了 Fragment 也只会调用 **onCreate** 方法，而不会去调用它的 **onResume** 方法，最后当 Activity 从后台返回到前台时，不仅会触发 **Activity.onResume** 方法，同时也会触发 **PermissionFragment** 的 **onResume** 方法，在这个方法申请权限就可以保证最终 **requestPermissions** 申请的时机是在 Activity **处于可见状态的情况**下。

#### 错误检测机制介绍

* 在框架的日常维护中，有很多人跟我反馈过框架有 Bug，但是经过排查和定位发现，这其中有 95% 的问题来自于调用者一些不规范操作导致的，这不仅对我造成很大的困扰，同时也极大浪费了很多小伙伴的时间和精力，于是我在框架中加入了很多审查元素，在 **debug 模式**、**debug 模式**、**debug 模式** 下，一旦有某些操作不符合规范，那么框架会直接抛出异常给调用者，并在异常信息中正确指引调用者纠正错误，例如：

    * 传入的 Context 实例不是 Activity 对象，框架会抛出异常，又或者传入的 Activity 的状态异常（已经 **Finishing** 或者 **Destroyed**），这种情况一般是在异步申请权限导致的，框架也会抛出异常，请在合适的时机申请权限，如果申请的时机无法预估，请在外层做好  Activity 状态判断再进行权限申请。

    * 如果调用者没有传入任何权限就申请权限的话，框架会抛出异常，又或者如果调用者传入的权限不是危险权限或者特殊权限，框架也会抛出异常，因为有的人会把普通权限当做危险权限传给框架，系统会直接拒绝。

    * 如果当前项目在没有适配分区存储的情况下，申请 `READ_EXTERNAL_STORAGE` 和 `WRITE_EXTERNAL_STORAGE` 权限

        * 当项目的 `targetSdkVersion >= 29` 时，需要在清单文件中注册 `android:requestLegacyExternalStorage="true"` 属性，否则框架会抛出异常，如果不加会导致一个问题，明明已经获取到存储权限，但是无法在 Android 10 的设备上面正常读写外部存储上的文件。

        * 当项目的 `targetSdkVersion >= 30` 时，则不能申请 `READ_EXTERNAL_STORAGE` 和 `WRITE_EXTERNAL_STORAGE` 权限，而是应该申请 `MANAGE_EXTERNAL_STORAGE` 权限

        * 如果当前项目已经适配了分区存储，那么只需要在清单文件中注册一个 meta-data 属性即可： `<meta-data android:name="ScopedStorage" android:value="true" />`

    * 如果申请的权限中包含后台定位权限， 那么这里面则不能包含和定位无关的权限，否则框架会抛出异常，因为 `ACCESS_BACKGROUND_LOCATION` 和其他非定位权限定位掺杂在一起申请，在 Android 11 上会出现不申请直接被拒绝的情况。

    * 如果申请的权限和项目中的 **targetSdkVersion** 对不上，框架会抛出异常，是因为 **targetSdkVersion** 代表着项目适配到哪个 Android 版本，系统会自动做向下兼容，假设申请的权限是 Android 11 才出现的，但是 **targetSdkVersion** 还停留在 29，那么在某些机型上的申请，会出现授权异常的情况，也就是用户明明授权了，但是系统返回的始终是 false。

    * 如果动态申请的权限没有在 `AndroidManifest.xml` 中进行注册，框架会抛出异常，因为如果不这么做，是可以进行申请权限，但是不会出现授权弹窗，直接被系统拒绝，并且系统不会给出任何弹窗和提示，并且这个问题在每个机型上面都是**必现的**。

    * 如果动态申请的权限有在 `AndroidManifest.xml` 中进行注册，但是设定了不恰当的 `android:maxSdkVersion` 属性值，框架会抛出异常，举个例子：`<uses-permission android:name="xxxx" android:maxSdkVersion="29" />`，这样的设定会导致在 Android 11 （`Build.VERSION.SDK_INT >= 30`）及以上的设备申请权限，系统会认为这个权限没有在清单文件中注册，直接拒绝本次的权限申请，并且也是不会给出任何弹窗和提示，这个问题也是必现的。

    * 如果你同时申请了 `MANAGE_EXTERNAL_STORAGE`、`READ_EXTERNAL_STORAGE`、`WRITE_EXTERNAL_STORAGE` 这三个权限，框架会抛出异常，告诉你不要同时申请这三个权限，这是因为在 Android 11 及以上设备上面，申请了 `MANAGE_EXTERNAL_STORAGE` 权限，则没有申请 `READ_EXTERNAL_STORAGE`、`WRITE_EXTERNAL_STORAGE` 权限的必要，这是因为申请了 `MANAGE_EXTERNAL_STORAGE` 权限，就等于拥有了比 `READ_EXTERNAL_STORAGE`、`WRITE_EXTERNAL_STORAGE` 更加强大的能力，如果硬要那么做反而适得其反，假设框架允许的情况下，会同时出现两种授权方式，一种是弹窗授权，另一种是跳页面授权，用户要进行两次授权，但是实际上面有了 `MANAGE_EXTERNAL_STORAGE` 权限就满足使用了，这个时候大家可能心中有一个疑问了，你不申请 `READ_EXTERNAL_STORAGE`、`WRITE_EXTERNAL_STORAGE` 权限，Android 11 以下又没有 `MANAGE_EXTERNAL_STORAGE` 这个权限，那不是会有问题？关于这个问题大家可以放心，框架会做判断，如果你申请了 `MANAGE_EXTERNAL_STORAGE` 权限，在 Android 11 以下框架会自动添加 `READ_EXTERNAL_STORAGE`、`WRITE_EXTERNAL_STORAGE` 来申请，所以在低版本下也不会因为没有权限导致的无法使用。

    * 如果你不需要上面这些检测，可通过调用 `unchecked` 方法来关闭，但是需要注意的是，我并不建议你去关闭这个检测，因为在 **release 模式** 时它是关闭状态，不需要你手动关闭，而它只在 **debug 模式** 下才会触发这些检测。

* 出现这些问题的原因是，我们对这些机制不太熟悉，而如果框架不加以限制，那么引发各种奇奇怪怪的问题出现，作为框架的作者，表示不仅你们很痛苦，作为框架作者表示也很受伤。因为这些问题不是框架导致的，而是调用者的某些操作不规范导致的。我觉得这个问题最好的解决方式是，由框架做统一的检查，因为我是框架的作者，对权限申请这块知识点有**较强的专业能力和足够的经验**，知道什么该做，什么不该做，这样就可以对这些骚操作进行一一拦截。

* 当权限申请出现问题时，它会提醒你，告诉你哪里错了该怎么去纠正。帮助大家少走弯路。

#### 框架亮点

* 适配 Android 12 的权限请求框架

* 适配所有 Android 版本的权限请求框架

* 简洁易用：采用链式调用的方式，使用只需一句代码

* 体积感人：功能在同类框架中是最全的，但是框架体积是垫底的

* 适配极端情况：无论在多么极端恶劣的环境下申请权限，框架依然坚挺

* 向下兼容属性：新权限在旧系统可以正常申请，框架会做自动适配，无需调用者适配

* 自动检测错误：如果出现错误框架会主动抛出异常给调用者（仅在 Debug 下判断，把 Bug 扼杀在摇篮中）

## License

```text
Copyright 2018 iXiao Hui

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```