package me.unts.Lentix.module.player;

import me.unts.Lentix.Lentix;
import me.unts.Lentix.event.EventTarget;
import me.unts.Lentix.event.events.EventUpdate;
import me.unts.Lentix.module.Category;
import me.unts.Lentix.module.Module;
import me.unts.Lentix.module.combat.aura.Killaura;
import me.unts.Lentix.utils.MathUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Kill_Insult extends Module {

    private File file;
    private File dir;


    public Kill_Insult(){
        super("Kill Insult", Keyboard.KEY_NONE, Category.PLAYER);
    }

    public void onEnable(){
        super.onEnable();
    }

    @EventTarget
    public void onUpdate(EventUpdate event){
        if(Killaura.target != null && Killaura.target.isDead && Lentix.instance.moduleManager.getModuleByName("Killaura").isToggled()){
            mc.thePlayer.sendChatMessage(getRandomInsult());
        }
    }

    public String getRandomInsult(){
        file = new File(mc.mcDataDir, "Lentix/KillInsults.txt");
        ArrayList<String> lines = new ArrayList<String>();

        String text;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                if(line.contains("%player%")){
                    line = line.replace("%player%", mc.session.getUsername());
                }
                if(line.contains("%target%") && Killaura.target != null){
                    line = line.replace("%target%", Killaura.target.getName());
                }
                lines.add(line);
                line = reader.readLine();
            }

            int index = MathUtils.getRandom(0, lines.size());
            return lines.get(index);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
