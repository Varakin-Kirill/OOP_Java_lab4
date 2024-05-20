import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String FILE_PATH = "C:\\Users\\Kirill\\IdeaProjects\\Lab_4\\src\\main\\java\\user.json"; // Путь к файлу на сервере

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            users = gson.fromJson(reader, listType);
            response.getWriter().write(gson.toJson(users));
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при чтении списка пользователей");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        System.out.println("abc3");
        StringBuilder jsonRequest = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonRequest.append(line);
            }
            System.out.println("abc4");
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка при чтении запроса");
            return;
        }

        Gson gson = new GsonBuilder().create();
        User user = gson.fromJson(jsonRequest.toString(), User.class);
        System.out.println("abc5");
        // Чтение текущего списка пользователей из файла
        List<User> users = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(FILE_PATH))) {
            Type listType = new TypeToken<ArrayList<User>>() {
            }.getType();
            System.out.println("abc5");
            users = gson.fromJson(fileReader, listType);
            System.out.println("abc5");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("abc5");

        // Добавление нового пользователя в список
        users.add(user);

        // Запись списка автомобилей обратно в файл
        try (FileWriter fileWriter = new FileWriter(FILE_PATH)) {
            gson.toJson(users, fileWriter);
            System.out.println("abc6");
        } catch (IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ошибка при записи автомобиля в файл");
            return;
        }
        System.out.println("abc7");

        // Отправка обновленного списка пользователей
        doGet(request, response);
    }
}