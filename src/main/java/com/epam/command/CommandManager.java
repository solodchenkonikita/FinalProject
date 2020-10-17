package com.epam.command;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static final Logger LOG = Logger.getLogger(CommandManager.class);

    private static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("noCommand", new NoCommand());
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("priceList", new PriceListCommand());
        commands.put("timetable", new TimetableCommand());
        commands.put("booking", new BookingCommand());
        commands.put("clientPage", new ClientCommand());
        commands.put("masterPage", new MasterCommand());
        commands.put("administratorPage", new AdministratorCommand());
        commands.put("language", new LanguageCommand());
    }

    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            LOG.trace("Command not found with name = " + commandName);
            return commands.get("noCommand");
        }
        return commands.get(commandName);
    }
}
