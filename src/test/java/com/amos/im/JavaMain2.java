package com.amos.im;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Objects;

/**
 * PROJECT: im
 * DESCRIPTION: note
 *
 * @author dell
 * @date 2019/3/27
 */
public class JavaMain2 {

    public static void main(String[] args) {
        HashSet<User> hashSet = new HashSet<>();
        hashSet.add(new User().setId(1).setName("333"));
        hashSet.add(new User().setId(1).setName("333"));
        System.out.println(new User().setId(1).setName("333").hashCode());
        System.out.println(new User().setId(1).setName("333").hashCode());
        System.out.println(hashSet);
    }

    @Data
    @Accessors(chain = true)
    private static class User {
        private int id;
        private String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return id == user.id &&
                    Objects.equals(name, user.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

}
