package mid.fabrics.commands.instance.with_content.with_group;

import mid.fabrics.commands.instance.Command;
import mid.data.StudyGroup;

public interface CommandWithGroup extends Command {
     void setData(StudyGroup studyGroup);
}
