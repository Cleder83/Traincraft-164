package traincraft.client.render;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import traincraft.client.render.models.blocks.ModelWaterWheel;
import traincraft.common.tile.TileWaterWheel;

public class RenderWaterWheel extends TileEntitySpecialRenderer {
	private ModelWaterWheel modelWaterWheel = new ModelWaterWheel();

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
		modelWaterWheel.render((TileWaterWheel) tileEntity, x, y, z);
	}
}
