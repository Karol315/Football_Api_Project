module org.zpoif.project.players.playerapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires okhttp3;
    requires com.opencsv;
    requires org.jsoup;
    opens org.zpoif.project.players.playerapp to com.google.gson, javafx.fxml; // Otwórz pakiet dla Gson
    opens ServerResp to com.google.gson;

    exports Vizualization;
    exports ScrapeModule.example.demoapp;
    opens ServerResp.SimpleObjects to com.google.gson;
    opens ServerResp.Wrappers to com.google.gson;
    opens ServerResp.Responses to com.google.gson;
}