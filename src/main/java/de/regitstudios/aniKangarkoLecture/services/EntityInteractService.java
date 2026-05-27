package de.regitstudios.aniKangarkoLecture.services;

import de.regitstudios.aniKangarkoLecture.settings.CowSettings;
import org.bukkit.Material;
import org.bukkit.entity.*;
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

        if(entity.getType() == CowSettings.getInstance().getExplodingEntityType() && entity.hasMetadata("CowCannon") && player.getInventory().getItemInMainHand().getType() == Material.BUCKET) {

            if(!player.hasPermission("cowcannon.cow.use")) {
                player.sendMessage("You don't have permission to milk cows :)");
                return;
            }

            if(!(entity instanceof LivingEntity livingEntity)) {
                return;
            }

            if(!(livingEntity instanceof Ageable ageableEntity)) {
                return;
            }


            if (ageableEntity.isAdult()) {
                //Hier wird eine Explosion an der Stelle der Kuh erzeugt. 4F = TNT Stärke.
                ageableEntity.getWorld().createExplosion(ageableEntity.getLocation(), 4F);
            } else {
                ageableEntity.getWorld().createExplosion(ageableEntity.getLocation(), 20F);
            }
        }
    }

    public void handleEntityInteractLate(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        final Entity entity = event.getRightClicked();
        if(checkIfHandIsNotMainHand(event.getHand())) {
            return;
        }

        if(!player.hasPermission("cowcannon.cow.use")) {
            player.sendMessage("You don't have permission to milk cows :)");
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
