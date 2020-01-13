package com.amos.im.dao.api;

import java.util.List;

/**
 * DESCRIPTION: Redis 存储 Api
 *
 * @author <a href="mailto:amos.wang@xiaoi.com">amos.wang</a>
 * @date 1/13/2020
 */
public interface RedisCache<T> {

    String key();

    Integer index();

    void save(T data);

    void delete(T data);

    T value(Integer index);

    List<T> values(Integer index);

}
