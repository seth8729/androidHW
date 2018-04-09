package com.paperscissorsstonegame;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class AITest {
    private   AI ms;

    @After
    public void tearDown() { ms= null;
    }

    @Test
    public void PlayerWin() {
        String result = "";
        while (!result.equals("恭喜，你贏了！")) {
            ms = new AI();
            result = ms.whoWin(1);
        }
        assertEquals("恭喜，你贏了！", result);
    }

    @Test
    public void ComputerWin() {
        String result = "";
        while (!result.equals("很可惜，你輸了！")) {
            ms = new AI();
            result = ms.whoWin(2);
        }
        assertEquals("很可惜，你輸了！", result);
    }

    @Test
    public void Tie() {
        String result = "";
        while (!result.equals("雙方平手！")) {
            ms = new AI();
            result = ms.whoWin(3);
        }
        assertEquals("雙方平手！", result);
    }
    @Test
    public void ComputerChoice_Scissors() {
        String result = "";
        while (!result.equals("剪刀")) {
            ms = new AI();
            ms.whoWin(1);
            result = ms.getComputerChoice();
        }
        assertEquals("剪刀", result);
    }

    @Test
    public void ComputerChoice_Rock() {
        String result = "";
        while (!result.equals("石頭")) {
            ms = new AI();
            ms.whoWin(2);
            result = ms.getComputerChoice();
        }
        assertEquals("石頭", result);
    }

    @Test
    public void ComputerChoice_Paper() {
        String result = "";
        while (!result.equals("布")) {
            ms = new AI();
            ms.whoWin(3);
            result = ms.getComputerChoice();
        }
        assertEquals("布", result);
    }
}