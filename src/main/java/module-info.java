module Vizualization {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires okhttp3;
    requires com.opencsv;
    requires org.jsoup;
    opens ServerResp to com.google.gson;

    exports Vizualization;
    exports Vizualization.demoapp;
    opens ServerResp.SimpleObjects to com.google.gson;
    opens ServerResp.Wrappers to com.google.gson;
    opens ServerResp.Responses to com.google.gson;
    exports Vizualization.Interfaces;
}