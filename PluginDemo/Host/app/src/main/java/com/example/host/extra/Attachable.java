package com.example.host.extra;

/**
 * Created by August on 2017/3/28.
 */

/**
 * 统一含有attach
 * @param <T>
 */
public interface Attachable<T> {
    void attach(T data);
}
