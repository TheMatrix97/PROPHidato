package hidato;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CeldaTest.class,
        MaquinaTest.class,
        RankingTest.class,
        GestorSavesTest.class,
        TaulerTest.class,
        TimeTest.class})
public class Testsuite {
    public static void main(String[] args) {
        JUnitCore.main("hidato.Testsuite");

    }
}
