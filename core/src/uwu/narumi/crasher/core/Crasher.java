package uwu.narumi.crasher.core;

import java.io.IOException;
import uwu.narumi.crasher.api.command.CommandManager;
import uwu.narumi.crasher.api.exploit.ExploitManager;
import uwu.narumi.crasher.api.helper.ProxyHelper;
import uwu.narumi.crasher.core.command.CrashCommand;
import uwu.narumi.crasher.core.exploit.Bot;
import uwu.narumi.crasher.core.exploit.Spigot;
import uwu.narumi.crasher.core.exploit.UDP;

public enum Crasher {

  INSTANCE;

  private final CommandManager commandManager;
  private final ExploitManager exploitManager;

  Crasher() {
    commandManager = new CommandManager(new CrashCommand());
    exploitManager = new ExploitManager(new Bot(), new Spigot(), new UDP());

    commandManager.startHandlingCommands();
  }

  public void init() throws IOException {
    ProxyHelper.loadProxies();

    System.out.println("\n"
        + "### Use \"help\" command for more information"
        + "\n");
  }

  public CommandManager getCommandManager() {
    return commandManager;
  }

  public ExploitManager getExploitManager() {
    return exploitManager;
  }
}
