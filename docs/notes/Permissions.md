# Permissions

## LuckyPerms
Das Plugin LuckyPerms ist soweit das marktführende Permissions Plugin in Minecraft.
https://luckperms.net/download

## Eigenes hinzufügen von Permissions
Um selber Permissions im Plugin verwalten zu können benötigt es eine Map.
UUID zu Permissions.
Diese Map ist aber nur dafür da, um es später einfacher zu machen Permissions zu entfernen oder hinzuzufügen.
Alternativ kann man so was auch in .yml h2 oder Datenbanken speichern.

UUID = Unique ID des Spielers
PermissionAttachment = Eine einzelne Permission

Map<UUID, PermissionAttachment> permissions = new HashMap<>();

Das Hinzufügen der Permissions erfolgt dann über: (Natürlich müsste man jede Permissions parallel auch im eigenen Speicher hinzufügen)

player.addAttachment(Plugin.getInstance(), "permission.text", "value, ob er es hat also meistens true", "falls, wie viele Ticks lang die Permission aktiv sein soll")

Das Entfernen einer Permissions erfolgt dann über:

player.removeAttachment("permission.text")

## Best Practice Code
final Map<UUID, PermissionAttachment> permissions = new HashMap<>();

if(permissions.containsKey(player.getUniqueId())) {
    player.removeAttachment(permissions.remove(player.getUniqueId));
}

final PermissionAttachment permission = player.addAttachment(Plugin.getInstance(), "permission.text", true);
permissions.put(player.getUniqueId(), permission);