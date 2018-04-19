package fr.paragoumba.projecttest.game;

import fr.paragoumba.projecttest.engine.*;
import fr.paragoumba.projecttest.engine.graph.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_M;

public class Game implements IGameLogic {
    @Override
    public void init(Window window) throws Exception {

    }

    @Override
    public void input(Window window, MouseInput mouseInput) {

    }

    @Override
    public void update(float interval, MouseInput mouseInput) {

    }

    @Override
    public void render(Window window) throws Exception {

    }

    @Override
    public void cleanup() {

    }

    /*private static final float MOUSE_SENSITIVITY = 0.2f;
    private static final float CAMERA_POS_STEP = 0.05f;

    private final Vector3f cameraInc;
    private final Renderer renderer;
    private final Camera camera;

    private GameItem[] gameItems;
    private Vector3f ambientLight;
    private PointLight[] pointLightList;
    private SpotLight[] spotLightList;
    private DirectionalLight directionalLight;
    private float lightAngle;

    private Vector3f lightPosition;

    public Game(){

        camera = new Camera();
        renderer = new Renderer();
        cameraInc = new Vector3f();

    }

    @Override
    public void init(Window window) throws Exception {

        renderer.init(window);

        float reflectance = 1f;

        Mesh cube = OBJLoader.loadMesh("/models/cube.obj");
        Mesh bb8 = OBJLoader.loadMesh("/models/bb8.obj");
        Mesh bunny = OBJLoader.loadMesh("/models/bunny.obj");
        Mesh touareg = OBJLoader.loadMesh("/models/Touareg.obj");
        //Mesh trees = OBJLoader.loadMesh("/models/trees lo-poly.obj");

        Texture cubeTex = new Texture("/textures/grassblock.png");

        Material cubeMaterial = new Material(cubeTex, reflectance);
        Material bunnyMaterial = new Material(new Vector4f(1, 1, 1, 1), reflectance);
        Material touaregMaterial = new Material(new Vector4f(0.2f, 0.2f, 1, 0), reflectance);
        //Material treesMaterial = new Material(new Vector4f(0.2f, 1, 0.2f, 1), reflectance);

        cube.setMaterial(cubeMaterial);
        bb8.setMaterial(cubeMaterial);
        bunny.setMaterial(bunnyMaterial);
        touareg.setMaterial(touaregMaterial);
        //trees.setMaterial(treesMaterial);

        GameItem gameItem = new GameItem(cube);
        gameItem.setScale(0.5f);
        gameItem.setPosition(0, 0, -2);

        GameItem gameItem2 = new GameItem(bb8);
        gameItem2.setScale(0.01f);
        gameItem2.setPosition(0, 2, -6);

        GameItem gameItem3 = new GameItem(bunny);
        gameItem3.setScale(0.5f);
        gameItem3.setPosition(0, 1, -4);

        lightPosition = new Vector3f(0, 0, 1);
        GameItem touaregItem = new GameItem(touareg);
        touaregItem.setScale(0.0001f);
        touaregItem.setPosition(lightPosition.x, lightPosition.y, lightPosition.z);

        /*GameItem treesItem = new GameItem(trees);
        treesItem.setScale(0.5f);
        treesItem.setPosition(2, 0, -2);*//*

        gameItems = new GameItem[]{gameItem, gameItem2, gameItem3, touaregItem/*, treesItem*//*};

        ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);
        Vector3f lightColour = new Vector3f(1, 1, 1);
        float lightIntensity = 1.0f;

        PointLight pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);

        SpotLight spotLight = new SpotLight(pointLight, new Vector3f(), 15);

        pointLightList = new PointLight[]{pointLight};
        spotLightList = new SpotLight[]{spotLight};

        lightPosition = new Vector3f(-1, 0, 0);
        lightColour = new Vector3f(1, 1, 1);
        directionalLight = new DirectionalLight(lightColour, lightPosition, lightIntensity);

    }

    @Override
    public void input(Window window, MouseInput mouseInput) {

        cameraInc.set(0, 0, 0);

        if (window.isKeyPressed(GLFW_KEY_W)) {

            cameraInc.z = -1;

        } else if (window.isKeyPressed(GLFW_KEY_S)) {

            cameraInc.z = 1;

        }

        if (window.isKeyPressed(GLFW_KEY_A)) {

            cameraInc.x = -1;

        } else if (window.isKeyPressed(GLFW_KEY_D)) {

            cameraInc.x = 1;

        }

        if (window.isKeyPressed(GLFW_KEY_UP)){

            gameItems[3].setPosition((float) (gameItems[3].getPosition().x - 0.1 * Math.cos(gameItems[3].getRotation().x)), gameItems[3].getPosition().y, (float) (gameItems[3].getPosition().z - Math.sin(0.1)));
            lightPosition.x -= 0.1;

        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)){

            gameItems[3].setRotation(gameItems[3].getRotation().x, gameItems[3].getRotation().y + 0.5f, gameItems[3].getRotation().z);
            lightPosition.z -= 0.1;

        } else if (window.isKeyPressed(GLFW_KEY_DOWN)){

            gameItems[3].setPosition((float) (gameItems[3].getPosition().x + 0.1 * Math.cos(gameItems[3].getRotation().x)), gameItems[3].getPosition().y, (float) (gameItems[3].getPosition().z - Math.sin(0.1)));
            lightPosition.x += 0.1;

        } else if (window.isKeyPressed(GLFW_KEY_LEFT)){

            gameItems[3].setRotation(gameItems[3].getRotation().x, gameItems[3].getRotation().y - 0.5f, gameItems[3].getRotation().z);
            lightPosition.z += 0.1;

        }

        if (window.isKeyPressed(GLFW_KEY_Z)) {

            cameraInc.y = -1;

        } else if (window.isKeyPressed(GLFW_KEY_X)) {

            cameraInc.y = 1;

        }

        float lightPos = pointLightList[0].getPosition().z;

        if (window.isKeyPressed(GLFW_KEY_N)) {

            this.pointLightList[0].getPosition().z = lightPos + 0.1f;

        } else if (window.isKeyPressed(GLFW_KEY_M)) {

            this.pointLightList[0].getPosition().z = lightPos - 0.1f;

        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {

        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        if (mouseInput.isRightButtonPressed()) {

            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);

        }

        lightAngle += 1.1f;

        if (lightAngle > 90) {

            directionalLight.setIntensity(0);

            if (lightAngle >= 360) {

                lightAngle = -90;

            }

        } else if (lightAngle <= -80 || lightAngle >= 80) {

            float factor = 1 - (Math.abs(lightAngle) - 80) / 10.0f;

            directionalLight.setIntensity(factor);

            directionalLight.getColor().y = Math.max(factor, 0.9f);
            directionalLight.getColor().z = Math.max(factor, 0.5f);

        } else {


            directionalLight.setIntensity(1);
            directionalLight.getColor().x = 1;
            directionalLight.getColor().y = 1;
            directionalLight.getColor().z = 1;

        }

        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);

    }

    @Override
    public void render(Window window) {

        renderer.render(window, camera, gameItems, ambientLight, pointLightList, spotLightList, directionalLight);

    }

    @Override
    public void cleanup() {

        renderer.cleanup();

        for (GameItem gameItem : gameItems) gameItem.getMesh().cleanup();

    }*/
}
