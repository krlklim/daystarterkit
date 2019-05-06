package mainPart.dataBaseUtil;

import mainPart.model.UserSession;

import java.util.List;

public interface UserSessionDao {

    long save(UserSession userSession);

    UserSession get(long id);

    List<UserSession> list();

    void update(long id, UserSession userSession);

    void delete(UserSession userSession);


}
