package com.paperscissorsstonegame;

public class AI {
    private int iComPlay;

    public String getComputerChoice() {
        switch (iComPlay) {
            case 1:  return "剪刀";
            case 2:  return "石頭";
            default: return "布";
        }
    }

    public String whoWin(int UserPlay) {
        this.iComPlay = (int)(Math.random()*3 + 1);
        String winner;
        // 1 – 剪刀, 2 – 石頭, 3 – 布.
        if ((UserPlay == 1 && iComPlay == 3)||(UserPlay == 2 && iComPlay == 1)||(UserPlay == 3 && iComPlay == 2)) {
            winner = "恭喜，你贏了！";
        }
        else if (UserPlay == iComPlay){
            winner = "雙方平手！";
        }
        else {
            winner = "很可惜，你輸了！";
        }

        return winner;
    }
}
