package com.licaigc.collection;

import java.util.Collection;

/**
 * Created by walfud on 2015/9/21.
 */
public class ForEach {

    /**
     * 求和函数. 目前仅支持 `int / long / float / double` 以及其对象类型. 不支持的类型直接返回 `initValue`
     * @param collection
     * @param initValue
     * @param <T>
     * @return
     */
    public static <T extends Number> T accumulate(Collection<T> collection, T initValue) {
        if (collection == null) {
            return initValue;
        }

        T result = initValue;
        for (T t : collection) {
            if (false) {
                // Stub
            } else if (initValue instanceof Integer) {
                Integer foo = (Integer) result;
                Integer bar = (Integer) t;
                foo += bar;
                result = (T) foo;
            } else if (initValue instanceof Long) {
                Long foo = (Long) result;
                Long bar = (Long) t;
                foo += bar;
                result = (T) foo;
            } else if (initValue instanceof Float) {
                Float foo = (Float) result;
                Float bar = (Float) t;
                foo += bar;
                result = (T) foo;
            } else if (initValue instanceof Double) {
                Double foo = (Double) result;
                Double bar = (Double) t;
                foo += bar;
                result = (T) foo;
            } else {
                // Nothing
            }
        }
        return result;
    }
}
