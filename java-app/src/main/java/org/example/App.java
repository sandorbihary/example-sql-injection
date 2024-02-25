package org.example;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class App {

    static Jdbi jdbi;

    public static void main(String[] args) throws IOException {
        // web server
        var app = Javalin.create(config -> {
            config.staticFiles.add("/public");
        });
        app.get("/words", App::getWords);
        app.post("/words", App::postWords);
        app.start();

        // database
        jdbi = Jdbi.create("jdbc:mysql://localhost:3306/example",
                "root",
                "my-secret-pw");
        initDatabase();
    }

    private static void initDatabase() throws IOException {
        try (
            Handle con = jdbi.open();
            InputStream is = App.class.getResourceAsStream("/db/init.sql");
        ) {
            con.execute(new String(is.readAllBytes()));
        }
    }

    static void getWords(Context ctx) {
        try (var con = jdbi.open()) {
            var searchExpr = Objects.requireNonNullElse(ctx.queryParam("search"), "");
            var words = con.createQuery("SELECT * FROM words WHERE en LIKE '%" + searchExpr + "%'")
                    .mapToMap()
                    .list();
            ctx.json(words);
        }
    }

    static void postWords(Context ctx) {
        try (var con = jdbi.open()) {
            var reqBody = ctx.bodyAsClass(Map.class);
            var en = reqBody.get("en");
            var pt = reqBody.get("pt");
            con.createUpdate("INSERT INTO words (en, pt) VALUES ('" + en + "', '" + pt + "')")
                    .execute();
        }
    }

}
