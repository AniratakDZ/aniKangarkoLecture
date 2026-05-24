# Getting Started

## Client Side vs. Server side

### Client Side
Das ist Minecraft. 
Es läuft direkt auf deinem PC, rendert Grafik, hört auf deinen Input von Maus und Tastatur oder sendet Daten zum Server.

### Server Side
Das ist der Minecraft-Server.
Alle Clients können sich mit dem Server verbinden.
Dieser teilt dann Daten mit den einzelnen Clients. Zum Beispiel die Minecraft Welt, Bewegung von Spielern oder ähnliches.
Außerdem läuft auf dem Server Logik z.B. die Bewegung von NPCs.

## Plugins vs Mods

### Plugins (Server Side)
Benutzen existierende Features vom Spiel und können diese manipulieren. Z.b. einen Superzombie mit mehr Leben, mehr Schaden etc.
Muss nur auf dem Server installiert sein.

### Mods (Client Side/ Server Side)
Kann komplett neue Features ins Spiel implementieren oder vorhandene komplett verändern.
Muss auf dem Client installiert sein und bei Multiplayer auch auf dem Server.

## Welche Arten von Minecraft Servern gibt es?

### Vanilla
Unterstützt keine Plugins.
Offiziell von Mojang erstellt.

### Bukkit
Erstellt von Minecraft Spielern.
Baut auf dem Vanilla Server auf und ermöglicht Plugins auf dem Server zu installieren.

### Spigot/Paper
Auch von Minecraft Spielern erstellt.
Baut auf den grundlagen von Bukkit auf und entwickelt es fundamental weiter.

## NMS (net.minecraft.server)
Das ganze System von Bukkit fundiert auf 3 Schichten.

### Bukkit (API)
Dient als Grundlage für Bukkit.
Hier drin sind jede Menge Interfaces die definieren wie Features funktionieren sollen.
In der Bukkit API wird versucht jedes Feature des NMS (Vanilla Minecraft Server) wieder zu spiegeln.
Man benötigt diese API da der NMS Code obfuscated ist (Funktion in Bukkit player.sendMessage() in NMS xyz.ghit()).
So ist es einfacher zu Coden da man ansonsten viel zu lange suchen müsste um die richtigen Funktionen die man braucht zu finden.

### CraftBukkit
Das ist die tatsächliche Implementation der Bukkit API.

### Beispiel
Bukkit API wird aufgerufen Player.sendMessage()

Der Aufruf geht dann durch die Implementation im CraftBukkit und wird verarbeitet.

Das Ergebnis daraus wird dann an den Vanilla Server gesendet und dieser wiederrum sendet dann das Packet an den Spieler und erzeugt eine neue Chat Nachricht.

## Wie funktionieren Plugins?

### TPS (Ticks per second)
Es läuft eine Art Herzschlag die ganze Zeit auf dem Server (Tick).
In jedem Tick läuft eine feste Schleife ab.

1. Server hört auf eingehende Packete (z.B. Zombie schlägt einen Spieler)
2. Server verarbeitet die Packete zu Events
3. Server informiert plugins welche auf die Events hören
4. Event wurde nicht durch ein Plugins unterbrochen -> Server verarbeitet die Logik ganz normal (Reduziert Leben des Spielers)
5. Event wurde unterbrochen -> Die Custom Logik des Plugins wird ausgeführt
6. Nächster Tick fängt an

Diesen Herzschlag gibt damit der Server konstant das Spiel synchron halten kann.
Die Tickrate beträgt 20 Ticks pro Sekunde. 
Also 20 mal pro Sekunde wird dieser Herzschlag ausgeführt.