package com.bulatmain.conference.infastructure.repository.dao;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerCreateDTO;
import com.bulatmain.conference.application.model.dto.organizer.OrganizerDTO;
import com.bulatmain.conference.application.port.gateway.OrganizerGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.infastructure.repository.db.CustomJdbcTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class OrganizerDAO
        extends DAO<OrganizerDTO>
        implements OrganizerGateway {
    private final ConferenceDAO conferenceDAO;
    private final OrganizerFromRS mapping =
            new OrganizerFromRS();

    @Autowired
    public OrganizerDAO(CustomJdbcTemplate source, ConferenceDAO conferenceDAO) {
        super(source);
        this.conferenceDAO = conferenceDAO;
    }

    @Override
    public Optional<OrganizerDTO> findById(String id) throws GatewayException {
        String sql = """
                SELECT *
                FROM organizer
                WHERE id = ?
                """;

        var query = new QueryStatement<OrganizerDTO>(sql, List.of(id), mapping);

        return findUnique(
                query,
                String.format("WARN: returned multiple organizers with same id %s", id)
        );
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

    @Override
    public ConferenceDTO addConference(ConferenceCreateDTO conf) throws GatewayException {
        return conferenceDAO.save(conf);
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
