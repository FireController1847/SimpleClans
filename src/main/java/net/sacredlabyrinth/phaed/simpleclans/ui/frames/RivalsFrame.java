package net.sacredlabyrinth.phaed.simpleclans.ui.frames;

import com.cryptomorin.xseries.XMaterial;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.RankPermission;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;
import net.sacredlabyrinth.phaed.simpleclans.ui.*;
import net.sacredlabyrinth.phaed.simpleclans.utils.Paginator;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static net.sacredlabyrinth.phaed.simpleclans.SimpleClans.lang;

public class RivalsFrame extends SCFrame {
	private final SimpleClans plugin = SimpleClans.getInstance();
	private final Paginator paginator;
	private final Clan subject;
	private final List<String> rivals;

	public RivalsFrame(Player viewer, SCFrame parent, Clan subject) {
		super(parent, viewer);
		this.subject = subject;
		rivals = subject.getRivals();
		paginator = new Paginator(getSize() - 9, rivals);
	}

	@Override
	public void createComponents() {

		for (int slot = 0; slot < 9; slot++) {
			if (slot == 2 || slot == 4 || slot == 6 || slot == 7)
				continue;
			add(Components.getPanelComponent(slot));
		}

		add(Components.getBackComponent(getParent(), 2, getViewer()));

		SCComponent add = new SCComponentImpl(lang("gui.rivals.add.title",getViewer()), null, XMaterial.RED_WOOL, 4);
		add.setVerifiedOnly(ClickType.LEFT);
		add.setListener(ClickType.LEFT, () -> InventoryDrawer.open(new AddRivalFrame(this, getViewer(), subject)));
		add.setPermission(ClickType.LEFT, RankPermission.RIVAL_ADD);
		add(add);

		add(Components.getPreviousPageComponent(6, this::previousPage, paginator, getViewer()));
		add(Components.getNextPageComponent(7, this::nextPage, paginator, getViewer()));

		int slot = 9;
		for (int i = paginator.getMinIndex(); paginator.isValidIndex(i); i++) {

			Clan clan = plugin.getClanManager().getClan(rivals.get(i));
			if (clan == null)
				continue;
			SCComponent c = new SCComponentImpl(
					lang("gui.clanlist.clan.title",getViewer(), clan.getColorTag(), clan.getName()),
					Collections.singletonList(lang("gui.rivals.clan.lore",getViewer())), XMaterial.RED_BANNER, slot	);
			c.setListener(ClickType.RIGHT, () -> InventoryController.runSubcommand(getViewer(),
					"rival remove", false, clan.getTag()));
			c.setPermission(ClickType.RIGHT, RankPermission.RIVAL_REMOVE);
			add(c);
			slot++;
		}
	}

	private void previousPage() {
		if (paginator.previousPage()) {
			updateFrame();
		}
	}

	private void nextPage() {
		if (paginator.nextPage()) {
			updateFrame();
		}
	}

	private void updateFrame() {
		InventoryDrawer.open(this);
	}

	@Override
	public @NotNull String getTitle() {
		return lang("gui.rivals.title",getViewer());
	}

	@Override
	public int getSize() {
		return 6 * 9;
	}
}
