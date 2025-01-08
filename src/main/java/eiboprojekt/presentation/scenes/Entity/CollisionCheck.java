package eiboprojekt.presentation.scenes.Entity;

import eiboprojekt.presentation.scenes.GameView.GamePanel;
import eiboprojekt.presentation.scenes.Entity.Entity;

public class CollisionCheck {
    GamePanel gp;

    public CollisionCheck(GamePanel gp) {
        this.gp = gp;
    }

    public void checkFeld(Entity entity) {

        // hier werden die grenzen von den Entitys berechnet
        // collidion wird detected -> 4 punkte müssen gecheckt werden -> Left x, Right
        // x, top y, bottom y
        int entityLeftWeltX = entity.weltX + entity.solideArea.x;
        int entityRightWeltX = entity.weltX + entity.solideArea.x + entity.solideArea.width;

        int entityTopWeltY = entity.weltY + entity.solideArea.y;
        int entityBottomWeltY = entity.weltY + entity.solideArea.y + entity.solideArea.height;

        // Collum und Row
        int entityLeftCol = entityLeftWeltX / gp.tileSize;
        int entityRightCol = entityRightWeltX / gp.tileSize;
        int entityTopRow = entityTopWeltY / gp.tileSize;
        int entityBottomRow = entityBottomWeltY / gp.tileSize;

        int feldNr1, feldNr2; // schulter teile fer figur

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWeltY - entity.speed) / gp.tileSize;
                feldNr1 = gp.feldM.karteFeldNr[entityLeftCol][entityTopRow];
                feldNr2 = gp.feldM.karteFeldNr[entityRightCol][entityTopRow];
                if (gp.feldM.alleFelder[feldNr1].collision || gp.feldM.alleFelder[feldNr2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWeltY + entity.speed) / gp.tileSize;
                feldNr1 = gp.feldM.karteFeldNr[entityLeftCol][entityBottomRow];
                feldNr2 = gp.feldM.karteFeldNr[entityRightCol][entityBottomRow];
                if (gp.feldM.alleFelder[feldNr1].collision || gp.feldM.alleFelder[feldNr2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWeltX - entity.speed) / gp.tileSize;
                feldNr1 = gp.feldM.karteFeldNr[entityLeftCol][entityTopRow];
                feldNr2 = gp.feldM.karteFeldNr[entityLeftCol][entityBottomRow];
                if (gp.feldM.alleFelder[feldNr1].collision || gp.feldM.alleFelder[feldNr2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWeltX + entity.speed) / gp.tileSize;
                feldNr1 = gp.feldM.karteFeldNr[entityRightCol][entityTopRow];
                feldNr2 = gp.feldM.karteFeldNr[entityRightCol][entityBottomRow];
                if (gp.feldM.alleFelder[feldNr1].collision || gp.feldM.alleFelder[feldNr2].collision) {
                    entity.collisionON = true;
                }
                break;
            default:
                break;

        }
    }

    public void checkPlayerMemberCollision(MainCharacter player, Entity[] members) {
        // Wir gehen durch jedes Mitglied im Array
        for (Entity member : members) {
            // Berechne die Grenzen des Spielers und des Mitglieds
            int playerLeftWeltX = player.weltX + player.solideArea.x;
            int playerRightWeltX = player.weltX + player.solideArea.x + player.solideArea.width;
            int playerTopWeltY = player.weltY + player.solideArea.y;
            int playerBottomWeltY = player.weltY + player.solideArea.y + player.solideArea.height;

            int memberLeftWeltX = member.weltX + member.solideArea.x;
            int memberRightWeltX = member.weltX + member.solideArea.x + member.solideArea.width;
            int memberTopWeltY = member.weltY + member.solideArea.y;
            int memberBottomWeltY = member.weltY + member.solideArea.y + member.solideArea.height;

            // Überprüfe, ob sich die Bereiche des Spielers und des Mitglieds überschneiden
            if (playerLeftWeltX < memberRightWeltX && playerRightWeltX > memberLeftWeltX &&
                    playerTopWeltY < memberBottomWeltY && playerBottomWeltY > memberTopWeltY) {
                // Es gibt eine Kollision zwischen dem Spieler und dem Mitglied
                player.collisionON = true;
                member.collisionON = true;
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {

        int index = 999; // Standardwert, falls keine Kollision gefunden wird
    
        for (int i = 0; i < gp.obj.length; i++) {
    
            if (gp.obj[i] != null) {
                // Setze die Position der solidArea des Spielers
                entity.solideArea.setX(entity.weltX + entity.solideArea.getX());
                entity.solideArea.setY(entity.weltY + entity.solideArea.getY());
    
                // Setze die Position der solidArea des Objekts
                gp.obj[i].solidArea.setX(gp.obj[i].worldX + gp.obj[i].solidAreaDefaultX);
                gp.obj[i].solidArea.setY(gp.obj[i].worldY + gp.obj[i].solidAreaDefaultY);
    
                // Überprüfe die Bewegungsrichtung
                switch (entity.direction) {
                    case "up":
                        entity.solideArea.setY(entity.solideArea.getY() - entity.speed);
                        if (entity.solideArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
    
                    case "down":
                        entity.solideArea.setY(entity.solideArea.getY() + entity.speed);
                        if (entity.solideArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
    
                    case "left":
                        entity.solideArea.setX(entity.solideArea.getX() - entity.speed);
                        if (entity.solideArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
    
                    case "right":
                    if (entity.solideArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;
                }
    
                // Zurücksetzen der Positionen
                entity.solideArea.setX(entity.solidAreaDefaultX);
                entity.solideArea.setY(entity.solidAreaDefaultY);
                gp.obj[i].solidArea.setX(gp.obj[i].solidAreaDefaultX);
                gp.obj[i].solidArea.setY(gp.obj[i].solidAreaDefaultY);
            }
        }
        return index;
    }
    

}
