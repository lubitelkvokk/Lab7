package mid.fabrics.commands.instance.with_content.with_int;


import mid.fabrics.commands.instance.Command;

public interface CommandWithId extends Command {
    void setData(Integer id);
}
