package com.bulatmain.conference.infastructure.repository.dao;

import com.bulatmain.conference.application.model.dto.map.TalkMapper;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerCreateDTO;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkBriefDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkCreateDTO;
import com.bulatmain.conference.application.model.dto.talk.TalkDTO;
import com.bulatmain.conference.application.port.gateway.TalkGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.infastructure.repository.db.CustomJdbcTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class TalkDAO
        extends DAO<TalkDTO>
        implements TalkGateway {
    private final SpeakerDAO speakerDAO;
    private final TalkMapper dtoMapper;
    private final TalkFromRS rsMapping =
            new TalkFromRS();

    @Autowired
    public TalkDAO(
            CustomJdbcTemplate source,
            SpeakerDAO speakerDAO,
            TalkMapper dtoMapper
    ) {
        super(source);
        this.speakerDAO = speakerDAO;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public Optional<TalkDTO> findByConfIdAndName(String conferenceId, String name)
            throws GatewayException {
        String sql = """
                SELECT *
                FROM talk
                WHERE   conference_id = ? and
                        name = ?
                """;
        var query = new QueryStatement<TalkDTO>(sql, List.of(conferenceId, name), rsMapping);

        var dtoOpt = findUnique(
                query,
                String.format(
                        "WARN: find multiple talks with same conference_id %s and name %s",
                        conferenceId,
                        name
                )
        );
        if (dtoOpt.isEmpty()) {
            return dtoOpt;
        }
        var dto = dtoOpt.get();
        var speakerIds = speakerDAO.getIdsByTalkId(dto.getId());
//        var listenerIds = listenerDAO.getIdsByTalkId(id);
        dto.setSpeakerIds(speakerIds);
        dto.setListenerIds(List.of());
        return Optional.of(dto);
    }

    @Override
    public Collection<TalkBriefDTO> findByConfId(String conferenceId)
            throws GatewayException {
        String sql = """
                SELECT *
                FROM talk
                WHERE conference_id = ?;
                """;
        var query = new QueryStatement<TalkDTO>(sql, List.of(conferenceId), rsMapping);
        return findAll(query).stream()
                .map(dtoMapper::dtoToBrief)
                .toList();
    }

    @Override
    public Collection<String> getIdsByConfId(String conferenceId) throws GatewayException {
        String sql = """
                SELECT id
                FROM talk
                WHERE conference_id = ?;
                """;
        try {
            return source.preparedStatement(sql, stmt -> {
                stmt.setString(1, conferenceId);
                var rs = stmt.executeQuery();
                var ids = new LinkedList<String>();
                while (rs.next()) {
                    ids.add(rs.getString("id"));
                }
                return ids;
            });
        } catch (SQLException e) {
            throw new GatewayException(e.getMessage());
        }
    }

    @Override
    public Collection<TalkBriefDTO> getTalks() throws GatewayException {
        String sql = """
                SELECT *
                FROM talk;
                """;

        var query = new QueryStatement<TalkDTO>(sql, List.of(), rsMapping);

        var dtos = findAll(query);
        return dtos.stream().map(dtoMapper::dtoToBrief).toList();
    }

    @Override
    public Optional<TalkDTO> findById(String id)
            throws GatewayException {
        String sql = """
                SELECT *
                FROM talk
                WHERE   id = ?
                """;
        var query = new QueryStatement<TalkDTO>(sql, List.of(id), rsMapping);

        var dtoOpt = findUnique(
                query,
                String.format(
                        "WARN: find multiple talks with same id %s",
                        id
                )
        );
        if (dtoOpt.isEmpty()) {
            return dtoOpt;
        }
        var dto = dtoOpt.get();
        var speakerIds = speakerDAO.getIdsByTalkId(id);
//        var listenerIds = listenerDAO.getIdsByTalkId(id);
        dto.setSpeakerIds(speakerIds);
        dto.setListenerIds(List.of());
        return Optional.of(dto);
    }
    
    @Override
    public TalkDTO save(TalkCreateDTO dto)
            throws GatewayException {
        String sql = """
                INSERT INTO talk(id, name, conference_id)
                VALUES (?, ?, ?);
                """;
        try {
            source.preparedStatement(sql, stmt -> {
                stmt.setString(1, dto.getId());
                stmt.setString(2, dto.getName());
                stmt.setString(3, dto.getConferenceId());
                stmt.executeUpdate();
            });
            return findById(dto.getId()).get();
        } catch (SQLException e) {
            throw new GatewayException(e.getMessage());
        }
    }

    @Override
    public SpeakerDTO addSpeaker(String talkId, SpeakerCreateDTO speakerCreateDto) throws GatewayException {
        var dto = speakerDAO.save(speakerCreateDto);
        String sqlInsertLink = """
                INSERT INTO talk_speaker(talk_id, speaker_id)
                VALUES (?, ?);
                """;
        try {
            source.preparedStatement(sqlInsertLink, stmt -> {
                stmt.setString(1, talkId);
                stmt.setString(2, dto.getId());
                stmt.executeUpdate();
            });
            return dto;
        } catch (SQLException e) {
            throw new GatewayException(e);
        }
    }

    private void linkSpeaker(String talkId, String speakerId) throws GatewayException {
        String sql = """
                INSERT INTO talk_speaker(talk_id, speaker_id)
                VALUES (?, ?);
                """;
        try {
            source.preparedStatement(sql, stmt -> {
                stmt.setString(1, talkId);
                stmt.setString(2, speakerId);
                stmt.executeUpdate();
            });
        } catch (SQLException e) {
            throw new GatewayException(e.getMessage());
        }
    }

    private static class TalkFromRS implements ResultSetMapping<TalkDTO> {
        @Override
        public TalkDTO convert(ResultSet rs) throws SQLException {
            return TalkDTO.builder()
                    .id(rs.getString("id"))
                    .conferenceId(rs.getString("conference_id"))
                    .name(rs.getString("name"))
                    .build();
        }
    }

}
