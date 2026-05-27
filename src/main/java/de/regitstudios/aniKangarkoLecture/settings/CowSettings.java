package de.regitstudios.aniKangarkoLecture.settings;

import de.regitstudios.aniKangarkoLecture.AniKangarkoLecture;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;

public class CowSettings {

    //Setting sollten immer ein Singleton sein.
    //Das sorgt dafür das jedes Settings file nur ein mal existieren kann.
    private final static CowSettings instance = new CowSettings();

    private File file;
    private YamlConfiguration config;

    private EntityType explodingEntityType;

    private CowSettings() {

    }

    //Hier laden wir das File, falls vorhanden, ansonsten wird das File erstellt und aus unserem resources Ordner in den Plugins Ordner geschoben.
    public void load() {
        file = new File(AniKangarkoLecture.getInstance().getDataFolder(), "settings.yml");

        if(!file.exists()) {
            AniKangarkoLecture.getInstance().saveResource("settings.yml", false);
        }

        config = new YamlConfiguration();
        config.options().parseComments(true);

        try {
            config.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        explodingEntityType = EntityType.valueOf(config.getString("explosion.entity-type"));
    }

    public static CowSettings getInstance() {
        return instance;
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public EntityType getExplodingEntityType() {
        return explodingEntityType;
    }

    //Immer bedenken das beim Setter das Value an der Instance sowie im settings file gesetzt werden muss.
    //Deswegen auch der set() aufruf hier.
    public void setExplodingEntityType(EntityType explodingEntityType) {
        this.explodingEntityType = explodingEntityType;
        set("explosion.entity-type", explodingEntityType.name());
    }
}
