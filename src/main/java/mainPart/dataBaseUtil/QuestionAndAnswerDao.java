package mainPart.dataBaseUtil;

import org.springframework.stereotype.Component;
import mainPart.model.QuestionAndAnswer;

import java.util.List;

@Component
public interface QuestionAndAnswerDao {

    QuestionAndAnswer get(long id);

    List<QuestionAndAnswer> getAll();

    long getRowCount();

    long getMaximumId();


}
