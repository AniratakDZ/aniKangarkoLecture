package de.regitstudios.aniKangarkoLecture;

import de.regitstudios.aniKangarkoLecture.commands.CowCommand;
import de.regitstudios.aniKangarkoLecture.listener.EntityInteractListener;
import de.regitstudios.aniKangarkoLecture.settings.CowSettings;
import org.bukkit.plugin.java.JavaPlugin;

public final class AniKangarkoLecture extends JavaPlugin {

    @Override
    public void onEnable() {
        //Hier registrieren wir im PluginManager unsere Listener, damit der Server weiß in welchem Plugin welche EventListener sind.
        getServer().getPluginManager().registerEvents(new EntityInteractListener(), this);
        //Um ein EventListener außerhalb dieser Funktion zu registrieren benötigt man:
        //Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
        //ACHTUNG! Es kann sein das beim Start des Plugins dadurch der EventListener NICHT geladen wird.

        getCommand("cow").setExecutor(new CowCommand());

        //Man sollte auch nicht vergessen alle Settings zu laden, sonst führt das später zu vielen Problemen.
        CowSettings.getInstance().load();
    }

    @Override
    public void onDisable() {

    }

    public static AniKangarkoLecture getInstance() {
        return getPlugin(AniKangarkoLecture.class);
    }
}
