package uwu.narumi.crasher.core.command;

import java.io.File;
import java.net.Proxy;
import uwu.narumi.crasher.api.command.Command;
import uwu.narumi.crasher.api.command.CommandInfo;
import uwu.narumi.crasher.api.exception.CommandException;
import uwu.narumi.crasher.api.helper.ProxyHelper;

@CommandInfo(
    alias = "LoadProxies",
    description = "Load proxies from file",
    usage = "loadproxies <socks/http> <.txt file path>",
    aliases = {"lp"}
)
public class LoadProxiesCommand extends Command {

  @Override
  public void execute(String... args) throws CommandException {
    try {
      Proxy.Type type = Proxy.Type.valueOf(args[0].toUpperCase());
      File file = new File(args[1]);
      ProxyHelper.loadProxiesFromFile(type, file);
    } catch (Exception e) {
      throw new CommandException(e.getMessage());
    }
  }
}
