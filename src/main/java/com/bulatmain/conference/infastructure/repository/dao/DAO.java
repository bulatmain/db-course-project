package com.bulatmain.conference.infastructure.repository.dao;

import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.infastructure.repository.db.CustomJdbcTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class DAO<T> {
    protected final CustomJdbcTemplate source;
    public Optional<T> findUnique(QueryStatement<T> queryStatement, String warnMessage)
            throws GatewayException {
        try {
            var dtos = queryStatement.executeWith(source);
            if (dtos.isEmpty()) {
                return Optional.empty();
            }
            if (1 < dtos.size()) {
                log.debug(warnMessage);
            }
            return Optional.of(dtos.iterator().next());
        } catch (SQLException e) {
            throw new GatewayException(e.getMessage());
        }
    }

    protected static class QueryStatement<T> {
        public QueryStatement(String sql, List<String> args, ResultSetMapping<T> mapping) {
            this.sql = sql;
            this.func = stmt -> {
                formStatement(stmt, args);
                var rs = stmt.executeQuery();
                return mapping.convertCollection(rs);
            };
        }

        private static void formStatement(PreparedStatement stmt, List<String> args)
                throws SQLException {
            int i = 1;
            var arg = args.listIterator(0);
            while (arg.hasNext()) {
                stmt.setString(i, arg.next());
                ++i;
            }
        }
        private final String sql;
        private final CustomJdbcTemplate.SQLFunction<PreparedStatement, Collection<T>> func;

        public Collection<T> executeWith(CustomJdbcTemplate source)
                throws SQLException {
            return source.preparedStatement(sql, func);
        }
    }
}
