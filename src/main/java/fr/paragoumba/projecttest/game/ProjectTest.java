package fr.paragoumba.projecttest.game;

import fr.paragoumba.projecttest.engine.GameEngine;
import fr.paragoumba.projecttest.engine.IGameLogic;

public class ProjectTest {

    public static void main(String[] args) {

        try {

            IGameLogic gameLogic = new DummyGame();
            GameEngine gameEngine = new GameEngine("Project : Test", 800, 400, true, gameLogic);
            gameEngine.start();

        } catch (Exception e){

            e.printStackTrace();
            System.exit(-1);

        }
    }
}
