# The Event System

## Events

Events sind Aktionen die im Spiel passieren oder von anderen Plugins ausgelöst werden
Auf dieser Seite findet man eine Auflistung aller Events von Spigot/Paper
https://spigot-event-list.s7a.dev/en

## Sync/Async

# Sync
Die meisten Events passieren direkt im Mainthread. 
Dort passiert in der Regel alles was direkt mit dem Spiel zusammen hängt. BlockBreakEvent, BlockPlaceEvent etc.
Passiert im GettingStarted.md genannten Herzschlag.

# Async
Einzelne Events z.B. Chat Events passieren in zusätzlichen Threads.
Das sorgt dafür das wenn der Server gerade hängt, trotzdem noch im Chat geschrieben werden kann (Solange der Mainthread hängt).
Passiert außerhalb des Herzschlags.

Es gibt Events die Sync und Async bearbeitet werden können.

## Eventlisteners
Wenn im Herzschlag ein Event ausgelöst wird, schaut der Server in allen Klassen (auch aus den Plugins) nach ob einer dieser einen Listener implementiert hat für dieses Event.

## @EventHandler
Die Annotation benutzt man für eine Funktion um auf ein spezielles Event zu hören.
Durch die Annotation weiß der Server direkt das in dieser Klasse auf ein Event gehört wird.
Die Funktionen geben nichts zurück sondern bearbeiten nur das eingehende Event mit den dazugehörigen Daten.

# Registrieren von Events
Der Server weiß trotz der Annotation theoretisch gar nichts von diesem Eventlistener.
Er durchsucht nicht einfach alle Klassen aller Plugins, das wäre viel zu ressourcenintensiv.
Deswegen müssen Events registriert werden. Dafür gibt es getServer() damit bekommt man die Instanz des Minecraft Servers.
Von diesem wiederrum können wir uns getPluginManager() holen. 
In diesem Plugin Manager können wir dann mit registerEvents() unseren EventListener registrieren.
Dadurch weiß der Server dann auch aha dieses Plugin hat einen Listener auf dem Event. 
Dann sendet er die Packete an das Plugin. 
Der Aufruf muss über onEnable() aus der Main Funktion gemacht werden, sodass die Listener direkt beim Starten des Plugins registriert werden.

# Event Priorities
Es kann sein das es zu einem ausgelösten Event mehrere Listener gibt.
Damit es hier keine Probleme gibt, existieren Event Prioritäten.
Hierfür kann man am @EventHandler eine Priorität festlegen.
Es gibt 6 Stück.

- LOWEST
- LOW
- NORMAL
- HIGH
- HIGHEST
- MONITOR

Dabei ruft der Server die Eventmethoden nach der Reihenfolge auf. Als erstes LOWEST als letztes MONITOR.
Setzt man keine Priorität dann ist der Standard auf NORMAL gesetzt.
Listener mit der Priorität MONITOR sollten keine Spielelogik enthalten und nur fürs Logging benutzt werden.

Prioritäten sind vor allem wichtig, wenn man mit anderen Plugins zusammen arbeitet oder man viele Plugins auf dem Server hat.

# Canceling Events
Die meisten Events können abgebrochen werden.
Das sind alle Events die als Interface Cancellable implementieren.
Wird die Funktion event.setCancelled(true) ausgeführt, so wird die Standardlogik des Events nicht mehr ausgeführt und überschrieben.

Da ein Event durch einen anderen Listener bereits cancelled sein kann, ist es möglich das mit if(event.isCancelled()) abzufragen.
Jetzt könnte man dann theoretisch das wieder "uncancelled" machen, indem man event.setCancelled(false) macht.

# Sonstiges
InteractEvents egal ob Entity oder ähnliches wird immer 2x abgefeuert.
Es wird ein mal für die Off-Hand und die Main-Hand abgefeuert.