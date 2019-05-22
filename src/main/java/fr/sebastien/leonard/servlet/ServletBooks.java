package fr.sebastien.leonard.servlet;

import com.google.gson.Gson;
import fr.sebastien.leonard.model.Book;
import fr.sebastien.leonard.services.StoreBookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Books")
public class ServletBooks extends HttpServlet {

    private StoreBookService service = new StoreBookService();
    private Gson gson = new Gson();

    @Override
    public void init() throws ServletException {
        super.init();

        service.store(new Book("Le Livre de la jungle", "Rudyard Kipling", 20L));
        service.store(new Book("Les Aristochats", "The Walt Disney Company", 15L));
        service.store(new Book("Poil de carotte", "Jules Renard",  50L));
        service.store(new Book("Trois petits cochons", "Paul Faucher", 5L));
        service.store(new Book("Harry Potter and the; Philosopher's Stone", "J.K. Rowling", 30L));
        service.store(new Book("The Chronicles of Narnia", "C.S Lewis", 12L));
        service.store(new Book("Percy Jackson et Le Voleur de foudre", "Rick Riordan", 1L));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("idToRemove") == null || request.getParameter("numberToRemove") == null) {
                response.getWriter().println("You must give the idToRemove and numberToRemove in parameters of the request");
                response.setStatus(400);

                return;
            }

            Long id = Long.parseLong(request.getParameter("idToRemove"));
            Long remove = Long.parseLong(request.getParameter("numberToRemove"));

            if(!service.removeUnitToBook(id, remove)) {
                response.getWriter().println("The given id isn't registered");
                response.setStatus(400);
            } else {
                response.getWriter().println("Ok");
                response.setStatus(200);
            }

        } catch (NullPointerException ex) {
            response.getWriter().println("You must give the idToRemove, and numberToRemove in parameters of the request");
            response.setStatus(400);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            if(request.getParameter("idToAdd") == null || request.getParameter("numberToAdd") == null) {
                response.getWriter().println("You must give the idToAdd and numberToAdd in parameters of the request");
                response.setStatus(400);

                return;
            }

            Long id = Long.parseLong(request.getParameter("idToAdd"));
            Long remove = Long.parseLong(request.getParameter("numberToAdd"));

            if(!service.addUnitToBook(id, remove)) {
                response.getWriter().println("The given id isn't registered");
                response.setStatus(400);
            } else {
                response.getWriter().println("Ok");
                response.setStatus(200);
            }

        } catch (NullPointerException ex) {
            response.getWriter().println("You must give the idToAdd, and numberToAdd in parameters of the request");
            response.setStatus(400);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Content-Type", "application/json");
        response.getWriter().println(this.gson.toJson(this.service.findAll()));
    }

}