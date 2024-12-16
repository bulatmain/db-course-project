package com.bulatmain.conference.infastructure.repository.dao;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;
import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.infastructure.repository.db.CustomJdbcTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConferenceDAO implements ConferenceGateway {
    private final CustomJdbcTemplate source;
    private final ConferenceFromRS confMapping =
            new ConferenceFromRS();

    private final OrganizerFromRS orgMapping =
            new OrganizerFromRS();

    @Override
    public Optional<ConferenceDTO> findByOrganizerIdAndName(String organizerId, String name)
            throws GatewayException {
        String sql = """
                SELECT *
                FROM conference
                WHERE   organizer_id = ? AND
                        name = ?;
                """;
        try {
            var conferences = source.preparedStatement(sql, stmt -> {
                stmt.setString(1, organizerId);
                stmt.setString(2, name);
                var rs = stmt.executeQuery();
                return confMapping.convertCollection(rs);
            });
            if (conferences.isEmpty()) {
                return Optional.empty();
            }
            if (1 < conferences.size()) {
                log.debug("WARN: returned multiple conferences with same organizerId {} and name {}", organizerId, name);
            }
            return Optional.of(conferences.iterator().next());
        } catch (SQLException e) {
            throw new GatewayException(e.getMessage());
        }
    }

    @Override
    public Collection<ConferenceDTO> getConferences() throws GatewayException {
        String sql = """
                SELECT *
                FROM conference;
                """;
        try {
            return source.statement(stmt -> {
                return confMapping.convertCollection(stmt.executeQuery(sql));
            });
        } catch (SQLException e) {
            throw new GatewayException(e.getMessage());
        }
    }

    @Override
    public ConferenceDTO save(ConferenceCreateDTO dto) throws GatewayException {
        String sqlCheckOrganizer = """
                SELECT id
                FROM organizer
                WHERE id = ?
                """;
        String sqlInsertOrg = """
                INSERT INTO organizer(id)
                VALUES (?);
                """;
        String sqlInsertConf = """
                BEGIN
                    INSERT INTO conference(organizer_id, name)
                    VALUES (?, ?);
                    
                    SELECT *
                    FROM conference
                    WHERE   organizer_id = ? AND
                            name = ?;
                COMMIT;
                """;
        try {
            var organizerExists = source.preparedStatement(sqlCheckOrganizer, stmt -> {
                stmt.setString(1, dto.getOrganizerId());
                var rs = stmt.executeQuery();
                var organizers = orgMapping.convertCollection(rs);
                return !organizers.isEmpty();
            });
            if (!organizerExists) {
                var sql = makeTransaction(sqlInsertOrg, sqlInsertConf);
                return source.preparedStatement(sql, stmt -> {
                    stmt.setString(1, dto.getOrganizerId());
                    stmt.setString(2, dto.getOrganizerId());
                    stmt.setString(3, dto.getConferenceName());
                    return confMapping.convert(stmt.executeQuery());
                });
            } else {
                return source.preparedStatement(sqlInsertConf, stmt -> {
                    stmt.setString(1, dto.getOrganizerId());
                    stmt.setString(2, dto.getConferenceName());
                    return confMapping.convert(stmt.executeQuery());
                });
            }
        } catch (SQLException e) {
            throw  new GatewayException(e.getMessage());
        }
    }

    private String makeTransaction(String sql1, String sql2) {
        String builder = "BEGIN\n" +
                sql1 +
                ";\n" +
                sql2 +
                ";\nCOMMIT;";
        return builder;
    }

    private static class ConferenceFromRS implements ResultSetMapping<ConferenceDTO> {
        public ConferenceDTO convert(ResultSet rs) throws SQLException {
            return ConferenceDTO.builder()
                    .id(rs.getString("id"))
                    .organizerId(rs.getString("organizer_id"))
                    .conferenceName(rs.getString("name"))
                    .build();
        }
    }

    private static class OrganizerFromRS implements ResultSetMapping<OrganizerDTO> {
        @Override
        public OrganizerDTO convert(ResultSet rs) throws SQLException {
            return OrganizerDTO.builder()
                    .id(rs.getString("id"))
                    .build();
        }
    }
}
