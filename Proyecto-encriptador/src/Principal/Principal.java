package Principal;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);

        //Ingreso al sistema.
        Scanner nombreUsuario = new Scanner(System.in);
        System.out.println("Saludos :), ¿Cuál es tu nombre?");
        var NombreUsuario = nombreUsuario.nextLine();

        // Opciones de conversion.
        while (true) {
            System.out.println("*****************************************************");
            System.out.println("¡Hola " + NombreUsuario + "!. Sea bienvenido/a al Conversor de Moneda =)");
            System.out.println(" ");
            System.out.println("Estas son las opciones de conversor que tenemos para ofrecerte:");
            System.out.println(" ");
            System.out.println("1) Dólar => Peso Argentino");
            System.out.println("2) Peso argentino -> Dólar");
            System.out.println("3) Dolar -> Real brasileño");
            System.out.println("4) Real brasileño -> Dolar ");
            System.out.println("5) Dolar -> Peso Colombiano ");
            System.out.println("6) Peso Colombiano -> Dolar");
            System.out.println("7) Ya no quiero realizar la conversión, SALIR =(");
            System.out.println(" ");
            System.out.println("Elije una opción valida (número de opción):");

            //Manejo de excepción cuando el usuario ingresa datos invalidos.
            int opcion = -1;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Opción inválida. Debes escoger alguna de las opciones.");
                scanner.nextLine();
                continue;
            }

            // Manejo de la salida del programa.
            if (opcion == 7) {
                System.out.println("Saliendo del conversor de moneda. ¡Hasta luego!");
                break;
            }

            //Guardado y manejo de los datos según su caso.
            String monedaAconvertir = "";
            String monedaConvertida = "";

            switch (opcion) {
                case 1:
                    monedaAconvertir = "USD";
                    monedaConvertida = "ARS";
                    break;
                case 2:
                    monedaAconvertir = "ARS";
                    monedaConvertida = "USD";
                    break;
                case 3:
                    monedaAconvertir = "USD";
                    monedaConvertida = "BRL";
                    break;
                case 4:
                    monedaAconvertir = "BRL";
                    monedaConvertida = "USD";
                    break;
                case 5:
                    monedaAconvertir = "USD";
                    monedaConvertida = "COP";
                    break;
                case 6:
                    monedaAconvertir = "COP";
                    monedaConvertida = "USD";
                    break;
                default:
                    System.out.println("Opción inválida. Debes escoger alguna de las opciones.");
                    continue;
            }

            System.out.println("Ingresa el valor que deseas convertir");
            int cantidadAConvertir = -1;
            try {
                cantidadAConvertir = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Debes ingresar solo números.");
                scanner.nextLine();
                continue;
            }

            //Manejo de la API.
            String clave = "dacec273076c40e02081094c";
            String direccion = "https://v6.exchangerate-api.com/v6/" + clave + "/pair/" + monedaAconvertir + "/" + monedaConvertida + "/" + cantidadAConvertir;

            HttpClient client;
            try {
                client = HttpClient.newHttpClient();
            } catch (Exception e) {
                throw new RuntimeException("Error al crear el cliente HTTP con SSL ignorado", e);
            }

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            // Usando Gson para extraer correctamente la respuesta.
            String responseBody = response.body();
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            double conversionResult = jsonObject.get("conversion_result").getAsDouble();

            //Transmitiendo la respuesta al usuario
            System.out.println("El valor " + cantidadAConvertir + " [" + monedaAconvertir + "] corresponde al valor final de ==> " + conversionResult + " [" + monedaConvertida + "]");
            System.out.println(" ");
            System.out.println("¿Deseas realizar otra conversión?");
            System.out.println("1) Si.");
            System.out.println("2) No, gracias.");

            int opcion2 = -1;
            try {
                opcion2 = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Saliendo del conversor de moneda. ¡Hasta luego!");
                break;
            }

            if (opcion2 == 2) {
                System.out.println("Saliendo del conversor de moneda. ¡Hasta luego!");
                break;
            }
        }
        scanner.close();
    }
}