package uwu.narumi.crasher.core;

import java.io.IOException;
import uwu.narumi.crasher.api.command.CommandManager;
import uwu.narumi.crasher.api.exploit.ExploitManager;
import uwu.narumi.crasher.api.helper.ProxyHelper;
import uwu.narumi.crasher.core.command.CrashCommand;
import uwu.narumi.crasher.core.command.HelpCommand;
import uwu.narumi.crasher.core.command.LoadProxiesCommand;
import uwu.narumi.crasher.core.command.RenewCommand;
import uwu.narumi.crasher.core.command.StopCommand;
import uwu.narumi.crasher.core.exploit.ACK;
//import uwu.narumi.crasher.core.exploit.Aegis;
import uwu.narumi.crasher.core.exploit.Aegis;
import uwu.narumi.crasher.core.exploit.Bot;
import uwu.narumi.crasher.core.exploit.Bungee;
import uwu.narumi.crasher.core.exploit.Custom;
import uwu.narumi.crasher.core.exploit.Encryption;
import uwu.narumi.crasher.core.exploit.Login;
import uwu.narumi.crasher.core.exploit.LsAntiBot;
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
    System.out.println("\n          ┌Bungee\n"
        + "          │\n"
        + "┌────╦────╩─Lobby───┐\n"
        + "│  First            │             MinecraftCrasher\n"
        + "│                   ╠───PVP       Created by なるみ\n"
        + "╚Second             │\n"
        + "                 MiniGames\n");

    commandManager = new CommandManager(new CrashCommand(), new StopCommand(), new HelpCommand(),
        new RenewCommand(), new LoadProxiesCommand());
    exploitManager = new ExploitManager(
        new Aegis(),
        new ACK(),
        new Bot(),
        new Bungee(),
        new Custom(),
        new Encryption(),
        new Login(),
        new LsAntiBot(),
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
  }

  public CommandManager getCommandManager() {
    return commandManager;
  }

  public ExploitManager getExploitManager() {
    return exploitManager;
  }
}
