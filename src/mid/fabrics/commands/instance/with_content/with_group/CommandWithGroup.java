package mid.fabrics.commands.instance.with_content.with_group;

import mid.data.StudyGroup;
import mid.fabrics.commands.instance.Command;

public interface CommandWithGroup extends Command {
     void setData(StudyGroup studyGroup);
}
