package com.bulatmain.conference.infastructure.repository.dao;

import com.bulatmain.conference.application.model.dto.conference.ConferenceCreateDTO;
import com.bulatmain.conference.application.model.dto.conference.ConferenceDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkCreateDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkDTO;
import com.bulatmain.conference.application.port.gateway.ConferenceGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.domain.talk.entity.Talk;
import com.bulatmain.conference.infastructure.repository.db.CustomJdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ConferenceDAO
        extends DAO<ConferenceDTO>
        implements ConferenceGateway {
    private final TalkDAO talkDAO;
    private final ConferenceFromRS confMapping =
            new ConferenceFromRS();

    @Autowired
    public ConferenceDAO(CustomJdbcTemplate source, TalkDAO talkDAO) {
        super(source);
        this.talkDAO = talkDAO;
    }

    @Override
    public Optional<ConferenceDTO> findById(String conferenceId) throws GatewayException {
        String sql = """
                SELECT *
                FROM conference
                WHERE id = ?
                """;

        var query = new QueryStatement<ConferenceDTO>(sql, List.of(conferenceId), confMapping);

        return findUnique(
                query,
                String.format("WARN: returned multiple conferences with same id %s", conferenceId)
        );
    }
    @Override
    public Optional<ConferenceDTO> findByOrganizerIdAndName(String organizerId, String name)
            throws GatewayException {
        String sql = """
                SELECT *
                FROM conference
                WHERE   organizer_id = ? AND
                        name = ?;
                """;

        var query = new QueryStatement<ConferenceDTO>(sql, List.of(organizerId, name), confMapping);

        return findUnique(
                query,
                String.format(
                        "WARN: returned multiple conferences with same organizerId %s and name %s",
                        organizerId,
                        name
                )
        );
    }

    @Override
    public ConferenceDTO save(ConferenceCreateDTO dto) throws GatewayException {
        String sql = """
                INSERT INTO conference(id, organizer_id, name)
                VALUES (?, ?, ?);
                """;
        var id = dto.getId();
        var orgId = dto.getOrganizerId();
        var name = dto.getConferenceName();
        try {
            source.preparedStatement(sql, stmt -> {
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

    @Override
    public TalkDTO addTalk(TalkCreateDTO talkCreateDto) throws GatewayException {
        return talkDAO.save(talkCreateDto);
    }

    @Override
    public Collection<ConferenceDTO> getConferences() throws GatewayException {
        String sql = """
                SELECT *
                FROM conference;
                """;

        var query = new QueryStatement<ConferenceDTO>(sql, List.of(), confMapping);

        return findAll(query);
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
