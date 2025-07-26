package com.spakborhills.view.gui;

import com.spakborhills.controller.*;
import com.spakborhills.controller.KeyHandler;
import com.spakborhills.model.entity.Entity;
import com.spakborhills.model.entity.Player;
import com.spakborhills.model.entity.PlayerView;
import com.spakborhills.model.entity.npc.NPC;
import com.spakborhills.model.entity.npc.NPCRegistry;
import com.spakborhills.model.entity.npc.NPCView;
import com.spakborhills.model.game.GameMap;
import com.spakborhills.model.game.PlantManager;
import com.spakborhills.model.items.Item;
import com.spakborhills.model.items.behavior.Edible;
import com.spakborhills.model.items.ShippingBin;
import com.spakborhills.controller.CollisionChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GamePanel extends  JPanel{
    MainFrame mainFrame;
    private final int oriTileSize = 16;
    private final int scale = 3;
    private final int tileSize = oriTileSize * scale;
    private final int maxScreenCol = 12;
    private final int maxScreenRow = 12;

    //Screen Setting
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // World Setting
    public final int maxWorldCol = 250;
    public final int maxWorldRow = 250;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    public TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    private Player player;
    private PlayerView playerView;
    private PlayerController playerController;
    public ArrayList<NPCView> npcList;
    private GameLoop gameLoop;
    private KeyHandler keyH = new KeyHandler();
    private PlantManager plantManager = new PlantManager(this);
    private NPC currentNPC;
    private NPCInteractionPanel npcInteractionPanel;
    private boolean npcInteractionActive = false;
    private NPCView interactingNPC = null;



    private String currentMap = "farm";
    private String currentTileType;
    private String frontTileType;

    private String previousMap = "";
    private int entranceWorldX = -1; // Mengubah menjadi WorldX/WorldY
    private int entranceWorldY = -1; // Mengubah menjadi WorldX/WorldY

    private boolean positionSetByReturn = false;

    private boolean inventoryOpened;

    private Map<String, GameMap> loadedGameMaps = new HashMap<>();

    // Map untut menentukan player masuk ke dalam house milik NPC yang mana
    private Map<String, String> houseEntrances = new HashMap<>();

    private ShippingBin shippingBin = new ShippingBin();
    public CollisionChecker collisionChecker;
    public ArrayList<NPCView> npcs = new ArrayList<>();


    public int getTileSize() {
        return tileSize;
    }

    public PlayerView getPlayerView() {
        return playerView;
    }

    public PlayerController getPlayerController() {
        return playerController;
    }

    public void setCurrentMap(String map) {
        // Jika peta saat ini BUKAN house, dan peta tujuan adalah house,
        // maka simpan peta saat ini sebagai previousMap dan posisi WorldX/Y
        if (!this.currentMap.split(" ")[0].equals("house") && map.split(" ")[0].equals("house")) {
            this.previousMap = this.currentMap;
            this.entranceWorldX = playerView.getWorldX(); // Simpan WorldX
            this.entranceWorldY = playerView.getWorldY(); // Simpan WorldY
            System.out.println("Set previousMap to: " + previousMap + " from entrance WorldX: " + entranceWorldX + ", WorldY: " + entranceWorldY);
        }
        this.currentMap = map; // ubah currentMap
    }

    public String getCurrentMap() {
        return this.currentMap;
    }

    public void setPreviousMap(String map) {
        this.previousMap = map;
    }

    public void setEntrancePosition(int x, int y) {
    }

    public void returnToPreviousMap() {
        // Debugging: Pastikan nilai-nilai ini benar sebelum digunakan
        System.out.println("Return: previousMap=" + previousMap + ", entranceWorldX=" + entranceWorldX + ", entranceWorldY=" + entranceWorldY);

        if (!previousMap.isEmpty() && entranceWorldX != -1 && entranceWorldY != -1) {
            this.currentMap = previousMap; // Mengatur currentMap ke peta sebelumnya

            // Set posisi pemain ke posisi masuk sebelumnya menggunakan WorldX/Y yang disimpan
            playerView.setWorldX(entranceWorldX);
            playerView.setWorldY(entranceWorldY);
            playerView.setWorldY(playerView.getWorldY() + tileSize);

            positionSetByReturn = true;

            System.out.println("Returned to map: " + currentMap + " at position: " + playerView.getWorldX()/tileSize + ", " + playerView.getWorldY()/tileSize);
        } else {
            System.out.println("Could not return to previous map. Data incomplete or already reset. Falling back to default farm position.");
            this.currentMap = "farm";
            playerView.setWorldX(tileSize * 95);
            playerView.setWorldY(tileSize * 122);
            // Muat peta farm dari loadedGameMaps
            // Ini akan ditangani di switch case di update()
            positionSetByReturn = false;
        }
    }

    public NPC getCurrentNPC() {
        return currentNPC;
    }

    public Player getPlayer() { return player;}

    public NPCInteractionPanel getNpcInteractionPanel() {
        return npcInteractionPanel;
    }

    public GamePanel(MainFrame mainFrame, LoginPanel loginPanel) {

        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(null);
        this.setDoubleBuffered(true); //improve rendering performance
        this.setFocusable(true);

        this.mainFrame = mainFrame;

        player = new Player(loginPanel.getPlayerName(), "male", loginPanel.getFarmName());
        playerView = new PlayerView(this, keyH, player);
        playerController = new PlayerController(player, playerView, this);

        // Load semua map di awal
        loadedGameMaps.put("farm", new GameMap(this, "farm", "/assets/FarmMaps/farm_map.txt"));
        loadedGameMaps.put("world", new GameMap(this, "world", "/assets/WorldMaps/WorldMaps"));
        loadedGameMaps.put("house", new GameMap(this, "house", "/assets/HouseMap/HouseInteriorMap"));

        // Menambahkan para NPC ke dalam game
        npcList = new ArrayList<>();
        NPCView abigailNPC = new NPCView(this, "Abigail", 117, 120);
        NPCView carolineNPC = new NPCView(this, "Caroline", 117, 120);
        NPCView dascoNPC = new NPCView(this, "Dasco", 117, 120);
        NPCView emilyNPC = new NPCView(this, "Emily", 117, 120);
        NPCView mayorTadiNPC = new NPCView(this, "Mayor Tadi", 117, 120);
        NPCView perryNPC = new NPCView(this, "Perry", 117, 120);

        currentNPC = NPCRegistry.getNPCPrototype("Abigail");

        npcList.add(abigailNPC);
        npcList.add(carolineNPC);
        npcList.add(dascoNPC);
        npcList.add(emilyNPC);
        npcList.add(mayorTadiNPC);
        npcList.add(perryNPC);

        // Untuk identifikasi house siapa yang dimasukin berdasarkan posisi terakhir player di map
        houseEntrances.put("162,163", "Abigail");
        houseEntrances.put("162,164", "Abigail");
        houseEntrances.put("163,163", "Abigail");
        houseEntrances.put("163,164", "Abigail");
        houseEntrances.put("164,163", "Abigail");
        houseEntrances.put("164,164", "Abigail");
        houseEntrances.put("140,214", "Dasco");
        houseEntrances.put("140,215", "Dasco");
        houseEntrances.put("141,214", "Dasco");
        houseEntrances.put("141,215", "Dasco");
        houseEntrances.put("142,214", "Dasco");
        houseEntrances.put("142,215", "Dasco");
        houseEntrances.put("128,117", "Caroline");
        houseEntrances.put("128,118", "Caroline");
        houseEntrances.put("129,117", "Caroline");
        houseEntrances.put("129,118", "Caroline");
        houseEntrances.put("130,117", "Caroline");
        houseEntrances.put("130,118", "Caroline");
        houseEntrances.put("100,45", "Perry");
        houseEntrances.put("100,46", "Perry");
        houseEntrances.put("101,45", "Perry");
        houseEntrances.put("101,46", "Perry");
        houseEntrances.put("102,45", "Perry");
        houseEntrances.put("102,46", "Perry");
        houseEntrances.put("203,119", "Emily");
        houseEntrances.put("203,120", "Emily");
        houseEntrances.put("204,119", "Emily");
        houseEntrances.put("204,120", "Emily");
        houseEntrances.put("205,119", "Emily");
        houseEntrances.put("205,120", "Emily");
        houseEntrances.put("232,40", "Mayor Tadi");
        houseEntrances.put("232,41", "Mayor Tadi");
        houseEntrances.put("233,40", "Mayor Tadi");
        houseEntrances.put("233,41", "Mayor Tadi");
        houseEntrances.put("234,40", "Mayor Tadi");
        houseEntrances.put("234,41", "Mayor Tadi");

        this.addKeyListener(keyH);
        gameLoop = new GameLoop(60, this::update, this::repaint);
        gameLoop.getGameTime().addObserver(plantManager);

        JButton backButton = new GameButton("Back to homescreen");
        backButton.setBounds(15, 25, 157, 25);
        this.add(backButton);

        backButton.addActionListener(e -> {
            mainFrame.switchPanel("home");
            pauseGame();
            keyH.resetKeys();
        });
    }

    public void startGame() {
        gameLoop.startGame();
    }

    public void pauseGame(){
        gameLoop.pause();
    }

    public void update(){
        if(!gameLoop.isRunning()){
            return;
        }

        if (player.getEnergy() <= -20){
            playerController.sleeping(player.getEnergy(), gameLoop.getGameTime().getInGameHours(), gameLoop.getGameTime().getInGameMinutes());
        }

        boolean wasInventoryOpened = inventoryOpened;
        if (keyH.isInventoryPressed()){
            inventoryOpened = !inventoryOpened;
            keyH.setInventoryPressed(false);
        }

        if (inventoryOpened) {
            // Print hanya saat inventory baru dibuka
            if (!wasInventoryOpened) {
                try {
                    System.out.println("Holding Item : " + player.getItemHeld().getName());
                } catch (NullPointerException _) {
                    System.out.println("Not holding any item");
                }

                if (player.getInventory().getPlayerInventory().isEmpty()) {
                    System.out.println("Inventory empty:(");
                    System.out.println("Press I to continue game");
                } else {
                    System.out.println("Inventory :");
                    for (Map.Entry<Item, Integer> entry : player.getInventory().getPlayerInventory().entrySet()) {
                        System.out.println(entry.getKey().getName() + ": " + entry.getValue());
                    }
                    playerController.chooseItem();
                    // Setelah chooseItem selesai, langsung lanjutkan game
                    inventoryOpened = false;
                    gameLoop.getGameTime().setStartTime(System.nanoTime());
                    System.out.println("Game continued");
                }
            }
            return;
        }

        gameLoop.getGameTime().updateGameTime();
        playerView.update();

        String[] currentMapParts = currentMap.split(" ");

        for (NPCView npc : npcList) {
            if (currentMapParts.length > 1) {
                if (currentMap.split(" ")[0].equals(npc.getCurrentMapName()) &&
                        currentMap.split(" ").length > 1 && currentMap.split(" ")[1].equals(npc.getNPCModel().getName().split(" ")[0])) {
                    npc.update();
                }
            }
        }

        if (gameLoop.getGameTime().getInGameHours() == 2){
            playerController.sleeping(player.getEnergy(), gameLoop.getGameTime().getInGameHours(), 0);
        }

        String oldMap = currentMap;
        // pindah map kalau melebihi boundary
        if (playerView.getWorldX() < 95 * tileSize &&
                (playerView.getWorldY() > 120 * tileSize && playerView.getWorldY() < 124 * tileSize) &&
                currentMap.equals("farm")){ //31 hardcode maxFarmCol
            currentMap = "world";
            System.out.println("Playerriu ke " + currentMap);
            positionSetByReturn = false;

            if (!oldMap.split(" ")[0].equals("house")) { // Jika peta sebelumnya bukan house
                this.previousMap = "";
                this.entranceWorldX = -1;
                this.entranceWorldY = -1;
            }
        }
        else if (playerView.getWorldX() > 217 * tileSize &&
                (playerView.getWorldY() > 149 * tileSize && playerView.getWorldY() < 153 * tileSize) &&
                currentMap.equals("world")){
            currentMap = "farm";
            System.out.println("Player pindah ke " + currentMap);
            positionSetByReturn = false;

            if ((!(oldMap.split(" ")[0]).equals("house"))) { // Jika peta sebelumnya bukan house
                this.previousMap = "";
                this.entranceWorldX = -1;
                this.entranceWorldY = -1;
            }
        }


        currentTileType = playerView.getCurrentTileType();
        frontTileType = playerView.getFrontTileType();

        String[] currentMapPartsForInteraction = currentMap.split(" ");
        String currentMapBaseNameForInteraction = currentMapPartsForInteraction[0];
        String currentHouseNPCNameForInteraction = (currentMapPartsForInteraction.length > 1) ? currentMapPartsForInteraction[1] : null; // Default ke null

        if (keyH.isEnterPressed()) {
            if (currentTileType != null) {
                if (playerController.holdingEdible()) {
                    playerController.eating((Edible) player.getItemHeld());
                } else if ((currentTileType.equals("046.png") || currentTileType.equals("047.png") ||
                        currentTileType.equals("040.png") || currentTileType.equals("041.png"))) { // Pintu masuk rumah

                    if (!currentMap.split(" ")[0].equals("house")) { // Pastikan tidak sudah di dalam house
                        // Mendapatkan koordinat tile player saat ini
                        int playerTileCol = playerView.getWorldX() / tileSize;
                        int playerTileRow = playerView.getWorldY() / tileSize;

                        // Membuat key untuk mencari di houseEntrances map
                        String entranceKey = playerTileCol + "," + playerTileRow;
                        String npcForThisHouse = houseEntrances.get(entranceKey); // mengambil nama npc untuk house


                        if (npcForThisHouse != null) {
                            // Ubah currentMap menjadi "house [NPCName]"
                            setCurrentMap("house " + npcForThisHouse);
                            System.out.println("Entering " + npcForThisHouse + "'s house.");
                            currentNPC = NPCRegistry.getNPCPrototype(npcForThisHouse);

                        } else {
                            // Jika tidak ada NPC yang terdaftar untuk pintu ini, masuk ke house default
                            // Jika semua rumah ada NPC-nya, bagian ini bisa dihapus
                            setCurrentMap("house default"); // Atau "house" saja
                            System.out.println("Entering a generic house.");
                        }

                    }
                    else{
                        for (NPCView npcView : this.npcs) {
                            if (npcView.getCurrentMapName().equals(currentMapBaseNameForInteraction)) {
                                if (npcView.isPlayerInInteractionRange(
                                        playerView.worldX, playerView.worldY, tileSize, playerView.getDirection())) {

                                    NPC realNPC = npcView.getNPCModel(); // 👈 PASTIKAN method getNPCModel() ada
                                    playerController.interactWithNPC(realNPC);
                                    break;
                                }
                            }
                        }
                    }
                } else if (currentTileType.equals("138.png") || currentTileType.equals("139.png")) { // Pintu keluar rumah
                    if (currentMap.split(" ")[0].equals("house")) { // Pastikan sedang di dalam house
                        System.out.println("Calling getOutTheHouse() from GamePanel");
                        playerController.getOutTheHouse();
                    }
                } else if (currentTileType.equals("000.png") && currentMap.equals("farm") && playerController.rightTool("Hoe")) {
                    playerController.tilling();
                } else if (frontTileType.equals("006.png")) {
                    if (playerController.rightTool("Fishing Rod")) {
                        System.out.println("try to fish");
                        playerController.fishing();
                        gameLoop.getGameTime().setStartTime(System.nanoTime());
                    }
                } else if (currentTileType.equals("004.png") || currentTileType.equals("147.png")) {
                    if (playerController.holdingSeed()) {
                        playerController.planting();
                    } else if (playerController.rightTool("Hoe")) {
                        playerController.recoverLand();
                    }
                } else if (currentTileType.equals("146.png") || currentTileType.equals("148.png")) {
                    if (player.getItemHeld() == null) {
                        playerController.harvesting();
                    }
                } else if (frontTileType.equals("079.png") || frontTileType.equals("080.png") ||
                        frontTileType.equals("090.png") ||frontTileType.equals("091.png") ||
                        frontTileType.equals("102.png") ||frontTileType.equals("103.png") && currentMap.equals("house default")) {
                    playerController.sleeping(player.getEnergy(), gameLoop.getGameTime().getInGameHours(), gameLoop.getGameTime().getInGameMinutes());
                } else if (frontTileType.equals("082.png") || frontTileType.equals("083.png") ||
                        frontTileType.equals("093.png") || frontTileType.equals("094.png") && currentMap.equals("house default")) {
                    playerController.watching();
                } else if ((frontTileType.equals("088.png") || frontTileType.equals("089.png") ||
                        frontTileType.equals("099.png") || frontTileType.equals("100.png") ||
                        frontTileType.equals("109.png") || frontTileType.equals("110.png") ||
                        frontTileType.equals("120.png") || frontTileType.equals("121.png")) && currentMap.equals("house default")) {
                    playerController.cooking();


                } else if (currentTileType.equals("009.png") || currentTileType.equals("010.png") || currentTileType.equals("011.png") || currentTileType.equals("012.png")) {
                    playerController.chooseItem();
                    if (player.getItemHeld() != null){
                        if (shippingBin.addItem(player.getItemHeld(), 1)){
                            player.getInventory().use(player.getItemHeld(), 1);
                            System.out.println("Item " + player.getItemHeld().getName() + " added to bin");
                        }
                        else{
                            System.out.println("Shipping bin is full");
                        }
                    }
                }
            }
            keyH.setEnterPressed(false);
        }

        if (keyH.isSpacePressed()) {
            if (currentMap.equals("farm")) {
                if (currentTileType.equals("148.png") || currentTileType.equals("004.png") &&
                        playerController.rightTool("Watering Can")) {
                    playerController.watering();
                }
            }  else if (currentMapBaseNameForInteraction.equals("house")) {
                boolean interactedWithNPC = false;
                for (NPCView npc : npcList) {
                    if (currentHouseNPCNameForInteraction != null && currentHouseNPCNameForInteraction.equals(npc.getNPCModel().getName()) &&
                            npc.isPlayerInInteractionRange(playerView.getWorldX(), playerView.getWorldY(), tileSize, playerView.direction)) {
                        // Pindah ke Panel untuk interaksi dengan NPC

                        mainFrame.mainPanel.add(npcInteractionPanel, "npc");
                        mainFrame.switchPanel("npc");
                        pauseGame();
                        interactedWithNPC = true;
                        keyH.setSpacePressed(false);
                        break;
                    }
                }
                if (!interactedWithNPC) {
                    System.out.println("Tidak ada interaksi");
                }
            }
        }

        if (keyH.isInventoryPressed()){
            if(player.getInventory().getPlayerInventory().isEmpty()) {
                System.out.println("Inventory kosong!");
            }
            else{
                System.out.println("Inventory :");
                for (Item item : player.getInventory().getPlayerInventory().keySet()) {
                    System.out.println("\n" + item.getName());
                }
            }
            keyH.setSpacePressed(false);
        }

        // Logic untuk switch map
        String targetMap = currentMap.split(" ")[0]; // Ambil nama base map dari currentMap

        if (tileM.getActiveMap() == null || !tileM.getActiveMap().getMapName().equals(targetMap)) {
            GameMap mapToLoad = loadedGameMaps.get(targetMap);
            if (mapToLoad != null) {
                tileM.setActiveMap(mapToLoad);
                System.out.println("Switched active map to: " + mapToLoad.getMapName());
            } else {
                System.err.println("Error: GameMap '" + targetMap + "' not found in loadedGameMaps!");
                // Fallback jika map tidak ditemukan
                tileM.setActiveMap(loadedGameMaps.get("farm")); // Kembali ke farm sebagai default
                this.currentMap = "farm";
                playerView.setWorldX(tileSize * 95);
                playerView.setWorldY(tileSize * 122);
            }

            // Atur posisi pemain jika ini adalah transisi peta dan bukan karena returnToPreviousMap()
            if (!positionSetByReturn) {
                if (targetMap.equals("farm")) {
                    if (previousMap.equals("house default")) {
                        playerView.setWorldX(tileSize * 118);
                        playerView.setWorldY(tileSize * 120);
                    } else {
                        playerView.setWorldX(tileSize * 95);
                        playerView.setWorldY(tileSize * 122);
                    }
                } else if (targetMap.equals("world")) {
                    playerView.setWorldX(tileSize * 216);
                    playerView.setWorldY(tileSize * 151);
                } else if (targetMap.equals("house")) {
                    playerView.setWorldX(tileSize * 120);
                    playerView.setWorldY(tileSize * 122);
                }
            }
            positionSetByReturn = false; // Reset flag setelah digunakan
        }
    }

    // Metode baru untuk menampilkan gambar
    public void showTemporaryPopUp(String imagePath, int durationMillis, int offset) {
        JWindow popup = new JWindow(SwingUtilities.getWindowAncestor(this));

        try {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl == null) {
                System.err.println("Error: Image not found at " + imagePath);
                return;
            }
            BufferedImage image = ImageIO.read(imageUrl);
            JLabel label = new JLabel(new ImageIcon(image));
            label.setBorder(new EmptyBorder(5, 5, 5, 5)); // Padding untuk gambar

            popup.add(label, BorderLayout.CENTER);
            popup.pack();

            int x = this.getLocationOnScreen().x + (this.getWidth() - popup.getWidth()) / 2;
            int y = (this.getLocationOnScreen().y + (this.getHeight() - popup.getHeight()) / 2) - offset;
            popup.setLocation(x, y);

            popup.setVisible(true);

            Timer timer = new Timer(durationMillis, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    popup.dispose();
                    ((Timer)e.getSource()).stop();
                }
            });
            timer.setRepeats(false);
            timer.start();

        } catch (Exception e) {
            System.err.println("Failed to load or display image: " + imagePath);
            e.printStackTrace();
        }
    }

    public String getPlayerFishingArea(int row, int col){
        if (currentMap.equals("farm")) {
            return "Pond";
        }
        else{
            if (col > 5 && col < 42 && row > 111 && row < 136){
                return "Mountain Lake";
            } else if (row < 50) {
                return "Ocean";
            } else if (col > 182 && row > 50) {
                return "Forest River";
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        //draw background tiles
        tileM.draw(g2);

        //draw player
        playerView.draw(g2);

        //draw time
        gameLoop.getGameTime().displayGameTime(g2);

        String[] currentMapParts = currentMap.split(" ");

        for (NPCView npc : npcList) {
            if (currentMapParts.length > 1) {
                if (currentMap.split(" ")[1].equals(npc.getNPCModel().getName().split(" ")[0])) {
                    npc.draw(g2);
                }
                else if (npcInteractionActive && interactingNPC != null) {
                    int boxWidth = tileSize * 6;
                    int boxHeight = tileSize * 4;
                    int boxX = (screenWidth - boxWidth) / 2;
                    int boxY = (screenHeight - boxHeight) / 2;

                    if (npcInteractionPanel != null) {
                        npcInteractionPanel.paintComponent(g2);
                    }

                    // Teks nama NPC
                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 20));
                    g2.drawString(interactingNPC.getNPCModel().getName(), boxX + 30, boxY + 40);

                    // Opsi interaksi
                    g2.setFont(new Font("Arial", Font.PLAIN, 16));
                    g2.drawString("1. Chat", boxX + 30, boxY + 80);
                    g2.drawString("2. Gift", boxX + 30, boxY + 110);
                    g2.drawString("3. Propose", boxX + 30, boxY + 140);
                    g2.drawString("4. Marry", boxX + 30, boxY + 170);
                }
            }
        }

        //draw energy
        g2.setColor(Color.white);
        g2.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        g2.drawString("Energy : " + Integer.toString(player.getEnergy()), 10, 25);
    }

    public PlantManager getPlantManager() {
        return plantManager;
    }
}
