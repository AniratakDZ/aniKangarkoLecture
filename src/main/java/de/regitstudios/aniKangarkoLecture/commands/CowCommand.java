package de.regitstudios.aniKangarkoLecture.commands;

import de.regitstudios.aniKangarkoLecture.AniKangarkoLecture;
import de.regitstudios.aniKangarkoLecture.settings.CowSettings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

        //Hier fragen wir ab ob mehr als ein Argument gegeben wurde, falls ja darf es nur ein EntityType sein, falls /Cow set benutzt wurde.
        if(strings.length > 1) {

            if(strings[0].equalsIgnoreCase("set")) {
                EntityType type;

                try {
                    type = EntityType.valueOf(strings[1].toUpperCase());
                } catch (IllegalArgumentException e) {
                    commandSender.sendMessage("Invalid entity type: " + strings[1]);
                    return true;
                }

                //Sicherheitsabfrage da es auch entities gibt die mit Particles zusammen hängen. Z.b. Armor Stands oder Ähnliches
                if(!type.isSpawnable() || !type.isAlive()) {
                    commandSender.sendMessage("You can only use living entities");
                    return true;
                }

                CowSettings.getInstance().setExplodingEntityType(type);
                commandSender.sendMessage("Set exploding type to: " + type);
                return true;
            }

            return false;
        }

        final LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), CowSettings.getInstance().getExplodingEntityType());

        //Hier fragen wir ab ob /cow -> baby <- eingetippt wurde. Falls ja wird die Kuh zu einer Babyvariante
        if(strings.length == 1 && strings[0].equalsIgnoreCase("baby")) {
            if(entity instanceof Ageable) {
                ((Ageable)entity).setBaby();
            } else {
                commandSender.sendMessage("This entitiy cannot be a baby");
                entity.remove();
                return true;
            }
        }

        //MetaData sollte man nur setzen wenn Attribute kurzzeitig gespeichert werden sollen
        //Bei Server restart wird die MetaData gelöscht. Ansonsten kann man *.getPersistentDataCointainer() machen.
        //Damit kann man Werte auch über dem Serverrestart hinaus speichern.
        entity.setMetadata("CowCannon", new FixedMetadataValue(AniKangarkoLecture.getInstance(), true));
        entity.setCustomName(ChatColor.RED + "Milk me :)");
        entity.setCustomNameVisible(true);
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(strings.length == 1) {
            return List.of("baby", "set");
        }

        if(strings.length == 2) {
            String name = strings[1].toUpperCase();
            //Hier lassen wir TabComplete über die Liste aller EntityTypes laufen. Da wir ja nur bestimmte wollen filtern wir hier auch nach isAlive und isSpawnable.
            return Arrays.stream(EntityType.values())
                    .filter(type -> type.isSpawnable() && type.isAlive() && type.name().startsWith(name))
                    .map(Enum::name)
                    .collect(Collectors.toList());
        }

        //Leere Liste, wenn wir null zurückgeben lassen würde, würden alle Online Spieler vorgeschlagen werden.
        return new ArrayList<>();
    }
}
