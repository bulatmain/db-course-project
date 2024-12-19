package com.bulatmain.conference.infastructure.repository.dao;

import com.bulatmain.conference.application.model.dto.speaker.SpeakerCreateDTO;
import com.bulatmain.conference.application.model.dto.speaker.SpeakerDTO;
import com.bulatmain.conference.application.port.gateway.SpeakerGateway;
import com.bulatmain.conference.application.port.gateway.exception.GatewayException;
import com.bulatmain.conference.infastructure.repository.db.CustomJdbcTemplate;
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
public class SpeakerDAO
        extends DAO<SpeakerDTO>
        implements SpeakerGateway {
    private final SpeakerFromRS mapping =
            new SpeakerFromRS();

    @Autowired
    public SpeakerDAO(CustomJdbcTemplate source) {
        super(source);
    }

    @Override
    public Optional<SpeakerDTO> findById(String id) throws GatewayException {
        String sql = """
                SELECT *
                FROM speaker
                WHERE id = ?
                """;

        var query = new QueryStatement<SpeakerDTO>(sql, List.of(id), mapping);

        return findUnique(
                query,
                String.format("WARN: returned multiple speakers with same id %s", id)
        );
    }

    @Override
    public SpeakerDTO save(SpeakerCreateDTO dto) throws GatewayException {
        String sqlInsert = """
                INSERT INTO speaker(id)
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

    private static class SpeakerFromRS implements ResultSetMapping<SpeakerDTO> {
        @Override
        public SpeakerDTO convert(ResultSet rs) throws SQLException {
            return SpeakerDTO.builder()
                    .id(rs.getString("id"))
                    .build();
        }
    }
}
