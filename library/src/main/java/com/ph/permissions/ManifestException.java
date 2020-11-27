package com.ph.permissions;

/**
 *    author : i小灰
 *    github : https://github.com/dahui888
 *    time   : 2020/11/11
 *    desc   : 动态申请的权限没有在清单文件中注册会抛出的异常
 */
final class ManifestException extends RuntimeException {

    ManifestException() {
        // 清单文件中没有注册任何权限
        super("No permissions are registered in the manifest file");
    }

    ManifestException(String permission) {
        // 申请的危险权限没有在清单文件中注册
        super(permission + ": Permissions are not registered in the manifest file");
    }
}