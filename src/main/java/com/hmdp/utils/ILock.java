package com.hmdp.utils;

public interface ILock {

    boolean tryLcok(long timeoutSec);

    void unLock();
}
