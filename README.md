# Band to Beat – Entwicklung interaktiver Anwendungen

Dieses Projekt ist eine interaktive Java-Anwendung aus dem dritten Semester meines Medieninformatik Studiums. Es handelt sich um ein kleines Spiel mit mehreren Szenen, Leveln, Dialogen und Sound-Integration.

## Projektübersicht

- **Name:** Band To Beat
- **Modul:** Entwicklung interaktiver Anwendungen
- **Studiensemester:** 3. Semester Medieninformatik
- **Umfang:** Java-Spiel mit Menü, Einführung, Spielpanel, mehreren Leveln und Endbildschirm
- **Ziel:** Praktische Umsetzung von interaktiven Benutzeroberflächen, Spielsteuerung und Medienintegration

## Verwendete Technologien

- **Java 21** als Programmiersprache
- **Maven** als Build- und Projektmanagement-Tool
- **JavaFX 21** für Benutzeroberfläche, Szenenwechsel und Grafik
- **FXML** für Layout-Definitionen in `src/main/resources/eiboprojekt`
- **Minim / Audio-Bibliotheken** für Sound und Musik
- **Systemabhängige JARs** aus dem `lib/`-Ordner für Audio-Verarbeitung

## Projektstruktur

- `pom.xml` – Maven-Projektdefinition und Abhängigkeiten
- `src/main/java/eiboprojekt` – Hauptpaket mit `App.java`
- `src/main/java/eiboprojekt/presentation/scenes` – Logik für Ansichten, Controller, Dialoge und Spiellevel
- `src/main/resources/eiboprojekt` – FXML-Dateien und CSS-Styles
- `assets/` – Grafiken, Karten, Dialogtexte und Sound-Ressourcen
- `lib/` – zusätzliche JAR-Bibliotheken für Audio-Funktionalität

## Lernziele

- Aufbau eines JavaFX-Projekts mit sauberer Szenen- und Steuerungsstruktur
- Einsatz von Maven zur Verwaltung von Abhängigkeiten und zum Starten des Projekts
- Erstellung eines einfachen Game-Loop-Systems mit mehreren Leveln
- Nutzung von FXML und CSS zur Trennung von Logik und Layout
- Einbindung von Audiodateien und Soundeffekten mit externen Bibliotheken
- Teamarbeit, Aufgabenteilung und Projektorganisation im Medieninformatik-Kontext

## Starten des Projekts

`mvn clean javafx:run` im Projektverzeichnis ausführen oder in der IDE auf `run Java` klicken

## Hinweise

- Der Einstiegspunkt ist `src/main/java/eiboprojekt/App.java`.
- Das Projekt nutzt ein `StackPane` für das Wechseln zwischen mehreren Views.
- Das Spiel enthält sowohl Menü- als auch Level-Ansichten und mehrere Charakterlevels.

## Contributors

- Paula Petrovic
- Nadia Tajja
- Laura Monaco Lorente
