package de.regitstudios.aniKangarkoLecture.services;

import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EntityInteractService {

    public void handleEntityInteract(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity entity = event.getRightClicked();

        //Ein PlayerInteractEntityEvent wird 2x abgefeuert. Ein mal für die Main-Hand und die Off-Hand.
        //Deswegen würde die Logik theoretisch 2x passieren, also fragen wir das in dem Fall ein mal zusätzlich ab.
        if(checkIfHandIsNotMainHand(event.getHand())) {
            return;
        }

        if(entity instanceof Cow cow && entity.hasMetadata("CowCannon") && player.getInventory().getItemInMainHand().getType() == Material.BUCKET) {
            if(cow.isAdult()) {
                //Hier wird eine Explosion an der Stelle der Kuh erzeugt. 4F = TNT Stärke.
                cow.getWorld().createExplosion(cow.getLocation(), 4F);
            } else {
                cow.getWorld().createExplosion(cow.getLocation(), 20F);
            }
        }
    }

    public void handleEntityInteractLate(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity entity = event.getRightClicked();
        if(checkIfHandIsNotMainHand(event.getHand())) {
            return;
        }

        if(entity instanceof Cow && entity.hasMetadata("CowCannon") && player.getInventory().getItemInMainHand().getType() == Material.BUCKET) {
            player.sendMessage("BOOM");
        }
    }

    private boolean checkIfHandIsNotMainHand(EquipmentSlot equipmentSlot) {
        return equipmentSlot != EquipmentSlot.HAND;
    }
}
