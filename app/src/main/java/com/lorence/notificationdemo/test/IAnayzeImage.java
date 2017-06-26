package com.lorence.notificationdemo.test;

import java.io.File;

/**
 * Created by enclaveit on 23/06/2017.
 */

public interface IAnayzeImage {
    abstract String getPathImageFromDevice();

    File getFileFromPath(String path);
}
