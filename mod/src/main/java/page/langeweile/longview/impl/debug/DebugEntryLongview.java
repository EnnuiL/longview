package page.langeweile.longview.impl.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.components.debug.DebugScreenDisplayer;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jspecify.annotations.Nullable;
import page.langeweile.longview.impl.LongviewImpl;

public class DebugEntryLongview implements DebugScreenEntry {
	private static final Identifier GROUP = Identifier.fromNamespaceAndPath("longview", "longview");
	public static final Identifier ENTRY = Identifier.fromNamespaceAndPath("longview", "status");

	@Override
	public void display(DebugScreenDisplayer displayer, @Nullable Level serverOrClientLevel, @Nullable LevelChunk clientChunk, @Nullable LevelChunk serverChunk) {
		// TODO - Automate this
		displayer.addToGroup(GROUP, "Longview 1.3.0");
		displayer.addToGroup(GROUP, String.format("Reverse Z: %s", LongviewImpl.isZReversed() ? "enabled" : "disabled"));
		displayer.addToGroup(GROUP, renderZeroToOneZ());
	}

	private static String renderZeroToOneZ() {
		if (RenderSystem.getDevice().isZZeroToOne() || LongviewImpl.supportsGlClipControl() && RenderSystem.getDevice().getBackendName().equals("OpenGL")) {
			return String.format("[0,1] Z: %s", LongviewImpl.isGlZZeroToOne() ? "enabled" : "disabled");
		} else {
			return String.format("[0,1] Z: %s (unsupported by backend)", LongviewImpl.isGlZZeroToOne() ? "allowed" : "disabled");
		}
	}

	@Override
	public boolean isAllowed(boolean reducedDebugInfo) {
		return true;
	}
}
