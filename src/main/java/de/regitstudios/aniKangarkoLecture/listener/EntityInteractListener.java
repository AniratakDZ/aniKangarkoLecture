package de.regitstudios.aniKangarkoLecture.listener;

import de.regitstudios.aniKangarkoLecture.services.EntityInteractService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class EntityInteractListener implements Listener {

    private final static EntityInteractService entityInteractService = new EntityInteractService();

    @EventHandler
    public void onEntityRightClick(PlayerInteractEntityEvent event) {
        //Best practice ist es services für so was zu benutzen.
        //Commands, Listener oder ähnliches sollte nur die Brücke zur Logik sein.
        //Die Logik kommt dann in den Service.
        entityInteractService.handleEntityInteract(event);
    }

    //Ein Beispiel für Event Prioritäten.
    //Diese Funktion würde ganz am Ende ausgerufen werden und definitiv nach der anderen onEntityRightClick Funktion.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityRightClickLate(PlayerInteractEntityEvent event) {
        entityInteractService.handleEntityInteractLate(event);
    }
}
