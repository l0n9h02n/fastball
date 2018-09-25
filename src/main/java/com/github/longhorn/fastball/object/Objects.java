package com.github.longhorn.fastball.object;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.FluentPropertyBeanIntrospector;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.lang.reflect.InvocationTargetException;

public final class Objects {
    private static final BeanUtilsBean BUB;

    static {
        PropertyUtilsBean pub = new PropertyUtilsBean();
        pub.addBeanIntrospector(new FluentPropertyBeanIntrospector());
        BUB = new BeanUtilsBean(new ConvertUtilsBean(), pub) {
            @Override
            public void copyProperty(Object dest, String name, Object value)
                    throws IllegalAccessException, InvocationTargetException {
                if (value != null) {
                    super.copyProperty(dest, name, value);
                }
            }
        };
    }

    private Objects() {
    }

    /**
     * Merge the properties from the source to the destination, the null value property will be ignored.
     *
     * @param desc the destination object
     * @param src  the source object
     * @throws InvocationTargetException if the caller does not have access to the property accessor method
     * @throws IllegalAccessException    if the property accessor method throws an exception
     */
    public static void merge(Object desc, Object src) throws InvocationTargetException, IllegalAccessException {
        BUB.copyProperties(desc, src);
    }
}
