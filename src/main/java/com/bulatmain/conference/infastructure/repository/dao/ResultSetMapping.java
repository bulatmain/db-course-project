package com.bulatmain.conference.infastructure.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;

public interface ResultSetMapping<T> {
    T convert(ResultSet rs) throws SQLException;

    default Collection<T> convertCollection(ResultSet rs) throws SQLException {
        var set = new HashSet<T>();
        while (rs.next()) {
            set.add(convert(rs));
        }
        return set;
    }
}
