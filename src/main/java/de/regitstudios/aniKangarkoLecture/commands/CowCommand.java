package de.regitstudios.aniKangarkoLecture.commands;

import de.regitstudios.aniKangarkoLecture.AniKangarkoLecture;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CowCommand implements CommandExecutor, TabExecutor {

    //CommandSender = Von wem die Command ausgeführt wurde (kann auch Serverkonsole sein)
    //Command = Informationen über den Command, im Prinzip alles was in der plugin.yml über den Command steht
    //Label = Das Label das zum Ausführen des Befehls genutzt wurde, kann /cow sein aber auch der alias /cowcannon
    //Args = Die Argumente die nach dem Label kommen Bsp: /cow i=(args[0]) am=(args[1]) really=(args[2]) pretty=(args[3])
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        //Hier fragen wir ab ob der Command von einem Spieler ausgeführt wurde, es ist auch möglich Commands über die Serverkonsole auszuführen.
        if(!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }

        //Es kann nur ein Argument zusätzlich gesetzt werden (baby). Falls es mehr als einer ist soll der Command nicht ausgeführt werden.
        if(strings.length > 1) {
            return false;
        }

        final Cow cow = player.getWorld().spawn(player.getLocation(), Cow.class);

        //Hier fragen wir ab ob /cow -> baby <- eingetippt wurde. Falls ja wird die Kuh zu einer Babyvariante
        if(strings.length == 1 && strings[0].equalsIgnoreCase("baby")) {
            cow.setBaby();
        }

        //MetaData sollte man nur setzen wenn Attribute kurzzeitig gespeichert werden sollen
        //Bei Server restart wird die MetaData gelöscht. Ansonsten kann man *.getPersistentDataCointainer() machen.
        //Damit kann man Werte auch über dem Serverrestart hinaus speichern.
        cow.setMetadata("CowCannon", new FixedMetadataValue(AniKangarkoLecture.getInstance(), true));
        cow.setCustomName(ChatColor.RED + "Milk me :)");
        cow.setCustomNameVisible(true);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(strings.length == 1) {
            return List.of("baby");
        }

        return new ArrayList<>();
    }
}
