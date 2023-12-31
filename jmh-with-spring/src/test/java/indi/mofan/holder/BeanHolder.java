package indi.mofan.holder;

/**
 * @author mofan
 * @date 2023/12/31 17:56
 */
public class BeanHolder {
    private static Object bean;

    public static void setBean(Object bean) {
        BeanHolder.bean = bean;
    }


    @SuppressWarnings("unchecked")
    public static <T> T getBean() {
        return (T) bean;
    }
}
