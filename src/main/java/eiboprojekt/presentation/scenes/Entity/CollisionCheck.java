package eiboprojekt.presentation.scenes.Entity;

import eiboprojekt.App;

public class CollisionCheck {

    private App app;

    public CollisionCheck(App app) {
        this.app = app;
    }

    public void checkFeld(Entity entity) {

        // hier werden die grenzen von den Entitys berechnet
        // collision wird detected -> 4 punkte müssen gecheckt werden -> Left x, Right
        // x, top y, bottom y
        int entityLeftWeltX = entity.weltX + entity.solideArea.x;
        int entityRightWeltX = entity.weltX + entity.solideArea.x + entity.solideArea.width;

        int entityTopWeltY = entity.weltY + entity.solideArea.y;
        int entityBottomWeltY = entity.weltY + entity.solideArea.y + entity.solideArea.height;

        // Column und Row
        int entityLeftCol = entityLeftWeltX / app.tileSize;
        int entityRightCol = entityRightWeltX / app.tileSize;
        int entityTopRow = entityTopWeltY / app.tileSize;
        int entityBottomRow = entityBottomWeltY / app.tileSize;

        int feldNr1, feldNr2; // Kollision soll auf Schulterhöhe passieren

        // Berechnet wo der Character als nächstes hingehen würde und ob da Collision
        // ist
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWeltY - entity.speed) / app.tileSize;
                feldNr1 = app.getGpController().feldM.karteFeldNr[entityLeftCol][entityTopRow];
                feldNr2 = app.getGpController().feldM.karteFeldNr[entityRightCol][entityTopRow];
                if (app.getGpController().feldM.alleFelder[feldNr1].collision
                        || app.getGpController().feldM.alleFelder[feldNr2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWeltY + entity.speed) / app.tileSize;
                feldNr1 = app.getGpController().feldM.karteFeldNr[entityLeftCol][entityBottomRow];
                feldNr2 = app.getGpController().feldM.karteFeldNr[entityRightCol][entityBottomRow];
                if (app.getGpController().feldM.alleFelder[feldNr1].collision
                        || app.getGpController().feldM.alleFelder[feldNr2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWeltX - entity.speed) / app.tileSize;
                feldNr1 = app.getGpController().feldM.karteFeldNr[entityLeftCol][entityTopRow];
                feldNr2 = app.getGpController().feldM.karteFeldNr[entityLeftCol][entityBottomRow];
                if (app.getGpController().feldM.alleFelder[feldNr1].collision
                        || app.getGpController().feldM.alleFelder[feldNr2].collision) {
                    entity.collisionON = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWeltX + entity.speed) / app.tileSize;
                feldNr1 = app.getGpController().feldM.karteFeldNr[entityRightCol][entityTopRow];
                feldNr2 = app.getGpController().feldM.karteFeldNr[entityRightCol][entityBottomRow];
                if (app.getGpController().feldM.alleFelder[feldNr1].collision
                        || app.getGpController().feldM.alleFelder[feldNr2].collision) {
                    entity.collisionON = true;
                }
                break;
            default:
                break;

        }
    }

    // Checkt ob der Character ein Objekt einsammelt
    public int checkObject(Entity entity, boolean player) {

        int index = 999; // Standardwert, falls keine Kollision gefunden wird

        for (int i = 0; i < app.getGpController().obj.length; i++) {

            if (app.getGpController().obj[i] != null) {
                // Setze die Position der solidArea des Spielers
                entity.solideArea.setX(entity.weltX + entity.solideArea.getX());
                entity.solideArea.setY(entity.weltY + entity.solideArea.getY());

                // Setze die Position der solidArea des Objekts
                app.getGpController().obj[i].solidArea
                        .setX(app.getGpController().obj[i].worldX + app.getGpController().obj[i].solidAreaDefaultX);
                app.getGpController().obj[i].solidArea
                        .setY(app.getGpController().obj[i].worldY + app.getGpController().obj[i].solidAreaDefaultY);

                // Überprüfe die Bewegungsrichtung
                switch (entity.direction) {
                    case "up":
                        entity.solideArea.setY(entity.solideArea.getY() - entity.speed);
                        if (entity.solideArea.intersects(app.getGpController().obj[i].solidArea)) {
                            if (app.getGpController().obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "down":
                        entity.solideArea.setY(entity.solideArea.getY() + entity.speed);
                        if (entity.solideArea.intersects(app.getGpController().obj[i].solidArea)) {
                            if (app.getGpController().obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "left":
                        entity.solideArea.setX(entity.solideArea.getX() - entity.speed);
                        if (entity.solideArea.intersects(app.getGpController().obj[i].solidArea)) {
                            if (app.getGpController().obj[i].collision) {
                                entity.collisionON = true;
                            }
                            if (player) {
                                index = i;
                            }
                        }
                        break;

                    case "right":
                        if (entity.solideArea.intersects(app.getGpController().obj[i].solidArea)) {
                            if (app.getGpController().obj[i].collision) {
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
                app.getGpController().obj[i].solidArea.setX(app.getGpController().obj[i].solidAreaDefaultX);
                app.getGpController().obj[i].solidArea.setY(app.getGpController().obj[i].solidAreaDefaultY);
            }
        }
        return index;
    }
}
