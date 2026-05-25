package de.regitstudios.aniKangarkoLecture.services;

import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EntityInteractService {

    public void handleEntityInteract(PlayerInteractEntityEvent event) {
        //Ein PlayerInteractEntityEvent wird 2x abgefeuert. Ein mal für die Main-Hand und die Off-Hand.
        //Deswegen würde die Logik theoretisch 2x passieren, also fragen wir das in dem Fall ein mal zusätzlich ab.
        if(checkIfHandIsNotMainHand(event.getHand())) {
            return;
        }
        if(event.getRightClicked().getType() == EntityType.COW) {
            final Cow cow = (Cow) event.getRightClicked();
            //Hier wird eine Explosion an der Stelle der Kuh erzeugt. 4F = TNT Stärke.
            cow.getWorld().createExplosion(cow.getLocation(), 10F);
        }
    }

    public void handleEntityInteractLate(PlayerInteractEntityEvent event) {
        if(checkIfHandIsNotMainHand(event.getHand())) {
            return;
        }
        if(event.getRightClicked().getType() == EntityType.COW) {
            event.getPlayer().sendMessage("BOOM");
        }
    }

    private boolean checkIfHandIsNotMainHand(EquipmentSlot equipmentSlot) {
        return equipmentSlot != EquipmentSlot.HAND;
    }
}
