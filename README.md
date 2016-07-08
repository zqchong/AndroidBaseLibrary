# AndroidBaseLibrary
我场 Android 平台基础库

# Usage

1. app/build.gradle:

`compile 'com.licaigc:androidbaselibrary:+'`

2. app/XxxApplication.java:

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

# Maintainer
得明(songdeming@licaigc.com)