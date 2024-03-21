package timer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.TreeSet;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.NoSuchElementException;


class DateTimerTest {
    @Test
    public void testDateIntervals() {
        // Créer un TreeSet et ajouter des dates (entiers représentant par exemple des timestamps)
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(0); // Ajoutez les dates réelles en tant qu'entiers
        dates.add(3);
        dates.add(6);

        // Initialiser DateTimer avec le TreeSet
        DateTimer timer = new DateTimer(dates);

        // Utiliser la méthode next() pour obtenir les intervalles et vérifier les résultats
        // Note: Ceci suppose que DateTimer implémente une logique pour fournir des intervalles via next()
        // Vous devrez adapter ce code en fonction de l'API réelle de votre classe DateTimer
        assertEquals(0, timer.next(), "Le premier intervalle doit être de 1");
        assertEquals(3, timer.next(), "Le deuxième intervalle doit être de 3");
        assertEquals(3, timer.next(), "Le troisième intervalle doit être de 6");
        // Ajoutez plus d'assertions au besoin pour couvrir vos cas de test
    }
    @Test //N_DT_next_affirmation
    public void T_DT_next_negation() {
        // Initialisation de DateTimer avec un TreeSet contenant {10, 15}
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(10);
        dates.add(15);
        DateTimer timer = new DateTimer(dates);

        // Consommation des intervalles disponibles
        timer.next(); // Première invocation
        timer.next(); // Deuxième invocation

        // Test de négation: vérifier le comportement lorsque plus aucun intervalle n'est disponible
        // Cette invocation devrait lever une NoSuchElementException
        assertThrows(NoSuchElementException.class, timer::next, "Une NoSuchElementException devrait être levée lorsque plus aucun intervalle n'est disponible");
    }


}