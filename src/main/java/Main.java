import controller.KaprekarController;
import service.KaprekarService;
import view.Menu;

/**
 * Punto de entrada.
 */
public class Main {
    public static void main(String[] args) {
        KaprekarService service = new KaprekarService(0); // velocidad inicial
        KaprekarController controller = new KaprekarController(service);
        Menu menu = new Menu(controller);
        menu.show();
    }
}
