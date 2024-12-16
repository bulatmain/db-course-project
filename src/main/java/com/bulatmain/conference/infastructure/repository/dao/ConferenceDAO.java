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

//    @Override
//    public Collection<ConferenceDTO> getConferences() throws GatewayException {
//        String sql = """
//                SELECT *
//                FROM conference;
//                """;
//        try {
//            return source.statement(stmt -> {
//                return confMapping.convertCollection(stmt.executeQuery(sql));
//            });
//        } catch (SQLException e) {
//            throw new GatewayException(e.getMessage());
//        }
//    }

    @Override
    public ConferenceDTO save(ConferenceCreateDTO dto) throws GatewayException {
        String sql = """
                INSERT INTO conference(id, organizer_id, name)
                VALUES (?, ?, ?);
                """;
        var orgId = dto.getOrganizerId();
        var name = dto.getConferenceName();
        try {
            source.preparedStatement(sql, stmt -> {
                var id = orgId + "$" + name;
                stmt.setString(1, id);
                stmt.setString(2, orgId);
                stmt.setString(3, name);
                stmt.executeUpdate();
            });
            return findByOrganizerIdAndName(orgId, name).get();
        } catch (SQLException e) {
            throw new GatewayException(e.getMessage());
        }
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


}
