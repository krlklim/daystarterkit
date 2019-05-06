package mainPart.dataBaseUtil.userScore;

import mainPart.model.UserScore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserScoreDao {

    UserScore get(long id);

    List<UserScore> getAll();

    long save(UserScore userScore);

    void update(UserScore userScore);
}
