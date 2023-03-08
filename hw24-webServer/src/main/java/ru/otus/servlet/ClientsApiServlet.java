package ru.otus.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.io.IOException;
import java.util.stream.Collectors;


public class ClientsApiServlet extends HttpServlet {

private final DBServiceClient dbServiceClient;
private final Gson gson;

    public ClientsApiServlet(DBServiceClient dbServiceClient, Gson gson) {
        this.dbServiceClient = dbServiceClient;
        this.gson = gson;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientJson = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Client newClient = gson.fromJson(clientJson, Client.class);
        dbServiceClient.saveClient(newClient);
    }
}
