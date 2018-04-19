package fr.paragoumba.projecttest.game;

import fr.paragoumba.projecttest.engine.*;
import fr.paragoumba.projecttest.engine.graph.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;
    private final Vector3f cameraInc;
    private final Renderer renderer;
    private final Camera camera;
    private GameItem[] gameItems;
    private SceneLight sceneLight;
    private Hud hud;
    private float lightAngle;
    private static final float CAMERA_POS_STEP = 0.05f;
    private float spotAngle = 0;
    private float spotInc = 1;

    public DummyGame(){

        camera = new Camera();
        renderer = new Renderer();
        cameraInc = new Vector3f();

        lightAngle = -90;

    }

    @Override
    public void init(Window window) throws Exception {

        renderer.init(window);

        float reflectance = 1f;

        Mesh cubeMesh = OBJLoader.loadMesh("/models/cube.obj");
        Mesh bb8Mesh = OBJLoader.loadMesh("/models/bb8.obj");
        Mesh bunnyMesh = OBJLoader.loadMesh("/models/bunny.obj");

        Texture cubeTex = new Texture("/textures/grassblock.png");

        Material cubeMaterial = new Material(cubeTex, reflectance);
        Material bunnyMaterial = new Material(new Vector4f(1, 1, 1, 1), reflectance);

        cubeMesh.setMaterial(cubeMaterial);
        bb8Mesh.setMaterial(cubeMaterial);
        bunnyMesh.setMaterial(bunnyMaterial);

        GameItem cube = new GameItem(cubeMesh);
        cube.setScale(0.5f);
        cube.setPosition(0, 0, -2);

        GameItem bb8 = new GameItem(bb8Mesh);
        bb8.setScale(0.01f);
        bb8.setPosition(0, 2, -6);

        GameItem bunny = new GameItem(bunnyMesh);
        bunny.setScale(0.5f);
        bunny.setPosition(0, 1, -4);
        
        GameItem cube2 = new GameItem(cubeMesh);
        cube2.setScale(0.5f);
        cube2.setPosition(1, 0, -2);

        GameItem cube3 = new GameItem(cubeMesh);
        cube3.setScale(0.5f);
        cube3.setPosition(2, 0, -2);

        GameItem cube4 = new GameItem(cubeMesh);
        cube4.setScale(0.5f);
        cube4.setPosition(3, 0, -1);

        GameItem cube5 = new GameItem(cubeMesh);
        cube5.setScale(0.5f);
        cube5.setPosition(3, 0, 0);

        GameItem cube6 = new GameItem(cubeMesh);
        cube6.setScale(0.5f);
        cube6.setPosition(-1, 0, -2);

        GameItem cube7 = new GameItem(cubeMesh);
        cube7.setScale(0.5f);
        cube7.setPosition(-2, 0, -2);

        GameItem cube8 = new GameItem(cubeMesh);
        cube8.setScale(0.5f);
        cube8.setPosition(-3, 0, -1);

        GameItem cube9 = new GameItem(cubeMesh);
        cube9.setScale(0.5f);
        cube9.setPosition(-3, 0, 0);

        gameItems = new GameItem[]{cube, bb8, bunny, cube2, cube3, cube4, cube5, cube6, cube7, cube8, cube9};
        sceneLight = new SceneLight();

        sceneLight.setAmbientLight(new Vector3f(0.3f, 0.3f, 0.3f));

        Vector3f lightColour = new Vector3f(1, 1, 1);
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 1.0f;
        PointLight pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);

        sceneLight.setPointLightList(new PointLight[]{pointLight});

        lightPosition = new Vector3f(0, 0.0f, 10f);
        pointLight = new PointLight(new Vector3f(1, 0, 0), lightPosition, lightIntensity);
        att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
        pointLight.setAttenuation(att);
        Vector3f coneDir = new Vector3f(0, 0, -1);
        float cutOff = (float) Math.cos(Math.toRadians(0));
        SpotLight spotLight = new SpotLight(pointLight, coneDir, cutOff);

        sceneLight.setSpotLightList(new SpotLight[]{spotLight, new SpotLight(spotLight)});

        lightPosition = new Vector3f(-1, 0, 0);
        lightColour = new Vector3f(1, 1, 1);
        sceneLight.setDirectionalLight(new DirectionalLight(lightColour, lightPosition, lightIntensity));

        // Create HUD
        hud = new Hud("DEMO");

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

        if (window.isKeyPressed(GLFW_KEY_Z)) {

            cameraInc.y = -1;

        } else if (window.isKeyPressed(GLFW_KEY_X)) {

            cameraInc.y = 1;

        }

        SpotLight[] spotLightList = sceneLight.getSpotLightList();
        float lightPos = spotLightList[0].getPointLight().getPosition().z;

        if (window.isKeyPressed(GLFW_KEY_N)) {

            spotLightList[0].getPointLight().getPosition().z = lightPos + 0.1f;

        } else if (window.isKeyPressed(GLFW_KEY_M)) {

            spotLightList[0].getPointLight().getPosition().z = lightPos - 0.1f;

        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {

        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse
        if (mouseInput.isRightButtonPressed()) {

            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);

        }

        // Update spot light direction
        spotAngle += spotInc * 0.05f;

        if (spotAngle > 2) {

            spotInc = -1;

        } else if (spotAngle < -2) {

            spotInc = 1;

        }

        double spotAngleRad = Math.toRadians(spotAngle);
        SpotLight[] spotLightList = sceneLight.getSpotLightList();
        Vector3f coneDir = spotLightList[0].getConeDirection();
        coneDir.y = (float) Math.sin(spotAngleRad);

        // Update directional light direction, intensity and colour
        DirectionalLight directionalLight = sceneLight.getDirectionalLight();
        lightAngle += 1.1f;

        if (lightAngle > 90) {

            directionalLight.setIntensity(0);

            if (lightAngle >= 360) {

                lightAngle = -90;

            }

        } else if (lightAngle <= -80 || lightAngle >= 80) {

            float factor = 1 - (float) (Math.abs(lightAngle) - 80) / 10.0f;

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

        hud.updateSize(window);
        renderer.render(window, camera, gameItems, sceneLight, hud);

    }

    @Override
    public void cleanup() {

        renderer.cleanup();

        for (GameItem gameItem : gameItems) gameItem.getMesh().cleanUp();

        hud.cleanup();

    }
}
