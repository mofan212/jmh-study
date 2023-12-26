package indi.mofan.util;

import java.lang.management.ManagementFactory;

/**
 * @author mofan
 * @date 2023/12/24 18:42
 */
public final class MyUtil {
    private MyUtil() {
    }

    public static void printRunningJvmName(String name) {
        System.out.println();
        System.out.println("---------------------------------------");
        System.out.println(name + " jvm name is : " + ManagementFactory.getRuntimeMXBean().getName());
        System.out.println("---------------------------------------");
        System.out.println();
    }
}
