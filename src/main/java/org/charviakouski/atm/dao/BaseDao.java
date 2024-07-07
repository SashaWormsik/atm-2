package org.charviakouski.atm.dao;

import java.io.IOException;
import java.util.List;

public interface BaseDao<T> {

    List<String> read() throws IOException;

    void save(T object) throws IOException;

}
