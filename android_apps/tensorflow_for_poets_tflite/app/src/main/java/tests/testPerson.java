package tests;

import com.scanfit.modeling.user.BodyType;
import com.scanfit.modeling.user.Person;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class testPerson extends TestCase {


    @Before
    public void setUp() {
        System.out.print("I get run before eeeevery test!");
    }

    @Test
    public void testPersonConstructor() {
        Person person = new Person("jack", "jackblack666", "jack@pirates.ru",
                "pirate", BodyType.MASCULINE, new Date());


        assertEquals(person.getName(), "jack");
    }
}
