package com.bulatmain.conference.infastructure.repository.dao;

import com.bulatmain.conference.application.model.dto.organizer.OrganizerCreateDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;
import com.bulatmain.conference.application.port.gateway.OrganizerGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.infastructure.repository.db.CustomJdbcTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizerDAO implements OrganizerGateway {
    private final CustomJdbcTemplate source;
    private final OrganizerFromRS orgMapping =
            new OrganizerFromRS();
    @Override
    public Optional<OrganizerDTO> findById(String id) throws GatewayException {
        String sql = """
                SELECT id
                FROM organizer
                WHERE id = ?
                """;
        try {
            var organizers = source.preparedStatement(sql, stmt -> {
                stmt.setString(1, id);
                var rs = stmt.executeQuery();
                return orgMapping.convertCollection(rs);
            });
            if (organizers.isEmpty()) {
                return Optional.empty();
            }
            if (1 < organizers.size()) {
                log.debug("WARN: returned multiple organizers with same id {}", id);
            }
            return Optional.of(organizers.iterator().next());
        } catch (SQLException e) {
            throw  new GatewayException(e.getMessage());
        }
    }

    @Override
    public OrganizerDTO save(OrganizerCreateDTO dto) throws GatewayException {
        String sqlInsert = """
                INSERT INTO organizer(id)
                VALUES (?);
                """;
        try {
            source.preparedStatement(sqlInsert, stmt -> {
                stmt.setString(1, dto.getId());
                stmt.executeUpdate();
            });
            return findById(dto.getId()).get();
        } catch (SQLException e) {
            throw new GatewayException(e.getMessage());
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
