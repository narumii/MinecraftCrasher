package uwu.narumi.crasher.core;

import java.io.IOException;
import uwu.narumi.crasher.api.command.CommandManager;
import uwu.narumi.crasher.api.exploit.ExploitManager;
import uwu.narumi.crasher.api.helper.ProxyHelper;
import uwu.narumi.crasher.core.command.CrashCommand;
import uwu.narumi.crasher.core.exploit.ACK;
import uwu.narumi.crasher.core.exploit.Auth;
import uwu.narumi.crasher.core.exploit.Bot;
import uwu.narumi.crasher.core.exploit.Bungee;
import uwu.narumi.crasher.core.exploit.Custom;
import uwu.narumi.crasher.core.exploit.Encryption;
import uwu.narumi.crasher.core.exploit.Login;
import uwu.narumi.crasher.core.exploit.Overload;
import uwu.narumi.crasher.core.exploit.Ping;
import uwu.narumi.crasher.core.exploit.PingJoin;
import uwu.narumi.crasher.core.exploit.SSH;
import uwu.narumi.crasher.core.exploit.Spigot;
import uwu.narumi.crasher.core.exploit.UDP;
import uwu.narumi.crasher.core.exploit.Username;

public enum Crasher {

  INSTANCE;

  private final CommandManager commandManager;
  private final ExploitManager exploitManager;

  Crasher() {
    System.out.println("          ┌BugeeCord\n"
        + "          │\n"
        + "┌────╦────╩─Lobby───┐\n"
        + "│  First            │             MinecraftCrasher\n"
        + "│                   ╠───PVP       Created by なるみ\n"
        + "╚Second             │\n"
        + "                 MiniGames\n");

    commandManager = new CommandManager(new CrashCommand());
    exploitManager = new ExploitManager(
        new ACK(),
        new Auth(),
        new Bot(),
        new Bungee(),
        new Custom(),
        new Encryption(),
        new Login(),
        new Overload(),
        new Ping(),
        new PingJoin(),
        new Spigot(),
        new SSH(),
        new UDP(),
        new Username()
    );
  }

  public void init() throws IOException {
    ProxyHelper.loadProxies();
    commandManager.startHandlingCommands();
  }

  public CommandManager getCommandManager() {
    return commandManager;
  }

  public ExploitManager getExploitManager() {
    return exploitManager;
  }
}
