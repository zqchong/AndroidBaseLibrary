# AndroidBaseLibrary [![Build Status](https://travis-ci.org/wealthworks/AndroidBaseLibrary.svg?branch=master)](https://travis-ci.org/wealthworks/AndroidBaseLibrary)
我场 Android 平台基础库

# Usage

app/**build.gradle:**

`compile 'com.licaigc:androidbaselibrary:+'`

建议各位在理解 [Semantic Versioning](http://semver.org/) 的前提下, 根据版本号合理规划引入方式. 我介绍几种常见的场景:

1. 需要某个稳定的功能: (适合大部分使用者)
`compile 'com.licaigc:androidbaselibrary:1.2.+'`
2. 希望使用最新加入的功能:
`compile 'com.licaigc:androidbaselibrary:1.+'`
3. 希望体验最新的功能和语法:
`compile 'com.licaigc:androidbaselibrary:+'`

app/**XxxApplication.java:**

```java
public class TimiApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ...

        AndroidBaseLibrary.initialize(getApplicationContext());

        ...
    }
}
```

具体使用, 请见 [wiki](https://github.com/wealthworks/AndroidBaseLibrary/wiki).

# Branch Management
master: 作为主要开发分支.

tag: 作为发布分支.

# Coding Style
```java
public class Foo {
    public static final String TAG = "Foo";

    public static final int DEFAULT_VALUE = 0;
    private static final String PREFS_BAR = "...";

    private long mId;
    private TextView mNameTv;

    public Foo() {

    }
    public Foo(id) {
        mId = id;
    }

    // Function
    public void foo() {

    }

    public void bar() {

    }

    // internal
    private void baz() {

    }

    protected void xyz() {

    }

    // Helper
    private static Foo sInstance;
    public static Foo getInstance() {
        ...
    }
}
```

# Contribution
发起 PR, 我会尽快 Merge.
请注意, 新增功能请写注释, 并**在 wiki 中补充说明**.

# Maintainer
得明(songdeming@licaigc.com)

# Powered by
[![licaigc](https://raw.githubusercontent.com/wealthworks/AndroidBaseLibrary/master/doc/licaigc.png)](http://www.talicai.com/)
