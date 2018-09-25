package com.github.longhorn.fastball.object;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class ObjectsTest {
    @Test
    public void testMerge() {
        Foo src = new Foo().setName("name").setBars(Arrays.asList(new Bar().setAddress("addr")));
        Foo dest = new Foo().setAge(18);
        try {
            Objects.merge(dest, src);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        assertThat(dest.getName()).isEqualTo("name");
        assertThat(dest.getAge()).isEqualTo(18);
        assertThat(dest.getBars().get(0).getAddress()).isEqualTo("addr");
    }

    public class Foo {
        private String name;
        private Integer age;
        private List<Bar> bars;

        public String getName() {
            return name;
        }

        public Foo setName(String name) {
            this.name = name;
            return this;
        }

        public Integer getAge() {
            return age;
        }

        public Foo setAge(Integer age) {
            this.age = age;
            return this;
        }

        public List<Bar> getBars() {
            return bars;
        }

        public Foo setBars(List<Bar> bars) {
            this.bars = bars;
            return this;
        }
    }

    public class Bar {
        private String address;

        public String getAddress() {
            return address;
        }

        public Bar setAddress(String address) {
            this.address = address;
            return this;
        }
    }
}
