package io.github.lix3nn53.guardiansofadelia.events;

import io.github.lix3nn53.guardiansofadelia.chat.PremiumRank;
import io.github.lix3nn53.guardiansofadelia.database.DatabaseQueries;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianData;
import io.github.lix3nn53.guardiansofadelia.guardian.GuardianDataManager;
import io.github.lix3nn53.guardiansofadelia.guardian.character.RPGCharacter;
import io.github.lix3nn53.guardiansofadelia.menu.CharacterSelectionMenuList;
import io.github.lix3nn53.guardiansofadelia.npc.QuestNPCManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantManager;
import io.github.lix3nn53.guardiansofadelia.npc.merchant.MerchantMenu;
import io.github.lix3nn53.guardiansofadelia.quests.Quest;
import io.github.lix3nn53.guardiansofadelia.utilities.ChatPalette;
import io.github.lix3nn53.guardiansofadelia.utilities.gui.GuiGeneric;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;

public class MyNPCRightClickEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEvent(NPCRightClickEvent event) {
        Player player = event.getClicker();

        NPC npc = event.getNPC();
        int id = npc.getId();

        if (id > 0 && id < 9) {
            //1-2-3-4-5-6-7-8 character selection npc
            PremiumRank requiredRank = PremiumRank.NONE;
            if (id > 6) requiredRank = PremiumRank.TITAN;
            else if (id > 4) requiredRank = PremiumRank.LEGEND;
            else if (id > 2) requiredRank = PremiumRank.HERO;

            if (!requiredRank.equals(PremiumRank.NONE)) {
                int requiredOrdinal = requiredRank.ordinal();

                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                PremiumRank premiumRank = guardianData.getPremiumRank();

                int playerOrdinal = premiumRank.ordinal();

                if (requiredOrdinal > playerOrdinal) {
                    player.sendMessage(ChatPalette.RED + "Required rank for this character slot is " + requiredRank);
                    return;
                }
            }

            if (DatabaseQueries.characterExists(player.getUniqueId(), id)) {
                GuiGeneric characterTeleportationMenu = CharacterSelectionMenuList.characterSelectionMenu(id);
                characterTeleportationMenu.openInventory(player);
            } else {
                GuiGeneric characterCreationMenu = CharacterSelectionMenuList.getCharacterCreationMenu(id);
                characterCreationMenu.openInventory(player);
            }
        } else {
            if (GuardianDataManager.hasGuardianData(player)) {
                GuardianData guardianData = GuardianDataManager.getGuardianData(player);
                if (guardianData.hasActiveCharacter()) {
                    RPGCharacter activeCharacter = guardianData.getActiveCharacter();

                    //progress interact tasks
                    List<Quest> questList = activeCharacter.getQuestList();
                    for (Quest quest : questList) {
                        boolean didProgress = quest.progressInteractTasks(player, id);
                        //don't want to open Menu on interact for quest so we return
                        if (didProgress) return;
                    }
                }
            }

            if (MerchantManager.isMerchant(id)) {
                MerchantMenu merchantMenu = MerchantManager.getMerchantMenu(id);
                merchantMenu.openInventory(player);
            } else {
                GuiGeneric questGui = QuestNPCManager.getQuestGui(player, id);
                questGui.openInventory(player);
            }
        }
    }

}
